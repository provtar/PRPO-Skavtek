package si.skavtko.entitete.enums;

public enum UserRole {
        PASSIVE (Values.PASSIVE),
        ACTIVE (Values.ACTIVE),
        ADMIN (Values.ADMIN);

        UserRole(String role) {
        //     if (!this.name().equals(role))
        // throw new IllegalArgumentException("Slabo definiran role, ta string ne obstaja.");
        }
        
        
    private String value;


    public String getValue() {
            return value;
        }


    public static class Values {
        public static final String PASSIVE = "PASSIVE";
        public static final String ACTIVE = "ACTIVE";
        public static final String ADMIN = "ADMIN";
    }   
    
}
