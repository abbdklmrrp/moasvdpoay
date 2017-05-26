package jtelecom.controller.admin;

import jtelecom.dao.product.ProductDao;
import jtelecom.dto.TariffServiceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Anna Rysakova on 19.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class ViewTariffWithServicesController {

    private static Logger logger = LoggerFactory.getLogger(ViewTariffWithServicesController.class);
    @Resource
    private ProductDao productDao;

    @RequestMapping(value = {"viewServicesInTariff"}, method = RequestMethod.GET)
    public ModelAndView getServicesInTariffForUpdate(@RequestParam(value = "id") Integer tariffId,
                                                     ModelAndView mav) {

        logger.debug("Receive tariff's id {} ", tariffId);
        List<TariffServiceDto> servicesByTariff = productDao.getServicesInfoByTariff(tariffId);
        logger.debug("Received services that are included in the tariff {}", servicesByTariff.toString());
        String customerType = productDao.getCustomerTypeByProductId(tariffId);
        logger.debug("Customer type: {} ", customerType);

        mav.addObject("servicesByTariff", servicesByTariff);
        mav.addObject("customerType", customerType);
        mav.setViewName("newPages/admin/viewServicesInTariff");
        return mav;
    }
}
