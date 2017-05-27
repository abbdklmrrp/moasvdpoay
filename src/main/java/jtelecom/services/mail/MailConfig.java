package jtelecom.services.mail;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * @author Moiseienko Petro
 * @since 15.04.2017.
 */

@Configuration
public class MailConfig {
    private static Logger logger= LoggerFactory.getLogger(MailConfig.class);
    private final static String MAIL_JNDI="mail/netcracker";
//    @Value("${mail.host}")
//    String host;
//    @Value("${mail.from}")
//    String from;
//    @Value("${mail.port}")
//    int port;
//    @Value("${mail.username}")
//    String username;
//    @Value("${mail.password}")
//    String password;
//    @Value("${mail.protocol}")
//    String protocol;
//    @Value("${mail.smtps.auth}")
//    boolean auth;

    @Bean(name = "mailSender")
    public JavaMailSenderImpl javaMailService() {
        Session session=null;
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        try {
            InitialContext initialContext=new InitialContext();
            session=(Session)initialContext.lookup(MAIL_JNDI);
        } catch (NamingException e) {
            logger.error("Lookup error {}",e);
        }
//        javaMailSender.setHost(host);
//        javaMailSender.setPort(port);
//        javaMailSender.setProtocol(protocol);
//        javaMailSender.setUsername(username);
//        javaMailSender.setPassword(password);
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", true);
//        properties.put("mail.smtp.starttls.enable", true);
//        properties.setProperty("mail.smtp.auth", "true");
//        properties.setProperty("mail.smtp.starttls.enable", "true");
//        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setSession(session);
        return javaMailSender;
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates/");
        return bean;
    }
}
