package si.skavtko.entitete;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


//vse anotacije vezane s persistance layerjem se doda kasneje
//treba bo ponovno skonstruirati, da doda vse atribute
@Entity
@Table(name = "Clani")
public class Clan {
    public enum UserRole {
        PASSIVE,
        USER,
        ADMIN,
    }

    public Clan (){

    }

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long user_id;

    private String ime;
    private String priimek;

    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

    
    
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    //dodatne lastnosti: uporabnisko ime, datum rojstva, kasneje, po potrebi

    
    //se doda se @override toString funkcije
}
