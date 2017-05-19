package jtelecom.mail;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import java.util.Properties;

/**
 * @author Moiseienko Petro
 * @since 15.04.2017.
 */

@Configuration
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
//    private Session session;

    @Bean(name = "mailSender")
    public JavaMailSenderImpl javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        try {
//            InitialContext initialContext=new InitialContext();
//            session=(Session)initialContext.lookup("mail/netcracker");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setProtocol(protocol);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        javaMailSender.setJavaMailProperties(properties);
//        javaMailSender.setSession(session);
        return javaMailSender;
    }
    @Bean(name="email")
    public Email email() {
        return new Email();
    }


    @Bean(name = "mailService")
    public MailService mailer() {
        MailService mailer = new MailService();
        mailer.setEmail(email());
        mailer.setMailSender(javaMailService());
        return mailer;
    }
    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates/");
        return bean;
    }
}
