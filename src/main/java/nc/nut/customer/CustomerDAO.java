package nc.nut.customer;

/**
 * @author Moiseienko Petro
 * @since 24.04.2017.
 */
public interface CustomerDAO {
    Customer checkCustomer(String name, String secretKey);
}
