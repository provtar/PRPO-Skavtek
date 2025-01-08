package si.skavtko.zrna;

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

import si.skavtko.dto.TerminDTO;
import si.skavtko.dto.TerminKratkoDTO;
import si.skavtko.entitete.Termin;
import si.skavtko.entitete.enums.TipTermina;

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

    public List<TerminDTO> getVsebovaniTermini(LocalDateTime datumOd, LocalDateTime datumDo, Long idClan) throws InvalidParameterException {
        if (datumDo == null || datumOd == null || idClan == null) {
            throw new InvalidParameterException("One or more imputs is null");
        }
        if (datumOd.compareTo(datumDo) >= 0) {
            throw new InvalidParameterException("datumDo je pred datumOd. Dogodek se ne more kasneje koncati kot se je zacel!!!");
        }
        List<TerminDTO> rez = entityManager.createNamedQuery("Termini.fromOdDoClan", TerminDTO.class)
        .setParameter("datumOd", datumOd)
        .setParameter("datumDo", datumDo)
        .setParameter("idClan", idClan)
        .getResultList();
        return rez;
    }

    public List<TerminDTO> posodobiTermine(LocalDateTime datumOd, LocalDateTime datumDo, Long idClan, List<TerminDTO> noviTermini) {
        List<TerminDTO> stari = getVsebovaniTermini(datumOd, datumDo, idClan);
        stari.get(0).getId();
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

        // Clan user = entityManager.getReference(Clan.class, idClan);
        ArrayList<TerminDTO> seznamNovi = new ArrayList<>();
        for(TerminDTO termin : noviTermini) {
            if(termin.getDatumDo() != null && termin.getDatumOd() != null) {
                Termin nov = new Termin(termin.getDatumOd(), termin.getDatumDo(), idClan, TipTermina.Zaseden);
                nov.setDatumDo(termin.getDatumDo());
                nov.setClanId(idClan);
                entityManager.persist(nov);
                seznamNovi.add(new TerminDTO(nov));
            }
        }
        return seznamNovi;
        
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
}
