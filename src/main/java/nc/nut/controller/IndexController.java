package nc.nut.controller;

import nc.nut.security.Authority;
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
    private SecurityAuthenticationHelper securityAuthenticationHelper;

    @RequestMapping({"index"})
    String index() {
        User currentUser = securityAuthenticationHelper.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        boolean isAdmin = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Authority.ADMIN.getAuth()));
        boolean isPMG = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Authority.PMG.getAuth()));
        boolean isCSR = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Authority.CSR.getAuth()));
        if (isAdmin) {
            return "redirect:/admin/index";
        }
        if (isPMG) {
            return "redirect:/pmg/index";
        }
        if (isCSR) {
            return "redirect:/csr/index";
        } else {
            return "redirect:/user/index";
        }
    }
}
