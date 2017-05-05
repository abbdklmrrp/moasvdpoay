package nc.nut.controller.csr;


import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.googleMaps.ServiceGoogleMaps;
import nc.nut.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Moiseienko Petro
 * @since 01.05.2017.
 */
@Controller
@RequestMapping({"csr"})
public class EditProfileController {
    @Resource
    private UserDAO userDAO;
    @Resource
    private ServiceGoogleMaps googleMaps;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/getProfile", method = RequestMethod.GET)
    public ModelAndView getProfile(HttpSession session) {
        ModelAndView model = new ModelAndView("csr/profile");
        Integer id = (Integer) session.getAttribute("id");
        User user = userDAO.getUserById(id);
        String[] address = user.getAddress().split(", ");
        model.addObject("name", user.getName());
        model.addObject("surname", user.getSurname());
        model.addObject("email", user.getEmail());
        model.addObject("phone", user.getPhone());
        model.addObject("city", address[0]);
        model.addObject("street", address[1]);
        model.addObject("building", address[2]);
        return model;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editProfile(User user,
                              @RequestParam(value = "city") String city,
                              @RequestParam(value = "street") String street,
                              @RequestParam(value = "building") String building, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        String address = city + ", " + street + ", " + building;
        String place = googleMaps.getRegion(address);
        Integer placeId = userDAO.findPlaceId(place);
        user.setAddress(address);
        user.setId(userId);
        user.setPlaceId(placeId);
        boolean success = userService.updateUser(user);
        if (success) {
            return "csr/index";
        }
        return "csr/userPage";
    }
}
