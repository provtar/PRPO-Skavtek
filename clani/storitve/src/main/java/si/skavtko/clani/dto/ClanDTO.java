package si.skavtko.clani.dto;

import si.skavtko.clani.entitete.Clan;

//TODO mu dodat email
public class ClanDTO {

    public ClanDTO(){}
    
    public ClanDTO(Long id, String ime, String priimek, String steg, String skavtskoIme) {
        this.id = id;
        this.ime = ime;
        this.priimek = priimek;
        this.steg = steg;
        this.skavtskoIme = skavtskoIme;
    }

    public ClanDTO(Clan clan){
        this.id = clan.getId();
        this.ime = clan.getIme();
        this.priimek = clan.getPriimek();
        this.steg = clan.getSteg();
        this.skavtskoIme = clan.getSkavtskoIme();
    }


    private Long id;

    private String ime;

    private String priimek;

    private String steg;

    private String skavtskoIme;

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

    public String getSkavtskoIme() {
        return skavtskoIme;
    }

    public void setSkavtskoIme(String skavtskoIme) {
        this.skavtskoIme = skavtskoIme;
    }

    @Override
    public String toString(){
        return "ClanDTO baby!\n";
    }

}
