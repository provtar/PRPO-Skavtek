package si.skavtko.clani.zrna;

import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotFoundException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.google.gson.Gson;
import com.kumuluz.ee.streaming.common.annotations.StreamProducer;

import si.skavtko.clani.dto.ClanDTO;
import si.skavtko.clani.dto.ClanMinDTO;
import si.skavtko.clani.entitete.Clan;
import si.skavtko.clani.entitete.enums.UserRole;

@ApplicationScoped
public class ClanZrno {

    @Inject
    @StreamProducer
    private Producer<String, String> producer;

    Gson gson = null;

    @PersistenceContext
    EntityManager entityManager;
    
    @PostConstruct
    private void init(){
        gson= new Gson();
    }

    @PreDestroy
    private void destroy(){
    }

    public ClanDTO getClan(Long id){
        Clan clan = entityManager.find(Clan.class, id);
        ClanDTO res = null;
        if(clan != null) res = new ClanDTO(clan);

        return res;
    }

    public List<ClanDTO> getClan(String ime, String priimek){
        List<ClanDTO> res = entityManager
        .createQuery("select new si.skavtko.clani.dto.ClanDTO(c.id, c.ime, c.priimek, c.steg, c.skavtskoIme) from Clan c where (:arg1 is null or c.ime = :arg1) and (:arg2 is null or c.priimek = :arg2) and c.role = :arg3", ClanDTO.class).
        setParameter("arg1", ime)
        .setParameter("arg2", priimek)
        .setParameter("arg3", UserRole.ACTIVE)
        .getResultList();

        return res;
    }

    public List<ClanDTO> getVarovanci(Long masterId){
        List<ClanDTO> res = entityManager.createNamedQuery("Varovanci.fromMaster", ClanDTO.class)
            .setParameter("masterId", masterId).getResultList();
        return res;
    }

    @Transactional
    public ClanDTO dodajClana(ClanDTO data, Long id){
        entityManager.getTransaction().begin();
        try{
            Clan newClan = new Clan();
            newClan.setIme(data.getIme());
            newClan.setPriimek(data.getPriimek());
            newClan.setSteg(data.getSteg());
            newClan.setSkavtskoIme(data.getSkavtskoIme());

            newClan.setRole(UserRole.PASSIVE);
            Clan master = entityManager.find(Clan.class, id);
            if (master == null) {
                throw new NotFoundException("Master z id-jem "+ id + " ne obstaja");
            }else{
                newClan.setMaster(master);
            }

            entityManager.persist(newClan);
            entityManager.flush();
            data = new ClanDTO(newClan);
            entityManager.getTransaction().commit();
            String novClanJson = gson.toJson(new ClanMinDTO(newClan));
            System.out.println("Sending topic post:");
            Future<RecordMetadata> rec = producer.send(new ProducerRecord<String,String>("clan-post", novClanJson),(metadata, res) ->{
                System.out.println("Sent data to topic" + metadata.topic());
                System.err.println("Sent data to topic" + metadata.topic());
            });
            RecordMetadata recdata = rec.get();
            System.out.println("Data record: " +  recdata.topic());
            // System.out.println("Data partition: " +  recdata.partition());
            System.out.println("Data value: " +  recdata.serializedValueSize());

        }catch(NotFoundException nfe){
            entityManager.getTransaction().rollback();
            throw nfe;
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        return data;
    }

    @Transactional
    public ClanDTO registrirajClana(String ime, String priimek, String password, String email)
    throws NonUniqueResultException, ConstraintViolationException{
        entityManager.getTransaction().begin();
        ClanDTO data = null;
        try{
            if(password == null || email == null) throw new ConstraintViolationException("Email ali priimek je null", null);
            List<Clan> enaki = entityManager.createNamedQuery("Clan.fromEmail", Clan.class).setParameter("email", email).getResultList();
            if(enaki.size() > 0) throw new NonUniqueResultException("Aktiven clan s tem emailom ze obstaja");
            Clan newClan = new Clan();
            newClan.setIme(ime);
            newClan.setPriimek(priimek);
            newClan.setPassword(password);
            newClan.setEmail(email);
            newClan.setRole(UserRole.ACTIVE);

            entityManager.persist(newClan);
            data = new ClanDTO(newClan);
            entityManager.getTransaction().commit();
            String novClanJson = gson.toJson(new ClanMinDTO(newClan));
            producer.send(new ProducerRecord<String,String>("clan-post", novClanJson));
        }catch(NonUniqueResultException nure){
            entityManager.getTransaction().rollback();
            throw nure;
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        return data;
    }

    @Transactional
    public ClanDTO login(String password, String email)
    throws NoResultException{
        entityManager.getTransaction().begin();
        ClanDTO data = null;
        try{
            if(password == null || email == null) throw new ConstraintViolationException("Email ali priimek je null", null);
            Clan user = entityManager.createNamedQuery("Clan.fromEmailInPassword", Clan.class).setParameter("email", email).setParameter("password", password).getSingleResult();
            data = new ClanDTO(user);
        }catch(NoResultException nre){
            entityManager.getTransaction().rollback();
            throw nre;
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return data;
    }

    //TODO metode za spreminjati status clana v obe smeri
    //TODO metode za spreminjat lastnistvo pasivnih clanov
    //TODO sprememba password in emaila, kvecjemu dat email se aktivnemu clanu
    //TODO delete za aktivnega clana
    // TODO za osebno spreljanje dajat kommente clanom

    @Transactional
    public ClanDTO posodobiClan(ClanDTO data){
        try{
            Clan clan = entityManager.getReference(Clan.class, data.getId());
            if(clan == null) return null;

            entityManager.getTransaction().begin();

            if(data.getIme() != null) clan.setIme(data.getIme());
            if(data.getPriimek() != null) clan.setPriimek(data.getPriimek());
            if(data.getSkavtskoIme() != null) clan.setSkavtskoIme(data.getSkavtskoIme());
            if(data.getSteg() != null) clan.setSteg(data.getSteg());

            clan = entityManager.merge(clan);
            data = new ClanDTO(clan);

            entityManager.getTransaction().commit();
            String novClanJson = gson.toJson(new ClanMinDTO(clan));
            producer.send(new ProducerRecord<String,String>("clan-put", novClanJson), (metadata, res) ->{
                System.out.println("Sent data to topic" + metadata.topic());
            });
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }

        return data;
    }

    @Transactional
    public void deleteClan(Long id){
        entityManager.getTransaction().begin();

        Clan clan = entityManager.find(Clan.class, id);
        if(clan != null)entityManager.remove(clan);

        entityManager.getTransaction().commit();
        producer.send(new ProducerRecord<String,String>("clan-delete", id.toString()));
    }
    
    public Boolean checkDBconnection(){
        try {
            // Test connection by interacting with the database
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("SELECT 1").getSingleResult(); // Simple query to test the connection
            entityManager.getTransaction().commit();
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while connecting to the database.");
            return false;
        }
    }
}
