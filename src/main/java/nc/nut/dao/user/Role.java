package nc.nut.dao.user;

import java.util.Objects;

/**
 * Created by Yuliya Pedash on 04.05.2017.
 */
public enum Role {
    Admin(1, "ADMIN"),
    PMG(2, "PMG"),
    CSR(3, "CSR"),
    Individual(4, "INDIVIDUAL"),
    Legal(5, "LEGAL"),
    Employee(6, "EMPLOYEE");

    private Integer id;
    private String name;

    Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    /**
     * This method gets <code>Role</code> object by id
     *
     * @param id id of role
     * @return Role object  or <code>null</code> if object by this id is not found
     */
    public static Role getRoleById(Integer id) {
        Role[] roles = values();
        for (Role role : roles) {
            if (Objects.equals(role.getId(), id)) {
                return role;
            }
        }
        return null;
    }
}
