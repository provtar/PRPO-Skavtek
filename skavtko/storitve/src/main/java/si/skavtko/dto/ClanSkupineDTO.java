package si.skavtko.dto;


//minimalne informacije o clanu skupine, opusti se nekaj podrobonsti o clanu
//rabi se ga samo, ko se vraca clane skupine
// TODO se mu bo dodalo vlogo v skupini
public class ClanSkupineDTO {

    private Long clanId;

    private  String ime;

    private String priimek;

    private String steg;

    public ClanSkupineDTO() {
    }

    public ClanSkupineDTO(Long id, String ime, String priimek, String steg) {
        this.clanId = id;
        this.ime = ime;
        this.priimek = priimek;
        this.steg = steg;
    }

    public Long getId() {
        return clanId;
    }

    public void setId(Long id) {
        this.clanId = id;
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
