package si.skavtko.entitete;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity

public class ClanSkupina {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Clan clan;

    @ManyToOne
    private Skupina skupina;

    //TODO dodat en enum z vlogo v skupini SVOD, Tehnika, SV, VV, SVV, Izvidnik, Vodnik, POdvodnik, Noviciatovc, Klan, Udelezenec

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public Skupina getSkupina() {
        return skupina;
    }

    public void setSkupina(Skupina skupina) {
        this.skupina = skupina;
    }

}
