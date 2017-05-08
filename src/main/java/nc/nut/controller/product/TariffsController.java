package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
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

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Anton Bulgakov
 * @since 04.05.2017.
 */
@Controller
@RequestMapping({"", "csr", "user"})
public class TariffsController {

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private ProductDao productDao;
    @Resource
    private UserDAO userDAO;

    private static Logger logger = LoggerFactory.getLogger(TariffsController.class);

    @RequestMapping(value = {"residential/tariffs"}, method = RequestMethod.GET)
    public String showTariffsForUser(Model model) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user: {}", currentUser.toString());
        Product currentTariff = productDao.getCurrentUserTariff(currentUser.getId());
        List<Product> tariffsByPlace = productDao.getAvailableTariffsByPlace(currentUser.getPlaceId());
        if (currentTariff != null) {
            logger.debug("Current tariff of user: {}", currentTariff.toString());
            model.addAttribute("currentTariff", currentTariff);
        }
        model.addAttribute("tariffsByPlace", tariffsByPlace);
        return "newPages/user/residential/Tariffs";
    }

    @RequestMapping(value = {"business/tariffs"}, method = RequestMethod.GET)
    public String showTariffsForCustomer(Model model) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Product currentTariff = productDao.getCurrentCustomerTariff(currentUser.getCustomerId());
        List<Product> tariffsForCustomers = productDao.getAvailableTariffsForCustomers();
        if (currentTariff != null) {
            model.addAttribute("currentTariff", currentTariff);
        }
        model.addAttribute("tariffsByPlace", tariffsForCustomers);
        return "newPages/user/business/Tariffs";
    }

    @RequestMapping(value = {"residential/activateTariff", "business/activateTariff"}, method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    public String activateTariff(@RequestParam Integer tariffId) {
        logger.debug("Method activateTariff param tariffId: {}", tariffId);
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user: {}", currentUser.toString());
        Boolean statusOperation = productDao.activateTariff(currentUser.getId(), tariffId);
        logger.debug("Status activation of tariff: {}", statusOperation);
        return statusOperation ? "Success" : "Fail";
    }

    @RequestMapping(value = {"residential/deactivateTariff", "business/deactivateTariff"}, method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    public String deactivateTariff(@RequestParam Integer tariffId) {
        logger.debug("Method deactivateTariff param tariffId: {}", tariffId);
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user: {}", currentUser.toString());
        Boolean statusOperation = productDao.deactivateTariff(currentUser.getId(), tariffId);
        logger.debug("Status deactivation of tariff: {}", statusOperation);
        return statusOperation ? "Success" : "Fail";
    }

    @RequestMapping(value = {"residential/showServicesOfTariff", "business/showServicesOfTariff"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public List<Product> showServicesOfTariff(@RequestParam Integer tariffId) {
        logger.debug("Method showServicesOfTariff param tariffId: {}", tariffId);
        List<Product> servicesOfTariff = productDao.getServicesOfTariff(tariffId);
        return servicesOfTariff;
    }
}
