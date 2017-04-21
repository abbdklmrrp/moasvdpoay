package nc.nut.config;

import nc.nut.mail.Email;
import nc.nut.mail.Mailer;
import nc.nut.mail.Recipient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Moiseienko Petro
 * @since 15.04.2017.
 */

@Configuration
@PropertySource(value = "classpath:gmail.com.properties")
public class MailConfig {
    @Value("${mail.host}")
    String host;
    @Value("${mail.from}")
    String from;
    @Value("${mail.port}")
    int port;
    @Value("${mail.username}")
    String username;
    @Value("${mail.password}")
    String password;
    @Value("${mail.protocol}")
    String protocol;
    @Value("${mail.smtps.auth}")
    boolean auth;

    @Bean(name = "mailSender")
    public JavaMailSenderImpl javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setProtocol(protocol);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        Properties properties=new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    @Bean(name = "templateMessage")
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        return simpleMailMessage;
    }
    @Bean(name="recipient")
    public Recipient recipient(){
        return new Recipient();
    }
    @Bean(name="email")
    public Email email(){
        Email email=new Email();
        email.setRecipient(recipient());
        email.setSimpleMailMessage(simpleMailMessage());
        return email;
    }

    @Bean(name="mailer")
    public Mailer mailer(){
    Mailer mailer=new Mailer();
    mailer.setEmail(email());
    mailer.setMailSender(javaMailService());
    return mailer;
    }
}
