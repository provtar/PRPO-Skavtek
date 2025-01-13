package si.skavtko.osebnospremljanje.entitete;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NamedQueries(
    value = {
        @NamedQuery(name = "OsebnoSpremljanje.fromClan", query = "select new si.skavtko.osebnospremljanje.dto.OsebnoSpremljanjeDTO(os.id, os.datum, os.naslov, os.vsebina, os.clan.id) "+
        "from OsebnoSpremljanje os where os.clan.id = :clanId")
    }
)
public class OsebnoSpremljanje {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Basic(optional = false)
    LocalDateTime datum;

    @Basic(optional = false)
    String naslov;

    String vsebina;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(updatable = false)
    ClanMin clan;

    public ClanMin getClan() {
        return clan;
    }

    public void setClan(ClanMin clan) {
        this.clan = clan;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public String getVsebina() {
        return vsebina;
    }

    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }

}
