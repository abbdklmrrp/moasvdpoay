package nc.nut.domain;

public class User {
    private String name;
    private String password;
    private String authorities;
    
    public User(String name, String password, String authorities) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }
    
    public User() {
    }
    
    public String getName() {
        return name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getAuthorities() {
        return authorities;
    }
}
