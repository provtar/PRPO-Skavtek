package si.skavtko.prisotnosti.entitete;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Ta query bo potem spadal med prisotnosti

@Entity
@Table(name = "srecanjeminprisotnosti")
public class SrecanjeMin {

    @Id
    private Long id;

    @Basic(optional = false)
    private String ime;

    @Column(updatable = false)
    private Long skupinaId;

    private Boolean belezenje;


    public Boolean getBelezenje() {
        return belezenje;
    }

    public void setBelezenje(Boolean belezenje) {
        this.belezenje = belezenje;
    }

    public Long getSkupinaId() {
        return skupinaId;
    }

    public void setSkupinaId(Long skupinaId) {
        this.skupinaId = skupinaId;
    }

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

}
