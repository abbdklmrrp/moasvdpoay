package nc.nut.mail;

import nc.nut.user.User;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Moiseienko Petro
 * @since 13.04.2017.
 */

public class Email {
    private SimpleMailMessage simpleMailMessage;
    private User recipient;

    public Email() {
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public SimpleMailMessage getSimpleMailMessage() {
        return simpleMailMessage;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }


    public SimpleMailMessage createMail(String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);
        message.setText(String.format(message.getText(), recipient.getName(), content));
        message.setSubject(subject);
        message.setTo(recipient.getEmail());
        message.setFrom("briariy2010@yandex.ru");
        return message;
    }


}
