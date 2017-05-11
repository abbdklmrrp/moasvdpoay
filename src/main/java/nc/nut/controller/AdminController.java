package nc.nut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rysakova Anna
 */
@Controller
@RequestMapping({"admin"})
public class AdminController {
    @RequestMapping({"getProfile"})
    public String indexAdmin() {
        return "newPages/admin/Profile";
    }
}
