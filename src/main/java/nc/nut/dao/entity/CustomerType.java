package nc.nut.dao.entity;

/**
 * Created by Yuliya Pedash on 29.04.2017.
 */
public enum CustomerType {
    Legal("Legal"),
    Individual("Individual");
    private String customerType;

    CustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }
}
