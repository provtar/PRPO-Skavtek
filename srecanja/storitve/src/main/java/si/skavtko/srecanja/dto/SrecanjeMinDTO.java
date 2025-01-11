package si.skavtko.srecanja.dto;

import si.skavtko.srecanja.entitete.Srecanje;

public class SrecanjeMinDTO {
   
        public SrecanjeMinDTO() {
        }
    
        public SrecanjeMinDTO(Srecanje srecanje) {
            this.id = srecanje.getId();
            this.ime = srecanje.getIme();
            this.skupinaId = srecanje.getSkupina().getId();
            this.belezenje = srecanje.getBelezenje();
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
