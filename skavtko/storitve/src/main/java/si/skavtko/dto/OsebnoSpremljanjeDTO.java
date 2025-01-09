package si.skavtko.dto;

import java.time.LocalDateTime;

import si.skavtko.entitete.OsebnoSpremljanje;

public class OsebnoSpremljanjeDTO {

    public OsebnoSpremljanjeDTO(OsebnoSpremljanje osebnoSpremljanje) {
        this.id = osebnoSpremljanje.getId();
        this.datum = osebnoSpremljanje.getDatum();
        this.naslov = osebnoSpremljanje.getNaslov();
        this.vsebina = osebnoSpremljanje.getVsebina();
        this.clanId = osebnoSpremljanje.getClan().getId();
    }

    public OsebnoSpremljanjeDTO(Long id, LocalDateTime datum, String naslov, String vsebina, Long clanId) {
        this.id = id;
        this.datum = datum;
        this.naslov = naslov;
        this.vsebina = vsebina;
        this.clanId = clanId;
    }

    public OsebnoSpremljanjeDTO() {
    }

    Long id;
    LocalDateTime datum;
    String naslov;
    String vsebina;
    Long clanId;

    public Long getClanId() {
        return clanId;
    }

    public void setClanId(Long clanId) {
        this.clanId = clanId;
    }

    
    public String getVsebina() {
        return vsebina;
    }

    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }


    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }
}
