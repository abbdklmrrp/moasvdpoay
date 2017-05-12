package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.security.SecurityAuthenticationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Bulgakov
 * @since 04.05.2017.
 */
@Controller
@RequestMapping({"residential", "business", "csr"})
public class TariffsController {

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private ProductDao productDao;
    @Resource
    private UserDAO userDAO;

    private static Logger logger = LoggerFactory.getLogger(TariffsController.class);

    @RequestMapping(value = {"/tariffs"}, method = RequestMethod.GET)
    public String showTariffsForUser(Model model) {
        List<Product> tariffs;
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user: {}", currentUser.toString());
        Product currentTariff = productDao.getCurrentCustomerTariff(currentUser.getCustomerId());;
        if (currentUser.getRole().equals(Role.RESIDENTIAL)) {
            tariffs = productDao.getAvailableTariffsByPlace(currentUser.getPlaceId());
        } else {
            tariffs = productDao.getAvailableTariffsForCustomers();
        }
        if (currentTariff != null) {
            logger.debug("Current tariff of user: {}", currentTariff.toString());
            model.addAttribute("currentTariff", currentTariff);
        }
        model.addAttribute("tariffs", tariffs);
        return "newPages/" + currentUser.getRole().getName().toLowerCase() + "/Tariffs";
    }

    @RequestMapping(value = {"activateTariff"}, method = RequestMethod.POST)
    @ResponseBody
    public String activateTariff(@RequestParam Integer tariffId) {
        logger.debug("Method activateTariff param tariffId: {}", tariffId);
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user: {}", currentUser.toString());
        Boolean statusOperation = productDao.activateTariff(currentUser.getId(), tariffId);
        logger.debug("Status activation of tariff: {}", statusOperation);
        return statusOperation ? "success" : "fail";
    }

    @RequestMapping(value = {"deactivateTariff"}, method = RequestMethod.POST)
    @ResponseBody
    public String deactivateTariff(@RequestParam Integer tariffId) {
        logger.debug("Method deactivateTariff param tariffId: {}", tariffId);
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user: {}", currentUser.toString());
        Boolean statusOperation = productDao.deactivateTariff(currentUser.getId(), tariffId);
        logger.debug("Status deactivation of tariff: {}", statusOperation);
        return statusOperation ? "success" : "fail";
    }

    @RequestMapping(value = {"showServicesOfTariff"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Product> showServicesOfTariff(@RequestParam Integer tariffId) {
        logger.debug("Method showServicesOfTariff param tariffId: {}", tariffId);
        return productDao.getServicesOfTariff(tariffId);
    }
}
