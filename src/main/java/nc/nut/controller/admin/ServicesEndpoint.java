package nc.nut.controller.admin;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dto.ServicesByCategoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rysakova Anna on 26.04.2017.
 */
@RestController
@RequestMapping({"admin"})
public class ServicesEndpoint { // GET: admin/category/1
    private static Logger logger = LoggerFactory.getLogger(ServicesEndpoint.class);
    @Resource
    private ProductDao productDao;

    @RequestMapping(value = {"category/{categoryId}"}, method = RequestMethod.GET)
    public List<ServicesByCategoryDto> servicesByTariff(@PathVariable("categoryId") String categoryId) {
        Map<String, List<Product>> allServicesWithCategory = productDao.getAllServicesWithCategory();
        logger.debug("Get a map where the key - the name of the category, the value - list of services");

        List<ServicesByCategoryDto> servicesByCategoryDtoList = new ArrayList<>();
        logger.debug("Create list of services by category");
        for (Map.Entry<String, List<Product>> key : allServicesWithCategory.entrySet()) {
            if (categoryId.equals(key.getKey())) {
                logger.debug("Receive category found, id {} ", categoryId);
                for (Product product : key.getValue()) {
                    ServicesByCategoryDto servicesByCategoryDto = new ServicesByCategoryDto();
                    servicesByCategoryDto.setId(product.getId());
                    servicesByCategoryDto.setName(product.getName());
                    servicesByCategoryDtoList.add(servicesByCategoryDto);
                    logger.debug("To list of services by catalog add {} ", servicesByCategoryDto);
                }
            }
        }
        return servicesByCategoryDtoList;
    }
}