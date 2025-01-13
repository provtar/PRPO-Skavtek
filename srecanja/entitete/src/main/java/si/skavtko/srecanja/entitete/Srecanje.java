package si.skavtko.srecanja.entitete;

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

//Ta query bo potem spadal med prisotnosti
@NamedQueries({
    @NamedQuery(name = "Srecanje.fromIdinDatum",
    query = "select distinct new si.skavtko.srecanja.dto.SrecanjeDTO(sr.id, sr.ime, sr.datumOd, sr.datumDo, sr.kraj, sr.opis, sr.belezenje, sr.skupina.id, sr.skupina.ime) "+
    "from Srecanje sr " +
    // " LEFT JOIN sr.prisotnosti p ON  (:cid is null or p.clan.id = :cid) "+"
    "JOIN sr.skupina s ON (s.id =:skid OR :skid is null) "+
    "WHERE (sr.datumOd >= :od OR cast(:od as date) is null)AND ((sr.datumDo is null and sr.datumOd <= :do) or sr.datumDo <= :do OR cast(:do as date) is null)")
})
@Entity
public class Srecanje {

    public Srecanje() {
        // this.prisotnosti = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;

    @Basic(optional = false)
    private String ime;

    //TODO enum z vidljivostjo srecanja, za iskanje srecanj
    //TODO kratek seznam vse ìh clanov, za iskanje po clanu, nesmiselno, se gleda to v prisotnostih

    private LocalDateTime datumOd;

    private LocalDateTime datumDo;

    private String kraj;

    private String opis;
    
    private Boolean belezenje = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "skupina", referencedColumnName = "id", updatable = false)
    private SkupinaMin skupina;

    // public Set<Prisotnost> getPrisotnosti() {
    //     return prisotnosti;
    // }

    // public void setPrisotnosti(Set<Prisotnost> prisotnosti) {
    //     this.prisotnosti = prisotnosti;
    // }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getIme() {
        return ime;
    }

    public LocalDateTime getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDateTime datumOd) {
        this.datumOd = datumOd;
    }
 
    public LocalDateTime getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDateTime datumDo) {
        this.datumDo = datumDo;
    }
    
    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Boolean getBelezenje() {
        return belezenje;
    }

    public void setBelezenje(Boolean belezenje) {
        this.belezenje = belezenje;
    }
    
    public SkupinaMin getSkupina() {
        return skupina;
    }

    public void setSkupina(SkupinaMin skupina) {
        this.skupina = skupina;
    }
}
