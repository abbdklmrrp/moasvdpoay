package jtelecom.controller.admin;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDAO;
import jtelecom.dto.TariffServiceDTO;
import jtelecom.services.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Anna Rysakova
 */
@Controller
@RequestMapping({"admin"})
public class UpdateServicesInTariffController {

    private static final String ERROR_FILL_IN_TARIFF_SERVICES = "Please, select services to tariff";
    private static final String ERROR_IN_CONNECTION = "Error with filling database";
    private static final String ERROR_TYPE = "Wrong type of input data";
    private static final String ERROR_EXIST = "Sorry, product doesn't exist";
    private static Logger logger = LoggerFactory.getLogger(UpdateServicesInTariffController.class);
    @Resource
    private ProductDAO productDAO;
    @Resource
    private ProductService productService;

    @RequestMapping(value = {"updateServicesInTariff/{id}"}, method = RequestMethod.GET)
    public ModelAndView getServicesInTariffForUpdate(@PathVariable(value = "id") Integer tariffId,
                                                     ModelAndView mav) {

        logger.debug("Receive tariff's id {} ", tariffId);
        List<TariffServiceDTO> servicesByTariff = productDAO.getServicesInfoByTariff(tariffId);
        logger.debug("Received services that are included in the tariff {}", servicesByTariff.toString());
        Map<String, List<Product>> allServicesWithCategory = productDAO.getServicesNotInTariff(tariffId);
        logger.debug("Get all service's categories {} ", allServicesWithCategory.toString());
        String customerType = productDAO.getCustomerTypeByProductId(tariffId);

        mav.addObject("allServicesWithCategory", allServicesWithCategory);
        mav.addObject("servicesByTariff", servicesByTariff);
        mav.addObject("customerType", customerType);
        mav.addObject("id", tariffId);
        mav.setViewName("newPages/admin/updateServicesInTariff");
        return mav;
    }

    @RequestMapping(value = {"updateServicesInTariff"}, method = RequestMethod.POST)
    public ModelAndView updateServicesInTariff(@RequestParam(value = "id") Integer id,
                                               @RequestParam(value = "selectedService") Integer[] servicesIdArray,
                                               RedirectAttributes attributes,
                                               ModelAndView mav) {

        logger.debug("Receive tariff's id {} ", id);
        Product foundProduct = productService.isValidProduct(id);
        if (!Objects.nonNull(foundProduct)) {
            logger.error("Product with ID = {}  does not exist in the database ", id);
            mav.addObject("error", ERROR_EXIST);
            mav.setViewName("newPages/admin/updateServicesInTariff/" + id);
        }

        if (servicesIdArray == null) {
            logger.error("Incoming data error with services ");
            mav.addObject("error", ERROR_FILL_IN_TARIFF_SERVICES);
            mav.setViewName("newPages/admin/updateServicesInTariff/" + id);
            return mav;
        }

        try {
            logger.debug("Convert a string array of service's ID to an integer array {} ", Arrays.toString(servicesIdArray));
            productService.updateFillingOfTariffsWithServices(servicesIdArray, id);
            logger.debug("Update tariff: removed unnecessary services, added new services");
            attributes.addFlashAttribute("msg", "Successfully updating");
        } catch (NumberFormatException e) {
            mav.addObject("error ", ERROR_TYPE);
            logger.error("Wrong parameter's type ", e.getMessage());
            attributes.addFlashAttribute("msg", "Sorry, try again later");
            mav.setViewName("newPages/admin/updateServicesInTariff/" + id);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {}", ex.getMessage());
            mav.addObject("error ", ERROR_IN_CONNECTION);
            attributes.addFlashAttribute("msg", "Sorry, try again later");
            mav.setViewName("newPages/admin/updateServicesInTariff/" + id);
            return mav;
        }

        mav.setViewName("redirect:/admin/viewServicesInTariff?id=" + id);
        return mav;
    }

}
