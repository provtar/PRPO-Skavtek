package si.skavtko.dto;

import java.time.LocalDateTime;

public class SrecanjeVremeDTO {

    public SrecanjeVremeDTO() {
    }

    public SrecanjeVremeDTO(SrecanjeDTO srecanje, String data) {
        this.id = srecanje.getId();
        this.ime= srecanje.getIme();
        this.datumOd = srecanje.getDatumOd();
        this.datumDo = srecanje.getDatumDo();
        this.kraj = srecanje.getKraj();
        this.opis = srecanje.getOpis();
        this.belezenje = srecanje.getBelezenje();
        this.idSkupine= srecanje.getId();
        this.imeSkupine = srecanje.getIme();
        String[] parts = data.split(";");
        this.temperatura = Float.parseFloat(parts[0]);
        this.padavine = Float.parseFloat(parts[1]);
    }

    private Long id;

    private String ime;

    private LocalDateTime datumOd;

    private LocalDateTime datumDo;

    private String kraj;

    private String opis;

    private Boolean belezenje;

    private Long idSkupine;

    private String imeSkupine;

    private Float temperatura;

    private Float padavine;

    public Float getPadavine() {
        return padavine;
    }

    public void setPadavine(Float padavine) {
        this.padavine = padavine;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public String getImeSkupine() {
        return imeSkupine;
    }

    public void setImeSkupine(String imeSkupine) {
        this.imeSkupine = imeSkupine;
    }

    public Long getIdSkupine() {
        return idSkupine;
    }

    public void setIdSkupine(Long idSkupine) {
        this.idSkupine = idSkupine;
    }

    public Boolean getBelezenje() {
        return belezenje;
    }

    public void setBelezenje(Boolean belezenje) {
        this.belezenje = belezenje;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public LocalDateTime getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDateTime datumDo) {
        this.datumDo = datumDo;
    }

    public LocalDateTime getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDateTime datumOd) {
        this.datumOd = datumOd;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
