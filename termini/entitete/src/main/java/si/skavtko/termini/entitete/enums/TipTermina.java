package si.skavtko.termini.entitete.enums;

public enum TipTermina {

    Prost (Values.PROS),
    Zaseden (Values.ZASE),
    Drugo (Values.DRUG);

    TipTermina(String role) {
        if (!this.name().equals(role))
        throw new IllegalArgumentException("Slabo definiran Termin, ta string ne obstaja.");
    }
    
    
    private String value;


    public String getValue() {
            return value;
    }


    public static class Values {
        public static final String PROS = "Prost";
        public static final String ZASE = "Zaseden";
        public static final String DRUG = "Drugo";
    }
}
