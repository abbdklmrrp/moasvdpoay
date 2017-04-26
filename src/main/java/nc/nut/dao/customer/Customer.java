package nc.nut.dao.customer;

/**
 * @author Moiseienko Petro
 * @since 24.04.2017.
 */
public class Customer {
    private int id;
    private String name;
    private String secretKey;

    public Customer(int id, String name, String secretKey) {
        this.id = id;
        this.name = name;
        this.secretKey = secretKey;
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
}
