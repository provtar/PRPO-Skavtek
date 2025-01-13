package si.skavtko.skupine.storitve.zrna;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
// import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.gson.Gson;
import com.kumuluz.ee.streaming.common.annotations.StreamProducer;

import si.skavtko.skupine.storitve.dto.ClanSkupineDTO;
import si.skavtko.skupine.storitve.dto.SkupinaClanaDTO;
import si.skavtko.skupine.storitve.dto.SkupinaDTO;
import si.skavtko.skupine.storitve.dto.SkupinaMinDTO;
import si.skavtko.skupine.entitete.ClanMin;
import si.skavtko.skupine.entitete.ClanSkupina;
import si.skavtko.skupine.entitete.Skupina;
import si.skavtko.skupine.entitete.embeddable.NamedLink;

@ApplicationScoped
public class SkupinaZrno {
    @Inject
    @StreamProducer
    private Producer<String, String> producer;

    Gson gson = null;

    EntityManagerFactory emf;

    @PostConstruct
    private void init(){
        gson = new Gson();
        emf = Persistence.createEntityManagerFactory("defaultPU");
    }

    @PreDestroy
    private void deinit(){
        emf.close();
    }

    @PersistenceContext
    EntityManager fixEntityManager;

    public SkupinaDTO getSkupina(Long id){
        
        
        SkupinaDTO res = new SkupinaDTO(fixEntityManager.createQuery("SELECT s " +
             "FROM Skupina s WHERE s.id = :id", Skupina.class).setParameter("id", id).getSingleResult());
        
        return res;
    }

    public List<SkupinaClanaDTO> getSkupinePoClanu(Long idClan){
        EntityManager entityManager = emf.createEntityManager();
        List<SkupinaClanaDTO> res = entityManager.createNamedQuery("Skupina.fromClan", SkupinaClanaDTO.class)
        .setParameter("clanId", idClan).getResultList();
        entityManager.close();
        return res;
    }

    public List<ClanSkupineDTO> getClaniPoSkupini(Long idSkupine){
        EntityManager entityManager = emf.createEntityManager();
        List<ClanSkupineDTO> res = entityManager.createNamedQuery("Clan.fromSkupina", ClanSkupineDTO.class).setParameter("skupinaId", idSkupine).getResultList();
        
        return res;
    }

