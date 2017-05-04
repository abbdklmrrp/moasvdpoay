package nc.nut.dao.product;

import nc.nut.dao.interfaces.Dao;

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

    boolean fillTariff(int idTariff, int idService);

    boolean addCategory(ProductCategories categories);

    int findIdCategory(ProductCategories categories);

    /**
     * Method returns all services that are available in place.
     * created by Yuliya Pedash
     * @param placeId id of place
     * @return all available services
     */
    List<Product> getAllAvailableServicesByPlace(Integer placeId);

    /**
     * Gets category of product by id
     * @param categoryId id of category
     * @return ProductCategory
     */

    ProductCategories getProductCategoryById(Integer categoryId);

    /**
     * Method returns current user`s tariff. If user hasn`t got tariff, method returns null.
     *
     * @param userId user Id.
     * @return current user`s tariff.
     */
    Product getCurrentUserTariff(Integer userId);

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
    List<Product> getAllServicesByCurrentUserTarifff(Integer userId);


    Map<String, List<Product>> getAllServicesWithCategory();
//    List<Product> getAllServicesWithCategory();

    List<Product> getAllProducts();

    List<Product> getServicesByTariff(Product product);

    List<Product> getServicesNotInTariff(Product product);

    boolean deleteServiceFromTariff(int idTariff, int idService);

    boolean disableTariffByID(int id);

    List<Product> getProductsByUserId(int id);

    List<Product> getActiveProductByUserId(Integer id);

    /**
     * Method update status of current tariff of user on Deactivate.
     *
     * @param userId user id.
     * @param tariffId tariff id.
     * @return status of deactivation.
     */
    public Boolean deactivateTariff(Integer userId, Integer tariffId);

    /**
     * Method returns all tariffs are available for customers. If there are no tariffs in this place, method returns empty list.
     *
     * @return list of tariffs.
     */
    public List<Product> getAvailableTariffsForCustomers();

    /**
     * Method returns tariff of customer according to customer id from params.
     * If no such customer or customer doesn`t have active tariff, method returns null.
     *
     * @param customerId id of customer.
     * @return tariff of customer.
     */
    public Product getCurrentCustomerTariff(Integer customerId);

    /**
     * Method creates order for activation new tariff for user with id from params.
     * If user has already had tariff, old tariff will be deactivated.
     *
     * @param userId id of user.
     * @param tariffId id of tariff.
     * @return status of operation.
     */
    public Boolean activateTariff(Integer userId, Integer tariffId);

    /**
     * Method returns list of services are in tariff with id from params.
     * If no tariff with such id or no services, method returns empty list.
     *
     * @param tariffId id of tariff.
     * @return list of services.
     */
    public List<Product> getServicesOfTariff(Integer tariffId);

}
