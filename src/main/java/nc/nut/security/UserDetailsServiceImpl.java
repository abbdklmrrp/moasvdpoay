package nc.nut.security;

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
    UserDetailsServiceProvider userDetailsServiceProvider;
    
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
