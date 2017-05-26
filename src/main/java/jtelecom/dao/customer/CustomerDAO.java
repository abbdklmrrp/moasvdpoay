package jtelecom.dao.customer;

import jtelecom.dao.interfaces.DAO;
import jtelecom.dao.user.User;

import java.util.List;

/**
 * @author Moiseienko Petro, Alistratenko Nikita
 * @since 24.04.2017.
 */
public interface CustomerDAO extends DAO<Customer> {
    Integer getCustomerId(String name, String secretKey);

    boolean changeSecretKey(int customerId, String newSecretKey);

    boolean changeInvoice(int customerId, int newInvoice);

    boolean changeName(int customerId, String newName);

    List<User> getAllUsers(int customerId);

    List<Customer> getAllBusinessCustomers();

    List<Customer> getLimitedQuantityCustomer(int start, int length, String sort, String search);

    Integer getCountCustomersWithSearch(String search);

    boolean isUnique(Customer customer);

    Integer saveCustomer(Customer customer);
}
