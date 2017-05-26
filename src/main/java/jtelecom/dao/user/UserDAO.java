package jtelecom.dao.user;

import jtelecom.dao.entity.CustomerType;
import jtelecom.dao.interfaces.DAO;

import java.util.List;

/**
 * Created by Rysakova Anna on 20.04.2017.
 */
public interface UserDAO extends DAO<User> {
    User findByUsername(String username);

    List<User> getAll();

    List<User> getAllClients();

    Integer findRole(String name);

    Integer findPlaceId(String name);

    User findByEmail(String email);

    User getUserByPhone(String phone);

    User getUserById(Integer id);

    boolean update(User user);

    Integer getCountUsersWithSearch(String search);

    List<User> getLimitedQuantityUsers(int start, int length, String sort, String search);

    Integer getCountAllUsersWithSearch(String search);

    List<User> getLimitedQuantityAllUsers(int start, int length, String sort, String search);

    Integer getCountUsersWithSearchOfCustomer(String search, int custID);

    List<User> getLimitedQuantityUsersOfCustomer(int start, int length, String sort, String search, int custID);

    List<User> getLimitedQuantityEmployeesOfCustomer(int start, int length, String sort, String search, int customerId);

    Integer getCountEmployeesWithSearchOfCustomer(String search,int customerId);

    boolean isUnique(User user);

    User getUserByComplaintId(int complaintId);

    User getUserByOrderId(int orderId);

    List<User> getUsersByCustomerType(CustomerType customerType);

    List<User> getUsersByProductId(int productId);

    boolean enableDisableUser(User user);

    boolean updatePassword(User user);
}