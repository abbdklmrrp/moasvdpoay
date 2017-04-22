package nc.nut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rysakova Anna
 */
@Controller
@RequestMapping({"user"})
public class UserController {
    @RequestMapping({"index"})
    String index(HttpServletRequest req) {
        String uri=req.getRequestURI();
        System.out.println(uri);
        return "user/index";
    }
}
