package nc.nut.services;

import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

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
        if (!user.getName().isEmpty() && !user.getName().equals(defaultUser.getName())) {
            defaultUser.setName(user.getName());
        }
        if (!user.getSurname().isEmpty() && !user.getSurname().equals(defaultUser.getSurname())) {
            defaultUser.setSurname(user.getSurname());
        }
        if (!user.getPhone().isEmpty() && !user.getPhone().equals(defaultUser.getPhone())) {
            defaultUser.setPhone(user.getPhone());
        }
        if (!user.getAddress().isEmpty() && !user.getAddress().equals(defaultUser.getAddress())) {
            defaultUser.setAddress(user.getAddress());
        }
        if (!Objects.equals(user.getPlaceId(), defaultUser.getPlaceId())) {
            defaultUser.setPlaceId(user.getPlaceId());
        }
        return userDAO.update(defaultUser);
    }
}
