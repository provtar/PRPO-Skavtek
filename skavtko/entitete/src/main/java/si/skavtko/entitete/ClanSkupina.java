package si.skavtko.entitete;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

//https://www.baeldung.com/jpa-entities-serializable

public class ClanSkupina {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //@JsonBackReference(value = "clan-cs")
    // @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Clan clan;
    
    //@JsonBackReference
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
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
