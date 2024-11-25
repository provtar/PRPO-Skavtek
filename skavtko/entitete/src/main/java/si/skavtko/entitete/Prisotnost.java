package si.skavtko.entitete;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Prisotnost {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    Srecanje srecanje;
}
