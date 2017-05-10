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

    @RequestMapping(value = "getUsersPage", method = RequestMethod.GET)
    public ModelAndView getUsers() throws IOException {
        return new ModelAndView("newPages/admin/Users");
    }
    @RequestMapping(value = {"getUsers"}, method = RequestMethod.GET)
    public ListHolder servicesByTariff(@ModelAttribute GridRequestDto request) {
        String sort=request.getSort();
        if(!sort.isEmpty()){
            String[] array=sort.split("=");
            if("true".equals(array[1])){
                sort=array[0]+" "+"ASC";
            }else{
                sort=array[0]+" "+"DESC";
            }}
        int start=request.getStartBorder();
        int length=request.getEndBorder();
        String search=request.getSearch();
        List<User> data=userDAO.getLimitedQuantityAllUsers(start,length,sort,search);
        int size=userDAO.getCountAllUsersWithSearch(search);
        return ListHolder.create(data, size);
    }
    @RequestMapping(value={"/activateUser"}, method=RequestMethod.POST)
    @ResponseBody
    public String activateUser(@RequestParam Integer userId){
        System.out.println("kyky");
        User user=userDAO.getUserById(userId);
        String message="";
            user.setEnable(1);
            message="success";
        boolean success=userService.updateUser(user);
        if(!success){
            message="Sorry, please try again";
        }
        return message;
    }

    @RequestMapping(value={"/deactivateUser"}, method=RequestMethod.POST)
    @ResponseBody
    public String deactivateUser(@RequestParam Integer userId){
        User user=userDAO.getUserById(userId);
        System.out.println("kyky");
        String message="";
        user.setEnable(0);
        message="success";
        boolean success=userService.updateUser(user);
        if(!success){
            message="Sorry, please try again";
        }
        return message;
    }
}
