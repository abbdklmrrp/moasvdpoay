package nc.nut.services;

import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.googleMaps.ServiceGoogleMaps;
import nc.nut.security.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Random;

/**
 * @author Moiseienko Petro
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

    public boolean updateUser(User user) {
        User defaultUser = userDAO.getUserById(user.getId());
        String formatAddress = serviceGoogleMaps.getFormattedAddress(user.getAddress());
        user.setAddress(formatAddress);
        if (!user.getName().isEmpty()) {
            defaultUser.setName(user.getName());
        }
        if (!user.getSurname().isEmpty()) {
            defaultUser.setSurname(user.getSurname());
        }
        if (!user.getPhone().isEmpty()) {
            defaultUser.setPhone(user.getPhone());
        }
        if (!user.getPassword().isEmpty()) {
            defaultUser.setPassword(encoder.encode(user.getPassword()));
        }
        if (!(user.getEnable() == null)) {
            defaultUser.setEnable(user.getEnable());
        }
//        if (!user.getAddress().isEmpty() && !user.getAddress().equals(defaultUser.getAddress())) {
//            defaultUser.setAddress(user.getAddress());
//            if (!Objects.equals(user.getPlaceId(), defaultUser.getPlaceId())) {
//                defaultUser.setPlaceId(user.getPlaceId());
//            }
//        }
        return userDAO.update(defaultUser);
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
