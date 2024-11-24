package si.skavtko.entitete;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import si.skavtko.entitete.embeddable.SkavtskoIme;
import si.skavtko.entitete.enums.UserRole;


//vse anotacije vezane s persistance layerjem se doda kasneje
//treba bo ponovno skonstruirati, da doda vse atribute
@Entity
@Table(name = "Clani")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(UserRole.Values.ADMIN)
@DiscriminatorColumn(name = "ROLE", discriminatorType = DiscriminatorType.STRING)
public class Clan {

    public Clan (){

    }

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long user_id;

    @Basic(optional = false)
    private String ime;
    @Basic(optional =  false)
    private String priimek;

    //TODO dodamo datum rojstva?

    //TODO spremenit v enum
    //@Enumerated(EnumType.STRING)
    private String steg;

    @Embedded
    private SkavtskoIme skavtskoIme;

    // @Column(name = "ROLE", nullable = false, insertable = false, updatable = false) 
    // @Enumerated(EnumType.STRING)
    // public UserRole role;

    // TODO Dodaajat Relacije v katerih je Clan
    @OneToMany(mappedBy = "clan", orphanRemoval = true)
    Set<ClanSkupina> skupine;

    public Long getId() {
        return user_id;
    }

    public void setId(Long id) {
        this.user_id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }


    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    // public UserRole getRole() {
    //     return role;
    // }

    // public void setRole(UserRole role) {
    //     this.role = role;
    // }
    

    public String getSteg() {
        return steg;
    }

    public void setSteg(String steg) {
        this.steg = steg;
    }
    public SkavtskoIme getSkavtskoIme() {
        return skavtskoIme;
    }

    public void setSkavtskoIme(SkavtskoIme skavtskoIme) {
        this.skavtskoIme = skavtskoIme;
    }
}
