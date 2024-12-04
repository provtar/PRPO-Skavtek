package si.skavtko.entitete;

import java.util.List;
import java.util.Set;


import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import si.skavtko.entitete.embeddable.NamedLink;

@Entity
public class Skupina {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic(optional = false)
    private String ime;

    //TODO enum s tipi skuine, ce je treba kje opisat kakèen tip je, ni nujno

    private String opis;


    //Nisem sigurn ali to dela, morem testirat
    @Embedded
    private List<NamedLink> povezave;

    //TODO sproti dopolni Relacije v katerih je
    //@JsonbTransient
    @OneToMany(mappedBy = "skupina", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    Set<ClanSkupina> clani;

    @JsonIgnore
    @OneToMany(mappedBy = "skupina", orphanRemoval = true)
    Set<Srecanje> srecanja;    


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
    
    public List<NamedLink> getPovezave() {
        return povezave;
    }

    public void setPovezave(List<NamedLink> povezave) {
        this.povezave = povezave;
    }
    
    public Set<ClanSkupina> getClani() {
        return clani;
    }

    public void setClani(Set<ClanSkupina> clani) {
        this.clani = clani;
    }

    public Set<Srecanje> getSrecanja() {
        return srecanja;
    }

    public void setSrecanja(Set<Srecanje> srecanja) {
        this.srecanja = srecanja;
    }

}
