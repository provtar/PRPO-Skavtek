package si.skavtko.zrna;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import si.skavtko.dto.OsebnoSpremljanjeDTO;
import si.skavtko.entitete.Clan;
import si.skavtko.entitete.OsebnoSpremljanje;
@ApplicationScoped
public class OsebnoSpremljanjeZrno {

    @PersistenceContext
    EntityManager entityManager;

    public List<OsebnoSpremljanjeDTO> findOsebnoSpremljanje(Long clanId){
        List<OsebnoSpremljanjeDTO> res = entityManager.createNamedQuery("OsebnoSpremljanje.fromClan", OsebnoSpremljanjeDTO.class)
        .setParameter("clanId", clanId).getResultList();
        return res;
    }

    @Transactional
    public OsebnoSpremljanjeDTO createOsebnoSpremljanje(OsebnoSpremljanjeDTO novoOsebnoSpremljanje){
        entityManager.getTransaction().begin();
        OsebnoSpremljanje osebnoSpremljanje = new OsebnoSpremljanje();
        try{
            Clan clan = entityManager.find(Clan.class, novoOsebnoSpremljanje.getClanId());
            if(clan == null) throw new NoResultException("Clana s tem id-jem ni");
            osebnoSpremljanje.setClan(clan);
            osebnoSpremljanje.setDatum(novoOsebnoSpremljanje.getDatum());
            osebnoSpremljanje.setNaslov(novoOsebnoSpremljanje.getNaslov());
            osebnoSpremljanje.setVsebina(novoOsebnoSpremljanje.getVsebina());
            entityManager.persist(osebnoSpremljanje);
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return new OsebnoSpremljanjeDTO(osebnoSpremljanje);
    }

    @Transactional
    public OsebnoSpremljanjeDTO updateOsebnoSpremljanje(OsebnoSpremljanjeDTO posodobljenoOsebnoSpremljanje){
        entityManager.getTransaction().begin();
        OsebnoSpremljanjeDTO res = null;
        try{
            OsebnoSpremljanje osebnoSpremljanje = entityManager.find(OsebnoSpremljanje.class, posodobljenoOsebnoSpremljanje.getId());
            if(osebnoSpremljanje == null) throw new NoResultException("Zapisa osebnega spremljanja s tem id-jem ni");
            osebnoSpremljanje.setDatum(posodobljenoOsebnoSpremljanje.getDatum());
            osebnoSpremljanje.setNaslov(posodobljenoOsebnoSpremljanje.getNaslov());
            osebnoSpremljanje.setVsebina(posodobljenoOsebnoSpremljanje.getVsebina());
            entityManager.persist(osebnoSpremljanje);
            res = new OsebnoSpremljanjeDTO(osebnoSpremljanje);
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return res;
    }

    @Transactional
    public void removeOsebnoSpremljanje(Long id){
        entityManager.getTransaction().begin();
        try {
            OsebnoSpremljanje osebnoSpremljanje = entityManager.getReference(OsebnoSpremljanje.class, id);
            if(osebnoSpremljanje != null) entityManager.remove(osebnoSpremljanje);
        } catch (Exception e) {
            System.out.println(e.toString());
            entityManager.getTransaction().rollback();
        }
        entityManager.getTransaction().commit();
    }
}
