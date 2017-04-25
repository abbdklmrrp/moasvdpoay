package nc.nut.mail;

/**
 * @author Moiseienko Petro
 * @since 13.04.2017.
 */

public class Recipient {
    private String address;
    private String name;

    public Recipient() {

    }

    public Recipient(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
