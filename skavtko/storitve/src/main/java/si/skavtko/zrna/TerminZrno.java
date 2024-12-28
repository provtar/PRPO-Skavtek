package si.skavtko.zrna;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import si.skavtko.dto.TerminDTO;

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
}
