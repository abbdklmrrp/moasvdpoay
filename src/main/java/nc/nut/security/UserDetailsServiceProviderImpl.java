package nc.nut.security;

import com.google.common.base.Preconditions;
import nc.nut.user.UserDAO;
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

/**
 * @author Rysakova Anna
 */
@Service
public class UserDetailsServiceProviderImpl implements UserDetailsServiceProvider {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserDAO userDAO;

    @Override
    public UserDetails provide(String username) throws UsernameNotFoundException {
        Preconditions.checkArgument(username != null && !username.isEmpty());

        logger.info("Providing user details for {}", username);

        nc.nut.user.User findUser = userDAO.findByUsername(username);

        if (findUser == null) {
            return null;
        }

        List<GrantedAuthority> auth = AuthorityUtils.createAuthorityList(findUser.getAuthorities());

        User user = new User(username, findUser.getPassword(), auth);

        logger.info("Something found for {}", username);

        return user;
    }

}
