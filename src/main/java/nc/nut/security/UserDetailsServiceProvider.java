package nc.nut.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Rysakova Anna
 */
public interface UserDetailsServiceProvider {

    UserDetails provide(String username) throws UsernameNotFoundException;
}
