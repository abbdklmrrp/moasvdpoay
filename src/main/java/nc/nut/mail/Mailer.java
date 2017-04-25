package nc.nut.mail;

import org.springframework.mail.MailSender;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author Moiseienko Petro
 * @since 13.04.2017.
 */

public class Mailer {
    private MailSender mailSender;
    private Email email;
    private Properties properties;

    public Mailer() {
        properties = new Properties();
        try {
            InputStream fis = getClass().getClassLoader().getResourceAsStream("mailTemplate.properties");
            properties.load(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(Recipient recipient, String subject, String text) throws EmailException {
        this.getEmail().setRecipient(recipient);
        email.createMail(subject, text);
        if (this.validate(email)) {
            mailSender.send(email.getSimpleMailMessage());
        }
    }

    private void sendDispatch(List<Recipient> recipients, String subject, String text) {
        for (Recipient recipient : recipients) {
            this.send(recipient, subject, text);
        }
    }

    public void sendComplaintAccepted(Recipient recipient, String number) {
        StringBuilder text = new StringBuilder(properties.getProperty("complaint.accepted.text"));
        int i = text.indexOf("?");
        text.replace(i, i + 1, number);
        String subject = properties.getProperty("complaint.accepted.subject");
        this.send(recipient, subject, text.toString());
    }

    public void sendComplaintConsidering(Recipient recipient, String number) {
        String subject = properties.getProperty("complaint.considering.subject");
        StringBuilder text = new StringBuilder(properties.getProperty("complaint.considering.text"));
        int i = text.indexOf("?");
        text.replace(i, i + 1, number);
        this.send(recipient, subject, text.toString());
    }

    public void sendComplaintSolved(Recipient recipient, String number) {
        String subject = properties.getProperty("complaint.solved.subject");
        StringBuilder text = new StringBuilder(properties.getProperty("complaint.solved.text"));
        int i = text.indexOf("?");
        text.replace(i, i + 1, number);
        this.send(recipient, subject, text.toString());
    }

    public void sendServiceActivated(Recipient recipient, String name) {
        String subject = properties.getProperty("service.activated.subject");
        StringBuilder text = new StringBuilder(properties.getProperty("service.activated.text"));
        int i = text.indexOf("?");
        text.replace(i, i + 1, name);
        this.send(recipient, subject, text.toString());
    }

    public void sendServiceSuspended(Recipient recipient, String name) {
        String subject = properties.getProperty("service.suspended.subject");
        StringBuilder text = new StringBuilder(properties.getProperty("service.suspended.text"));
        int i = text.indexOf("?");
        text.replace(i, i + 1, name);
        this.send(recipient, subject, text.toString());
    }

    public void sendServiceDeactivated(Recipient recipient, String name) {
        String subject = properties.getProperty("service.deactivated.subject");
        StringBuilder text = new StringBuilder(properties.getProperty("service.deactivated.text"));
        int i = text.indexOf("?");
        text.replace(i, i + 1, name);
        this.send(recipient, subject, text.toString());
    }

    public void sendRegistrationMail(Recipient recipient) {
        String subject = properties.getProperty("registration.subject");
        String text = properties.getProperty("registration.text");
        this.send(recipient, subject, text);
    }

    public void sendProposal(List<Recipient> recipients, String description) {
        String subject = properties.getProperty("new.proposal.subject");
        StringBuilder text = new StringBuilder(properties.getProperty("new.proposal.text"));
        int i = text.indexOf("?");
        text.replace(i, i + 1, description);
        this.sendDispatch(recipients, subject, text.toString());
    }

    private boolean validate(Email email) throws EmailException {
        if (email.getText() == null) {
            throw new EmailException(EmailException.MISSING_CONTENT);
        } else if (email.getSubject() == null || email.getSubject().equals("")) {
            throw new EmailException(EmailException.MISSING_SUBJECT);
        } else if (email.getRecipient() == null) {
            throw new EmailException(EmailException.MISSING_RECIPIENT);
        }
        return true;
    }

}
