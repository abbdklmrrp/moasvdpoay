package nc.nut.dao;


import nc.nut.entity.User;

import java.util.List;

public interface UserDAO {

    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByPhone(String phone);

    List<User> getAll();

}
