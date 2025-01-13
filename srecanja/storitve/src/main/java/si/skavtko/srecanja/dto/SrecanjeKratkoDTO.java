package si.skavtko.srecanja.dto;

import java.time.LocalDateTime;

public class SrecanjeKratkoDTO {
    
    
        public SrecanjeKratkoDTO() {
        }
    
        public SrecanjeKratkoDTO(Long id, String ime, LocalDateTime datumOd, LocalDateTime datumDo, String kraj, Long idSkupine, String imeSkupine) {
            this.id = id;
            this.ime = ime;
            this.datumOd = datumOd;
            this.datumDo = datumDo;
            this.kraj = kraj;
            this.idSkupine = idSkupine;
            this.imeSkupine = imeSkupine;
        }
    
        private Long id;
    
        private String ime;
    
        private LocalDateTime datumOd;
    
        private LocalDateTime datumDo;
    
        private String kraj;

        private Long idSkupine;

        private String imeSkupine;
    
        public String getImeSkupine() {
            return imeSkupine;
        }

        public void setImeSkupine(String imeSkupine) {
            this.imeSkupine = imeSkupine;
        }

        public Long getIdSkupine() {
            return idSkupine;
        }
    
        public void setIdSkupine(Long idSkupine) {
            this.idSkupine = idSkupine;
        }

        public String getKraj() {
            return kraj;
        }
    
        public void setKraj(String kraj) {
            this.kraj = kraj;
        }
    
        public LocalDateTime getDatumDo() {
            return datumDo;
        }
    
        public void setDatumDo(LocalDateTime datumDo) {
            this.datumDo = datumDo;
        }
    
        public LocalDateTime getDatumOd() {
            return datumOd;
        }
    
        public void setDatumOd(LocalDateTime datumOd) {
            this.datumOd = datumOd;
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
