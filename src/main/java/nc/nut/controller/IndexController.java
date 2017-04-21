package nc.nut.controller;

import nc.nut.entity.Role;
import nc.nut.security.AuthorizedUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rysakova Anna
 */
@Controller
public class IndexController {

    @RequestMapping({ "index" })
    String index() {
        User currentUser = AuthorizedUser.safeGet();
        if (currentUser == null) {
            return "redirect:/login";
        }
        boolean isAdmin = currentUser.getAuthorities().contains(Role.admin);
        boolean isManager = currentUser.getAuthorities().contains(Role.manager);
        boolean isSupport = currentUser.getAuthorities().contains(Role.support);
        if (isAdmin) {
            return "/admin/admin";
        }
        if (isManager) {
            return "/manager/manager";
        }
        if (isSupport) {
            return "/support/support";
        } else {
            return "/user/user";
        }
        
    }
}
