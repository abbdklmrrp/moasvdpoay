package nc.nut.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Rysakova Anna
 */
public enum Role implements GrantedAuthority {

    USER,
    ADMIN,
    PMG,
    CSR;

    @Override
    public String getAuthority() {
        return name();
    }

}
