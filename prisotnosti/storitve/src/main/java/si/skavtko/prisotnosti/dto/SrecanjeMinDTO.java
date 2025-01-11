package si.skavtko.prisotnosti.dto;

public class SrecanjeMinDTO {

    public SrecanjeMinDTO() {
    }

    private Long id;
    private String ime;
    private Long skupinaId;
    private Boolean belezenje;

    public Boolean getBelezenje() {
        return belezenje;
    }

    public void setBelezenje(Boolean belezenje) {
        this.belezenje = belezenje;
    }

    public Long getSkupinaId() {
        return skupinaId;
    }

    public void setSkupinaId(Long skupinaId) {
        this.skupinaId = skupinaId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
