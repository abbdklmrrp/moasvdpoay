package jtelecom.dao.product;

import jtelecom.dao.interfaces.Dao;
import jtelecom.dto.TariffServiceDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rysakova Anna , Alistratenko Nikita on 23.04.2017.
 */
public interface ProductDao extends Dao<Product> {

    List<Product> getByTypeId(int id);

    List<Product> getByCategoryId(int id);

    List<Product> getByProcessingStatus(int id);

    List<String> findProductTypes();

    List<ProductCategories> findProductCategories();

    List<Product> getAllServices();


    List<Product> getAllTariffs();

    List<Product> getAllEnabledTariffs();

    List<Product> getAllServices(String categoryName);

    List<Product> getAllFreeTariffs();

    void fillInTariffWithServices(ArrayList<TariffServiceDto> tariffServiceDtos);

    boolean addCategory(ProductCategories categories);

    int findIdCategory(ProductCategories categories);

    /**
     * Method returns all services that are available in place.
     * created by Yuliya Pedash
     *
     * @param placeId id of place
     * @return all available services
     */
    List<Product> getAllAvailableServicesByPlace(Integer placeId);

    /**
     * Gets category of admin by id
     *
     * @param categoryId id of category
     * @return ProductCategory
     */

    ProductCategories getProductCategoryById(Integer categoryId);

    /**
     * Method returns all tariffs are available in place with id from params. If there are no tariffs in this place, method returns empty list.
     *
     * @param placeId id of place.
     * @return list of tariffs.
     */
    List<Product> getAvailableTariffsByPlace(Integer placeId);

    /**
     * This method returns all services that are included in current user's Tariff.
     *
     * @param userId id of user
     * @return list of products with service type.
     */
    List<Product> getAllServicesByCurrentUserTariff(Integer userId);


    Map<String, List<Product>> getAllServicesWithCategory();
//    List<Product> getAllServicesWithCategory();

    List<Product> getAllProducts();

    List<Product> getServicesByTariff(Product product);

//    List<Product> getServicesNotInTariff(Product product);

    void deleteServiceFromTariff(ArrayList<TariffServiceDto> tariffServiceDtos);

    boolean disableEnableProductByID(int id);

    List<Product> getProductsByUserId(int id);

    List<Product> getActiveProductsByUserId(Integer id);

    /**
     * Method update status of current tariff of user on Deactivate.
     *
     * @param userId   user id.
     * @param tariffId tariff id.
     * @return status of deactivation.
     */
    Boolean deactivateTariff(Integer userId, Integer tariffId);

    /**
     * Method returns all tariffs are available for customers. If there are no tariffs in this place, method returns empty list.
     *
     * @return list of tariffs.
     */
    List<Product> getAvailableTariffsForCustomers();

    /**
     * Method returns all services that are available for customers.
     * If there are no services for customer, method returns empty list.
     *
     * @return
     */
    List<Product> getServicesAvailableForCustomer();

    /**
     * Method returns tariff of customer according to customer id from params.
     * If no such customer or customer doesn`t have active tariff, method returns null.
     *
     * @param customerId id of customer.
     * @return tariff of customer.
     */
    Product getCurrentCustomerTariff(Integer customerId);

    /**
     * Method creates order for activation new tariff for user with id from params.
     * If user has already had tariff, old tariff will be deactivated.
     *
     * @param userId   id of user.
     * @param tariffId id of tariff.
     * @return status of operation.
     */
    boolean activateTariff(Integer userId, Integer tariffId);

    /**
     * Method returns list of services are in tariff with id from params.
     * If no tariff with such id or no services, method returns empty list.
     *
     * @param tariffId id of tariff.
     * @return list of services.
     */
    List<Product> getServicesOfTariff(Integer tariffId);

    Integer getCountProductsWithSearch(String search);

    List<Product> getLimitedQuantityProduct(int start, int length, String sort, String search);

    List<Product> getProductForResidentialCustomerWithoutPrice();

    /**
     * This method returns Product object in which <code>basePrice</code>
     * will be determined by price configured for place in prices table.
     *
     * @param productId id of product
     * @param placeId   id of place
     * @return found Product
     */
    Product findProductWithPriceSetByPlace(Integer productId, Integer placeId);


}