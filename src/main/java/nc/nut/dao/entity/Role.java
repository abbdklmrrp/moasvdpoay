package nc.nut.dao.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Rysakova Anna
 */
public enum Role implements GrantedAuthority {

    user,
    admin,
    manager,
    support;

    @Override
    public String getAuthority() {
        return name();
    }

}
