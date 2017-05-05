package nc.nut.controller;

import nc.nut.dao.user.*;
import nc.nut.security.SecurityAuthenticationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author Rysakova Anna
 */
@Controller
@RequestMapping({"user"})
public class UserController {

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    UserDAO dao;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private
    @RequestMapping({"index"})
    String index() {
        return "user/index";
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    ModelAndView getProfile() {
        User currentUser = securityAuthenticationHelper.getCurrentUser();
        nc.nut.dao.user.User user = dao.findByEmail(currentUser.getUsername());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/userProfile");
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    String editInfo(@ModelAttribute("user")nc.nut.dao.user.User user){
        logger.warn(Integer.toString(user.getId()));
        logger.warn(Boolean.toString(dao.update(user)));
        return "redirect:profile";
    }
}
