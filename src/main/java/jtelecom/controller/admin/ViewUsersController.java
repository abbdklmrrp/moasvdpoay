package jtelecom.controller.admin;

import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 09.05.2017.
 */
@RestController
@RequestMapping({"admin", "business"})
public class ViewUsersController {
    private static Logger logger = LoggerFactory.getLogger(ViewUsersController.class);
    @Resource
    private UserDAO userDAO;
    @Resource
    private UserService userService;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;

    @RequestMapping(value = "getUsersPage", method = RequestMethod.GET)
    public ModelAndView getUsersPage() throws IOException {
        return new ModelAndView("newPages/admin/Users");
    }

    @RequestMapping(value = "getEmployeesPage", method = RequestMethod.GET)
    public ModelAndView getEmployeesPage() throws IOException {
        return new ModelAndView("newPages/business/Employees");
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

    @RequestMapping(value={"getEmployees"}, method=RequestMethod.GET)
    public ListHolder getEmployees(@ModelAttribute GridRequestDto requestDto){
        User user=userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Integer customerId=user.getCustomerId();
        String sort = requestDto.getSort();
        int start = requestDto.getStartBorder();
        int length = requestDto.getEndBorder();
        String search = requestDto.getSearch();
        List<User> data = userDAO.getLimitedQuantityEmployeesOfCustomer(start, length, sort, search,customerId);
        int size = userDAO.getCountEmployeesWithSearchOfCustomer(search,customerId);
        logger.debug("Get users in interval:" + start + " : " + length);
        return ListHolder.create(data, size);
    }


    @RequestMapping(value = {"/activateUser"}, method = RequestMethod.POST)
    @ResponseBody
    public String activateUser(@RequestParam Integer userId) {
        User user = userDAO.getUserById(userId);
        String message;
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
        String message;
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
