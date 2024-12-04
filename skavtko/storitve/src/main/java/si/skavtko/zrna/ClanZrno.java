package si.skavtko.zrna;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotFoundException;

import si.skavtko.dto.ClanDTO;
import si.skavtko.entitete.Clan;
import si.skavtko.entitete.enums.UserRole;

@ApplicationScoped
public class ClanZrno {

    @PersistenceContext
    EntityManager entityManager;
    
    @PostConstruct
    private void init(){
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
        .createQuery("select new si.skavtko.dto.ClanDTO(c.id, c.ime, c.priimek, c.steg, c.skavtskoIme) from Clan c where (:arg1 is null or c.ime = :arg1) and (:arg2 is null or c.priimek = :arg2) and c.role = :arg3", ClanDTO.class).
        setParameter("arg1", ime)
        .setParameter("arg2", priimek)
        .setParameter("arg3", UserRole.ACTIVE)
        .getResultList();

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

    //TODO metode za spreminjati status clana v obe smeri
    //TODO metode za spreminjat lastnistvo pasivnih clanov

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
    }
}
