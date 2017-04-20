package nc.nut.user;

/**
 * Created by Anna on 20.04.2017.
 */
public interface UserDAO {
    User findByName(String name);
}
