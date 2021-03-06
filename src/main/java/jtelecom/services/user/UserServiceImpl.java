package jtelecom.services.user;


import jtelecom.dao.customer.Customer;
import jtelecom.dao.customer.CustomerDAO;
import jtelecom.dao.entity.CustomerType;
import jtelecom.dao.user.Role;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dao.user.UserStatus;
import jtelecom.googleMaps.ServiceGoogleMaps;
import jtelecom.services.customer.CustomerService;
import jtelecom.services.customer.CustomerServiceImpl;
import jtelecom.services.mail.MailService;
import jtelecom.security.Md5PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private Md5PasswordEncoder encoder;
    @Resource
    private CustomerDAO customerDAO;
    @Resource
    private MailService mailService;
    @Resource
    private CustomerService customerService;
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final static String SUCCESS_SAVING = "User successfully saved";
    private final static String FAILED_SAVING = "Registration failed.Please try again";
    private final static String NOT_UNIQUE_EMAIL = "User email isn't unique";


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateUser(User editedUser) {
        User oldUser = userDAO.getUserById(editedUser.getId());
        logger.warn("before changes" + oldUser.toString());
        boolean isPasswordChanged = false;
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
            isPasswordChanged = true;
        }
        if (!(editedUser.getStatus() == null)) {
            oldUser.setStatus(editedUser.getStatus());
        }
        boolean success = userDAO.update(oldUser);
        if (success) {
            if (isPasswordChanged) {
                mailService.sendPasswordChangedEmail(oldUser);
            }
            logger.warn("after changes" + oldUser.toString());
        }
        return success;
    }

    private String save(User user) {
        String message = validateFields(user).toString();
        if (message.isEmpty()) {
            boolean unique = userDAO.isUnique(user);
            if (unique) {
                boolean success = userDAO.save(user);
                if (success) {
                    return SUCCESS_SAVING;
                }
                return FAILED_SAVING;
            }
            return NOT_UNIQUE_EMAIL;
        }
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String saveWithGeneratingPassword(User user) {
        String password = passwordGenerator();
        user.setPassword(password);
        String message = save(user);
        mailService.sendRegistrationWithoutPasswordEmail(user);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String saveBusinessUser(User user, String companyName, String secretKey) {
        Integer customerId = customerDAO.getCustomerId(companyName, secretKey);
        if (customerId == null) {
            return "Secret key is wrong";
        } else {
            user.setCustomerId(customerId);
            return saveWithGeneratingPassword(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String saveResidentialWithPasswordGenerating(User user) {
        String password = passwordGenerator();
        user.setPassword(password);
        String message = saveResidential(user);
        if (message.equals(SUCCESS_SAVING)) {
            mailService.sendRegistrationWithoutPasswordEmail(user);
        }
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String saveResidentialWithoutPasswordGenerating(User user) {
        String message = saveResidential(user);
        if (message.equals(SUCCESS_SAVING)) {
            mailService.sendRegistrationEmail(user);
        }
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean generateNewPassword(int userId) {
        User user = userDAO.getUserById(userId);
        String password = passwordGenerator();
        user.setPassword(password);
        boolean success = userDAO.updatePassword(user);
        if (success) {
            mailService.sendNewPasswordEmail(user);
        }
        return success;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean enableDisableUser(User user) {
        boolean success = userDAO.enableDisableUser(user);
        if (success) {
            if (user.getStatus() == UserStatus.ENABLE) {
                mailService.sendActivatedEmail(user);
            } else if (user.getStatus() == UserStatus.DISABLE) {
                mailService.sendBannedEmail(user);
            }
        }
        return success;
    }

    /**
     * Method saves customer to user, gets its id
     * and saves user
     *
     * @param user user to saving
     * @return message about success of the operation
     */
    @Transactional
    private String saveResidential(User user) {
        user.setRole(Role.RESIDENTIAL);
        String message = validateFields(user).toString();
        if (message.isEmpty()) {
            boolean unique = userDAO.isUnique(user);
            if (unique) {
                Customer customer = new Customer(user.getEmail(), user.getPassword());
                customer.setCustomerType(CustomerType.Residential);
                Integer customerId = customerService.saveForUser(customer);
                if (customerId != null) {
                    user.setCustomerId(customerId);
                    boolean success = userDAO.save(user);
                    if (success) {
                        return SUCCESS_SAVING;
                    } else {
                        return FAILED_SAVING;
                    }
                } else {
                    return FAILED_SAVING;
                }
            }
            return NOT_UNIQUE_EMAIL;
        }
        return message;
    }

    /**
     * Method validates user's fields and checks
     * that field can't be empty or null
     * For email field checks that email must contains
     * sign "@"
     *
     * @param user user for validates
     * @return message about wrong fields. If all fields filled
     * right method returns empty string
     */
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
        } else if (!user.getEmail().contains("@")) {
            message.append("Incorrect email");
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

    /**
     * Method generates random password.
     * Password contains 8 signs and can contain
     * upper or lower letters and numbers
     *
     * @return generated password
     */
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
