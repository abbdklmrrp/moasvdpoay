package jtelecom.services.product;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.user.User;
import jtelecom.dto.ServicesCatalogRowDTO;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anna Rysakova
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
     * Method check that product name exist in database. Return {@code true}
     * if exist, {@code false} if not exist.
     *
     * @param product {@code Product} object
     * @return {@code true} if exist, {@code false} if not exist.
     * @see Product
     */
    boolean isExistProductName(Product product);

    /**
     * Method check that product name for updating product exist in database.
     * Return {@code true} if exist, {@code false} if not exist.
     *
     * @param product {@code Product} object
     * @return {@code true} if exist, {@code false} if not exist.
     * @see Product
     */
    boolean isExistProductNameForUpdate(Product product, Integer id);

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
     * This method insert to database received {@link ArrayList} of {@code TariffServiceDTO}
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
     * @param productId  {@code Product} ID
     * @see Product
     */
    void updateFillingOfTariffsWithServices(Integer[] servicesId, Integer productId);

    /**
     * Method saves product and dispatch
     * info about it from users. Type od the users
     * must be same to product's status
     * {@link jtelecom.dao.entity.CustomerType}
     *
     * @param product this product
     * @return id of the saved product
     */
    Integer saveProduct(Product product);

    /**
     * Method changes status of the product to the opposite
     * ( see {@link jtelecom.dao.product.ProductStatus})
     *
     * @param productId id of the product
     * @return result of the operation
     */
    boolean disableEnableProduct(int productId);

    /**
     * This method returns {@link Product} object with price determined by user {@link jtelecom.dao.user.Role}.
     * For Residential user price in this Product will be determined by {@link User#placeId}.
     * Otherwise, for {@link Product#basePrice} will be set as base_price of this {@link Product} from data base.
     *
     * @param user      user
     * @param productId id of product
     * @return product
     * @author Yuliya Pedash.
     */
    Product getProductForUser(User user, Integer productId);

    /**
     * This method  gets list of {@link Product} with type Service limited by begin and end indexes,
     * search pattern, user role and category id. Then it gets all the {@link Product} of customer of
     * this user. After that method combines this information to form{@link ServicesCatalogRowDTO}
     * list.
     *
     * @param start      begin index
     * @param end        end index
     * @param sort       column to sort on
     * @param search     search pattern
     * @param user       user object
     * @param categoryId id of category
     * @return list of {@link ServicesCatalogRowDTO}
     * @author Yuliya Pedash.
     * @see jtelecom.dao.product.ProductType
     */
    List<ServicesCatalogRowDTO> getLimitedServicesForUser(User user, Integer start, Integer end, String sort, String search, Integer categoryId);

    /**
     * This method gets total count of all {@link Product} with type Service limited by user role,
     * search pattern and category.
     *
     * @param user       user
     * @param search     search pattern
     * @param categoryId id of category
     * @return total count
     * @author Yuliya Pedash.
     * @see jtelecom.dao.user.Role
     * @see jtelecom.dao.product.ProductType
     */
    Integer getCountForServicesWithSearch(User user, String search, Integer categoryId);

}
