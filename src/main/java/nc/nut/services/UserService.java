package nc.nut.services;

import nc.nut.controller.EditProfileController;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.googleMaps.ServiceGoogleMaps;
import nc.nut.security.Md5PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Random;

/**
 * @author Moiseienko Petro, Nikita Alistratenko
 * @since 03.05.2017.
 */
@Service
public class UserService {
    @Resource
    private UserDAO userDAO;
    @Resource
    private ServiceGoogleMaps serviceGoogleMaps;
    @Resource
    Md5PasswordEncoder encoder;

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean updateUser(User editedUser) {
        User oldUser = userDAO.getUserById(editedUser.getId());
        logger.warn("before changes" + oldUser.toString());
        //if address was changed
        if (!oldUser.getAddress().equals(editedUser.getAddress())) {
            String formatAddress = serviceGoogleMaps.getFormattedAddress(editedUser.getAddress());
            oldUser.setAddress(formatAddress);
            if ((!editedUser.getAddress().isEmpty()) || (!formatAddress.isEmpty())) {
                oldUser.setAddress(formatAddress);
                oldUser.setPlaceId(userDAO.findPlaceId(serviceGoogleMaps.getRegion(formatAddress)));
            }
        }
        if (!editedUser.getName().isEmpty()) {
            oldUser.setName(editedUser.getName());
        }
        if (!editedUser.getSurname().isEmpty()) {
            oldUser.setSurname(editedUser.getSurname());
        }
        if (!editedUser.getPhone().isEmpty()) {
            oldUser.setPhone(editedUser.getPhone());
        }
        if (!editedUser.getPassword().isEmpty()) {
            oldUser.setPassword(encoder.encode(editedUser.getPassword()));
        }
        if (!(editedUser.getEnable() == null)) {
            oldUser.setEnable(editedUser.getEnable());
        }
//        if (!user.getAddress().isEmpty() && !user.getAddress().equals(defaultUser.getAddress())) {
//            defaultUser.setAddress(user.getAddress());
//            if (!Objects.equals(user.getPlaceId(), defaultUser.getPlaceId())) {
//                defaultUser.setPlaceId(user.getPlaceId());
//            }
//        }
        logger.warn("after changes" + oldUser.toString());
        return userDAO.update(oldUser);
    }

    public boolean save(User user) {
        String place = serviceGoogleMaps.getRegion(user.getAddress());
        Integer placeId = userDAO.findPlaceId(place);
        user.setPlaceId(placeId);
        String address = user.getAddress();
        user.setAddress(serviceGoogleMaps.getFormattedAddress(address));
        boolean success = userDAO.save(user);
        // todo send email with registration info
        return success;
    }

    public boolean saveWithGeneratePassword(User user) {
        String password = passwordGenerator();
        user.setPassword(password);
        String place = serviceGoogleMaps.getRegion(user.getAddress());
        Integer placeId = userDAO.findPlaceId(place);
        user.setPlaceId(placeId);
        String address = user.getAddress();
        user.setAddress(serviceGoogleMaps.getFormattedAddress(address));
        boolean success = userDAO.save(user);
        //todo send email with registration info and new password
        return success;
    }

    private String passwordGenerator() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            char next = 0;
            int range = 10;

            switch (random.nextInt(3)) {
                case 0: {
                    next = '0';
                    range = 10;
                }
                break;
                case 1: {
                    next = 'a';
                    range = 26;
                }
                break;
                case 2: {
                    next = 'A';
                    range = 26;
                }
                break;
            }

            password.append((char) ((random.nextInt(range)) + next));
        }
        return password.toString();
    }
}
