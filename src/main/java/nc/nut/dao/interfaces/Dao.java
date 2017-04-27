package nc.nut.dao.interfaces;

/**
 * Created by Yuliya Pedash, Anna on 27.04.2017.
 */
public interface Dao<T> {
    T getById(int id);

    boolean update(T object);

    boolean save(T object);

    boolean delete(T object);
}
