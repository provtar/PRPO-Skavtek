package si.skavtko.dto;

public class SkupinaMinDTO {

    private Long id;
    private String ime;
    private String opis;

    public SkupinaMinDTO() {
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
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
