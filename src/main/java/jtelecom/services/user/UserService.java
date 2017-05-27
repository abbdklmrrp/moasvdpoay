package jtelecom.services.user;

import jtelecom.dao.user.User;

/**
 * @author Moiseienko Petro
 * @since 19.05.2017.
 */
public interface UserService {
    boolean updateUser(User editedUser);

    String saveWithGeneratingPassword(User user);

    String saveBusinessUser(User user, String companyName, String secretKey);

    String saveResidentialWithPasswordGenerating(User user);

    String saveResidentialWithoutPasswordGenerating(User user);

    boolean generateNewPassword(int userId);

    boolean enableDisableUser(User user);

}
