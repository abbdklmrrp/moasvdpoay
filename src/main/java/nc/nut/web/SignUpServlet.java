package nc.nut.web;

import nc.nut.mail.Mailer;
import nc.nut.mail.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Moiseienko Petro
 * @since 17.04.2017.
 */
@Controller
public class SignUpServlet {
    @Autowired
    private Recipient recipient;
    @Autowired
    private Mailer mailer;

    @RequestMapping(value = "/signUpServlet", method = RequestMethod.POST)
    public String signUpUser(@RequestParam(value = "firstName") String sgnFName,
                             @RequestParam(value = "lastName") String sgnSName,
                             @RequestParam(value = "email") String sgnEmail,
                             @RequestParam(value = "password") String sgnPass) {

        System.out.println(sgnFName);
        System.out.println(sgnSName);
        System.out.println(sgnEmail);
        System.out.println(sgnPass);
        recipient.setName(sgnFName+" "+sgnSName);
        recipient.setAddress(sgnEmail);
        mailer.sendRegistrationMail(recipient);



            return "mail";
        }
    }
