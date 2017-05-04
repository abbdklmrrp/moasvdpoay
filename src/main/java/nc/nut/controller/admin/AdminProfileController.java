package nc.nut.controller.admin;

import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.googleMaps.ServiceGoogleMaps;
import nc.nut.security.SecurityAuthenticationHelper;
import nc.nut.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author Moiseienko Petro
 * @since 03.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class AdminProfileController {
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;
    @Resource
    private UserService userService;
    private Integer userId;
    @Resource
    private ServiceGoogleMaps googleMaps;

    @RequestMapping(value = "/getAdminProfile", method = RequestMethod.GET)
    public ModelAndView getCsrProfile() {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        ModelAndView model = new ModelAndView();
        userId = user.getId();
        String[] address = user.getAddress().split(", ");
        model.addObject("name", user.getName());
        model.addObject("surname", user.getSurname());
        model.addObject("email", user.getEmail());
        model.addObject("phone", user.getPhone());
        model.addObject("city", address[0]);
        model.addObject("street", address[1]);
        model.addObject("building", address[2]);
        model.setViewName("admin/profile");
        return model;
    }

    @RequestMapping(value = "/editAdminProfile", method = RequestMethod.POST)
    public String editProfile(User user,
                              @RequestParam(value = "city") String city,
                              @RequestParam(value = "street") String street,
                              @RequestParam(value = "building") String building) {
        String address = city + ", " + street + ", " + building;
        String place = googleMaps.getRegion(address);
        Integer placeId = userDAO.findPlaceId(place);
        user.setAddress(address);
        user.setPlaceId(placeId);
        user.setId(userId);
        boolean b = userService.updateUser(user);
        if (b) return "admin/index";
        return "admin/profile";
    }
}