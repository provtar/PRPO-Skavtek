package si.skavtko.zrna;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import si.skavtko.entitete.Dovoljenje;

//S tem se ne bomo se ukvarjali
// Ne se ukvarjati s tem za zdaj
@ApplicationScoped
public class PermissionZrno {
    @PersistenceContext
    EntityManager entityManager;

    public static Dovoljenje getDovoljenje(Long userId, Long skupinaId){
        return null;
    }
}
