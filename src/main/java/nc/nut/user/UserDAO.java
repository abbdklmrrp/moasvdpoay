package nc.nut.user;

import java.util.List;

/**
 * Created by Anna on 20.04.2017.
 */
public interface UserDAO {
    User findByUsername(String username);

    boolean save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    List<User> getAll();

    List<User> getAllClient();

    Integer findRole(String name);

    Integer findPlaceId(String name);
}
