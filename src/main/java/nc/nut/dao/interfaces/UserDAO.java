package nc.nut.dao.interfaces;


import nc.nut.dao.entity.User;

import java.util.List;

public interface UserDAO {

    boolean save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByPhone(String phone);

    List<User> getAll();

}
