package si.skavtko.zrna;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import si.skavtko.entitete.Clan;
import si.skavtko.entitete.ClanSkupina;
import si.skavtko.entitete.Skupina;

@ApplicationScoped
public class SkupinaZrno {
    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    private void init(){
            }

    @PreDestroy
    private void destroy(){
    }

    //za posameznega uporabika - za vec uporabnikov hkrati za zdaj bo skrbel resource skupina
    
    public Skupina getSkupina(Long id){
        Skupina skupina = entityManager.find(Skupina.class, id);
        //BUG: ce klices detach, se ne bo izvedena poizvedba v database
        //Poizvedba se klice, ko je podatek ze vrnjen resource classu
        //Se pozanimati pr profesorju
        //entityManager.detach(clan);

        return skupina;
    }

    //TODO zbrisat al posodobt t metodo, za zdaj je samo za primer
    public List<Skupina> getSkupinePoClanu(Long idClan){
        List<Skupina> res = entityManager.createQuery("select s from Skupina s JOIN s.clani sc JOIN sc.clan c WHERE c.id =:arg1", Skupina.class).setParameter("arg1", idClan).getResultList();

        return res;
    }

    public List<Clan> getClaniPoSkupini(Long idSkupine){
        List<Clan> res = entityManager.createQuery("select c from Clan c JOIN c.skupine sc JOIN sc.skupina s WHERE s.id =:arg1", Clan.class).setParameter("arg1", idSkupine).getResultList();
        
        return res;
    }

    //POST za ustvarjanje skupina
    @Transactional
    public Skupina novaSkupina(Skupina skupina){//dela, manjka kaksen try catch block
        entityManager.getTransaction().begin();
        Skupina novaSkupina = new Skupina();
        novaSkupina.setIme(skupina.getIme());
        novaSkupina.setOpis(skupina.getOpis());
        novaSkupina.setPovezave(skupina.getPovezave());
        novaSkupina.setSrecanja(new HashSet<>());
        novaSkupina.setClani(new HashSet<ClanSkupina>());
        
        entityManager.persist(novaSkupina);
        entityManager.getTransaction().commit();
        return novaSkupina;
    }

    //PUT za posodabljanje skupine, clane se posodablja posebej
    @Transactional
    public Skupina posodobiSkupino(Skupina skupina){
        entityManager.getTransaction().begin();
        Skupina novaSkupina;
        try{
        novaSkupina = entityManager.find(Skupina.class, skupina.getId());
        if(skupina.getIme() != null)novaSkupina.setIme(skupina.getIme());
        if(skupina.getOpis() != null)novaSkupina.setOpis(skupina.getOpis());
        if(skupina.getPovezave() != null) novaSkupina.setPovezave(skupina.getPovezave());
        
        entityManager.merge(novaSkupina);
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return novaSkupina;
    }




    //lahko se naredi samo za enega clana naeknkrat
    //TODO, rabs en clanskupina objekt, ce bos imel se dodatne operacije
    @Transactional
    public ClanSkupina dodajClana(Long skupinaId, Long clanId){//dela, manjka kaksen try catch block
        entityManager.getTransaction().begin();
        ClanSkupina cs;
        try{
        Skupina skupina = entityManager.getReference(Skupina.class, skupinaId);
        Set<ClanSkupina> clani = skupina.getClani();  
        Clan clan = entityManager.find(Clan.class, clanId);  
        Set<ClanSkupina> skupine = clan.getSkupine();

        cs = new ClanSkupina();
        cs.setClan(clan);
        cs.setSkupina(skupina);
        entityManager.persist(cs);
        
        clani.add(cs);
        skupine.add(cs);
        entityManager.merge(clan);
        entityManager.merge(skupina);
        }catch (Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        
        entityManager.getTransaction().commit();
        return cs;
    }

    @Transactional
    public Set<ClanSkupina> dodajClane(Long skupinaId, List<Long> claniId){//dela, manjka kaksen try catch block
        entityManager.getTransaction().begin();
        Skupina skupina;
        try{
        skupina = entityManager.getReference(Skupina.class, skupinaId);
        ClanSkupina cs;

        for(Long clanId : claniId){
            Clan clan = entityManager.find(Clan.class, clanId); 
            //To mora bit notr v loopu, ne vem zakaj, ni optimalno
            Set<ClanSkupina> skupine = clan.getSkupine();
            Set<ClanSkupina> clani = skupina.getClani();

            cs = new ClanSkupina();
            cs.setClan(clan);
            cs.setSkupina(skupina);
            entityManager.persist(cs);
        
            clani.add(cs);
            skupine.add(cs);
            entityManager.merge(clan);
            entityManager.merge(skupina);
        }

        entityManager.merge(skupina);

        }catch (Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        
        entityManager.getTransaction().commit();
        return skupina.getClani();
    }

    @Transactional
    public void deleteSkupino( Long skupinaId){
        entityManager.getTransaction().begin();
        Skupina skupina = entityManager.find(Skupina.class, skupinaId);
        
        entityManager.remove(skupina);
        entityManager.getTransaction().commit();
    }

    public void deleteClanaSkupine( Long skupinaId, Long clanId){
        entityManager.getTransaction().begin();
        ClanSkupina skupinaClan = entityManager.createQuery("select cs from ClanSkupina cs where cs.clan.id = :arg1 and sc.skupina.id = :arg2", ClanSkupina.class).setParameter("arg1", clanId).setParameter("arg2", skupinaId).getSingleResult();
        
        entityManager.remove(skupinaClan);

        entityManager.getTransaction().commit();
    }

    //TODO odstrani seznam clanov
}
