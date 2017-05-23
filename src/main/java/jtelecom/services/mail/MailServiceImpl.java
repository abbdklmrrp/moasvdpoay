package jtelecom.services.mail;

import jtelecom.dao.product.Product;
import jtelecom.dao.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Moiseienko Petro
 * @since 13.04.2017.
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private MailSender mailSender;
    @Resource
    private Email email;
    private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    public MailServiceImpl() {
    }

    private class MailThread extends Thread {
        private SimpleMailMessage message;

        private MailThread(SimpleMailMessage message) {
            this.message = message;
        }

        public void run() {
            try {
                mailSender.send(message);
            } catch (MailException e) {
                logger.error("Wrong email address {}", e.getMessage());
            }
        }

    }

    private void send(String to, Map<String, Object> model, EmailTemplatePath path) {
        SimpleMailMessage message = email.createEmail(to, model, path);
        try {
            validate(message);
        } catch (EmailException e) {
            logger.error(e.getMessage());
            return;
        }
        new MailServiceImpl.MailThread(message).start();
    }

    @Override
    public void sendRegistrationEmail(User user) {
        Map<String, Object> model = new MapBuilder(user)
                .setUserName()
                .setUserSurname()
                .build();
        send(user.getEmail(), model, EmailTemplatePath.REGISTRATION);
    }

    @Override
    public void sendRegistrationWithoutPasswordEmail(User user) {
        Map<String, Object> model = new MapBuilder(user)
                .setUserName()
                .setUserSurname()
                .setUserPassword()
                .build();
        send(user.getEmail(), model, EmailTemplatePath.REGISTRATION_WITHOUT_PASSWORD);
    }

    @Override
    public void sendComplaintSentEmail(User user, int complaintId) {
        Map<String, Object> model = new MapBuilder(user)
                .setUserName()
                .setUserSurname()
                .setComplaintId(complaintId)
                .build();
        send(user.getEmail(), model, EmailTemplatePath.COMPLAINT_SENT);
    }

    @Override
    public void sendComplaintProcessingEmail(User user, int complaintId) {
        Map<String, Object> model = new MapBuilder(user)
                .setUserName()
                .setUserSurname()
                .setComplaintId(complaintId)
                .build();
        send(user.getEmail(), model, EmailTemplatePath.COMPLAINT_PROCESSING);
    }

    @Override
    public void sendComplaintProcessedEmail(User user, int complaintId) {
        Map<String, Object> model = new MapBuilder(user)
                .setUserName()
                .setUserSurname()
                .setComplaintId(complaintId)
                .build();
        send(user.getEmail(), model, EmailTemplatePath.COMPLAINT_PROCESSED);
    }

    @Override
    public void sendNewPasswordEmail(User user) {
        Map<String, Object> model = new MapBuilder(user)
                .setUserName()
                .setUserSurname()
                .setUserPassword()
                .build();
        send(user.getEmail(), model, EmailTemplatePath.NEW_PASSWORD);
    }

    @Override
    public void sendNewProductDispatch(List<User> users, Product product) {
        for (User user : users) {
            Map<String, Object> model = new MapBuilder(user, product)
                    .setUserName()
                    .setUserSurname()
                    .setProductName()
                    .setProductType()
                    .setProductDescription()
                    .build();
            send(user.getEmail(), model, EmailTemplatePath.NEW_PRODUCT);
        }
    }

    @Override
    public void sendProductProcessingEmail(User user, Product product) {
        Map<String, Object> model = new MapBuilder(user, product)
                .setUserName()
                .setUserSurname()
                .setProductType()
                .setProductName()
                .build();
        send(user.getEmail(), model, EmailTemplatePath.PRODUCT_PROCESSING);
    }

    public void sendProductActivatedEmail(User user, Product product) {
        Map<String, Object> model = new MapBuilder(user, product)
                .setUserName()
                .setUserSurname()
                .setProductType()
                .setProductName()
                .build();
        send(user.getEmail(), model, EmailTemplatePath.PRODUCT_ACTIVATED);
    }

    @Override
    public void sendProductDeactivated(User user, Product product) {
        Map<String, Object> model = new MapBuilder(user, product)
                .setUserName()
                .setUserSurname()
                .setProductType()
                .setProductName()
                .build();
        send(user.getEmail(), model, EmailTemplatePath.PRODUCT_DEACTIVATED);
    }

    @Override
    public void sendProductSuspendedEmail(User user, Product product, Calendar beginDate, Calendar endDate) {
        Map<String, Object> model = new MapBuilder(user, product)
                .setUserName()
                .setUserSurname()
                .setProductType()
                .setProductName()
                .setBeginDate(beginDate)
                .setEndDate(endDate)
                .build();
        send(user.getEmail(), model, EmailTemplatePath.PRODUCT_SUSPENDED);
    }

    @Override
    public void sendProductDeletedDispatch(List<User> users, Product product) {
        for (User user : users) {
            Map<String, Object> model = new MapBuilder(user, product)
                    .setUserName()
                    .setUserSurname()
                    .setProductName()
                    .setProductType()
                    .build();
            send(user.getEmail(), model, EmailTemplatePath.PRODUCT_DELETED);
        }
    }

    @Override
    public void sendPasswordChangedEmail(User user) {
        Map<String, Object> model = new MapBuilder(user)
                .setUserName()
                .setUserSurname()
                .build();
        send(user.getEmail(), model, EmailTemplatePath.PASSWORD_CHANGED);
    }

    @Override
    public void sendProductWillSuspendEmail(User user, Product product, Calendar beginDate, Calendar endDate) {
        Map<String, Object> model = new MapBuilder(user, product)
                .setUserName()
                .setUserSurname()
                .setProductType()
                .setProductName()
                .setBeginDate(beginDate)
                .setEndDate(endDate)
                .build();
        send(user.getEmail(), model, EmailTemplatePath.PRODUCT_WILL_SUSPEND);
    }

    private void validate(SimpleMailMessage email) {
        if (email.getText() == null || email.getText().isEmpty()) {
            throw new EmailException(EmailException.MISSING_CONTENT);
        } else if (email.getSubject() == null || email.getSubject().isEmpty()) {
            throw new EmailException(EmailException.MISSING_SUBJECT);
        } else if (email.getTo() == null) {
            throw new EmailException(EmailException.MISSING_RECIPIENT);
        }
    }
}
