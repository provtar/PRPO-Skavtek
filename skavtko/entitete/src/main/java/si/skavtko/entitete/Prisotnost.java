package si.skavtko.entitete;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import si.skavtko.entitete.enums.TipPrisotnosti;

@Entity
@NamedQueries({
    @NamedQuery(name = "Prisotnosti.fromSrecanje",
    query = "select new si.skavtko.dto.PrisotnostDTO(p.id, p.prisotnost, p.opomba, p.clan.id, p.clan.ime, p.clan.priimek, p.srecanje.id, p.srecanje.ime) "+
    " from Prisotnost p join p.srecanje s on s.id = :srecanjeId"),
    @NamedQuery(name = "Prisotnosti.fromClanInSkupina",
    query = "select new si.skavtko.dto.PrisotnostDTO(p.id, p.prisotnost, p.opomba, p.clan.id, p.clan.ime, p.clan.priimek, p.srecanje.id, p.srecanje.ime) "+
    " from Prisotnost p join p.clan c on c.id = :clanId join p.srecanje s on (:skupinaId is null or s.skupina.id = :skupinaId)"
    )
})
public class Prisotnost {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipPrisotnosti prisotnost;

    private String opomba;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Srecanje srecanje;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
