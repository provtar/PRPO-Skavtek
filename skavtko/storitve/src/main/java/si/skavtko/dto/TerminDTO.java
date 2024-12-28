package si.skavtko.dto;

import java.time.LocalDateTime;

import si.skavtko.entitete.enums.TipTermina;

public class TerminDTO {

    public TerminDTO(Long id, LocalDateTime datumOd, LocalDateTime datumDo, TipTermina tip, String ime, String priimek) {
        this.id = id;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.tip = tip;
        this.ime = ime;
        this.priimek = priimek;
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private LocalDateTime datumOd;

    public LocalDateTime getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDateTime datumOd) {
        this.datumOd = datumOd;
    }

    private LocalDateTime datumDo;

    public LocalDateTime getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDateTime datumDo) {
        this.datumDo = datumDo;
    }

    private TipTermina tip;

    public TipTermina getTip() {
        return tip;
    }

    public void setTip(TipTermina tip) {
        this.tip = tip;
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

}
