package si.skavtko.prisotnosti.zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.google.gson.Gson;
import com.kumuluz.ee.streaming.common.annotations.StreamListener;

import si.skavtko.prisotnosti.dto.SrecanjeMinDTO;
import si.skavtko.prisotnosti.entitete.SrecanjeMin;

@ApplicationScoped
public class KafkaSrecanjeConsumer {

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

    @StreamListener(topics = {"srecanje-post"})
    public void srecanjePost(ConsumerRecord<String, String> record) {
	    System.out.println("SrecanjMin post: " + record.value());
        SrecanjeMinDTO novoSrecanjeDTO = gson.fromJson(record.value(), SrecanjeMinDTO.class);
        novoSrecanjeMin(novoSrecanjeDTO);
    }

    @Transactional
    private void novoSrecanjeMin(SrecanjeMinDTO data){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            SrecanjeMin novoSrecanjeMin = new SrecanjeMin();
            if(data.getId() != null) novoSrecanjeMin.setId(data.getId());
            if(data.getIme() != null) novoSrecanjeMin.setIme(data.getIme());
            if(data.getSkupinaId() != null) novoSrecanjeMin.setSkupinaId(data.getSkupinaId());
            if(data.getBelezenje() != null) novoSrecanjeMin.setBelezenje(data.getBelezenje());
            else novoSrecanjeMin.setBelezenje(false);
            
            entityManager.persist(novoSrecanjeMin);
            entityManager.getTransaction().commit();
            // System.out.println("ustvaril skupinnoMin: " + novoSrecanjeMin.getId());
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }finally{
            entityManager.close();
        }
        
    }

    @StreamListener(topics = {"srecanje-put"})
    public void skupinaMinPut(ConsumerRecord<String, String> record) {
	    System.out.println("Srecanje put: "+record.value());
        SrecanjeMinDTO updatedSrecanjeMinDTO = gson.fromJson(record.value(), SrecanjeMinDTO.class);
        putSrecanjeMin(updatedSrecanjeMinDTO);
    }

    @Transactional
    private void putSrecanjeMin(SrecanjeMinDTO data){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            SrecanjeMin updateSrecanjeMin = entityManager.find(SrecanjeMin.class, data.getId());
            if(data.getId() != null) updateSrecanjeMin.setId(data.getId());
            if(data.getIme() != null) updateSrecanjeMin.setIme(data.getIme());
            if(data.getSkupinaId() != null) updateSrecanjeMin.setSkupinaId(data.getSkupinaId());
            entityManager.merge(updateSrecanjeMin);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }finally{
            entityManager.close();
        }
    }

    @StreamListener(topics = {"srecanje-delete"})
    public void skupinaMinDelete(ConsumerRecord<String, String> record) {
        Long id = Long.parseLong(record.value(), 10);
        deleteSrecanjeMin(id);
    }

    @Transactional
    private void deleteSrecanjeMin(Long id){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            System.out.println("Deleting srecanje: " + id);
            SrecanjeMin deleteSrecanjeMin = entityManager.find(SrecanjeMin.class, id);
            if(deleteSrecanjeMin != null)entityManager.remove(deleteSrecanjeMin);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }finally{
            entityManager.close();
        }
        
    }
}
