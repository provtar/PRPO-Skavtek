package si.skavtko.skupine.entitete;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "clan", "skupina" }) })
@NamedQueries(value = {
    @NamedQuery(name = "Clan.fromSkupina", query = "select new si.skavtko.skupine.storitve.dto.ClanSkupineDTO(cs.clan.id, cs.clan.ime, cs.clan.priimek, cs.clan.steg) from ClanSkupina cs where cs.skupina.id = :skupinaId"),
    @NamedQuery(name = "Skupina.fromClan", query = "select new si.skavtko.skupine.storitve.dto.SkupinaClanaDTO(cs.skupina.id, cs.skupina.ime) from ClanSkupina cs where cs.clan.id = :clanId"),
    @NamedQuery(name = "CS.fromCinS", query = "select cs from ClanSkupina cs where cs.clan.id = :clanId and cs.skupina.id = :skupinaId")
})
public class ClanSkupina {
    //Je smiselno, da ima id?, bi blo boljse sam rabit clan pa skupino kot mulitple key
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "clan", referencedColumnName = "id", updatable = false)
    private ClanMin clan;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "skupina", referencedColumnName = "id", updatable = false)
    private Skupina skupina;

    //TODO dodat en enum z vlogo v skupini SVOD, Tehnika, SV, VV, SVV, Izvidnik, Vodnik, POdvodnik, Noviciatovc, Klan, Udelezenec, vlogo vkljucit v DTO

    public ClanMin getClan() {
        return clan;
    }

    public void setClan(ClanMin clan) {
        this.clan = clan;
    }

    public Skupina getSkupina() {
        return skupina;
    }

    public void setSkupina(Skupina skupina) {
        this.skupina = skupina;
    }

}