    @Transactional
    public SkupinaDTO novaSkupina(SkupinaDTO skupina){//dela, manjka kaksen try catch block
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
        Skupina novaSkupina = new Skupina();

        novaSkupina.setIme(skupina.getIme());
        novaSkupina.setOpis(skupina.getOpis());
        if(skupina.getPovezave() != null)novaSkupina.setPovezave(skupina.getPovezave());
        else novaSkupina.setPovezave(new ArrayList<NamedLink>());
        
        entityManager.persist(novaSkupina);
        entityManager.getTransaction().commit();

        skupina = new SkupinaDTO(novaSkupina);
        String novaSkupinaJson = gson.toJson(new SkupinaMinDTO(novaSkupina));
        producer.send(new ProducerRecord<String,String>("skupina-post", novaSkupinaJson));
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            throw e;
        }finally{
            entityManager.close();
        }
        return skupina;
    }

    @Transactional
    public SkupinaDTO posodobiSkupino(SkupinaDTO skupina){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        try{
        Skupina novaSkupina = entityManager.find(Skupina.class, skupina.getId());
        if (novaSkupina != null) {
            if(skupina.getIme() != null)novaSkupina.setIme(skupina.getIme());
            if(skupina.getOpis() != null)novaSkupina.setOpis(skupina.getOpis());
            if(skupina.getPovezave() != null) novaSkupina.setPovezave(skupina.getPovezave());
        
            entityManager.merge(novaSkupina);
            entityManager.getTransaction().commit();

            String novaSkupinaJson = gson.toJson(new SkupinaMinDTO(novaSkupina));
            producer.send(new ProducerRecord<String,String>("skupina-put", novaSkupinaJson));
            skupina = new SkupinaDTO(novaSkupina);
        }else{
            throw new NoResultException("Ni skupine.");
        }
        }catch(NoResultException nre){
            entityManager.getTransaction().rollback();
            throw nre;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }finally{
            entityManager.close();
        }
        
        return skupina;
    }

    @Transactional
    public ClanSkupineDTO dodajClana(Long skupinaId, Long clanId){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        ClanSkupineDTO res;
        ClanSkupina cs;

        try{
            Skupina skupina = entityManager.getReference(Skupina.class, skupinaId);
            ClanMin clan = entityManager.find(ClanMin.class, clanId);

            cs = new ClanSkupina();
            cs.setClan(clan);
            cs.setSkupina(skupina);
            entityManager.persist(cs);

            res = new ClanSkupineDTO(clanId, clan.getIme(), clan.getPriimek(), clan.getSteg());
        }catch (Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            res = null;
        }finally{
            entityManager.close();
        }
        
        entityManager.getTransaction().commit();
        return res;
    }

    @Transactional
    public Set<ClanSkupineDTO> dodajClane(Long skupinaId, List<Long> claniId){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Skupina skupina;
        Set<ClanSkupineDTO> res = new HashSet<>();
        try{
        skupina = entityManager.getReference(Skupina.class, skupinaId);
        ClanSkupina cs;

            for(Long clanId : claniId){
                ClanMin clan;
                clan = entityManager.find(ClanMin.class, clanId);
                if(clan != null){
                    // TODO suboptimalno, ampak dela, nekaj prevec poizvedb se klice, vsaj clana bi si ga lahko evitiral
                    // Resitev, dobit seznam id-jev clanov in dobt, ce ze obstaja id
                    try {
                        entityManager.createNamedQuery("CS.fromCinS", ClanSkupina.class)
                        .setParameter("clanId", clanId).setParameter("skupinaId", skupinaId).getSingleResult();
                    } catch (NoResultException nre) {
                        cs = new ClanSkupina();
                        cs.setClan(clan);
                        cs.setSkupina(skupina);
                        entityManager.persist(cs);

                        res.add(new ClanSkupineDTO(clan.getId(), clan.getIme(), clan.getPriimek(), clan.getSteg()));
                        entityManager.merge(clan);
                        continue;
                    }
                }
            }
            entityManager.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage() + " Komi tuki sem ga ulovu");
            entityManager.getTransaction().rollback();
            throw e;
        }finally{
            entityManager.close();
        }
        return res;
    }

    @Transactional
    public void deleteSkupino( Long skupinaId){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Skupina skupina = entityManager.find(Skupina.class, skupinaId);
        try{
            if(skupina != null)entityManager.remove(skupina);
            entityManager.getTransaction().commit();

            producer.send(new ProducerRecord<String,String>("skupina-delete", skupinaId.toString()));
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }finally{
            entityManager.close();
        }
    }

    @Transactional
    public void deleteClanaSkupine( Long skupinaId, Long clanId){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
                ClanSkupina skupinaClan = entityManager.createNamedQuery("CS.fromCinS", ClanSkupina.class)
                .setParameter("clanId", clanId).setParameter("skupinaId", skupinaId).getSingleResult();
                entityManager.remove(skupinaClan);
                entityManager.getTransaction().commit();
        }catch(NoResultException nre){
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            entityManager.close();
        }
    }

    @Transactional
    public void deleteClaneSkupine(Long skupinaId, List<Long> clani){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            for(Long clanId : clani){
                try{
                    ClanSkupina skupinaClan = entityManager.createNamedQuery("CS.fromCinS", ClanSkupina.class)
                    .setParameter("clanId", clanId).setParameter("skupinaId", skupinaId).getSingleResult();
                    entityManager.remove(skupinaClan);
                }catch(NoResultException nre){
                    continue;
                }
            }
            entityManager.getTransaction().commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            throw e;
        }finally{
            entityManager.close();
        }
    }

    public Boolean checkDBconnection(){
        EntityManager entityManager = emf.createEntityManager();
        
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
        }finally{
            entityManager.close();
        }
    }
}
