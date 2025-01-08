package si.skavtko.zrna;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import si.skavtko.dto.ClanSkupineDTO;
import si.skavtko.dto.PrisotnostDTO;
import si.skavtko.dto.PrisotnostPutDTO;
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

    public List<PrisotnostDTO> isciPoSrecanju(Long idSrecanja){
        List<PrisotnostDTO> res = entityManager.createNamedQuery("Prisotnosti.fromSrecanje", PrisotnostDTO.class)
        .setParameter("srecanjeId", idSrecanja).getResultList();
        return res;
    }

    public List<PrisotnostDTO> isciPoClanuInSkupini(Long idClana, Long idSkupine){
        List<PrisotnostDTO> res = entityManager.createNamedQuery("Prisotnosti.fromClanInSkupina", PrisotnostDTO.class)
        .setParameter("clanId", idClana).setParameter("skupinaId", idSkupine).getResultList();
        return res;
    }

    // TODO verzijo, ki ji samo posljes listo prisotnosti in jo persista namesto te stale
    @Transactional
    public List<PrisotnostDTO> dodajPrisotnosti(Long idSrecanja){
        entityManager.getTransaction().begin();
        ArrayList<PrisotnostDTO> prisotni = new ArrayList<>();
        try{
            Srecanje srecanje = entityManager.find(Srecanje.class, idSrecanja);
            if(srecanje == null) throw new NoResultException();
            if(srecanje.getBelezenje() == true) return isciPoSrecanju(idSrecanja);
            srecanje.setBelezenje(true);
            List<ClanSkupineDTO> clani = skupinaZrno.getClaniPoSkupini(srecanje.getSkupina().getId());

            for(ClanSkupineDTO c : clani){
                Clan cc = entityManager.find(Clan.class, c.getClanId());
                if(cc == null) continue; //TODO, razmisli, ce je tko bolj pametno, ali ce rajsi posljes napako, ce je en clan napacen
                //TODO, pazi, da se prisotnosti ne podvojijo
                //Skoraj boljse sam zaznat napako in jo poslat tistemu, ki je neumne podatke posiljal
                Prisotnost prisotnost = new Prisotnost();

                prisotnost.setPrisotnost(TipPrisotnosti.Prisoten);
                prisotnost.setClan(cc);
                prisotnost.setSrecanje(srecanje);
                entityManager.persist(prisotnost);

                srecanje.getPrisotnosti().add(prisotnost);
                prisotni.add(new PrisotnostDTO(prisotnost));
            }
            entityManager.merge(srecanje);
            entityManager.getTransaction().commit();
            entityManager.refresh(srecanje);
            // System.out.println("Post prisotnosti" + srecanje.getBelezenje());
            entityManager.clear();
            // if (srecanje != null) {
            //     System.out.println("Post prisotnosti:" + srecanje.getBelezenje());
            // }
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        return prisotni;
    }

    @Transactional
    public List<PrisotnostDTO> posodobiPrisotnosti(List<PrisotnostPutDTO> prisotnosti){
        ArrayList<PrisotnostDTO> res = new ArrayList<>(prisotnosti.size());
        entityManager.getTransaction().begin();
        try{
            for(PrisotnostPutDTO p : prisotnosti){
                Prisotnost prisotnost = entityManager.find(Prisotnost.class, p.getId());
                if(prisotnost != null){ //TODO isto tukaj, sam poslt napako, ce je null?
                    prisotnost.setOpomba(p.getOpomba());
                    prisotnost.setPrisotnost(p.getPrisotnost());
                    entityManager.persist(prisotnost);
                    res.add(new PrisotnostDTO(prisotnost));
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
                    prisotnost.getSrecanje().getPrisotnosti().remove(prisotnost);
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

    public void zbrisiPrisotnostiSrecanja(Long srecanjeId){
        entityManager.getTransaction().begin();
        List<PrisotnostDTO> prisotnosti = entityManager.createNamedQuery("Prisotnosti.fromSrecanje", PrisotnostDTO.class)
        .setParameter("srecanjeId", srecanjeId).getResultList();
        Srecanje srecanje = entityManager.find(Srecanje.class, srecanjeId);
        if(srecanje == null) throw new NoResultException("Srecanja z id: " + srecanjeId + "ni.\n");
        srecanje.setBelezenje(false);
        try{
            for(PrisotnostDTO id : prisotnosti){
                Prisotnost prisotnost = entityManager.find(Prisotnost.class, id.getId()); 
                if(prisotnost != null){
                    srecanje.getPrisotnosti().remove(prisotnost);
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
