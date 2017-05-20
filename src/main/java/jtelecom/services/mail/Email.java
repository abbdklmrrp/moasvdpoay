package jtelecom.services.mail;

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


    public Email() {
        properties = new Properties();
        try {
            InputStream fis = getClass().getClassLoader().getResourceAsStream("templates/mailSubjectTemplate.properties");
            properties.load(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimpleMailMessage createEmail(String to, Map<String,Object> model, EmailTemplatePath path){
    SimpleMailMessage message=new SimpleMailMessage();
    String subject=properties.getProperty(path.getSubject());
    String content=geFreeMarkerTemplateContent(model,path.getTemplateName());
    message.setTo(to);
    message.setSubject(subject);
    message.setText(content);
    return message;
   }


    private String geFreeMarkerTemplateContent(Map<String, Object> model, String fileName){
        StringBuffer content = new StringBuffer();
        try{
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate(fileName),model));
            return content.toString();
        }catch(Exception e){
            System.out.println("Exception occurred while processing mailTemplate:"+e.getMessage());
        }
        return "";
    }



}
