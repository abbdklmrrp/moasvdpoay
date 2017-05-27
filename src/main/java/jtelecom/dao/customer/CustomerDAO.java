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

    /**
     * Method finds all customers with type business
     *
     * @return list of the customers
     */
    List<Customer> getAllBusinessCustomers();

    List<Customer> getLimitedQuantityCustomer(int start, int length, String sort, String search);

    Integer getCountCustomersWithSearch(String search);

    /**
     * Method checks is customer's name is unique
     *
     * @param customer this customer
     * @return <code>true</code> if is unique ,<code>false</code> otherwise
     */
    boolean isUnique(Customer customer);

    /**
     * Method saves customer
     *
     * @param customer this customer
     * @return id of the saved customer
     */
    Integer saveCustomer(Customer customer);
}
