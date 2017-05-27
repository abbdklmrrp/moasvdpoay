package jtelecom.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Anna Rysakova
 */
public interface UserDetailsServiceProvider {
    /**
     * This method  return <code>UserDetails</code> object that contains info about current user :
     * <code>username</code>, <code>password</code>, <code>authority</code>.
     *
     * @param username username the username identifying the user whose data is required
     * @return <code>UserDetails</code> with : <code>username</code>,
     * <code>password</code>, <code>authority</code>
     * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority
     */
    UserDetails provide(String username) throws UsernameNotFoundException;
}

