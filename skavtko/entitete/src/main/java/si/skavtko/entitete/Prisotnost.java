package si.skavtko.entitete;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import si.skavtko.entitete.enums.TipPrisotnosti;

@Entity
public class Prisotnost {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipPrisotnosti prisotnost;

    private String opomba;

    @ManyToOne(fetch = FetchType.LAZY)
    Srecanje srecanje;

    @ManyToOne(fetch = FetchType.LAZY)
    Clan clan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipPrisotnosti getPrisotnost() {
        return prisotnost;
    }

    public void setPrisotnost(TipPrisotnosti prisotnost) {
        this.prisotnost = prisotnost;
    }

    public String getOpomba() {
        return opomba;
    }

    public void setOpomba(String opomba) {
        this.opomba = opomba;
    }

    public Srecanje getSrecanje() {
        return srecanje;
    }

    public void setSrecanje(Srecanje srecanje) {
        this.srecanje = srecanje;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }


}
