package nc.nut.service.interf;

import nc.nut.user.User;

/**
 * @author Rysakova Anna
 */
public interface UserService {
    User findByName(String name);
}
