package nc.nut.controller;

import nc.nut.dao.product.ProductStatus;
import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.googleMaps.ServiceGoogleMaps;
import nc.nut.security.Md5PasswordEncoder;
import nc.nut.security.SecurityAuthenticationHelper;
import nc.nut.services.UserService;
import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Moiseienko Petro
 * @since 05.05.2017.
 */
@Controller
@RequestMapping({"admin", "csr", "pmg", "user"})
public class EditProfileController {

    private static Logger logger = LoggerFactory.getLogger(EditProfileController.class);

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;
    @Resource
    private UserService userService;
    @Resource
    private ServiceGoogleMaps serviceGoogleMaps;
    private Map<Role, String> roleMap;

    @RequestMapping(value = "/getProfile", method = RequestMethod.GET)
    public ModelAndView getProfile() {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        ModelAndView model = new ModelAndView();
        model.addObject("user", user);
        String urlBegin;
        if (roleMap == null) {
            roleMap = new HashMap<>();
            roleMap.put(Role.Admin, "admin");
            roleMap.put(Role.CSR, "csr");
            roleMap.put(Role.PMG, "pmg");
        }
        urlBegin = roleMap.getOrDefault(user.getRole(), "user");
        String view = "newPages/" + urlBegin + "/Profile";//Revniuk for new page
        model.addObject("pattern", urlBegin);
        model.setViewName(view);//Revniuk
        return model;
    }

//    /**
//     * @Author Moiseienko Petro
//     * @Author Nikita Alistratenko
//     */
//    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
//    public String editProfile(User editedUser, RedirectAttributes attributes, HttpServletRequest request) {
//        User sessionUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
//        String urlPatterForCertainUserRole = roleMap.getOrDefault(sessionUser.getRole(), "user");
//        String place = serviceGoogleMaps.getRegion(editedUser.getAddress());
//        Integer placeId = userDAO.findPlaceId(place);
//        editedUser.setPlaceId(placeId);
//        editedUser.setId(sessionUser.getId());
//        if (userService.updateUser(editedUser)) {
//            attributes.addFlashAttribute("msg", "User has been updated");
//        } else {
//            attributes.addFlashAttribute("msg", "User has not been updated");
//        }
//        return "redirect:/" + urlPatterForCertainUserRole + "/getProfile";
//    }

    /**
     * @param editedUser new user info
     * @param attributes for passing messages to jsp
     * @param request    to get additional parameters
     * @return redirect to view
     * @Author Nikita Alistratenko
     */
    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String editProfile1(@ModelAttribute("user") User editedUser, RedirectAttributes attributes, HttpServletRequest request) {
        //Message of the updating
        String errorMessage = null;
        //needs to get id for user from db. JSP/SpringSecurity does not contain its id
        User sessionUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        //needs to set placeID to new copy of user
        Integer placeId = sessionUser.getPlaceId();
        label:
        {
            //if address was changed
            if (!editedUser.getAddress().equals(sessionUser.getAddress())) {
                String place = serviceGoogleMaps.getRegion(editedUser.getAddress());
                placeId = userDAO.findPlaceId(place);
                //checks if entered address isn't in database
                if (placeId == null || placeId == 0) {
                    errorMessage = "Our company does not provide service for this region";
                    break label;
                }
            }
            editedUser.setPlaceId(placeId);
            //if new password was entered
            if (!editedUser.getPassword().isEmpty()) {
                //checks if old pass is wrong
                if (!sessionUser.getPassword().equals(new Md5PasswordEncoder().encode(request.getParameter("oldPassword")))) {
                    errorMessage = "You have entered wrong old password. Please, try again.";
                    break label;
                }
            }
            editedUser.setId(sessionUser.getId());
            logger.warn(editedUser.toString() + ". oldpass: " + request.getParameter("oldPassword"));
            if (!userService.updateUser(editedUser)) {
                errorMessage = "User has not been updated";
            }
        }
        attributes.addFlashAttribute("msg", errorMessage);
        return "redirect:/" + sessionUser.getRole().getNameInLowwerCase() + "/getProfile";
    }

}
