package si.skavtko.zrna;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.google.gson.Gson;
import com.kumuluz.ee.streaming.common.annotations.StreamListener;

import si.skavtko.dto.ClanMinDTO;
import si.skavtko.entitete.ClanMin;

@ApplicationScoped
public class KafkaClanConsumer {

    Gson gson = null;

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    private void init(){
        gson = new Gson();
    }

    @StreamListener(topics = {"clan-post"})
    public void clanPost(ConsumerRecord<String, String> record) {
	    System.out.println(record.value());
        ClanMinDTO novClanDTO = gson.fromJson(record.value(), ClanMinDTO.class);
        novClanMin(novClanDTO);
    }

    @Transactional
    private void novClanMin(ClanMinDTO data){
        entityManager.getTransaction().begin();
        try{
            ClanMin novClan = new ClanMin();
            if(data.getId() != null) novClan.setId(data.getId());
            if(data.getIme() != null) novClan.setIme(data.getIme());
            if(data.getPriimek() != null) novClan.setPriimek(data.getPriimek());
            if(data.getSteg() != null) novClan.setSteg(data.getSteg());
            entityManager.persist(novClan);
            entityManager.getTransaction().commit();
            // System.out.println("ustvaril clana" + novClan.getId());
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }
        
    }

    @StreamListener(topics = {"clan-put"})
    public void clanPut(ConsumerRecord<String, String> record) {
	    System.out.println(record.value());
        ClanMinDTO updateClanDTO = gson.fromJson(record.value(), ClanMinDTO.class);
        putClanMin(updateClanDTO);
    }

    @Transactional
    private void putClanMin(ClanMinDTO data){
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
        }
    }

    @StreamListener(topics = {"clan-delete"})
    public void clanDelete(ConsumerRecord<String, String> record) {
	    // System.out.println("clan delete: " + record.value());
        Long id = Long.parseLong(record.value(), 10);
        deleteClanMin(id);
    }

    @Transactional
    private void deleteClanMin(Long id){
        entityManager.getTransaction().begin();
        try{
            // System.out.println(id);
            ClanMin clanToDelete = entityManager.find(ClanMin.class, id);
            if(clanToDelete != null)entityManager.remove(clanToDelete);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }
        
    }
}
