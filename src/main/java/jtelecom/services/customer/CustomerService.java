package jtelecom.services.customer;

import jtelecom.dao.customer.Customer;

/**
 * @author Moiseienko Petro
 * @since 19.05.2017.
 */
public interface CustomerService {

    /**
     * Method  checks that fields filled right,
     * checks that customer's name be unique
     * and saves customer
     *
     * @param customer customer to save
     * @return message about fail of the operation.
     * If operation success method returns null
     */
    String save(Customer customer);

    /**
     * Method checks that fields filled right,
     * checks that customer's name be unique
     * and saves customer for residential user
     * and gets his id
     *
     * @param customer this customer
     * @return id of the customer. If operation failed returns null
     */
    Integer saveForUser(Customer customer);

}
