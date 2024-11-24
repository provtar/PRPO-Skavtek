package si.skavtko.zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import si.skavtko.entitete.Clan;
import si.skavtko.entitete.enums.UserRole;

@ApplicationScoped
public class ClanZrno {

    @PersistenceContext(unitName = "skavtko")
    EntityManager entityManager;
    
    @PostConstruct
    private void init(){
            }

    @PreDestroy
    private void destroy(){
    }

    //za posameznega uporabika - za vec uporabnikov hkrati za zdaj bo skrbel resource skupina
    
    public Clan getClan(Long id){
        Clan clan = entityManager.find(Clan.class, id);
        entityManager.detach(clan);
        return clan;
    }

    //TODO zbrisat al posodobt t metodo, za zdaj je samo za primer
    public Clan getClan(String ime, String priimek){
        Clan res = entityManager.createQuery("select c from Clani c where (:arg1 is null or c.ime = :arg1) and (:arg2 is null or c.ime = :arg2)", Clan.class).setParameter("arg1", ime).setParameter("arg2", ime).getSingleResult();
        entityManager.detach(res);
        return res;
    }

    //TEST, vidim, ce vrne ID
    @Transactional
    public Clan dodajClana(Clan data){
        entityManager.getTransaction().begin();
        // Clan novClan = new Clan();
        // novClan.setIme(data.getIme());
        // novClan.setPriimek(data.getPriimek());
        // novClan.setRole(data.getRole());
        entityManager.persist(data);
        entityManager.flush();
        entityManager.getTransaction().commit();
        //entityManager.detach(data);
        return data;
    }

    @Transactional
    public Clan posodobiClan(Clan data){
        try {
            entityManager.getReference(Clan.class, data.getId());
        } catch(EntityNotFoundException enf){
            return null;
        } catch(Exception e){
            throw e;
        }
        entityManager.getTransaction().begin();
        Clan clan = entityManager.merge(data);
        entityManager.flush();
        entityManager.getTransaction().commit();
        //TODO error handling
        entityManager.detach(clan);
        return clan;
    }

    @Transactional
    public void deleteClan( Long id){
        entityManager.getTransaction().begin();
        Clan clan = entityManager.find(Clan.class, id);
        entityManager.remove(clan);
        entityManager.getTransaction().commit();
    }
}
