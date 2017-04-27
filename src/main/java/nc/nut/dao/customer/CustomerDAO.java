package nc.nut.dao.customer;

import nc.nut.dao.interfaces.Dao;
import nc.nut.dao.user.User;

import java.util.List;

/**
 * @author Moiseienko Petro, Alistratenko Nikita
 * @since 24.04.2017.
 */
public interface CustomerDAO extends Dao<Customer> {
    Customer checkCustomer(String name, String secretKey);

    boolean changeSecretKey(int customerId, String newSecretKey);

    boolean changeInvoice(int customerId, int newInvoice);

    boolean changeName(int customerId, String newName);

    List<User> getAllUsers(int customerId);

}
