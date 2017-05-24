package jtelecom.services.product;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.user.User;
import jtelecom.dto.ProductCatalogRowDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Anna Rysakova on 18.05.2017.
 */
public interface ProductService {

    Product foundProduct(Integer productId);

    Product getCategory(ProductCategories category, Product product);

    boolean isEmptyFieldsOfNewCategory(ProductCategories categories);

    boolean isEmptyFieldOfProduct(Product product);

    @Transactional
    void updateFillingOfTariffsWithServices(Integer[] servicesId, Product product);

    void fillInTariffWithServices(Integer idTariff, Integer[] arrayOfIdServices);

    boolean updateProduct(Product updateProduct);

    Integer saveProduct(Product product);

    boolean disableEnableProduct(int productId);

    void validateBasePriceByCustomerType(Product product);

    Product getProductForUser(User user, Integer id);

    List<ProductCatalogRowDTO> getLimitedServicesForUser(User user, Integer start, Integer length, String sort, String search, Integer categoryId);

    Integer getCountForServicesWithSearch(User user, String search, Integer categoryId);

}
