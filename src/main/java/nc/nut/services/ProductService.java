package nc.nut.services;


import nc.nut.dao.order.Order;
import nc.nut.dao.price.Price;
import nc.nut.dao.price.PriceDao;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.product.ProductType;
import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.dto.ProductCatalogRowDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Anna on 26.04.2017.
 */
@Service
public class ProductService {

    @Resource
    nc.nut.dao.order.OrderDao orderDao;
    @Resource
    private ProductDao productDao;
    @Resource
    private PriceDao priceDao;

    public Product getCategory(ProductCategories category, Product product) {
        if (!category.getName().equals("")) {
            productDao.addCategory(category);
            int newCategoryId = productDao.findIdCategory(category);
            product.setCategoryId(newCategoryId);
        }
        return product;
    }

    public boolean checkEmptyNewCategory(ProductCategories categories) {
        return !(!categories.getName().equals("") && categories.getDescription().equals(""));
    }

    public boolean checkEmptyFieldIfProduct(Product product) {
        return !(product.getName().equals("") || product.getDescription().equals(""));
    }

    public void saveProduct(Product product) {
        if (Objects.equals(product.getProductType(), ProductType.Tariff)) {
            product.setCategoryId(null);
            productDao.save(product);
        }
        if (Objects.equals(product.getProductType(), ProductType.Service)) {
            productDao.save(product);
        }
    }

    public void fillTariff(String service, int idTariff) {
        String[] arr = service.split(",");
        for (String a : arr) {
            productDao.fillTariff(idTariff, Integer.parseInt(a));
        }
    }

    public boolean checkUniqueCategoryServices(String services) {
        String[] arr = services.split(",");
        List<Product> allServices = productDao.getAllServices();
        Set<Integer> serviceCategoryId = new HashSet<>();
        for (String s : arr) {
            for (Product p : allServices) {
                if (Integer.parseInt(s) == p.getId()) {
                    serviceCategoryId.add(p.getCategoryId());
                }
            }
        }
        return arr.length == serviceCategoryId.size();
    }

    public void updateFillTariff(String service, Product product) {
        List<Product> oldServiceList = productDao.getServicesByTariff(product);
        ArrayList<Integer> oldServiceIdList = new ArrayList<>();
        for (Product p : oldServiceList) {
            oldServiceIdList.add(p.getId());
        }
        String[] arr = service.split(",");
        List newServiceList = Arrays.asList(arr);
        oldServiceIdList.removeAll(newServiceList);
        removeServiceFromTariff(oldServiceIdList, product);
    }

    public void updateTariffWithNewServices(String service, Product product) {
        List<Product> oldServiceList = productDao.getServicesByTariff(product);
        ArrayList<Integer> oldServiceIdList = new ArrayList<>();
        for (Product p : oldServiceList) {
            oldServiceIdList.add(p.getId());
        }
        String[] arr = service.split(",");
        List newServiceList = Arrays.asList(arr);
        newServiceList.removeAll(oldServiceIdList);
        for (Object i : newServiceList) {
            fillTariff((String) i, product.getId());
        }
    }

    public void removeServiceFromTariff(List serviceIdList, Product product) {
        for (Object s : serviceIdList) {
            productDao.deleteServiceFromTariff(product.getId(), (Integer) s);
        }
    }

    public void updateProduct(Product updateProduct) {
        int productId = updateProduct.getId();
        Product product = productDao.getById(productId);
        if (!updateProduct.getName().equals("") & !updateProduct.getName().equals(product.getName())) {
            product.setName(updateProduct.getName());
        }
        if (!updateProduct.getDescription().equals("") & !updateProduct.getDescription().equals(product.getDescription())) {
            product.setDescription(updateProduct.getDescription());
        }
        if (updateProduct.getDurationInDays() != product.getDurationInDays()) {
            product.setDurationInDays(updateProduct.getDurationInDays());
        }
        if (updateProduct.getProcessingStrategy() != product.getProcessingStrategy()) {
            product.setProcessingStrategy(updateProduct.getProcessingStrategy());
        }
        if (updateProduct.getStatus() != product.getStatus()) {
            product.setStatus(updateProduct.getStatus());
        }
        productDao.update(product);
    }

    /**
     * This method takes user and returns all products that can be applied to this user sorted
     * by categories.
     * It firstly gets all the products that can be shown to user depending on his place
     * for Residential user or all products for Business user.
     * For more details about user types see: {@link Role}
     * Then it gets all orders by this users customer's representatives.
     * Then it sorts all products by categories and determines status for
     * every product for current user.
     * For more details about statuses  see: {@link nc.nut.dao.entity.OperationStatus}
     * Besides statuses included in {@link nc.nut.dao.entity.OperationStatus} in current
     * business case statue can also be:
     * 'In Tariff' if user has this service in one of his tariffs
     * <code>Null</code> if user doesn't have order for this product or user's order status for this product is 'Deactivated'
     * created by Yuliya Pedash.
     *
     * @param user user
     * @return Map with Key - category name, Value - data transfer object
     * @see ProductCatalogRowDTO
     */
    public Map<String, List<ProductCatalogRowDTO>> getCategoriesWithProductsForUser(User user) {
        List<Order> orders = orderDao.getOrdersByCustomerIdAndPlaceId(user.getCustomerId(),
                user.getPlaceId());
        List<Product> productWithoutStatuses = user.getRole() == Role.Individual ?
                productDao.getAllAvailableServicesByPlace(user.getPlaceId()) :
                productDao.getAllServices();
        Map<String, List<ProductCatalogRowDTO>> categoriesWithProducts = new HashMap<>();
        List<Product> servicesOfCurrentUserTariff = productDao.getAllServicesByCurrentUserTarifff(user.getId());
        for (Product product : productWithoutStatuses) {
            String categoryName = productDao.getProductCategoryById(product.getCategoryId()).getName();
            if (!categoriesWithProducts.containsKey(categoryName)) {
                categoriesWithProducts.put(categoryName, new ArrayList<>());
            }
            List<ProductCatalogRowDTO> allProductCatalogRowsForCategoryDTO = categoriesWithProducts.get(categoryName);
            String status = getStatusForProductAsString(product, orders, servicesOfCurrentUserTariff);
            Price price = null;
            if (user.getRole() == Role.Individual) {
                price = priceDao.getPriceByProductIdAndPlaceId(product.getId(), user.getPlaceId());
            } else {
                price = new Price(null, product.getId(), product.getBasePrice());
            }
            ProductCatalogRowDTO productCatalogRowDTO = new ProductCatalogRowDTO(product, status, price);
            allProductCatalogRowsForCategoryDTO.add(productCatalogRowDTO);

        }
        return categoriesWithProducts;
    }

    /**
     * This method takes product and returns status for it as String
     * If user does not have order for this product <code>Null</code> is returned.
     * If user has this product in his tariff <code>String</code> with value "In Tariff" returned.
     * Helper method for {@link #getCategoriesWithProductsForUser(User)}
     *
     * @param product                  Product object
     * @param ordersByUser             orders
     * @param servicesIncludedInTariff list with services included in user current tariff
     * @return String value of operation status
     */
    private String getStatusForProductAsString(Product product, List<Order> ordersByUser, List<Product> servicesIncludedInTariff) {
        for (Order order : ordersByUser) {
            if (order.getProductId().equals(product.getId())) {
                return order.getCurrentStatus().getName();
            }
        }
        if (servicesIncludedInTariff.contains(product)) {
            return "In Tariff";
        }
        return null;

    }
}