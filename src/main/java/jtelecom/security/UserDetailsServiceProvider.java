package jtelecom.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Anna Rysakova
 */
public interface UserDetailsServiceProvider {
    UserDetails provide(String username) throws UsernameNotFoundException;
}

