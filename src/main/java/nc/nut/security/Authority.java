package nc.nut.security;

/**
 * @author Rysakova Anna
 */
public enum Authority {
    //    USER("USER"),
//    ADMIN("ADMIN"),
//    PMG("PMG"),
//    CSR("CSR");
    ADMIN("ADMIN"),
    PMG("PMG"),
    CSR("CSR"),
    INDIVIDUAL("INDIVIDUAL"),
    LEGAL("LEGAL"),
    EMPLOYEE("EMPLOYEE");

    private final String auth;


    Authority(String auth) {
        this.auth = auth;
    }

    public static String[] valueStrings() {
        Authority[] values = values();
        String[] auths = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            auths[i] = values[i].auth;
        }
        return auths;
    }

    public String getAuth() {
        return auth;
    }
}
