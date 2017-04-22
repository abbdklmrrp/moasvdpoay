package nc.nut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rysakova Anna
 */
@Controller
@RequestMapping({ "manager" })
public class ManagerController {
    @RequestMapping({ "index" })
    String index() {
        return "manager/index";
    }
}
