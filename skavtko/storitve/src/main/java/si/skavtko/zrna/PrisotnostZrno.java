package si.skavtko.zrna;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import si.skavtko.dto.ClanSkupineDTO;
import si.skavtko.entitete.Clan;
import si.skavtko.entitete.Prisotnost;
import si.skavtko.entitete.Srecanje;
import si.skavtko.entitete.enums.TipPrisotnosti;

@ApplicationScoped
public class PrisotnostZrno {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    SkupinaZrno skupinaZrno;

    //se isce po srecanju, po clanu in skupini
    //se doda za vse prisotne na srecanju hkrati, za sedaj, pole se jih lahko doloci po tagu
    //filtri za odsotnosti
    //se posodablja eno prisotnost na enkrat ali celo listo in bulk, kar celo listo in bulk
    //se brise vse prisotnosti hkrati
    //kasneje dodamo anlitiko

    public List<Prisotnost> isciPoSrecanju(Long idSrecanja){
        List<Prisotnost> res = entityManager.createQuery("select p from Prisotnost p join p.srecanje s on s.id = :arg1", Prisotnost.class)
        .setParameter("arg1", idSrecanja).getResultList();
        return res;
    }

    public List<Prisotnost> isciPoClanuInSkupini(Long idClana, Long idSkupine){
        List<Prisotnost> res = entityManager.createQuery("select p from Prisotnost p join p.clan c on c.id = :arg1 join p.srecanje s on (:arg2 is null or s.skupina.id = :arg2)", Prisotnost.class)
        .setParameter("arg1", idClana).setParameter("arg2", idSkupine).getResultList();
        return res;
    }

    //v parameter se doda se tage prisotnih
    // TODO verzijo, ki ji samo posljes listo prisotnosti in jo persista namesto te stale
    @Transactional
    public List<Prisotnost> dodajPrisotnosti(Long idSrecanja){
        entityManager.getTransaction().begin();
        ArrayList<Prisotnost> prisotni = new ArrayList<>();
        try{
            Srecanje srecanje = entityManager.find(Srecanje.class, idSrecanja);
            List<ClanSkupineDTO> clani = skupinaZrno.getClaniPoSkupini(srecanje.getSkupina().getId());
            for(ClanSkupineDTO c : clani){
                Prisotnost prisotnost = new Prisotnost();
                prisotnost.setPrisotnost(TipPrisotnosti.Pozen);
                Clan cc = entityManager.getReference(Clan.class, c.getId());
                prisotnost.setClan(cc);
                prisotnost.setSrecanje(srecanje);
                prisotni.add(prisotnost);
                entityManager.persist(prisotnost);
                srecanje.getPrisotnosti().add(prisotnost);
                cc.getPrisotnosti().add(prisotnost);
                entityManager.merge(c);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return prisotni;
    }

    @Transactional
    public List<Prisotnost> posodobiPrisotnosti(List<Prisotnost> prisotnosti){
        ArrayList<Prisotnost> res = new ArrayList<>(prisotnosti.size());
        entityManager.getTransaction().begin();
        try{
            for(Prisotnost p : prisotnosti){
                Prisotnost prisotnost = entityManager.find(Prisotnost.class, p.getId());
                if(prisotnost != null){
                    prisotnost.setOpomba(p.getOpomba());
                    prisotnost.setPrisotnost(p.getPrisotnost());
                    entityManager.persist(prisotnost);
                    res.add(prisotnost);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return res;
    }

    @Transactional
    public void zbrisiPrisotnosti(List<Long> prisotnosti){
        entityManager.getTransaction().begin();
        try{
            for(Long id : prisotnosti){
                Prisotnost prisotnost = entityManager.find(Prisotnost.class, id); 
                if(prisotnost != null){
                    entityManager.remove(prisotnost);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return;
        }
        entityManager.getTransaction().commit();
    }
}
