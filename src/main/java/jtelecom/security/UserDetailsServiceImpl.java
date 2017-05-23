package jtelecom.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Rysakova Anna
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserDetailsServiceProvider userDetailsServiceProvider;

    /**
     * This method search user by his username. Use <code>UserDetails</code> for get user from database
     *
     * @param username the username identifying the user whose data is required
     * @return user record
     * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("Bad username: " + username);
        }

        UserDetails provided = userDetailsServiceProvider.provide(username);

        if (provided == null) {
            throw new UsernameNotFoundException("Nothing found by: " + username);
        }

        return new User(provided.getUsername(), provided.getPassword(), provided.getAuthorities());
    }
}
