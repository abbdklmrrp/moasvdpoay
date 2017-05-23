package jtelecom.controller;

import jtelecom.dao.user.UserDAO;
import jtelecom.security.SecurityAuthenticationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @author Rysakova Anna, Nikita Alistratenko
 */
@Controller
@RequestMapping
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDAO userDao;

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;

//    @RequestMapping({"/", "/login"})
//    public String login() {
//        User currentUser = securityAuthenticationHelper.getCurrentUser();
//        if (currentUser != null) {
//            return "redirect:index.htm";
//        }
//        return "newPages/Login";
//    }

    @RequestMapping({"/"})
    public String index() {
        User currentUser = securityAuthenticationHelper.getCurrentUser();
        if (currentUser != null) {
            return "redirect:" + userDao.findByEmail(currentUser.getUsername()).getRole().getNameInLowwerCase() + "/getProfile";
        }
        return "newPages/About";
    }

    @RequestMapping({"/login"})
    public String login() {
        User currentUser = securityAuthenticationHelper.getCurrentUser();
        if (currentUser != null) {
            return "redirect:" + userDao.findByEmail(currentUser.getUsername()).getRole().getNameInLowwerCase() + "/getProfile";
        }
        return "newPages/Login";
    }

//    @RequestMapping("/failure")
//    public String failure(HttpServletRequest request, Model model) {
//        Object authenticationException = WebUtils.getSessionAttribute(request, WebAttributes.AUTHENTICATION_EXCEPTION);
//
//        if (authenticationException instanceof AuthenticationException) {
//            AuthenticationException ex = (AuthenticationException) authenticationException;
//
//            logger.error("Redirected after unsuccessful authentication, details: {}", ex);
//
//            WebUtils.setSessionAttribute(request, WebAttributes.AUTHENTICATION_EXCEPTION, null);
//
//            if (authenticationException instanceof BadCredentialsException) {
//                model.addAttribute("error", "Unknown user");
//            } else {
//                model.addAttribute("error", "Error");
//            }
//            return "newPages/Login";
//        } else {
//            return "redirect:/login.htm";
//        }
//    }

    @RequestMapping("accessDenied")
    public String accessDenied() {
        return "newPages/AccessDenied";
    }
    @RequestMapping("/failure")
    public String failure(HttpServletRequest request, Model model) {
        Object authenticationException = WebUtils.getSessionAttribute(request, WebAttributes.AUTHENTICATION_EXCEPTION);

        if (authenticationException instanceof AuthenticationException) {
            AuthenticationException ex = (AuthenticationException) authenticationException;

            logger.error("Redirected after unsuccessful authentication, details: {}", ex);

            WebUtils.setSessionAttribute(request, WebAttributes.AUTHENTICATION_EXCEPTION, null);

            if (authenticationException instanceof BadCredentialsException) {
                model.addAttribute("error", "Invalid username or password");
            } else {
                model.addAttribute("error", "Error");
            }
        }
        return "newPages/Login";
    }


//
//    @RequestMapping(value = "/userRegistration", method = RequestMethod.GET)
//    public String getRegistrationPage() {
//        return "newPages/Registration";
//    }

//    @RequestMapping(value = "/userRegistration", method = RequestMethod.POST)
//    public ModelAndView registerUser(@ModelAttribute("user") jtelecom.dao.user.User user, RedirectAttributes attributes) {
//        ModelAndView mw = new ModelAndView();
//        boolean result = false;
//        logger.warn(user.toString());
//        if (result) {
//            attributes.addFlashAttribute("msg", "You have been registered");
//        } else {
//            attributes.addFlashAttribute("msg", "You have not been registered");
//        }
//        mw.setViewName("redirect:/");
//        return mw;
//    }
}
