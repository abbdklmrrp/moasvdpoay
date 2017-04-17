package nc.nut.security;

import com.google.common.base.Preconditions;
import nc.nut.domain.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsServiceProviderImpl implements UserDetailsServiceProvider {
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    UserService userService;
    
    @Override
    public UserDetails provide(String username) throws UsernameNotFoundException {
        Preconditions.checkArgument(username != null && !username.isEmpty());
        
        logger.info("Providing user details for {}", username);
        
        nc.nut.domain.User byName = userService.findByName(username);
        
        if (byName == null) {
            return null;
        }
        
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(byName.getAuthorities());
        
        User user = new User(username, byName.getPassword(), auth);
        
        logger.info("Something found for {}", username);
        
        return user;
    }
    
}
