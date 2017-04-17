package nc.nut.controller;

import nc.nut.security.SecurityAuthenticationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class LoginController {
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    SecurityAuthenticationHelper securityAuthenticationHelper;
    
    @RequestMapping({ "/", "/login" })
    String login() {
        User currentUser = securityAuthenticationHelper.getCurrentUser();
        if (currentUser != null) {
            return "redirect:index.htm";
        }
        return "login";
    }
    
    @RequestMapping("/failure")
    String failure(HttpServletRequest request, Model model) {
        Object authenticationException = WebUtils.getSessionAttribute(request, WebAttributes.AUTHENTICATION_EXCEPTION);
        
        if (authenticationException instanceof AuthenticationException) {
            AuthenticationException ex = (AuthenticationException) authenticationException;
            
            logger.error("Redirected after unsuccessful authentication, details: {}", ex);
            
            WebUtils.setSessionAttribute(request, WebAttributes.AUTHENTICATION_EXCEPTION, null);
            
            if (authenticationException instanceof BadCredentialsException) {
                model.addAttribute("error", "Unknown user");
            } else {
                model.addAttribute("error", "Error");
            }
            return "login";
        } else {
            return "redirect:/login.htm";
        }
    }
}
