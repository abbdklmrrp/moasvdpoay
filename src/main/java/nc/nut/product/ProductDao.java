package nc.nut.product;

import java.util.List;

/**
 * Created by Anna on 23.04.2017.
 */
public interface ProductDao {
    void addProduct(Product product);

    List<ProductTypes> findProductTypes();

    List<ProductCategories> findProductCategories();
}
