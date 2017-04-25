package nc.nut.service.impls;

import nc.nut.security.AuthorizedUser;
import nc.nut.service.interf.UserService;
import nc.nut.user.User;
import nc.nut.user.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Rysakova Anna
 * @author Dyrda Sergiy
 */
//@Service("userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDAO userDAO;

    @Override
    public User findByName(String name) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            System.out.println("WHAT A FUCK = loadUserByUsername - " + email);
            throw new UsernameNotFoundException("User " + email + " is not found");
        }

        return new AuthorizedUser(user);
        }
}
