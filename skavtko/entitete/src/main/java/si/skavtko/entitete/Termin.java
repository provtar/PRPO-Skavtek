package si.skavtko.entitete;


import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import si.skavtko.entitete.enums.TipTermina;

@Entity

@NamedQueries(value = {
    @NamedQuery(name = "Termini.fromOdDoClan",
    query = "select new si.skavtko.dto.TerminDTO(t.id, t.datumOd, t.datumDo, t.tip, t.clan) "+
    "from Termin t JOIN t.clan c ON (c.id = :clanId) "+
    "WHERE t.datumOd <= :datumDo AND t.datumDo >= :datumOd"),
    @NamedQuery(name = "Termini.fromOdDoClanKratko",
    query = "select new si.skavtko.dto.TerminKratkoDTO(t.id, t.datumOd, t.datumDo, t.tip) "+
    "from Termin t JOIN t.clan c ON (c.id = :clanId) "+
    "WHERE t.datumOd <= :datumDo AND t.datumDo >= :datumOd")
})

public class Termin {

    public Termin(LocalDateTime datumOd, LocalDateTime datumDo, Clan clan, TipTermina tip) {
        if (datumOd == null || datumDo == null || clan == null || tip == null) {
            throw new IllegalArgumentException("Podani morajo biti vsi parametri");
        }
        if (datumOd.isAfter(datumDo)) {
            throw new IllegalArgumentException("'datumOd' mora biti pred 'datumDo'.");
        }

        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.clan = clan;
        this.tip = tip;
    }

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;

    @Basic(optional = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clanId", referencedColumnName = "id")
    private Clan clan;
    
    @Basic(optional = false)
    private LocalDateTime datumDo;

    @Basic(optional = false)
    private LocalDateTime datumOd;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private TipTermina tip;

    public TipTermina getTip() {
        return tip;
    }

    public void setTip(TipTermina tip) {
        this.tip = tip;
    }

    public LocalDateTime getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDateTime datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDateTime getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDateTime datumDo) {
        this.datumDo = datumDo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }
}
