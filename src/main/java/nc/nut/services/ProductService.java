package nc.nut.services;


import nc.nut.dao.entity.OperationStatus;
import nc.nut.dao.order.Order;
import nc.nut.dao.price.Price;
import nc.nut.dao.price.PriceDao;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.product.ProductType;
import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.utils.ProductCatalogRow;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Rysakova Anna on 26.04.2017.
 */
@Service
public class ProductService {

    @Resource
    nc.nut.dao.order.OrderDao orderDao;
    @Resource
    private ProductDao productDao;
    @Resource
    private PriceDao priceDao;

    /**
     * Rysakova Anna
     *
     * @param category
     * @param product
     * @return
     */
    public Product getCategory(ProductCategories category, Product product) {
        if (!category.getName().equals("")) {
            productDao.addCategory(category);
            int newCategoryId = productDao.findIdCategory(category);
            product.setCategoryId(newCategoryId);
        }
        return product;
    }

    /**
     * Rysakova Anna
     *
     * @param categories
     * @return
     */
    public boolean checkEmptyNewCategory(ProductCategories categories) {
        return !(!categories.getName().equals("") && categories.getDescription().equals(""));
    }

    /**
     * Rysakova Anna
     *
     * @param product
     * @return
     */
    public boolean checkEmptyFieldIfProduct(Product product) {
        return !(product.getName().equals("") || product.getDescription().equals(""));
    }

    /**
     * Rysakova Anna
     *
     * @param product
     */
    public void saveProduct(Product product) {
        if (product.getProductType() == ProductType.Tariff) {
            product.setCategoryId(null);
            productDao.save(product);
        }
        if (product.getProductType() == ProductType.Service) {
            productDao.save(product);
        }
    }

    /**
     * Rysakova Anna
     *
     * @param servicesId
     * @param idTariff
     */
    public void fillTariff(String[] servicesId, int idTariff) {
        for (String o : servicesId) {
            productDao.fillTariff(idTariff, Integer.parseInt(o));
        }
    }

    /**
     * Rysakova Anna
     *
     * @param servicesId
     * @return
     */
    public boolean checkUniqueCategoryServices(String[] servicesId) {
        List<Product> allServices = productDao.getAllServices();
        Set<Integer> serviceCategoryId = new HashSet<>();
        for (String o : servicesId) {
            for (Product p : allServices) {
                System.out.println("p = " + p.getId() + " o " + o);
                if (Integer.parseInt(o) == p.getId()) {
                    serviceCategoryId.add(p.getCategoryId());
                }
            }
        }
        return servicesId.length == serviceCategoryId.size();
    }

    /**
     * Rysakova Anna
     *
     * @param servicesId
     * @param product
     */
    public void updateFillTariff(String[] servicesId, Product product) {
        List<Product> oldServiceList = productDao.getServicesByTariff(product);
        ArrayList<Integer> oldServiceIdList = new ArrayList<>();
        for (Product p : oldServiceList) {
            oldServiceIdList.add(p.getId());
        }
        List newServicesId = Arrays.asList(servicesId);
        oldServiceIdList.removeAll(newServicesId);
        removeServiceFromTariff(oldServiceIdList, product);
    }

    /**
     * Rysakova Anna
     *
     * @param servicesId
     * @param product
     */
    public void updateTariffWithNewServices(String[] servicesId, Product product) {
        List<Product> oldServiceList = productDao.getServicesByTariff(product);
        ArrayList<Integer> oldServiceIdList = new ArrayList<>();
        for (Product p : oldServiceList) {
            oldServiceIdList.add(p.getId());
        }
        List newServicesId = Arrays.asList(servicesId);
        newServicesId.removeAll(oldServiceIdList);
        fillTariff(servicesId, product.getId());

    }

    /**
     * Rysakova Anna
     *
     * @param serviceIdList
     * @param product
     */
    private void removeServiceFromTariff(List serviceIdList, Product product) {
        for (Object o : serviceIdList) {
            productDao.deleteServiceFromTariff(product.getId(), (Integer) o);
        }
    }

