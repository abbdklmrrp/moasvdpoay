package nc.nut.controller.admin;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dto.CategoryDto;
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
@RequestMapping({"services"})
public class ServicesEndpoint { // GET: services/tariffs/3G
    private Logger logger = LoggerFactory.getLogger(ServicesEndpoint.class);
    @Resource
    ProductDao productDao;

    @RequestMapping(value = {"tariffs/{tariff}"}, method = RequestMethod.GET)
    public List<CategoryDto> servicesByTariff(@PathVariable("tariff") String tariffId) {
        Map<String, List<Product>> allServicesWithCategory = productDao.getAllServicesWithCategory();

        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Map.Entry<String, List<Product>> key : allServicesWithCategory.entrySet()) {
            if (tariffId.equals(key.getKey())) {
                for (Product product : key.getValue()) {
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setId(product.getId());
                    categoryDto.setName(product.getName());
                    categoryDtoList.add(categoryDto);
                }
            }
        }
        return categoryDtoList;
    }


}

