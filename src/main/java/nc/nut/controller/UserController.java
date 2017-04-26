package nc.nut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rysakova Anna
 */
@Controller
@RequestMapping({"user"})
public class UserController {
    @RequestMapping({"index"})
    String index() {
        return "user/index";
    }
}
