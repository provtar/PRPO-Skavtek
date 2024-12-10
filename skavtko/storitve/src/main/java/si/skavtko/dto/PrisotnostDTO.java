package si.skavtko.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import si.skavtko.entitete.Prisotnost;
import si.skavtko.entitete.enums.TipPrisotnosti;

@Schema(description = "DTO za prisotnosti, kdo bi si mislil...", name = "Prisotnost", example = "{\r\n" + //
        "        \"id\": 22,\r\n" + //
        "        \"prisotnost\": \"Prisoten\",\r\n" + //
        "        \"opomba\": Bil je navihan,\r\n" + //
        "        \"idClana\": 2,\r\n" + //
        "        \"imeClana\": \"Peter\",\r\n" + //
        "        \"priimekClana\": \"Pan\",\r\n" + //
        "        \"idSrecanja\": 9,\r\n" + //
        "        \"imeSrecanja\": \"Izlet na otok\"\r\n" + //
        "    }")
public class PrisotnostDTO {

    public PrisotnostDTO(Long id, TipPrisotnosti prisotnost, String opomba, Long idClana, String imeClana,
            String priimekClana, Long idSrecanja, String imeSrecanja) {
        this.id = id;
        this.prisotnost = prisotnost;
        this.opomba = opomba;
        this.idClana = idClana;
        this.imeClana = imeClana;
        this.priimekClana = priimekClana;
        this.idSrecanja = idSrecanja;
        this.imeSrecanja = imeSrecanja;
    }

    public PrisotnostDTO() {
    }

    public PrisotnostDTO(Prisotnost prisotnost) {
        this.id = prisotnost.getId();
        this.prisotnost = prisotnost.getPrisotnost();
        this.opomba = prisotnost.getOpomba();
        this.idClana = prisotnost.getClan().getId();
        this.imeClana = prisotnost.getClan().getIme();
        this.priimekClana = prisotnost.getClan().getPriimek();
        this.idSrecanja = prisotnost.getSrecanje().getId();
        this.imeSrecanja = prisotnost.getSrecanje().getIme();
    }

    
    private Long id;

    private TipPrisotnosti prisotnost;

    private String opomba;

    private Long idClana;

    private String imeClana;

    private String priimekClana;

    private Long idSrecanja;

    private String imeSrecanja;

    public String getImeSrecanja() {
        return imeSrecanja;
    }

    public void setImeSrecanja(String imeSrecanja) {
        this.imeSrecanja = imeSrecanja;
    }

    public Long getIdSrecanja() {
        return idSrecanja;
    }

    public void setIdSrecanja(Long idSrecanja) {
        this.idSrecanja = idSrecanja;
    }

    public String getPriimekClana() {
        return priimekClana;
    }

    public void setPriimekClana(String priimekClana) {
        this.priimekClana = priimekClana;
    }

    public String getImeClana() {
        return imeClana;
    }

    public void setImeClana(String imeClana) {
        this.imeClana = imeClana;
    }

    public Long getIdClana() {
        return idClana;
    }

    public void setIdClana(Long idClana) {
        this.idClana = idClana;
    }

    public String getOpomba() {
        return opomba;
    }

    public void setOpomba(String opomba) {
        this.opomba = opomba;
    }

    public TipPrisotnosti getPrisotnost() {
        return prisotnost;
    }

    public void setPrisotnost(TipPrisotnosti prisotnost) {
        this.prisotnost = prisotnost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
