package nc.nut.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Moiseienko Petro
 * @since 15.04.2017.
 */

@Controller
public class LinkMailServlet {
    @RequestMapping(method = RequestMethod.GET, value = {"/linkMailServlet"})
    public String dispatch() {
        return "mailForm";
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/mail"})
    public String goMail() {
        return "mail";
    }


}
