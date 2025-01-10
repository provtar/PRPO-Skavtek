package si.skavtko.zrna;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.google.gson.Gson;
import com.kumuluz.ee.streaming.common.annotations.StreamListener;

import si.skavtko.dto.SkupinaMinDTO;
import si.skavtko.entitete.SkupinaMin;

@ApplicationScoped
public class KafkaSkupinaConsumer {

    Gson gson = null;

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    private void init(){
        gson = new Gson();
    }

    @StreamListener(topics = {"skupina-post"})
    public void clanPost(ConsumerRecord<String, String> record) {
	    System.out.println(record.value());
        SkupinaMinDTO novaSkupinaMinDTO = gson.fromJson(record.value(), SkupinaMinDTO.class);
        novaSkupinaMin(novaSkupinaMinDTO);
    }

    @Transactional
    private void novaSkupinaMin(SkupinaMinDTO data){
        entityManager.getTransaction().begin();
        try{
            SkupinaMin novaSkupinaMin = new SkupinaMin();
            if(data.getId() != null) novaSkupinaMin.setId(data.getId());
            if(data.getIme() != null) novaSkupinaMin.setIme(data.getIme());
            if(data.getOpis() != null) novaSkupinaMin.setOpis(data.getOpis());
            
            entityManager.persist(novaSkupinaMin);
            entityManager.getTransaction().commit();
            // System.out.println("ustvaril skupinnoMin: " + novaSkupinaMin.getId());
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }
        
    }

    @StreamListener(topics = {"skupina-put"})
    public void skupinaMinPut(ConsumerRecord<String, String> record) {
	    // System.out.println(record.value());
        SkupinaMinDTO updatedSkupinaMinDTO = gson.fromJson(record.value(), SkupinaMinDTO.class);
        putSkupinaMin(updatedSkupinaMinDTO);
    }

    @Transactional
    private void putSkupinaMin(SkupinaMinDTO data){
        entityManager.getTransaction().begin();
        try{
            SkupinaMin updateSkupinaMin = entityManager.find(SkupinaMin.class, data.getId());
            if(data.getId() != null) updateSkupinaMin.setId(data.getId());
            if(data.getIme() != null) updateSkupinaMin.setIme(data.getIme());
            if(data.getOpis() != null) updateSkupinaMin.setOpis(data.getOpis());
            entityManager.merge(updateSkupinaMin);
            entityManager.getTransaction().commit();
            // System.out.println("posodobil skupinnoMin: " + updateSkupinaMin.getId());
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }
    }

    @StreamListener(topics = {"skupina-delete"})
    public void skupinaMinDelete(ConsumerRecord<String, String> record) {
        Long id = Long.parseLong(record.value(), 10);
        deleteClanMin(id);
    }

    @Transactional
    private void deleteClanMin(Long id){
        entityManager.getTransaction().begin();
        try{
            System.out.println("Deleting skupina: " + id);
            SkupinaMin deleteSkupinaMin = entityManager.find(SkupinaMin.class, id);
            if(deleteSkupinaMin != null)entityManager.remove(deleteSkupinaMin);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }
        
    }
}
