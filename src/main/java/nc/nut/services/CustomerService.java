package nc.nut.services;

import nc.nut.dao.customer.Customer;
import nc.nut.dao.customer.CustomerDAO;
import nc.nut.dao.entity.CustomerType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Moiseienko Petro
 * @since 12.05.2017.
 */
@Service
public class CustomerService {
    @Resource
    private CustomerDAO customerDAO;


    public String save(Customer customer) {
        String message = validateCustomer(customer).toString();
        if (message.isEmpty()) {
            boolean unique = customerDAO.isUnique(customer);
            if (unique) {
                boolean success = customerDAO.save(customer);
                if (success) {
                    return null;
                } else {
                    return "Creating failed. Please, try again";
                }
            } else {
                if (customer.getCustomerType() == CustomerType.Residential) {
                    return "Email isn't unique";
                } else {
                    return "Customer isn't unique";
                }
            }
        } else {
            return message;
        }
    }

    private StringBuilder validateCustomer(Customer customer) {
        String name = customer.getName();
        StringBuilder message = new StringBuilder();
        if (name == null || name.isEmpty()) {
            if (customer.getCustomerType() == CustomerType.Residential) {
                message.append("Email ");
            } else if (customer.getCustomerType() == CustomerType.Business) {
                message.append("Customer name ");
            }
            message.append("is empty;");
        }
        if (customer.getCustomerType() == CustomerType.Business) {
            if (customer.getSecretKey() == null || customer.getSecretKey().isEmpty()) {
                message.append("Secret key is empty");
            }
        }
        return message;
    }
}
