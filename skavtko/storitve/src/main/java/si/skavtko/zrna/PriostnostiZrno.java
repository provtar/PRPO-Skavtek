package si.skavtko.zrna;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class PriostnostiZrno {
    @PersistenceContext
    EntityManager entityManager;
}
