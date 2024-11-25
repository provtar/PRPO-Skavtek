package si.skavtko.entitete;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Srecanje {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long user_id;

    @ManyToOne
    Skupina skupina;
}
