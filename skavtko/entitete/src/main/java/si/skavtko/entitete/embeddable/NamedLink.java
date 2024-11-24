package si.skavtko.entitete.embeddable;

import javax.persistence.Embeddable;

@Embeddable
public class NamedLink {
    
    public NamedLink(String link, String name) {
        this.link = link;
        this.name = name;
    }

    private String link;
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
