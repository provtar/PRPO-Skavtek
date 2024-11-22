package si.skavtko.dto;


import si.skavtko.entitete.Clan;
import si.skavtko.entitete.Clan.UserRole;

public class ClanDTO {
    
    public ClanDTO(){}
    

    public ClanDTO(Long id, String ime, String priimek, UserRole role) {
        this.id = id;
        this.ime = ime;
        this.priimek = priimek;
        this.role = role;
    }

    public ClanDTO(Clan clan){
        this.id = clan.getId();
        this.ime = clan.getIme();
        this.priimek = clan.getPriimek();
        this.role = clan.getRole();
    }

    public Clan toClan(){
        Clan clan = new Clan();
        clan.setId(id);
        clan.setIme(ime);
        clan.setPriimek(priimek);
        clan.setRole(role);
        return clan;
    }

    private Long id;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    private String ime;
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    private String priimek;

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    //zacasna resitev, boljse implementirat kasn enum al podobno
    //lih bi ločil na active al passive
    private UserRole role = UserRole.PASSIVE;
    public UserRole getRole() {
        return role;
    }


    public void setRole(UserRole role) {
        this.role = role;
    }


    //dodatne lastnosti: uporabnisko ime, datum rojstva, kasneje, po potrebi

    

}
