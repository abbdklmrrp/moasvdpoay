package jtelecom.services.user;


import jtelecom.dao.customer.Customer;
import jtelecom.dao.customer.CustomerDAO;
import jtelecom.dao.entity.CustomerType;
import jtelecom.dao.user.Role;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.googleMaps.ServiceGoogleMaps;
import jtelecom.services.mail.MailService;
import jtelecom.security.Md5PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author Moiseienko Petro, Nikita Alistratenko
 * @since 03.05.2017.
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDAO userDAO;
    @Resource
    private ServiceGoogleMaps serviceGoogleMaps;
    @Resource
    Md5PasswordEncoder encoder;
    @Resource
    private CustomerDAO customerDAO;
    @Resource
    private MailService mailService;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        if (!(editedUser.getName() == null) && !editedUser.getName().isEmpty()) {
            oldUser.setName(editedUser.getName());
        }
        if (!(editedUser.getSurname() == null) && !editedUser.getSurname().isEmpty()) {
            oldUser.setSurname(editedUser.getSurname());
        }
        if (!(editedUser.getPhone() == null) && !editedUser.getPhone().isEmpty()) {
            oldUser.setPhone(editedUser.getPhone());
        }
        if (!(editedUser.getPassword() == null) && !editedUser.getPassword().isEmpty()) {
            oldUser.setPassword(encoder.encode(editedUser.getPassword()));
        }
        if (!(editedUser.getStatus() == null)) {
            oldUser.setStatus(editedUser.getStatus());
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

    private String save(User user) {
        String message = validateFields(user).toString();
        if (message.isEmpty()) {
            boolean unique = userDAO.isUnique(user);
            if (unique) {
                boolean success = userDAO.save(user);
                if (success) {
                    return "User successfully saved";
                }
                return "User creating failed. Please try again";
            }
            return "User email isn't unique";
        }
        return message;
    }

    public String saveWithGeneratingPassword(User user) {
        String password = passwordGenerator();
        user.setPassword(password);
        String message = save(user);
        mailService.sendRegistrationWithoutPasswordEmail(user);
        return message;
    }

    public String saveBusinessUser(User user, String companyName, String secretKey) {
        Integer customerId = customerDAO.getCustomerId(companyName, secretKey);
        if (customerId == null) {
            return "Secret key is wrong";
        } else {
            user.setCustomerId(customerId);
            return saveWithGeneratingPassword(user);
        }
    }

    public String saveResidentialWithPasswordGenerating(User user) {
        String password = passwordGenerator();
        user.setPassword(password);
        String message = saveResidential(user);
        if(message.equals("User successfully saved")){
            mailService.sendRegistrationWithoutPasswordEmail(user);
        }
        return message;
    }

    public String saveResidentialWithoutPasswordGenerating(User user) {
        String message=saveResidential(user);
        if(message.equals("User successfully saved")){
            mailService.sendRegistrationEmail(user);
        }
        return message;
    }

    public boolean generateNewPassword(User user){
        String password=passwordGenerator();
        user.setPassword(password);
        boolean success=updateUser(user);
        if (success) {
            mailService.sendNewPasswordEmail(user);
        }
        return success;
    }

    private String saveResidential(User user) {
        user.setRole(Role.RESIDENTIAL);
        String message = validateFields(user).toString();
        if (message.isEmpty()) {
            boolean unique = userDAO.isUnique(user);
            if (unique) {
                Customer customer = new Customer(user.getEmail(), user.getPassword());
                customer.setCustomerType(CustomerType.Residential);
                boolean isSaveCustomer = customerDAO.save(customer);
                if (isSaveCustomer) {
                    Integer customerId = customerDAO.getCustomerId(user.getEmail(), user.getPassword());
                    user.setCustomerId(customerId);
                    boolean success = userDAO.save(user);
                    if (success) {
                        return "User successfully saved";
                    } else {
                        return "Registration failed.Please try again";
                    }
                } else {
                    return "Registration failed.Please try again";
                }
            }
            return "User email isn't unique";
        }
        return message;
    }

    private StringBuilder validateFields(User user) {
        StringBuilder message = new StringBuilder();
        if (user.getSurname() == null || user.getSurname().isEmpty()) {
            message.append("Surname is empty;");
        }
        if (user.getRole() == null) {
            message.append("Role is empty;");
        }
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            message.append("Phone is empty;");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            message.append("Name is empty;");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            message.append("Password is empty");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            message.append("Email is empty;");
        }
        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            message.append("Address is empty;");
        } else {
            String address = user.getAddress();
            user.setAddress(serviceGoogleMaps.getFormattedAddress(address));
            String place = serviceGoogleMaps.getRegion(user.getAddress());
            Integer placeId = userDAO.findPlaceId(place);
            if (placeId == null) {
                message.append("Our company does not provide service for this region");
            } else {
                user.setPlaceId(placeId);
            }
        }
        return message;
    }

    private static String passwordGenerator() {
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
