package si.skavtko.clani.dto;

import si.skavtko.clani.entitete.Clan;

public class ClanMinDTO {
    
    private Long id;

    private  String ime;

    private String priimek;

    private String steg;

    public ClanMinDTO() {
    }

    public ClanMinDTO(Clan clan) {
        this.id = clan.getId();
        this.ime = clan.getIme();
        this.priimek = clan.getPriimek();
        this.steg = clan.getSteg();
    }

    public ClanMinDTO(Long id, String ime, String priimek, String steg) {
        this.id = id;
        this.ime = ime;
        this.priimek = priimek;
        this.steg = steg;
    }
    
    public Long getId() {
        return id;
    }

    public void setClanId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getSteg() {
        return steg;
    }

    public void setSteg(String steg) {
        this.steg = steg;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

}
