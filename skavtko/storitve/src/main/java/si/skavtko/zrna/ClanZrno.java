package si.skavtko.zrna;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import si.skavtko.entitete.Clan;
import si.skavtko.entitete.enums.UserRole;

@RequestScoped
public class ClanZrno {

    @PersistenceContext
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
        //BUG: ce klices detach, se ne bo izvedena poizvedba v database
        //Poizvedba se klice, ko je podatek ze vrnjen resource classu
        //Se pozanimati pr profesorju

        return clan;
        // return entityManager.find(Clan.class, id);
    }

    //TODO zbrisat al posodobt t metodo, za zdaj je samo za primer
    public List<Clan> getClan(String ime, String priimek){
        List<Clan> res = entityManager.createQuery("select c from Clan c where (:arg1 is null or c.ime = :arg1) and (:arg2 is null or c.ime = :arg2)", Clan.class).setParameter("arg1", ime).setParameter("arg2", ime).getResultList();

        return res;
    }

    //TEST, vidim, ce vrne ID
    @Transactional

    public Clan dodajClana(Clan data){//dela, manjka kaksen try catch block
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
        Clan clan;
        try {
            clan = entityManager.getReference(Clan.class, data.getId());
        } catch(EntityNotFoundException enf){
            return null;
        } catch(Exception e){
            throw e;
        }
        entityManager.getTransaction().begin();
        //BUG ne detachat, nikoli, nimam pojma sploh kdaj bi se to metodo rabilo, 
        //enkrat ko deteachas, ne mores pridobit podatkov
        //entityManager.detach(clan);
        clan.setIme(data.getIme());
        clan.setPriimek(data.getPriimek());
        clan = entityManager.merge(clan);
        entityManager.flush();
        entityManager.getTransaction().commit();
        //TODO error handling
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
