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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import si.skavtko.entitete.embeddable.SkavtskoIme;
import si.skavtko.entitete.enums.UserRole;


@Entity

@Table(name = "clan")
public class Clan{

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
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Clan master;

    
    private String username;
    @JsonIgnore
    private String password;
    @Column(updatable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "master", orphanRemoval = true)
    private Set<Clan> varovanci;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}