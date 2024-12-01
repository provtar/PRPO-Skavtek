package si.skavtko.entitete.enums;

public enum TipPrisotnosti {

    PRST (Values.PRST),
    ODST (Values.ODST),
    ONLN (Values.ONLN),
    POZN (Values.POZN);

    TipPrisotnosti(String role) {
        if (!this.name().equals(role))
        throw new IllegalArgumentException("Slabo definiran role, ta string ne obstaja.");
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
