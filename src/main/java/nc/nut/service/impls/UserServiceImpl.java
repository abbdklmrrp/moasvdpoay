package nc.nut.service.impls;

import nc.nut.dao.UserDAO;
import nc.nut.security.AuthorizedUser;
import nc.nut.service.interf.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Rysakova Anna
 */
@Service("userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDAO userDAO;

    @Override
    public nc.nut.entity.User findByName(String name) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        nc.nut.entity.User user = userDAO.getByPhone(phone);
        if (user == null) {
            System.out.println("WHAT A FUCK = loadUserByUsername - " + phone);
            throw new UsernameNotFoundException("User " + phone + " is not found");
        }

        return new AuthorizedUser(user);
        }
}
