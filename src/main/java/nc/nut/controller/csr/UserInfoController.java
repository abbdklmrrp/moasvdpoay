package nc.nut.controller.csr;

import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
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
    private static Logger logger= LoggerFactory.getLogger(UserInfoController.class);

    @RequestMapping(value = "getDetails", method = RequestMethod.GET)
    public ModelAndView getDetails(@RequestParam(value = "id") int id, HttpSession session) throws IOException {
        ModelAndView model = new ModelAndView();
        User user = userDAO.getUserById(id);
        session.setAttribute("userId", id);
        model.setViewName("newPages/csr/UserInfo");
        model.addObject("user", user);
        logger.debug("Get to user page, id "+id);
        return model;
    }


}
