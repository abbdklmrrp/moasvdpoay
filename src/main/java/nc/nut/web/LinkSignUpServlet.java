package nc.nut.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Moiseienko Petro
 * @since 17.04.2017.
 */
@Controller
public class LinkSignUpServlet {
    @RequestMapping(method = RequestMethod.GET, path = "/linkSignUpServlet")
    public String dispatch() {
        return "signUp";
    }
}
