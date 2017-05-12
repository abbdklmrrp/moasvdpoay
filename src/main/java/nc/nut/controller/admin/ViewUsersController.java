package nc.nut.controller.admin;

import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.grid.GridRequestDto;
import nc.nut.grid.ListHolder;
import nc.nut.services.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Moiseienko Petro
 * @since 09.05.2017.
 */
@RestController
@RequestMapping({"admin"})
public class ViewUsersController {
    @Resource
    private UserDAO userDAO;
    @Resource
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(ViewUsersController.class);

    @RequestMapping(value = "getUsersPage", method = RequestMethod.GET)
    public ModelAndView getUsers() throws IOException {
        return new ModelAndView("newPages/admin/Users");
    }

    @RequestMapping(value = {"getUsers"}, method = RequestMethod.GET)
    public ListHolder getUsers(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        String search = request.getSearch();
        List<User> data = userDAO.getLimitedQuantityAllUsers(start, length, sort, search);
        int size = userDAO.getCountAllUsersWithSearch(search);
        logger.debug("Get users in interval:" + start + " : " + length);
        return ListHolder.create(data, size);
    }

    @RequestMapping(value = {"/activateUser"}, method = RequestMethod.POST)
    @ResponseBody
    public String activateUser(@RequestParam Integer userId) {
        User user = userDAO.getUserById(userId);
        String message = "";
        user.setEnable(1);
        boolean success = userService.updateUser(user);
        if (!success) {
            logger.error("Error in activating user:" + userId);
            message = "Sorry, please try again";
        } else {
            message = "success";
            logger.debug("User activated: " + userId);
        }
        return message;
    }

    @RequestMapping(value = {"/deactivateUser"}, method = RequestMethod.POST)
    @ResponseBody
    public String deactivateUser(@RequestParam Integer userId) {
        User user = userDAO.getUserById(userId);
        String message = "";
        user.setEnable(0);
        boolean success = userService.updateUser(user);
        if (!success) {
            logger.error("Error in deactivating user:" + userId);
            message = "Sorry, please try again";
        } else {
            message = "success";
            logger.debug("User deactivated: " + userId);
        }
        return message;
    }
}
