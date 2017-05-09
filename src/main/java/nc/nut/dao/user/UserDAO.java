package nc.nut.dao.user;

import nc.nut.dao.interfaces.Dao;

import java.util.List;

/**
 * Created by Rysakova Anna on 20.04.2017.
 */
public interface UserDAO extends Dao<User> {
    User findByUsername(String username);

    List<User> getAll();

    List<User> getAllClients();

    Integer findRole(String name);

    Integer findPlaceId(String name);

    User findByEmail(String email);

    User getUserByPhone(String phone);

    User getUserById(Integer id);

    boolean update(User user);

    Integer getCountUsersWithSearch(String search);

    List<User> getLimitedQuantityUsers(int start, int length,String sort, String search);

}