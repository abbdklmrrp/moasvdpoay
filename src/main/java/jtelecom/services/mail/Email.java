package jtelecom.services.mail;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import freemarker.template.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;


/**
 * @author Moiseienko Petro
 * @since 13.04.2017.
 */
@Component
public class Email {
    @Autowired
    private Configuration freemarkerConfiguration;
    private Properties properties;
    private static Logger logger = LoggerFactory.getLogger(Email.class);
    private static final String EMAIL_SUBJECTS_FILENAME="templates/mailSubjectTemplate.properties";

    /**
     * When object created it load property file with templates of the subject for email
     */
    public Email() {
        properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(EMAIL_SUBJECTS_FILENAME);
            properties.load(inputStream);

        } catch (IOException e) {
            logger.error("Property not loaded {}", e);
        }
    }

    /**
     * Method creates email message.<br>
     * Content creates using template files
     * See {@link Email#getFreeMarkerTemplateContent(Map, String)}
     *
     * @param to    recipient address
     * @param model map for creating content of the message using templates
     * @param path  enum that contains path to subject template and to content template file
     * @return filled message
     */
    SimpleMailMessage createEmail(String to, Map<String, Object> model, EmailTemplatePath path) {
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = properties.getProperty(path.getSubject());
        String content = getFreeMarkerTemplateContent(model, path.getTemplateName());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        return message;
    }

    /**
     * Method creates content of the message using {@link FreeMarkerTemplateUtils}
     *
     * @param model    map with parameters
     * @param fileName filename of the template file
     * @return filled content of the message
     */
    private String getFreeMarkerTemplateContent(Map<String, Object> model, String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate(fileName), model));
            return content.toString();
        } catch (IOException | TemplateException e) {
            logger.error("Exception occurred while processing mailTemplate: {}", e);
        }
        return "";
    }


}
