package nc.nut.controller;

import nc.nut.controller.admin.AddProductController;
import nc.nut.dao.user.UserDAO;
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
    private UserDAO dao;

    private static Logger logger = LoggerFactory.getLogger(AddProductController.class);

    @RequestMapping({"/business/getProfile"})
    public String indexBusiness() {
        return "newPages/user/business/Profile";
    }

    @RequestMapping({"/residential/getProfile"})
    public String indexResidential() {
        return "newPages/user/residential/Profile";
    }

    @RequestMapping({"getProfile"})
    public String indexEmployee() {
        return "newPages/employee/Profile";
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public ModelAndView getProfile() {
        User currentUser = securityAuthenticationHelper.getCurrentUser();
        nc.nut.dao.user.User user = dao.findByEmail(currentUser.getUsername());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/userProfile");
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public String editInfo(@ModelAttribute("user") nc.nut.dao.user.User user) {
        logger.warn(Integer.toString(user.getId()));
        logger.warn(Boolean.toString(dao.update(user)));
        return "redirect:profile";
    }
}
