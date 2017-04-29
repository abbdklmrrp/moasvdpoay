package nc.nut.dao.user;

import nc.nut.dao.interfaces.Dao;

import java.util.List;

/**
 * Created by Rysakova Anna on 20.04.2017.
 */
public interface UserDAO extends Dao<User> {
    User findByUsername(String username);

    List<User> getAll();

    List<User> getAllClient();

    Integer findRole(String name);

    Integer findPlaceId(String name);

    User findByEmail(String email);

}