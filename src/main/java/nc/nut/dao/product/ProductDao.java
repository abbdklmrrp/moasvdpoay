package nc.nut.dao.product;

import java.util.List;

/**
 * Created by Rysakova Anna on 23.04.2017.
 */
public interface ProductDao {
    void addProduct(Product product);

    List<ProductTypes> findProductTypes();

    List<ProductCategories> findProductCategories();

    List<Product> getServices();

    List<Product> getTariffs();

    List<Product> getServices(String categoryName);

    List<Product> getAllFreeTariffs();

    void identifyTariff(int idTariff, int idService);
}
