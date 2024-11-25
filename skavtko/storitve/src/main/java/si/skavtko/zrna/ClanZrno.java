package si.skavtko.zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import si.skavtko.dto.ClanDTO;
import si.skavtko.entitete.Clan;
import si.skavtko.entitete.Clan.UserRole;

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

    //za posameznega uporabika - za vec uporabnikov hkrati za zdaj bo skrbel resource skupina
    
    public ClanDTO getClan(Long id){
        Clan clan = entityManager.find(Clan.class, id);
        clan.setRole(UserRole.PASSIVE);
        return new ClanDTO(clan);
    }

    @Transactional
    public ClanDTO dodajClana(Clan data){
        entityManager.getTransaction().begin();
        Clan novClan = data;
        
        entityManager.persist(novClan);
        entityManager.flush();
        entityManager.getTransaction().commit();

        return new ClanDTO(novClan);
    }

    @Transactional
    public ClanDTO posodobiClan(ClanDTO data){
        Clan clan = data.toClan();

        entityManager.merge(clan);
        entityManager.flush();

        //TODO error handling

        return new ClanDTO(clan);
    }

    @Transactional
    public void deleteClan( Long id){
        Clan clan = entityManager.find(Clan.class, id);
        entityManager.detach(clan);
    }

    //nekaj metod bo imelo @Transactional annotatio
}
