package nc.nut.controller.csr;


import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
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

    @RequestMapping(value = "/getProfile", method = RequestMethod.GET)
    public ModelAndView getProfile(HttpSession session) {
        ModelAndView model = new ModelAndView();
        Integer id = (Integer) session.getAttribute("id");
        User user = userDAO.getUserById(id);
        model.addObject("name", user.getName());
        model.addObject("surname", user.getSurname());
        model.addObject("email", user.getEmail());
        model.addObject("phone", user.getPhone());
        String[] address = user.getAddress().split(", ");
        model.addObject("city", address[0]);
        model.addObject("street", address[1]);
        model.addObject("building", address[2]);
        model.setViewName("csr/profile");
        return model;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editProfile(@RequestParam(value = "name") String name,
                              @RequestParam(value = "surname") String surname,
                              @RequestParam(value = "email") String email,
                              @RequestParam(value = "phone") String phone,
                              @RequestParam(value = "city") String city,
                              @RequestParam(value = "street") String street,
                              @RequestParam(value = "buiding") String building) {
        User user = new User();
        return "csr/index";
    }
}
