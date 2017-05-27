package jtelecom.services.mail;

import jtelecom.dao.complaint.Complaint;
import jtelecom.dao.product.Product;
import jtelecom.dao.user.User;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author Moiseienko Petro
 * @since 19.05.2017.
 */
public interface MailService {

    /**
     * Method sends email with info about successfully registration
     *
     * @param user user which signed up
     */
    void sendRegistrationEmail(User user);

    /**
     * Method sends email with info about successfully registration
     * where it was without inputting password
     *
     * @param user user which signed up
     */
    void sendRegistrationWithoutPasswordEmail(User user);

    /**
     * Method sends email with info about sending email
     *
     * @param user        user which lodged
     * @param complaintId id of the complaint
     */
    void sendComplaintSentEmail(User user, int complaintId);

    /**
     * Method sends email when user's complaint in processing
     *
     * @param user        user which lodged
     * @param complaintId id of the complaint
     */
    void sendComplaintProcessingEmail(User user, int complaintId);

    /**
     * Method send email when user's complaint processed
     *
     * @param user        user which lodged
     * @param complaintId id of the complaint
     */
    void sendComplaintProcessedEmail(User user, int complaintId);

    /**
     * Method send email with info about new user's password
     *
     * @param user user which got new password
     */
    void sendNewPasswordEmail(User user);

    /**
     * Method sends dispatch for users with info about new product
     *
     * @param users   this users
     * @param product this product
     */
    void sendNewProductDispatch(List<User> users, Product product);

    /**
     * Method sends email when product was activated
     *
     * @param user    user which activated product
     * @param product this product
     */
    void sendProductActivatedEmail(User user, Product product);

    /**
     * Method sends email when new product in processing
     *
     * @param user    user which connected product
     * @param product this product
     */
    void sendProductProcessingEmail(User user, Product product);

    /**
     * Method sends email when product was deactivated
     *
     * @param user    user which deactivated product
     * @param product this product
     */
    void sendProductDeactivated(User user, Product product);

    /**
     * Method sends email when product was suspended now
     *
     * @param user      this user
     * @param product   this product
     * @param beginDate start date of the suspending
     * @param endDate   end date of the suspending
     */
    void sendProductSuspendedEmail(User user, Product product, Calendar beginDate, Calendar endDate);

    /**
     * Method sends dispatch with info when product was deleted
     *
     * @param users   this users
     * @param product this product
     */
    void sendProductDeletedDispatch(List<User> users, Product product);

    /**
     * Method sends email when password was changed
     *
     * @param user user which changed password
     */
    void sendPasswordChangedEmail(User user);

    /**
     * Method sends email when product was suspended in future
     *
     * @param user      this user
     * @param product   this product
     * @param beginDate start date of the suspending
     * @param endDate   end date of the suspending
     */
    void sendProductWillSuspendEmail(User user, Product product, Calendar beginDate, Calendar endDate);

    /**
     * Method sends custom emails
     *
     * @param to      recipient's email address
     * @param subject subject of the email
     * @param text    content of the email
     */
    boolean sendCustomEmail(String to, String subject, String text);

    /**
     * Method sends email when user was activated in the system
     *
     * @param user this user
     */
    void sendActivatedEmail(User user);

    /**
     * Method send email when user was banned in the system
     *
     * @param user this user
     */
    void sendBannedEmail(User user);
}
