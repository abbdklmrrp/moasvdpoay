package jtelecom.controller.csr;

import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Moiseienko Petro
 * @since 11.05.2017.
 */
@RestController
@RequestMapping({"csr"})
public class UserInfoController {

    @Resource
    private UserDAO userDAO;
    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @RequestMapping(value = "getDetails", method = RequestMethod.GET)
    public ModelAndView getDetails(@RequestParam(value = "id") int id, HttpSession session) throws IOException {
        ModelAndView model = new ModelAndView();
        User user = userDAO.getUserById(id);
        session.setAttribute("userId", id);
        model.setViewName("newPages/csr/UserInfo");
        model.addObject("user", user);
        logger.debug("Get to user page, id " + id);
        return model;
    }


}
