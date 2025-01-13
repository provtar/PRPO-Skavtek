package si.skavtko.skupine.storitve.dto;

import java.util.Collection;
import java.util.List;

import si.skavtko.skupine.entitete.Skupina;
import si.skavtko.skupine.entitete.embeddable.NamedLink;

//vsi podatki, ki so vazni za skupino, za zdaj ime, opis in pametni linki
public class SkupinaDTO {

    private Long id;

    private String ime;

    private String opis;

    private List<NamedLink> povezave;

    public SkupinaDTO() {
    }

    public SkupinaDTO(Skupina skupina) {
        this.id = skupina.getId();
        this.ime = skupina.getIme();
        this.opis = skupina.getOpis();
        this.povezave = skupina.getPovezave();
    }

    public SkupinaDTO(Long id, String ime, String opis, Collection<NamedLink> povezave) {
        this.id = id;
        this.ime = ime;
        this.opis = opis;
        this.povezave = (List<NamedLink>) povezave;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public List<NamedLink> getPovezave() {
        return povezave;
    }

    public void setPovezave(List<NamedLink> povezave) {
        this.povezave = povezave;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
