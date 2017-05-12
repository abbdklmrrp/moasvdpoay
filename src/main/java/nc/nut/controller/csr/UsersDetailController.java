package nc.nut.controller.csr;

import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.grid.GridRequestDto;
import nc.nut.grid.ListHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 28.04.2017.
 */
@RestController
@RequestMapping({"csr"})
public class UsersDetailController {
    @Resource
    private UserDAO userDAO;
    private static Logger logger = LoggerFactory.getLogger(UsersDetailController.class);


    @RequestMapping(value = "getUsersPage", method = RequestMethod.GET)
    public ModelAndView getUsers() throws IOException {
        return new ModelAndView("newPages/csr/Users");
    }

    @RequestMapping(value = {"getUsers"}, method = RequestMethod.GET)
    public ListHolder getUsers(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getLength();
        String search = request.getSearch();
        List<User> data = userDAO.getLimitedQuantityUsers(start, length, sort, search);
        int size = userDAO.getCountUsersWithSearch(search);
        return ListHolder.create(data, size);
    }


}
