package nc.nut.dao.user;

/**
 * @author Rysakova Anna
 */
public class User {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private Integer placeId;
    private Integer customerId;
    private String password;
    private String authority;
    private Role role;
    private Integer enable;


    public User(String name, String password, String authorities) {
        this.name = name;
        this.password = password;
        this.authority = authorities;
    }

    public User() {
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthorities(String authorities) {
        this.authority = authorities;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("User{").append("name='").append(name).append('\'').append(", password='").append(password).append('\'').append(", authorities='").append(authority).append('\'').append('}').toString();
    }
}
