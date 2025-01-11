package si.skavtko.skupine.storitve.dto;

import si.skavtko.skupine.entitete.Skupina;

public class SkupinaMinDTO {

    private static Integer maxLength = 25;

    private Long id;
    private String ime;
    private String opis;

    public SkupinaMinDTO() {
    }

    public SkupinaMinDTO(Skupina skupina) {
        this.id = skupina.getId();
        this.ime = skupina.getIme();
        if(skupina.getOpis() != null && skupina.getOpis().length() >= maxLength){
            opis = skupina.getOpis().substring(0, maxLength) + "...";
        }else{
            this.opis = skupina.getOpis();
        }
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
