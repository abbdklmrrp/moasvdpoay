package nc.nut.web;

import nc.nut.mail.Mailer;
import nc.nut.mail.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Moiseienko Petro
 * @since 15.04.2017.
 */

@Controller
public class MailerServlet {

    @Autowired
    private Recipient recipient;
    @Autowired
    private Mailer mailer;

    @RequestMapping(method = RequestMethod.POST, path = "/mailerServlet")
    public String sendMessage(HttpServletRequest request) {
        String address = request.getParameter("recipient");
        String name = request.getParameter("name");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");
        recipient.setName(name);
        recipient.setAddress(address);
        mailer.send(recipient, subject, content);
        return "mail";
    }
}
