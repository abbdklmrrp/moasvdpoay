package nc.nut.web;

import nc.nut.googleMaps.ServiceGoogleMaps;
import nc.nut.user.User;
import nc.nut.user.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Moiseienko Petro
 * @since 17.04.2017.
 */
@Controller
public class SignUpController {
    @Resource
    private UserDAO userDAO;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUpUser(@RequestParam(value = "firstName") String name,
                             @RequestParam(value = "lastName") String surname,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "phoneNumber") String phone,
                             @RequestParam(value = "city") String city,
                             @RequestParam(value = "street") String street,
                             @RequestParam(value = "building") String building,
                             HttpSession session) {
        String address = city + ", " + street + ", " + building;
        ServiceGoogleMaps maps = new ServiceGoogleMaps();
        String place = maps.getRegion(address);
        int placeId = userDAO.findPlaceId(place);
        int roleId = userDAO.findRole("USER");
        int customerId = (int) session.getAttribute("companyId");
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRoleId(roleId);
        user.setAddress(address);
        user.setPlaceId(placeId);
        user.setCustomerId(customerId);
        if (!userDAO.save(user)) {
            return "signUp";
        } else return "login";
    }
}
