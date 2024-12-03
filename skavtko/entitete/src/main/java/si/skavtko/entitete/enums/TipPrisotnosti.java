package si.skavtko.entitete.enums;

public enum TipPrisotnosti {

    Prisoten (Values.PRST),
    Odsoten (Values.ODST),
    Online (Values.ONLN),
    Pozen (Values.POZN);

    TipPrisotnosti(String role) {
        if (!this.name().equals(role))
        throw new IllegalArgumentException("Slabo definirana prisotnost, ta string ne obstaja.");
    }
    
    
    private String value;


public String getValue() {
        return value;
    }


public static class Values {
    public static final String PRST = "Prisoten";
    public static final String ODST = "Odsoten";
    public static final String ONLN = "Online";
    public static final String POZN = "Pozen";
}   

}
