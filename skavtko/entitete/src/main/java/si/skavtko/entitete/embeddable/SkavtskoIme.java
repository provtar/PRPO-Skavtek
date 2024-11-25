package si.skavtko.entitete.embeddable;

import javax.persistence.Embeddable;

@Embeddable
public class SkavtskoIme {
    private String lastnost;
    private String vod;

    
    public SkavtskoIme() {
    }

    public SkavtskoIme(String lastnost, String vod) {
        this.lastnost = lastnost;
        this.vod = vod;
    }
    
    public String getVod() {
        return vod;
    }
    public void setVod(String vod) {
        this.vod = vod;
    }
    public String getLastnost() {
        return lastnost;
    }
    public void setLastnost(String lastnost) {
        this.lastnost = lastnost;
    }
}
