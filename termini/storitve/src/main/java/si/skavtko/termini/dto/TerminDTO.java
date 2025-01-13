package si.skavtko.termini.dto;

import java.time.LocalDateTime;

import si.skavtko.termini.entitete.Termin;
import si.skavtko.termini.entitete.enums.TipTermina;

public class TerminDTO {

    public TerminDTO() {}

    public TerminDTO(Termin termin){
        this.id = termin.getId();
        this.datumOd = termin.getDatumOd();
        this.datumDo = termin.getDatumDo();
        this.tip = termin.getTip();
        this.clanId = termin.getClanId();
    }

    public TerminDTO(Long id, LocalDateTime datumOd, LocalDateTime datumDo, TipTermina tip, Long clanId) {
        this.id = id;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.tip = tip;
        this.clanId = clanId;
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

    private Long clanId;

    public Long getClanId() {
        return clanId;
    }

    public void setClanId(Long clanId) {
        this.clanId = clanId;
    }

}
