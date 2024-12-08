package si.skavtko.dto;

import java.time.LocalDateTime;

import si.skavtko.entitete.Srecanje;

public class SrecanjeDTO {

    public SrecanjeDTO() {
    }

    public SrecanjeDTO(Srecanje srecanje) {
        this.id = srecanje.getId();
        this.ime= srecanje.getIme();
        this.datumOd = srecanje.getDatumOd();
        this.datumDo = srecanje.getDatumDo();
        this.kraj = srecanje.getKraj();
        this.opis = srecanje.getOpis();
        this.belezenje = srecanje.getBelezenje();
        this.idSkupine= srecanje.getSkupina().getId();
    }

    public SrecanjeDTO(Long id, String ime, LocalDateTime datumOd, LocalDateTime datumDo, String kraj, String opis, Boolean belezenje,
            Long idSkupine) {
        this.id = id;
        this.ime = ime;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.kraj = kraj;
        this.opis = opis;
        this.belezenje = belezenje;
        this.idSkupine = idSkupine;
    }

    private Long id;

    private String ime;

    private LocalDateTime datumOd;

    private LocalDateTime datumDo;

    private String kraj;

    private String opis;

    private Boolean belezenje;

    private Long idSkupine;

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
