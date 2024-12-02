package si.skavtko.entitete;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import si.skavtko.entitete.embeddable.SkavtskoIme;
import si.skavtko.entitete.enums.UserRole;


//vse anotacije vezane s persistance layerjem se doda kasneje
//treba bo ponovno skonstruirati, da doda vse atribute
@Entity



@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(UserRole.Values.ADMIN)
@DiscriminatorColumn(name = "ROLE", discriminatorType = DiscriminatorType.STRING)

//TODO implement serializable
public class Clan implements Serializable{

    public Clan (){

    }

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;

    @Basic(optional = false)
    private String ime;
    @Basic(optional =  false)
    private String priimek;

    //TODO dodamo datum rojstva?

    //TODO spremenit v enum ali v eno novo podtabelo
    //@Enumerated(EnumType.STRING)
    private String steg;

    @Embedded
    private SkavtskoIme skavtskoIme;

    @Column(name = "ROLE", nullable = false, insertable = false, updatable = false) 
    @Enumerated(EnumType.STRING)
    public UserRole role;

    // TODO Dodaajat Relacije v katerih je Clan
    
    //@JsonbTransient
    //@JsonManagedReference(value = "clan-cs")
    @JsonIgnore
    @OneToMany(mappedBy = "clan", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ClanSkupina> skupine;

    @JsonIgnore
    @OneToMany(mappedBy = "clan", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Prisotnost> prisotnosti;

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


    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    

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
    
    
    public Set<ClanSkupina> getSkupine() {
        return skupine;
    }

    public void setSkupine(Set<ClanSkupina> skupine) {
        this.skupine = skupine;
    }

    public Set<Prisotnost> getPrisotnosti() {
        return prisotnosti;
    }

    public void setPrisotnosti(Set<Prisotnost> prisotnosti) {
        this.prisotnosti = prisotnosti;
    }
}