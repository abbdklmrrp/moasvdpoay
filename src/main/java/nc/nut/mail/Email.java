package nc.nut.mail;

import org.springframework.mail.SimpleMailMessage;

/**
 * @author Moiseienko Petro
 * @since 13.04.2017.
 */

public class Email {
    private SimpleMailMessage simpleMailMessage;
    private Recipient recipient;
    private String text;
    private String subject;

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public SimpleMailMessage getSimpleMailMessage() {
        return simpleMailMessage;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public String getText() {
        return text;
    }

    public String getSubject() {
        return subject;
    }

    public void createMail(String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);
        message.setText("Dear " + recipient.getName() + ",\n" + content);
        this.text = message.getText();
        message.setSubject(subject);
        this.subject = subject;
        message.setTo(recipient.getAddress());
        message.setFrom("briariy2010@yandex.ru");
        this.simpleMailMessage = message;
    }


}
