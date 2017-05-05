package nc.nut.controller;

import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.googleMaps.ServiceGoogleMaps;
import nc.nut.security.SecurityAuthenticationHelper;
import nc.nut.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
        System.out.println("hey");
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        ModelAndView model = new ModelAndView("profile");
        model.addObject("user", user);
        String urlBegin;
        if (roleMap==null) {
            roleMap = new HashMap<>();
            roleMap.put(Role.Admin, "admin");
            roleMap.put(Role.CSR, "csr");
            roleMap.put(Role.PMG, "pmg");
        }
        urlBegin = roleMap.getOrDefault(user.getRole(), "user");
        model.addObject("pattern", urlBegin);
        return model;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String editProfile(User user) {
        User sessionUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        String urlBegin;
        String place = serviceGoogleMaps.getRegion(user.getAddress());
        Integer placeId = userDAO.findPlaceId(place);
        user.setPlaceId(placeId);
        user.setId(sessionUser.getId());
        boolean success = userService.updateUser(user);
        if (success) {
            urlBegin = roleMap.getOrDefault(sessionUser.getRole(), "user");
            return urlBegin + "/index";
        }
        return "profile";
    }

    @RequestMapping(value = "editUser", method = RequestMethod.POST)
    public String editUserProfile(User user, HttpSession session) {
        String place = serviceGoogleMaps.getRegion(user.getAddress());
        Integer placeId = userDAO.findPlaceId(place);
        Integer id = (Integer) session.getAttribute("userId");
        user.setPlaceId(placeId);
        user.setId(id);
        boolean success = userService.updateUser(user);
        if (success) {
            return "csr/index";
        }
        return "csr/userPage";
    }
}
