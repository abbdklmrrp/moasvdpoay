package jtelecom.services.product;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;

/**
 * Created by Anna Rysakova on 18.05.2017.
 */
public interface ProductService {

    Product getCategory(ProductCategories category, Product product);

    boolean isEmptyFieldsOfNewCategory(ProductCategories categories);

    boolean isEmptyFieldOfProduct(Product product);

    void updateFillingOfTariffsWithServices(Integer[] servicesId, Product product);

    void fillInTariffWithServices(Integer idTariff, Integer[] arrayOfIdServices);

    boolean updateProduct(Product updateProduct);

    Integer saveProduct(Product product);

    boolean disableEnableProduct(int productId);
}