    /**
     * Rysakova Anna
     *
     * @param updateProduct
     */
    public void updateProduct(Product updateProduct) {
        int productId = updateProduct.getId();
        Product product = productDao.getById(productId);
        if (!updateProduct.getName().equals("") & !updateProduct.getName().equals(product.getName())) {
            product.setName(updateProduct.getName());
        }
        if (!updateProduct.getDescription().equals("") & !updateProduct.getDescription().equals(product.getDescription())) {
            product.setDescription(updateProduct.getDescription());
        }
        if (!Objects.equals(updateProduct.getDurationInDays(), product.getDurationInDays())) {
            product.setDurationInDays(updateProduct.getDurationInDays());
        }
        if (!Objects.equals(updateProduct.getNeedProcessing(), product.getNeedProcessing())) {
            product.setNeedProcessing(updateProduct.getNeedProcessing());
        }
        if (!Objects.equals(updateProduct.getStatus(), product.getStatus())) {
            product.setStatus(updateProduct.getStatus());
        }
        productDao.update(product);
    }

    /**
     * This method takes user and returns all products that can be shown for him
     * on 'Order Service' page with goal to show user orders.
     * It firstly gets all the products that can be shown to user depending on his place
     * for individual user or all products for legal user.
     * Then it gets all orders by this users customer's representatives.
     * Then it sorts all products by categories and determines status for
     * every product for current user.
     * Status can be:
     * 'Active' if user order status for this product is Active,
     * 'In process' if user's order status for this product is 'In Process',
     * 'Suspended' if user's  order status for this product is 'Suspended',
     * 'In Tariff' if user has this service in one of his statuses
     * <code>Null</code> if user doesn't have order for this product or user's order status for this product is 'Deactivated'
     * created by Yuliya Pedash.
     *
     * @param user user
     * @return Map with Key - catogry name, Value - row that represents data that should b shown to user
     */
    public Map<String, List<ProductCatalogRow>> getCategoriesWithProductsForUser(User user) {
        List<Order> ordersByUsersCompanyAndPlace = orderDao.getOrdersByCustomerIdAndPlaceId(user.getCustomerId(),
                user.getPlaceId());
        List<Product> productsToShowWithoutStatuses;
        productsToShowWithoutStatuses = user.getRole() == Role.Individual ?
                productDao.getAllAvailableServicesByPlace(user.getPlaceId()) :
                productDao.getAllServices();
        Map<String, List<ProductCatalogRow>> categoriesWithProducts = new HashMap<>();
        List<Product> servicesOfCurrentUserTariff = productDao.getAllServicesByCurrentUserTarifff(user.getId());
        for (Product product : productsToShowWithoutStatuses) {
            String categoryName = productDao.getProductCategoryById(product.getCategoryId()).getName();
            if (!categoriesWithProducts.containsKey(categoryName)) {
                List<ProductCatalogRow> allProductCatalogRowsForCategory = new ArrayList<>();
                categoriesWithProducts.put(categoryName, allProductCatalogRowsForCategory);
            }
            List<ProductCatalogRow> allProductCatalogRowsForCategory = categoriesWithProducts.get(categoryName);
            OperationStatus operationStatus = getStatusForProduct(product, ordersByUsersCompanyAndPlace, servicesOfCurrentUserTariff);
            String status = operationStatus == null ? null : operationStatus.name();
            Price price = null;
            if (user.getRole() == Role.Individual) {
                price = priceDao.getPriceByProductIdAndPlaceId(product.getId(), user.getPlaceId());
            }
            ProductCatalogRow productCatalogRow = new ProductCatalogRow(product, status, price);
            allProductCatalogRowsForCategory.add(productCatalogRow);

        }
        return categoriesWithProducts;
    }

    /**
     * This method takes product and returns status for it.
     * If user does not have order for this product <code>Null</code> is returned.
     * Helper method for {@link #getCategoriesWithProductsForUser(User)}
     *
     * @param product      Product object
     * @param ordersByUser orders
     * @return operation status
     */
    private OperationStatus getStatusForProduct(Product product, List<Order> ordersByUser, List<Product> servicesIncludedInTariff) {
        for (Order order : ordersByUser) {
            if (order.getProductId().equals(product.getId())) {
                return order.getCurrentStatus();
            }
        }
        if (servicesIncludedInTariff.contains(product)) {
            return OperationStatus.InTariff;
        }
        return null;

    }
}