package si.skavtko.zrna;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import si.skavtko.dto.ClanSkupineDTO;
import si.skavtko.dto.SkupinaClanaDTO;
import si.skavtko.dto.SkupinaDTO;
import si.skavtko.entitete.Clan;
import si.skavtko.entitete.ClanSkupina;
import si.skavtko.entitete.Skupina;
import si.skavtko.entitete.embeddable.NamedLink;

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

    public SkupinaDTO getSkupina(Long id){
        Skupina skupina = entityManager.find(Skupina.class, id);
        SkupinaDTO res = new SkupinaDTO(skupina);
        return res;
    }

    public List<SkupinaClanaDTO> getSkupinePoClanu(Long idClan){
        List<SkupinaClanaDTO> res = entityManager.createNamedQuery("Skupina.fromClan", SkupinaClanaDTO.class)
        .setParameter("clanId", idClan).getResultList();

        return res;
    }

    public List<ClanSkupineDTO> getClaniPoSkupini(Long idSkupine){
        List<ClanSkupineDTO> res = entityManager.createNamedQuery("Clan.fromSkupina", ClanSkupineDTO.class).setParameter("skupinaId", idSkupine).getResultList();
        
        return res;
    }

    @Transactional
    public SkupinaDTO novaSkupina(SkupinaDTO skupina){//dela, manjka kaksen try catch block
        entityManager.getTransaction().begin();
        try{
        Skupina novaSkupina = new Skupina();

        novaSkupina.setIme(skupina.getIme());
        novaSkupina.setOpis(skupina.getOpis());
        if(skupina.getPovezave() != null)novaSkupina.setPovezave(skupina.getPovezave());
        else novaSkupina.setPovezave(new ArrayList<NamedLink>());
        
        entityManager.persist(novaSkupina);
        skupina = new SkupinaDTO(novaSkupina);
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            throw e;
        }

        entityManager.getTransaction().commit();
        return skupina;
    }

    @Transactional
    public SkupinaDTO posodobiSkupino(SkupinaDTO skupina){
        entityManager.getTransaction().begin();

        try{
        Skupina novaSkupina = entityManager.find(Skupina.class, skupina.getId());
        if (novaSkupina != null) {
            if(skupina.getIme() != null)novaSkupina.setIme(skupina.getIme());
            if(skupina.getOpis() != null)novaSkupina.setOpis(skupina.getOpis());
            if(skupina.getPovezave() != null) novaSkupina.setPovezave(skupina.getPovezave());
        
            entityManager.merge(novaSkupina);
            skupina = new SkupinaDTO(novaSkupina);
        }else{
            throw new NoResultException("Ni skupine.");
        }
        }catch(NoResultException nre){
            entityManager.getTransaction().rollback();
            throw nre;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return skupina;
    }

    @Transactional
    public ClanSkupineDTO dodajClana(Long skupinaId, Long clanId){
        entityManager.getTransaction().begin();
        ClanSkupineDTO res;
        ClanSkupina cs;

        try{
            Skupina skupina = entityManager.getReference(Skupina.class, skupinaId);
            Clan clan = entityManager.find(Clan.class, clanId);

            cs = new ClanSkupina();
            cs.setClan(clan);
            cs.setSkupina(skupina);
            entityManager.persist(cs);

            res = new ClanSkupineDTO(clanId, clan.getIme(), clan.getPriimek(), clan.getSteg());
        }catch (Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        
        entityManager.getTransaction().commit();
        return res;
    }

    @Transactional
    public Set<ClanSkupineDTO> dodajClane(Long skupinaId, List<Long> claniId){
        entityManager.getTransaction().begin();
        Skupina skupina;
        Set<ClanSkupineDTO> res = new HashSet<>();
        try{
        skupina = entityManager.getReference(Skupina.class, skupinaId);
        ClanSkupina cs;

            for(Long clanId : claniId){
                Clan clan = entityManager.getReference(Clan.class, clanId);
                if(clan != null){
                    // TODO suboptimalno, ampak dela, nekaj prevec poizvedb se klice, vsaj clana bi si ga lahko evitiral
                    // Resitev, dobit seznam id-jev clanov in dobt, ce ze obstaja id
                    try {
                        entityManager.createNamedQuery("CS.fromCinS", ClanSkupina.class)
                        .setParameter("clanId", clanId).setParameter("skupinaId", skupinaId).getSingleResult();
                    } catch (NoResultException nre) {
                        cs = new ClanSkupina();
                        cs.setClan(clan);
                        cs.setSkupina(skupina);
                        entityManager.persist(cs);

                        res.add(new ClanSkupineDTO(clan.getId(), clan.getIme(), clan.getPriimek(), clan.getSteg()));
                        entityManager.merge(clan);
                        continue;
                    }
            }

                
            }
        

        }catch (Exception e){
            System.out.println(e.getMessage() + " Komi tuki sem ga ulovu");
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return res;
    }

    @Transactional
    public void deleteSkupino( Long skupinaId){
        entityManager.getTransaction().begin();
        Skupina skupina = entityManager.find(Skupina.class, skupinaId);
        // System.out.println("Dobil skupino");
        try{
        if(skupina != null) entityManager.remove(skupina);
        entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }
    }

    @Transactional
    public void deleteClanaSkupine( Long skupinaId, Long clanId){
        entityManager.getTransaction().begin();
        try{
                ClanSkupina skupinaClan = entityManager.createNamedQuery("CS.fromCinS", ClanSkupina.class)
                .setParameter("clanId", clanId).setParameter("skupinaId", skupinaId).getSingleResult();
                entityManager.remove(skupinaClan);
            
        }catch(NoResultException nre){
            return;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        entityManager.getTransaction().commit();
    }

    @Transactional
    public void deleteClaneSkupine(Long skupinaId, List<Long> clani){
        entityManager.getTransaction().begin();
        try{
            for(Long clanId : clani){
                try{
                    ClanSkupina skupinaClan = entityManager.createNamedQuery("CS.fromCinS", ClanSkupina.class)
                    .setParameter("clanId", clanId).setParameter("skupinaId", skupinaId).getSingleResult();
                    entityManager.remove(skupinaClan);
                }catch(NoResultException nre){
                    continue;
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            throw e;
        }
        entityManager.getTransaction().commit();
    }

}
