package nc.nut.services;

import nc.nut.dao.product.Product;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Moiseienko Petro
 * @since 03.05.2017.
 */
@Service
public class UserService {
    @Resource
    private UserDAO userDAO;


    public boolean updateUser(User user) {
        User defaultUser = userDAO.getUserById(user.getId());
        if (!user.getName().equals("") & !user.getName().equals(defaultUser.getName())) {
            defaultUser.setName(user.getName());
        }
        if (!user.getSurname().equals("") & !user.getSurname().equals(defaultUser.getSurname())) {
            defaultUser.setSurname(user.getSurname());
        }
        if (!user.getPhone().equals("") & !user.getPhone().equals(defaultUser.getPhone())) {
            defaultUser.setPhone(user.getPhone());
        }
        if (!user.getAddress().equals("") & !user.getAddress().equals(defaultUser.getAddress())) {
            defaultUser.setAddress(user.getAddress());
        }
        if (user.getPlaceId() != defaultUser.getPlaceId()) {
            defaultUser.setPlaceId(user.getPlaceId());
        }
        return userDAO.update(defaultUser);
    }
}
