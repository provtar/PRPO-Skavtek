package si.skavtko.dto;

import java.time.LocalDateTime;

import si.skavtko.entitete.Clan;
import si.skavtko.entitete.Termin;
import si.skavtko.entitete.enums.TipTermina;

public class TerminDTO {

    public TerminDTO(Termin termin){
        this.id = termin.getId();
        this.datumOd = termin.getDatumOd();
        this.datumDo = termin.getDatumDo();
        this.tip = termin.getTip();
        this.clan = termin.getClan();
    }

    public TerminDTO(Long id, LocalDateTime datumOd, LocalDateTime datumDo, TipTermina tip, Clan clan) {
        this.id = id;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.tip = tip;
        this.clan = clan;
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

    private Clan clan;

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

}
