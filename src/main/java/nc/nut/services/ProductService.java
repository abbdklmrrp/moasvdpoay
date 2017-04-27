package nc.nut.services;


import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Anna on 26.04.2017.
 */
@Service
public class ProductService {

    @Resource
    ProductDao productDao;

    public void addProduct(Product product) {
        int typeId = product.getTypeId();
        switch (typeId) {
            case 1:
                product.setCategoryId(null);
                productDao.addProduct(product);
            case 2:
                List<ProductCategories> productCategories = productDao.findProductCategories();
                for (ProductCategories p : productCategories) {
//                    if(product.ge)
                }

        }
    }
}
