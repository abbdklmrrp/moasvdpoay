package nc.nut.services;


import nc.nut.dao.order.Order;
import nc.nut.dao.price.Price;
import nc.nut.dao.price.PriceDao;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.dto.ProductCatalogRowDTO;
import nc.nut.dto.TariffServiceDto;
import nc.nut.util.ProductUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    /**
     * Rysakova Anna
     *
     * @param category
     * @param product
     * @return
     */
    public Product getCategory(ProductCategories category, Product product) {
        if (!category.getCategoryName().isEmpty()) {
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
    public boolean isEmptyFieldsOfNewCategory(ProductCategories categories) {
        return (categories.getCategoryName().isEmpty() ^ categories.getCategoryDescription().isEmpty());
    }

    /**
     * Rysakova Anna
     *
     * @param product
     * @return
     */
    public boolean isEmptyFieldOfProduct(Product product) {
        return (product.getName().isEmpty() || product.getDescription().isEmpty() || (product.getBasePrice().compareTo(BigDecimal.ZERO) <= 0));
    }

    /**
     * Rysakova Anna
     *
     * @param servicesIdArray
     * @return
     */
    public boolean isCategoriesUnique(Integer[] servicesIdArray) {
        List<Product> allServices = productDao.getAllServices();
        Set<Integer> serviceCategoryId = new HashSet<>();
        for (Integer serviceId : servicesIdArray) {
            for (Product product : allServices) {
                if (Objects.equals(serviceId, product.getId())) {
                    serviceCategoryId.add(product.getCategoryId());
                }
            }
        }
        return servicesIdArray.length == serviceCategoryId.size();
    }

    /**
     * Rysakova Anna
     *
     * @param servicesId
     * @param product
     */
    public void updateFillingOfTariffsWithServices(Integer[] servicesId, Product product) {
        List<Integer> oldServiceIdList = getIdServicesOfTariff(product);
        List<Integer> newServicesId = ProductUtil.convertArrayToCollection(servicesId);

        Collection uniqueServicesInFirstCollection = ProductUtil.getUniqueElementsInFirstCollection(oldServiceIdList, newServicesId);
        Integer[] servicesToRemove = ProductUtil.convertCollectionToArray(uniqueServicesInFirstCollection);
        ArrayList<TariffServiceDto> tariffServiceDtos = fillInDTOForBatchUpdate(product.getId(), servicesToRemove);
        productDao.deleteServiceFromTariff(tariffServiceDtos);

        uniqueServicesInFirstCollection = ProductUtil.getUniqueElementsInFirstCollection(newServicesId, oldServiceIdList);
        Integer[] servicesToFillInTariff = ProductUtil.convertCollectionToArray(uniqueServicesInFirstCollection);
        tariffServiceDtos = fillInDTOForBatchUpdate(product.getId(), servicesToFillInTariff);
        productDao.fillInTariffWithServices(tariffServiceDtos);
    }

    /**
     * Anna Rysakova
     *
     * @param product
     * @return
     */
    private ArrayList<Integer> getIdServicesOfTariff(Product product) {
        List<Product> serviceList = productDao.getServicesByTariff(product);
        ArrayList<Integer> serviceIdList = new ArrayList<>();
        for (Product product1 : serviceList) {
            serviceIdList.add(product1.getId());
        }
        return serviceIdList;
    }

    /**
     * Anna Rysakova
     *
     * @param idTariff
     * @param arrayOfIdServices
     */
    public void fillInTariffWithServices(Integer idTariff, Integer[] arrayOfIdServices) {
        ArrayList<TariffServiceDto> products = fillInDTOForBatchUpdate(idTariff, arrayOfIdServices);
        productDao.fillInTariffWithServices(products);
    }

    /**
     * Anna Rysakova
     *
     * @param idTariff
     * @param arrayOfIdServices
     * @return
     */
    private ArrayList<TariffServiceDto> fillInDTOForBatchUpdate(Integer idTariff, Integer[] arrayOfIdServices) {
        ArrayList<TariffServiceDto> products = new ArrayList<>();
        for (Integer idService : arrayOfIdServices) {
            TariffServiceDto tariffServiceDto = new TariffServiceDto();
            tariffServiceDto.setIdTariff(idTariff);
            tariffServiceDto.setIdService(idService);
            products.add(tariffServiceDto);
        }
        return products;
    }

    /**
     * Rysakova Anna
     *
     * @param arrayOfIdServices
     * @param product
     */
    // TODO: 07.05.2017
    public void updateTariffWithNewServices(Integer[] arrayOfIdServices, Product product) {
        ArrayList<Integer> oldServiceIdList = getIdServicesOfTariff(product);
        ArrayList<Integer> newServiceIdList = ProductUtil.convertArrayToCollection(arrayOfIdServices);

//        productDao.fillInTariffWithServices(product.getId(), arrayOfIdServices);

    }

    /**
     * Rysakova Anna
     *
     * @param updateProduct
     */
    public boolean updateProduct(Product updateProduct) {
        int productId = updateProduct.getId();
        Product product = productDao.getById(productId);
        if (!updateProduct.getName().isEmpty() & !updateProduct.getName().equals(product.getName())) {
            product.setName(updateProduct.getName());
        }
        if (!updateProduct.getDescription().isEmpty() & !updateProduct.getDescription().equals(product.getDescription())) {
            product.setDescription(updateProduct.getDescription());
        }
        if (!Objects.equals(updateProduct.getDurationInDays(), product.getDurationInDays())) {
            product.setDurationInDays(updateProduct.getDurationInDays());
        }
        if (updateProduct.getProcessingStrategy() != product.getProcessingStrategy()) {
            product.setProcessingStrategy(updateProduct.getProcessingStrategy());
        }
        if (updateProduct.getStatus() != product.getStatus()) {
            product.setStatus(updateProduct.getStatus());
        }
        if (!Objects.equals(updateProduct.getBasePrice(), product.getBasePrice())) {
            product.setBasePrice(updateProduct.getBasePrice());
        }
        return productDao.update(product);
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
        List<Order> orders = orderDao.getOrdersByCustomerId(user.getCustomerId());
        List<Product> productWithoutStatuses = user.getRole() == Role.Individual ?
                productDao.getAllAvailableServicesByPlace(user.getPlaceId()) :
                productDao.getAllServices();
        Map<String, List<ProductCatalogRowDTO>> categoriesWithProducts = new HashMap<>();
        List<Product> servicesOfCurrentUserTariff = productDao.getAllServicesByCurrentUserTariff(user.getId());
        for (Product product : productWithoutStatuses) {
            String categoryName = productDao.getProductCategoryById(product.getCategoryId()).getCategoryName();
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