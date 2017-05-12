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
    public String index() {
        User currentUser = securityAuthenticationHelper.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        boolean isAdmin = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Authority.ADMIN.getAuth()));
        boolean isPMG = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Authority.PMG.getAuth()));
        boolean isCSR = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Authority.CSR.getAuth()));
        boolean isBusiness = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Authority.BUSINESS.getAuth()));
        boolean isResidential = currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Authority.RESIDENTIAL.getAuth()));
        if (isAdmin) {
            return "redirect:/admin/getProfile";
        }
        if (isPMG) {
            return "redirect:/pmg/getProfile";
        }
        if (isCSR) {
            return "redirect:/csr/getProfile";
        }
        if (isBusiness) {
            return "redirect:/business/getProfile";
        }
        if (isResidential) {
            return "redirect:/residential/getProfile";
        } else {
            return "redirect:/employee/getProfile";
        }
    }
}
