package nc.nut.dao;

/**
 * Created by Yuliya Pedash on 26.04.2017.
 */
public interface Dao<T> {
    T save(T product);

    boolean delete(T product);

    T getById(int id);
}
