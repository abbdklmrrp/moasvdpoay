package nc.nut.security;

/**
 * @author Rysakova Anna
 */
public enum Authority implements AuthorityConstants {
    USER(USER_VALUE),
    ADMIN(ADMIN_VALUE),
    MANAGER(MANAGER_VALUE),
    SUPPORT(SUPPORT_VALUE);
    
    private final String id;
    
    Authority(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public static String[] valueStrings() {
        Authority[] values = values();
        String[] auths = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            auths[i] = values[i].id;
        }
        return auths;
    }
}
