package si.skavtko.skupine.storitve.dto;

//rabi se za vizualizacijo skupin v katerih je clan
//trenutno samo id in ime skupine, lahko se razsiri s kratkim opisom
public class SkupinaClanaDTO {
    
    private Long id;

    private String ime;
    
    public SkupinaClanaDTO() {
    }

    public SkupinaClanaDTO(Long id, String ime) {
        this.id = id;
        this.ime = ime;
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
