package si.skavtko.termini.dto;

import java.time.LocalDateTime;

import si.skavtko.termini.entitete.enums.TipTermina;

public class TerminKratkoDTO {

    public TerminKratkoDTO(Long id, LocalDateTime datumOd, LocalDateTime datumDo, TipTermina tip) {
        this.id = id;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.tip = tip;
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

}
