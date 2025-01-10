package si.skavtko.srecanja.entitete;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "skupinaminsrecanja")
public class SkupinaMin {

    @Id
    @Basic(optional = false)
    private Long id;

    @Basic(optional = false)
    private String ime;

    private String opis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }
    
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
