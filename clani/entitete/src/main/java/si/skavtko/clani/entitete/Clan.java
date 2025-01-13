package si.skavtko.clani.entitete;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import si.skavtko.clani.entitete.enums.UserRole;


@Entity
@NamedQueries(value = {
    @NamedQuery(name = "Clan.fromEmail", query = "select c from Clan c where c.role = '" + UserRole.Values.ACTIVE + "' and c.email = :email"),
    @NamedQuery(name = "Clan.fromEmailInPassword", query = "select c from Clan c where c.role = '" + UserRole.Values.ACTIVE + "' and c.email = :email and c.password = :password"),
    @NamedQuery(name = "Varovanci.fromMaster", query = "select new si.skavtko.clani.dto.ClanDTO(c.id, c.ime, c.priimek, c.steg, c.skavtskoIme) from Clan c "+
        " where c.master.id = :masterId")

})
@Table(name = "clan")
public class Clan{

    public Clan (){
        this.varovanci = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;

    @Basic(optional = false)
    private String ime;
    @Basic(optional =  false)
    private String priimek;

    //TODO spremenit v eno novo podtabelo
    private String steg;

    private String skavtskoIme;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    public UserRole role;

    private String password;
    // POZOR, ce zelis pasivnega v aktivnega spremenit mora biti to true
    @Column(updatable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master")
    private Clan master;

    // TODO delete, pusti samo na drugi strani (master manyTo ONe del)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "master", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Clan> varovanci;

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
    public String getSkavtskoIme() {
        return skavtskoIme;
    }

    public void setSkavtskoIme(String  skavtskoIme) {
        this.skavtskoIme = skavtskoIme;
    }

    public Clan getMaster() {
        return master;
    }

    public void setMaster(Clan master) {
        this.master = master;
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

    public Set<Clan> getVarovanci() {
        return varovanci;
    }
}