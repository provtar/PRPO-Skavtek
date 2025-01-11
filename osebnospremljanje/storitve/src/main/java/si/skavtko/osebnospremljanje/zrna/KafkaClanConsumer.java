package si.skavtko.osebnospremljanje.zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.google.gson.Gson;
import com.kumuluz.ee.streaming.common.annotations.StreamListener;

import si.skavtko.osebnospremljanje.dto.ClanMinDTO;
import si.skavtko.osebnospremljanje.entitete.ClanMin;

@ApplicationScoped
public class KafkaClanConsumer {

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

    @StreamListener(topics = {"clan-post"})
    public void clanPost(ConsumerRecord<String, String> record) {
        ClanMinDTO novClanDTO = gson.fromJson(record.value(), ClanMinDTO.class);
        novClanMin(novClanDTO);
    }

    @Transactional
    private void novClanMin(ClanMinDTO data){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            ClanMin novClan = new ClanMin();
            if(data.getId() != null) novClan.setId(data.getId());
            if(data.getIme() != null) novClan.setIme(data.getIme());
            if(data.getPriimek() != null) novClan.setPriimek(data.getPriimek());
            if(data.getSteg() != null) novClan.setSteg(data.getSteg());
            entityManager.persist(novClan);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }finally{
            entityManager.close();
        }
        
    }

    @StreamListener(topics = {"clan-put"})
    public void clanPut(ConsumerRecord<String, String> record) {
        ClanMinDTO updateClanDTO = gson.fromJson(record.value(), ClanMinDTO.class);
        putClanMin(updateClanDTO);
    }

    @Transactional
    private void putClanMin(ClanMinDTO data){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            ClanMin updateClan = entityManager.find(ClanMin.class, data.getId());
            if(data.getIme() != null) updateClan.setIme(data.getIme());
            if(data.getPriimek() != null) updateClan.setPriimek(data.getPriimek());
            if(data.getSteg() != null) updateClan.setSteg(data.getSteg());
            entityManager.merge(updateClan);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }finally{
            entityManager.close();
        }
    }

    @StreamListener(topics = {"clan-delete"})
    public void clanDelete(ConsumerRecord<String, String> record) {
        Long id = Long.parseLong(record.value(), 10);
        deleteClanMin(id);
    }

    @Transactional
    private void deleteClanMin(Long id){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            ClanMin clanToDelete = entityManager.find(ClanMin.class, id);
            if(clanToDelete != null)entityManager.remove(clanToDelete);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }finally{
            entityManager.close();
        }
        
    }
}
