package jtelecom.services.user;

import jtelecom.dao.user.User;

/**
 * @author Moiseienko Petro
 * @since 19.05.2017.
 */
public interface UserService {
    /**
     * This method compares user with updating field
     * and user before updating. Then it updates user in database
     * and send notification about changes
     * if password was changed
     *
     * @param editedUser user with updating fields
     * @return success of the operation
     */
    boolean updateUser(User editedUser);

    /**
     * Method generates password to the user and
     * checks that fields filled right,
     * checks that user's email be unique
     * saves it in the database
     * and send notification about registration
     *
     * @param user user to saving
     * @return message about success of the operation
     */
    String saveWithGeneratingPassword(User user);

    /**
     * Method generates password to the business user, then find id of
     * the user's customer and  checks that fields filled right,
     * checks that user's email be unique saves it to the database
     * and send notification about registration
     *
     * @param user        user to saving
     * @param companyName customer's name
     * @param secretKey   customer's secret key
     * @return message about success of the operation
     */
    String saveBusinessUser(User user, String companyName, String secretKey);

    /**
     * Method generates new password,
     * checks that fields filled right,
     * checks that user's email be unique
     * creates new customer in the database
     * and saves this residential user
     * and send notification about registration
     *
     * @param user user to saving
     * @return message about success of the operation
     */
    String saveResidentialWithPasswordGenerating(User user);

    /**
     * Method creates new customer in the database,
     * checks that fields filled right,
     * checks that user's email be unique,
     * saves this residential user
     * and send notification about registration
     *
     * @param user user to saving
     * @return message about success of the operation
     */
    String saveResidentialWithoutPasswordGenerating(User user);

    /**
     * Method generates new password to the user,
     * set up  it to the user and updates this user in the database
     * and send notification about changes
     *
     * @param userId id of the this user
     * @return success of the operation
     */
    boolean generateNewPassword(int userId);

    /**
     * Method changes status of the user
     * and send notification about changes
     *
     * @param user this user
     * @return success of the operation
     */
    boolean enableDisableUser(User user);

}
