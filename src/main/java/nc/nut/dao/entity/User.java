package nc.nut.dao.entity;

/**
 * @author Rysakova Anna
 * @author Nikita Alistratenko
 */
public class User {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private boolean enable;
    private String password;
    private Role role;
    private long customer_id;
    private long place_id;

    public User() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public long getPlace_id() {
        return place_id;
    }

    public void setPlace_id(long place_id) {
        this.place_id = place_id;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id
                + ", name=" + name
                + ", surname=" + surname
                + ", email=" + email
                + ", phone=" + phone
                + ", address=" + address
                + ", enable=" + enable
                + ", password=" + password
                + ", role=" + role.getAuthority()
                + ", customer_id=" + customer_id
                + ", place_id=" + place_id + '}';
    }

}
