package nc.nut.security;

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
}
