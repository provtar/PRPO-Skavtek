package si.skavtko.srecanja.zrna;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.gson.Gson;
import com.kumuluz.ee.streaming.common.annotations.StreamProducer;

import si.skavtko.srecanja.dto.SrecanjeMinDTO;
import si.skavtko.srecanja.dto.SrecanjeDTO;
import si.skavtko.srecanja.entitete.SkupinaMin;
import si.skavtko.srecanja.entitete.Srecanje;

@ApplicationScoped
public class SrecanjeZrno {
    @PersistenceContext
    EntityManager entityManager;

    
    @Inject
    @StreamProducer
    private Producer<String, String> producer;

    Gson gson = null;

    @PostConstruct
    private void init(){
        gson = new Gson();
            }

    @PreDestroy
    private void destroy(){
    }

    public SrecanjeDTO getSrecanje(Long id) throws NoResultException{
        
        Srecanje srecanje = entityManager.find(Srecanje.class, id);
        entityManager.refresh(srecanje);
        if(srecanje == null) throw new NoResultException("Srecanja z id: " + id + "ni.\n");
        SrecanjeDTO res = new SrecanjeDTO(srecanje);
        // System.out.println("Srecanje:" +srecanje.getBelezenje());
        return res;
    }

    //TODO dodat pametne query parametre, ki so potrebni, al tukaj al v prisotnosti spada iskanje po stanju prisotnosti
    // ce se ce iskat po clanu, treba dodat cabila oz. clanSrecanja tabelo, ali se samo fetcha prisotnosti
    public List<SrecanjeDTO> getSrecanjaPoClanuInSkupini(
        // Long idClan, 
        Long idSkupina, LocalDateTime datumOd, LocalDateTime datumDo){
        List<SrecanjeDTO> res = entityManager.createNamedQuery("Srecanje.fromIdinDatum", SrecanjeDTO.class)
        .setParameter("skid", idSkupina)
        // .setParameter("cid", idClan)
        .setParameter("od", datumOd)
        .setParameter("do", datumDo)
        .getResultList();

        // entityManager.getCriteriaBuilder().createQuery(null)

        return res;
    }

    @Transactional
    public Boolean belezi(Long srecanjeId){
        entityManager.getTransaction().begin();
        try{
            Srecanje srecanje = entityManager.find(Srecanje.class, srecanjeId);
            if(srecanje == null) throw new NoResultException("Ni srecanja");
            srecanje.setBelezenje(true);
            entityManager.merge(srecanje);
        }catch (Exception e){
            System.out.println("Exception v belezi v srecanja" + e.getMessage());
            entityManager.getTransaction().rollback();
            return false;
        }
        entityManager.getTransaction().commit();
        return true;
    }

    //POST za ustvarjanje srecanj
    //TODO ustvarjanje notification in pre-srecanje funkcij (cekat raypoloyljivost), vse se shrani v prisotnosti
    //TODO ce je belezenje ustvari prisotnosti, za zdaj preskocim, pa kaksne clane se doda v srecanje da so obvesceni, dolocit kataera stran klice drugo, ce sploh klice
    @Transactional
    public SrecanjeDTO novoSrecanje(SrecanjeDTO srecanje, Long idSkupine){
        entityManager.getTransaction().begin();
        Srecanje novoSrecanje;
        try{
            SkupinaMin skupina = entityManager.getReference(SkupinaMin.class, idSkupine);
            if(skupina == null)throw new NoResultException("Skupine z id-jem "+ idSkupine + " ni!");
            novoSrecanje = new Srecanje();
            novoSrecanje.setBelezenje(false);
            novoSrecanje.setDatumOd(srecanje.getDatumOd());
            novoSrecanje.setDatumDo(srecanje.getDatumDo());
            novoSrecanje.setIme(srecanje.getIme());
            novoSrecanje.setKraj(srecanje.getKraj());
            novoSrecanje.setOpis(srecanje.getOpis());
            novoSrecanje.setSkupina(skupina);
            
            entityManager.persist(novoSrecanje);
            srecanje = new SrecanjeDTO(novoSrecanje);
            entityManager.getTransaction().commit();
            
        String novoSrecanjeJson = gson.toJson(new SrecanjeMinDTO(novoSrecanje));
        producer.send(new ProducerRecord<String,String>("srecanje-post", novoSrecanjeJson));
        }catch(NoResultException nre){
            entityManager.getTransaction().rollback();
            throw nre;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        return srecanje;
    }

    @Transactional
    public SrecanjeDTO posodobiSrecanje(SrecanjeDTO srecanje)throws NoResultException{
        entityManager.getTransaction().begin();
        
        try{

            Srecanje novoSrecanje = entityManager.find(Srecanje.class, srecanje.getId());
            if(novoSrecanje == null) throw new NoResultException("Srecanja z id-jem: " + srecanje.getId() + "ni.");
            if(srecanje.getDatumOd() != null) novoSrecanje.setDatumOd(srecanje.getDatumOd());
            if(srecanje.getDatumDo() != null)  novoSrecanje.setDatumDo(srecanje.getDatumDo());
            if(srecanje.getIme() != null)  novoSrecanje.setIme(srecanje.getIme());
            if(srecanje.getKraj() != null) novoSrecanje.setKraj(srecanje.getKraj());
            if(srecanje.getOpis() != null) novoSrecanje.setOpis(srecanje.getOpis());
            entityManager.merge(novoSrecanje);
            srecanje = new SrecanjeDTO(novoSrecanje);
            entityManager.getTransaction().commit();
            
        String posodobljenoSrecanjeJson = gson.toJson(new SrecanjeMinDTO(novoSrecanje));
        producer.send(new ProducerRecord<String,String>("srecanje-put", posodobljenoSrecanjeJson));

        }catch(NoResultException nre){
            entityManager.getTransaction().rollback();
            throw nre;
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        return srecanje;
    }

    @Transactional
    public void deleteSrecanje( Long srecanjeId){
        entityManager.getTransaction().begin();
        try{
            Srecanje srecanje = entityManager.find(Srecanje.class, srecanjeId);
            
            entityManager.remove(srecanje);
            entityManager.getTransaction().commit();
            
            producer.send(new ProducerRecord<String,String>("srecanje-delete", srecanjeId.toString()));
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

}
