package nc.nut.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.product.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Rysakova Anna on 01.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class ViewProductController {

    @Resource
    private ProductDao productDao;
    private static Logger logger = LoggerFactory.getLogger(ViewProductController.class);

    @RequestMapping(value = "getAllProducts", method = RequestMethod.GET)
    public String getAllProducts(Model model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Product> products = productDao.getAllProducts();
        model.addAttribute("productList", mapper.writeValueAsString(products));
        return "newPages/admin/Products";
    }

    @RequestMapping(value = "getDetailsProduct", method = RequestMethod.GET)
    public ModelAndView getDetailsProduct(@RequestParam(value = "id") int id,
                                          ModelAndView mav,
                                          HttpSession session) {

        List<Product> productList = productDao.getAllProducts();
        for (Product product : productList) {

            if (product.getId() == id) {
                session.setAttribute("productId", id);
                mav.addObject("type_id", product.getProductType().getId());

                if (product.getProductType().equals(ProductType.Service)) {
                    mav.addObject("category_id", product.getCategoryId());
                    mav.setViewName("admin/updateService");
                }
                if (product.getProductType().equals(ProductType.Tariff)) {
                    List<Product> servicesByTariff = productDao.getServicesByTariff(product);
                    List<Product> servicesNotInTariff = productDao.getServicesNotInTariff(product);
                    mav.addObject("servicesByTariff", servicesByTariff);
                    mav.addObject("servicesNotInTariff", servicesNotInTariff);
                    mav.setViewName("admin/updateTariff");
                }
            }
        }
        return mav;
    }
}

