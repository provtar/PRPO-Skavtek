package si.skavtko.prisotnosti.dto;

import si.skavtko.prisotnosti.entitete.enums.TipPrisotnosti;

public class PrisotnostPutDTO {
    public PrisotnostPutDTO() {
    }

    private Long id;

    private TipPrisotnosti prisotnost;

    private String opomba;

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
