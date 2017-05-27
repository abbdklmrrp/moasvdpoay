package jtelecom.security;

/**
 * @author Anna Rysakova
 */
public enum Authority {
    ADMIN("ADMIN"),
    PMG("PMG"),
    CSR("CSR"),
    RESIDENTIAL("RESIDENTIAL"),
    BUSINESS("BUSINESS"),
    EMPLOYEE("EMPLOYEE");

    private final String auth;

    Authority(String auth) {
        this.auth = auth;
    }

    public static String[] valueStrings() {
        Authority[] values = values();
        String[] auths = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            auths[i] = values[i].auth.toLowerCase();
        }
        return auths;
    }
}
