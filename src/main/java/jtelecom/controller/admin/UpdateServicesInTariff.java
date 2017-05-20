package jtelecom.controller.admin;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDao;
import jtelecom.dto.TariffServiceDto;
import jtelecom.services.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Anna Rysakova on 16.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class UpdateServicesInTariff {

    private static final String ERROR_FILL_IN_TARIFF_SERVICES = "Please, select services to tariff";
    private static final String ERROR_IN_CONNECTION = "Error with filling database";
    private static final String ERROR_TYPE = "Wrong type of input data";
    private static Logger logger = LoggerFactory.getLogger(UpdateProductController.class);
    @Resource
    private ProductDao productDao;
    @Resource
    private ProductService productService;

    @RequestMapping(value = {"updateServicesInTariff/{id}"}, method = RequestMethod.GET)
    public ModelAndView getServicesInTariffForUpdate(@PathVariable(value = "id") Integer tariffId,
                                                     ModelAndView mav) {

        logger.debug("Receive tariff's id {} ", tariffId);
        List<TariffServiceDto> servicesByTariff = productDao.getServicesByTariff(tariffId);
        logger.debug("Received services that are included in the tariff {}", servicesByTariff.toString());
        Map<String, List<Product>> allServicesWithCategory = productDao.getAllServicesWithCategory();
        logger.debug("Get all service's categories {} ", allServicesWithCategory.toString());
        String customerType = productDao.getCustomerTypeByProductId(tariffId);

        mav.addObject("allServicesWithCategory", allServicesWithCategory);
        mav.addObject("servicesByTariff", servicesByTariff);
        mav.addObject("customerType", customerType);
        mav.setViewName("newPages/admin/updateServicesInTariff");
        return mav;
    }

    @RequestMapping(value = {"updateServicesInTariff"}, method = RequestMethod.POST)
    public ModelAndView updateServicesInTariff(Product product,
                                               @RequestParam(value = "id") Integer id,
                                               @RequestParam(value = "selectedService") Integer[] servicesIdArray,
                                               ModelAndView mav) {

        logger.debug("Receive tariff's id {} ", id);
        try {
            Product tariff = productDao.getById(id);
            logger.debug("Checked that the tariff exists {} ", tariff.toString());
        } catch (DataAccessException ex) {
            logger.error("Product with ID = {}  does not exist in the database ", id);
        }

        if (servicesIdArray == null) {
            logger.error("Incoming data error with services ");
            mav.addObject("error", ERROR_FILL_IN_TARIFF_SERVICES);
            mav.setViewName("newPages/admin/updateServicesInTariff/" + id);
            return mav;
        }

        try {
            logger.debug("Convert a string array of service's ID to an integer array {} ", Arrays.toString(servicesIdArray));
            productService.updateFillingOfTariffsWithServices(servicesIdArray, product);
            logger.debug("Update tariff: removed unnecessary services, added new services");
        } catch (NumberFormatException e) {
            mav.addObject("error ", ERROR_TYPE);
            logger.error("Wrong parameter's type ", e.getMessage());
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {}", ex.getMessage());
            mav.addObject("error ", ERROR_IN_CONNECTION);
            mav.setViewName("newPages/admin/updateServicesInTariff/" + id);
            return mav;
        }

        logger.debug("Attribute 'productId' was removed from session");
        mav.setViewName("redirect:/admin/viewServicesInTariff?id=" + id);
        return mav;
    }

}
