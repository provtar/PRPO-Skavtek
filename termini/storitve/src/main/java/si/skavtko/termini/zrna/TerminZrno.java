package si.skavtko.termini.zrna;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import si.skavtko.termini.dto.TerminDTO;
import si.skavtko.termini.entitete.Termin;
import si.skavtko.termini.entitete.enums.TipTermina;

@ApplicationScoped
public class TerminZrno {
    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    private void init(){
    }

    @PreDestroy
    private void destroy(){
    }

    public List<TerminDTO> getVsebovaniTermini(LocalDateTime datumOd, LocalDateTime datumDo, Long clanId) throws InvalidParameterException {
        if (datumDo == null || datumOd == null || clanId == null) {
            throw new InvalidParameterException("One or more imputs is null");
        }
        if (datumOd.compareTo(datumDo) >= 0) {
            throw new InvalidParameterException("datumDo je pred datumOd. Dogodek se ne more kasneje koncati kot se je zacel!!!");
        }
        List<TerminDTO> rez = entityManager.createNamedQuery("Termini.fromOdDoClan", TerminDTO.class)
        .setParameter("datumOd", datumOd)
        .setParameter("datumDo", datumDo)
        .setParameter("clanId", clanId)
        .getResultList();
        return rez;
    }

    public List<TerminDTO> posodobiTermine(LocalDateTime datumOd, LocalDateTime datumDo, Long clanId, List<TerminDTO> noviTermini) {
        entityManager.getTransaction().begin();
        try {
            List<TerminDTO> stari = getVsebovaniTermini(datumOd, datumDo, clanId);
            for(TerminDTO terminD : stari) {
                // odstrani stare
                if(datumOd.isAfter(terminD.getDatumOd())) { // first edge case
                    Termin termin = entityManager.find(Termin.class, terminD.getId());
                    termin.setDatumOd(datumOd);
                    entityManager.merge(termin);
                }
                if(terminD.getDatumDo().isAfter(datumDo)) { // second edge case
                    Termin termin = entityManager.find(Termin.class, terminD.getId());
                    termin.setDatumDo(datumDo);
                    entityManager.merge(termin);
                }
                if (!datumOd.isAfter(terminD.getDatumOd()) && !terminD.getDatumDo().isAfter(datumDo)) {
                    Termin termin = entityManager.find(Termin.class, terminD.getId());
                    entityManager.remove(termin);
                }
            }

            // Clan user = entityManager.getReference(Clan.class, clanId);
            ArrayList<TerminDTO> seznamNovi = new ArrayList<>();
            for(TerminDTO termin : noviTermini) {
                if(termin.getDatumDo() != null && termin.getDatumOd() != null) {
                    Termin nov = new Termin(termin.getDatumOd(), termin.getDatumDo(), clanId, TipTermina.Zaseden);
                    nov.setDatumDo(termin.getDatumDo());
                    nov.setClanId(clanId);
                    entityManager.persist(nov);
                    seznamNovi.add(new TerminDTO(nov));
                }
            }
            entityManager.getTransaction().commit();
            return seznamNovi;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        
    }

    @Transactional
    public TerminDTO novTermin(TerminDTO data) {
        TerminDTO novTermin = null;
        try {
            entityManager.getTransaction().begin();
            // Clan user = entityManager.getReference(Clan.class, data.getClan().getId());
            Termin nov = new Termin(data.getDatumOd(), data.getDatumDo(), data.getClanId(), TipTermina.Zaseden);
            entityManager.persist(nov);
            entityManager.flush();
            novTermin = new TerminDTO(nov);
            entityManager.getTransaction().commit();
        }catch(NotFoundException nfe){
            entityManager.getTransaction().rollback();
            throw nfe;
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        return novTermin;
    }

    
    @Transactional
    public Boolean checkDBconnection(){
        try {
            // Test connection by interacting with the database
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("SELECT 1").getSingleResult(); // Simple query to test the connection
            entityManager.getTransaction().commit();
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while connecting to the database.");
            return false;
        }
    }
}
