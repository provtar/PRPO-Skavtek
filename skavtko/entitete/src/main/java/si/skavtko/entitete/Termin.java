package si.skavtko.entitete;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Termin {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long user_id;

    @ManyToOne
    Clan clan;
}
