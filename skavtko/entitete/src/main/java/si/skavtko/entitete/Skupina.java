package si.skavtko.entitete;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import si.skavtko.entitete.embeddable.NamedLink;

@Entity
public class Skupina {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long skupinaId;

    @Basic(optional = false)
    private String imeSkupine;

    //TODO enum s tipi skuine, ce je treba kje opisat kakèen tip je, ni nujno

    private String opis;

    //Nisem sigurn ali to dela, morem testirat
    private ArrayList<NamedLink> povezave;

    //TODO sproti dopolni Relacije v katerih je
    @OneToMany(mappedBy = "skupina", orphanRemoval = true)
    Set<ClanSkupina> clani;
    
    @OneToMany(mappedBy = "master", orphanRemoval = true)
    Set<ClanPassive> childs;

    public Long getSkupinaId() {
        return skupinaId;
    }

    public void setSkupinaId(Long skupinaId) {
        this.skupinaId = skupinaId;
    }
    
    public String getImeSkupine() {
        return imeSkupine;
    }

    public void setImeSkupine(String imeSkupine) {
        this.imeSkupine = imeSkupine;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public ArrayList<NamedLink> getPovezave() {
        return povezave;
    }

    public void setPovezave(ArrayList<NamedLink> povezave) {
        this.povezave = povezave;
    }

}
