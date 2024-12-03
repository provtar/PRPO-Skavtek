package si.skavtko.entitete;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Srecanje {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;

    @Basic(optional = false)
    private String ime;

    private LocalDateTime datumOd;

    private LocalDateTime datumDo;

    private String kraj;

    private String opis;
    
    //morda boljse ta podatek spravt v prisotnosti
    private Boolean belezenje = false;

    @JsonIgnore //ce to odstranis ima probleme s serializacijo, zakaj se to zgodi, ker klices getReference, zato ne dobis nazaj cele entitete
    @ManyToOne(fetch = FetchType.LAZY)
    private Skupina skupina;

    @JsonIgnore
    @OneToMany(mappedBy = "srecanje" , fetch = FetchType.LAZY)
    private Set<Prisotnost> prisotnosti;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getIme() {
        return ime;
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
    
    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Boolean getBelezenje() {
        return belezenje;
    }

    public void setBelezenje(Boolean belezenje) {
        this.belezenje = belezenje;
    }
    
    public Skupina getSkupina() {
        return skupina;
    }

    public void setSkupina(Skupina skupina) {
        this.skupina = skupina;
    }

    public Set<Prisotnost> getPrisotnosti() {
        return prisotnosti;
    }

    public void setPrisotnosti(Set<Prisotnost> prisotnosti) {
        this.prisotnosti = prisotnosti;
    }
}
