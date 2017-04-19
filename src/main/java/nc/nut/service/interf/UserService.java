package nc.nut.service.interf;

import nc.nut.dao.entity.User;

/**
 * @author Rysakova Anna
 */
public interface UserService {
    User findByName(String name);
}
