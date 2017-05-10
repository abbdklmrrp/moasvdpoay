package nc.nut.controller.csr;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.grid.GridRequestDto;
import nc.nut.grid.ListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    @Resource
    private ObjectMapper objectMapper;

    private List<User> clients;

    @RequestMapping(value = "getUsersPage", method = RequestMethod.GET)
    public ModelAndView getUsers(Model model) throws IOException {
//        clients = userDAO.getAllClients();
//        model.addAttribute("userList", objectMapper.writeValueAsString(clients));
        return new ModelAndView("newPages/csr/Users");
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
        int length=request.getLength();
        String search=request.getSearch();
        List<User> data=userDAO.getLimitedQuantityUsers(start,length,sort,search);
        int size=userDAO.getCountUsersWithSearch(search);
        return ListHolder.create(data, size);
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String editUser(HttpSession session) {
        session.removeAttribute("userId");
        return "csr/index";
    }

    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    public String addProduct(HttpSession session, Model model) throws IOException {
//        int userId=(int)session.getAttribute("userId");
//        List<Product> products=productDao.getUnConnectedProducts(userId);
//        ObjectMapper mapper = new ObjectMapper();
//        model.addAttribute("productList",mapper.writeValueAsString(products));
        return "csr/index";
    }

    @RequestMapping(value = "viewOrders", method = RequestMethod.POST)
    public String viewOrders(Model model) throws IOException {
        return "csr/index";
    }

    @RequestMapping(value = "sendPassword", method = RequestMethod.POST)
    public String sendPassword(HttpSession session) {
        return "csr/index";
    }

    @RequestMapping(value = "getDetails", method = RequestMethod.GET)
    public ModelAndView getDetails(@RequestParam(value = "id") int id, HttpSession session) throws IOException {
        ModelAndView model = new ModelAndView();
        for (User user1 : clients) {
            if (user1.getId() == id) {
                session.setAttribute("userId", id);
                model.setViewName("csr/userPage");
                model.addObject("user", user1);
                return model;
            }
        }
        model.setViewName("csr/users");
        return model;
    }

}
