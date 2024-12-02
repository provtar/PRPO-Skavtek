package si.skavtko.dto;

import si.skavtko.entitete.embeddable.SkavtskoIme;
import si.skavtko.entitete.enums.UserRole;


public class ClanDTO {

    public ClanDTO(){}

    public ClanDTO(Long id, String ime, String priimek, String steg) {
        this.id = id;
        this.ime = ime;
        this.priimek = priimek;
        this.steg = steg;
    }

    private Long id;

    private String ime;

    private String priimek;

    private String steg;


    public String getSteg() {
        return steg;
    }

    public void setSteg(String steg) {
        this.steg = steg;
    }

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

    @Override
    public String toString(){
        return "ClanDTO baby!\n";
    }

}
