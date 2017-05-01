package nc.nut.dao.product;

import nc.nut.dao.interfaces.Dao;

import java.util.List;

/**
 * Created by Rysakova Anna , Alistratenko Nikita on 23.04.2017.
 */
public interface ProductDao extends Dao<Product> {

    List<Product> getByTypeId(int id);

    List<Product> getByCategoryId(int id);

    List<Product> getByProcessingStatus(int id);

    List<ProductTypes> findProductTypes();

    List<ProductCategories> findProductCategories();

    List<Product> getAllServices();

    List<Product> getAllTariffs();

    List<Product> getAllServices(String categoryName);

    List<Product> getAllFreeTariffs();

    void fillTariff(int idTariff, int idService);

    boolean addCategory(ProductCategories categories);

    List<ProductCategories> findIdCategory(ProductCategories categories);

    /**
     * Method returns all services that are available in place.
     * created by Yuliya Pedash
     * @param placeId id of place
     * @return all available services
     */
    List<Product> getAllAvailableServicesByPlace(Integer placeId);

    /**
     *
     * @param categoryId
     * @return
     */

    ProductCategories getProductCategoryById(Integer categoryId);

    /**
     * Method returns current user`s tariff. If user hasn`t got tariff, method returns null.
     *
     * @param userId user Id.
     * @return current user`s tariff.
     */
    public Product getCurrentUserTariff(Integer userId);

    /**
     * Method returns all tariffs are available in place with id from params. If there are no tariffs in this place, method returns empty list.
     *
     * @param placeId id of place.
     * @return list of tariffs.
     */
    public List<Product> getAvailableTariffsByPlace(Integer placeId);

}
