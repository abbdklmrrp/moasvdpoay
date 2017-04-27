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
}
