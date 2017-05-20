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

    void sendRegistrationEmail(User user);

    void sendRegistrationWithoutPasswordEmail(User user);

    void sendComplaintSentEmail(User user, int complaintId);

    void sendComplaintProcessingEmail(User user, int complaintId);

    void sendComplaintProcessedEmail(User user, int complaintId);

    void sendNewPasswordEmail(User user);

    void sendNewProductDispatch(List<User> users, Product product);

    void sendProductActivatedEmail(User user, Product product);

    void sendProductProcessingEmail(User user, Product product);

    void sendProductDeactivated(User user, Product product);

    void sendProductSuspendedEmail(User user, Product product, Calendar beginDate, Calendar endDate);

    void sendProductDeletedDispatch(List<User> users, Product product);
}
