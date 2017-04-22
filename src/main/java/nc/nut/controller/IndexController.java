package nc.nut.controller;

import nc.nut.security.AuthorityConstants;
import nc.nut.security.SecurityAuthenticationHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author Rysakova Anna
 */
@Controller
public class IndexController {
    @Resource
    SecurityAuthenticationHelper securityAuthenticationHelper;

    @RequestMapping({"index"})
    String index() {
        User currentUser = securityAuthenticationHelper.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        boolean isAdmin = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(AuthorityConstants.ADMIN_VALUE));
        boolean isManager = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(AuthorityConstants.MANAGER_VALUE));
        boolean isSupport = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(AuthorityConstants.SUPPORT_VALUE));
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
