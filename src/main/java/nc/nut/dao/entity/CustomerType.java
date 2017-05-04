package nc.nut.dao.entity;

import java.util.Objects;

/**
 * Created by Yuliya Pedash on 29.04.2017.
 */
public enum CustomerType {
    Legal(1, "Legal"),
    Individual(2, "Individual");

    private Integer id;
    private String name;

    CustomerType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * This method gets <code>CustomerType</code> object by id
     *
     * @param id id of customer type
     * @return CustomerType object or <code>null</code> if object by this id is not found
     */
    public static CustomerType getCustomerTypeById(Integer id) {
        CustomerType[] customerTypes = values();
        for (CustomerType customerType : customerTypes) {
            if (Objects.equals(customerType.getId(), id)) {
                return customerType;
            }
        }
        return null;
    }
}
