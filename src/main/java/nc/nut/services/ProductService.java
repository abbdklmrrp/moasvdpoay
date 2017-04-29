package nc.nut.services;


import javafx.util.Pair;
import nc.nut.dao.entity.OperationStatus;
import nc.nut.dao.entity.Price;
import nc.nut.dao.order.Order;
import nc.nut.dao.price.PriceDao;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Anna on 26.04.2017.
 */
@Service
public class ProductService {

    @Resource
    private ProductDao productDao;
    @Resource
    private PriceDao priceDao;
    @Resource
    nc.nut.dao.order.OrderDao orderDao;

    //TODO validator -- uniq
    public int getCategory(String name, String description) {
        if (Objects.nonNull(name)) {
            ProductCategories category = new ProductCategories();
            category.setName(name);
            category.setDescription(description);

            List<ProductCategories> productCategories = productDao.findProductCategories();
            int idCategory = 0;
            for (ProductCategories pc : productCategories) {
                if (Objects.equals(pc.getName().trim().toUpperCase(), name.trim().toUpperCase())) {
                    idCategory = pc.getId();
                } else {
                    productDao.addCategory(category);
                    idCategory = productDao.findIdCategory(category).get(0).getId();
                }
            }
            return idCategory;
        }
        return 0;
    }

    public int checkIdCategory(int categoryID, int newCategory) {
        if ((newCategory != 0) & (categoryID != newCategory)) {
            return newCategory;
        }
        return categoryID;
    }

    /**
     * This method takes user and returns all products that can be shown for him
     * on 'Order Service' page.
     * It firstly gets all the products that can be shown to user depending on his place.
     * Then it gets all orders by this users company's employees in users place.
     * Then it sorts all products by categories and determines status for
     * every product for current user.
     * Status can be:
     * 'Active' if user order status for this product is Active,
     * 'In process' if user's order status for this product is 'In Process',
     * 'Suspended' if user's  order status for this product is 'Suspended',
     * Null if user doesn't have order for this product or user's order status for this product is 'Deactivated'
     * created by Yuliya Pedash.
     *
     * @param user
     * @return Map with Key - category name, Value - Map( with Key - Product, Value - product's status for current user)
     */
    public Map<String, Map<Product, String>> getCategoriesWithProductsToShow(User user) {
        List<Order> ordersByUsersCompanyAndPlace = orderDao.getOrdersByCustomerIdAndPlaceId(user.getCustomerId(),
                user.getPlaceId());
        List<Product> productsToShowWithoutStatuses = productDao.getAllAvailableServicesByPlace(user.getPlaceId());
        Map<String, Map<Product, String>> categoriesWithProducts = new HashMap<>();

        for (Product product : productsToShowWithoutStatuses) {
            String categoryName = productDao.getProductCategoryById(product.getCategoryId()).getName();
            if (!categoriesWithProducts.containsKey(categoryName)) {
                Map<Product, String> allProductWithStatusesForCategory = new HashMap<>();
                categoriesWithProducts.put(categoryName, allProductWithStatusesForCategory);
            }
            Map<Product, String> allProductWithStatusesForCategory = categoriesWithProducts.get(categoryName);
            OperationStatus operationStatus = getStatusForProduct(product, ordersByUsersCompanyAndPlace);
            allProductWithStatusesForCategory.put(product, operationStatus == null
                    ? null
                    : operationStatus.getStatus());
        }
        return categoriesWithProducts;
    }
    //todo change with lamda

    /**
     * This method takes product and returns status for it.
     * Helper method for @link
     *
     * @param product Product object
     * @param ordersByUser orders
     * @return operation status
     */
    private OperationStatus getStatusForProduct(Product product, List<Order> ordersByUser) {
        for (Order order : ordersByUser) {
            if (order.getProductId().equals(product.getId())) {
                return order.getCurrentStatus();
            }
        }
        return null;

    }
}


