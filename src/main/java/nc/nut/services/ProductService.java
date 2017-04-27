package nc.nut.services;


import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Created by Anna on 26.04.2017.
 */
@Service
public class ProductService {

    @Resource
    private ProductDao productDao;

    //TODO validator -- uniq
    public int getCategory(String name, String description) {
        if (Objects.nonNull(name)) {
            ProductCategories category = new ProductCategories();
            category.setName(name);
            category.setDescription(description);

            List<ProductCategories> productCategories = productDao.findProductCategories();
            int idCategory = 0;
            for (ProductCategories pc : productCategories) {
                if (Objects.equals(pc.getName().trim().toUpperCase(), name.trim().toUpperCase())) {
                    idCategory = pc.getId();
                } else {
                    productDao.addCategory(category);
                    idCategory = productDao.findIdCategory(category).get(0).getId();
                }
            }
            return idCategory;
        }
        return 0;
    }

    public int checkIdCategory(int categoryID, int newCategory) {
        if ((newCategory != 0) & (categoryID != newCategory)) {
            return newCategory;
        }
        return categoryID;
    }

}
