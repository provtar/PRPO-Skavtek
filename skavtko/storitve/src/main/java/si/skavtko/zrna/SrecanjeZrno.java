package si.skavtko.zrna;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import si.skavtko.entitete.Clan;
import si.skavtko.entitete.ClanSkupina;
import si.skavtko.entitete.Prisotnost;
import si.skavtko.entitete.Skupina;
import si.skavtko.entitete.Srecanje;

@ApplicationScoped
public class SrecanjeZrno {
    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    private void init(){
            }

    @PreDestroy
    private void destroy(){
    }

    public Srecanje getSrecanje(Long id){
        Srecanje srecanje = entityManager.find(Srecanje.class, id);

        return srecanje;
    }

    //TODO dodat pametne query parametre, ki so potrebni, al tukaj al v prisotnosti spada iskanje po stanju prisotnosti
    public List<Srecanje> getSrecanjaPoClanuInSkupini(Long idClan, Long idSkupina, LocalDateTime datumOd, LocalDateTime datumDo){
        List<Srecanje> res = entityManager.createQuery("select sr from Srecanje sr JOIN sr.prisotnosti p ON (:arg2 is null or p.clan.id = :arg2) JOIN sr.skupina s ON (s.id =:arg1 OR :arg1 is null) WHERE (sr.datumOd > :od OR :od is null) AND (sr.datumDos < :do OR :do is null)", Srecanje.class)
        .setParameter("arg1", idSkupina).setParameter("arg2", idClan)
        .setParameter("od", datumOd).setParameter("do", datumDo)
        .getResultList();

        return res;
    }

    //POST za ustvarjanje srecanj
    // TODO ustvarjanje notification in pre-srecanje funkcij (cekat raypoloyljivost), vse se shrani v prisotnosti

    //TODO ce je belezenje ustvari prisotnosti, za zdaj preskocim, pa kaksne clane se doda v srecanje da so obvesceni
    @Transactional
    public Srecanje novoSrecanje(Srecanje srecanje, Long idSkupine){
        entityManager.getTransaction().begin();
        Srecanje novoSrecanje;
        try{
            novoSrecanje = new Srecanje();
            novoSrecanje.setBelezenje(srecanje.getBelezenje());
            novoSrecanje.setDatumOd(srecanje.getDatumOd());
            novoSrecanje.setDatumDo(srecanje.getDatumDo());
            novoSrecanje.setIme(srecanje.getIme());
            novoSrecanje.setKraj(srecanje.getKraj());
            novoSrecanje.setOpis(srecanje.getOpis());
            Skupina skupina = entityManager.getReference(Skupina.class, idSkupine);
            novoSrecanje.setSkupina(skupina);
            skupina.getSrecanja().add(novoSrecanje);
            entityManager.merge(skupina); //provi brez tega za vidt, ce se set sam posodobi
            novoSrecanje.setPrisotnosti(new HashSet<Prisotnost>());

            
            entityManager.persist(novoSrecanje);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return novoSrecanje;
    }

    //PUT za posodabljanje skupine, clane se posodablja posebej
    @Transactional
    public Srecanje posodobiSrecanje(Srecanje srecanje){
        entityManager.getTransaction().begin();
        Srecanje novoSrecanje;
        try{
            novoSrecanje = entityManager.find(Srecanje.class, srecanje.getId());
            if(novoSrecanje == null) return null;
            if(srecanje.getDatumOd() != null) novoSrecanje.setDatumOd(srecanje.getDatumOd());
            if(srecanje.getDatumDo() != null)  novoSrecanje.setDatumDo(srecanje.getDatumDo());
            if(srecanje.getIme() != null)  novoSrecanje.setIme(srecanje.getIme());
            if(srecanje.getKraj() != null) novoSrecanje.setKraj(srecanje.getKraj());
            if(srecanje.getOpis() != null) novoSrecanje.setOpis(srecanje.getOpis());
            entityManager.merge(novoSrecanje);
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return novoSrecanje;
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
    public void deleteSrecanje( Long srecanjeId){
        entityManager.getTransaction().begin();
        Srecanje srecanje = entityManager.find(Srecanje.class, srecanjeId);
        
        entityManager.remove(srecanje);
        entityManager.getTransaction().commit();
    }

}
