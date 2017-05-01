package nc.nut.dao.customer;

import nc.nut.dao.entity.CustomerType;

/**
 * @author Moiseienko Petro , Alistratenko Nikite
 * @since 24.04.2017.
 */
public class Customer {
    private int id;
    private String name;
    private String secretKey;
    private int invoice;
    private CustomerType customerType;



    public Customer(int id, String name, String secretKey) {
        this.id = id;
        this.name = name;
        this.secretKey = secretKey;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }
}
