package jtelecom.services.product;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.user.User;
import jtelecom.dto.ProductCatalogRowDTO;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna Rysakova on 18.05.2017.
 */
public interface ProductService {

    /**
     * Methods verify that in database exist product with ID productID.
     * Return {@code null} if {@code Product} doesn't exist
     *
     * @param productId {@code Product} ID
     * @return {@code Product} object if in database exist product with ID productId
     * return {@code null} if product wasn't found
     * @see Product
     */
    Product isValidProduct(Integer productId) throws EmptyResultDataAccessException;

    /**
     * Returns {@code true} if, and only if, fields {@code name}
     * and {@code description}of {@link Product} is {@code 0}.
     *
     * @param product incoming object of {@code Product}
     * @return {@code true} if fields of{@code Product} is {@code 0}, otherwise
     * {@code false}
     * @see Product
     */
    boolean isEmptyFieldOfProduct(Product product);

    /**
     * Returns {@code true} if, and only if, fields {@code categoryName} and {@code categoryDescription}
     * of {@link ProductCategories} is {@code 0}.
     *
     * @param categories incoming object of {@link ProductCategories}
     * @return {@code true} if fields of{@link ProductCategories} is {@code 0}, otherwise
     * {@code false}
     * @see ProductCategories
     */
    boolean isEmptyFieldsOfNewCategory(ProductCategories categories);

    /**
     * If {@code Product} customer type is {@code Residential},
     * this method set {@code basePrice}  to {@code null}
     *
     * @param product {@code Product}
     * @see Product
     */
    void validateBasePriceByCustomerType(Product product);

    /**
     * If <code>ProductCategories</code> was created, this method add
     * it to database and return <code>ID</code> of new <code>ProductCategories</code>.
     * If new <code>ProductCategories</code> was created,
     * received key of new <code>ProductCategories</code> set to <code>Product</code> if
     *
     * @param category <code>Product</code> category
     * @param product  incoming object of <code>Product</code>
     * @return <code>Product</code>
     * @see Product
     * @see ProductCategories
     */
    Product getCategory(ProductCategories category, Product product);

    /**
     * This method compare new received {@link Product} with existing in database.
     * If this {@code Product} exist, method compare these fields and rewrite {@code Product}.
     * Received object of {@code Product} update to database.
     *
     * @param updateProduct {@code Product} for update
     * @see Product
     */
    boolean updateProduct(Product updateProduct);

    /**
     * This method insert to database received {@link ArrayList} of {@code TariffServiceDto}
     * which contains tariff ID and array of ID services.
     *
     * @param idTariff          tariff ID
     * @param arrayOfIdServices array of ID services
     */
    void fillInTariffWithServices(Integer idTariff, Integer[] arrayOfIdServices);

    /**
     * <p>This method update services in tariff. For this, the method takes out old services from the database to {@link List}
     * and compare with {@link List} of new services. </p>
     * <p>First, services that are not included in the new list are deleted
     * Then new services are inserted into the database</p>
     *
     * @param servicesId {@code Array} of ID services
     * @param product    {@code Product}
     * @see Product
     */
    void updateFillingOfTariffsWithServices(Integer[] servicesId, Product product);

    Integer saveProduct(Product product);

    boolean disableEnableProduct(int productId);

    Product getProductForUser(User user, Integer id);

    List<ProductCatalogRowDTO> getLimitedServicesForUser(User user, Integer start, Integer length, String sort, String search, Integer categoryId);

    Integer getCountForServicesWithSearch(User user, String search, Integer categoryId);

}
