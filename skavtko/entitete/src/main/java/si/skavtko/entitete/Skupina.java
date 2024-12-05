package si.skavtko.entitete;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import si.skavtko.entitete.embeddable.NamedLink;

@Entity
public class Skupina {

    public Skupina() {
        this.povezave = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic(optional = false)
    private String ime;

    //TODO enum s tipi skuine, ce je treba kje opisat kakèen tip je, ni nujno

    private String opis;


    // TODO Nisem sigurn ali to dela, morem testirat
    @ElementCollection
    @CollectionTable(name = "povezave", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "povezave")
    private List<NamedLink> povezave;



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
    
}
