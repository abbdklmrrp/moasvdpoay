package jtelecom.services.product;


import jtelecom.dao.entity.OperationStatus;
import jtelecom.dao.order.Order;
import jtelecom.dao.order.OrderDao;
import jtelecom.dao.price.PriceDao;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.product.ProductDao;
import jtelecom.dao.user.Role;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.ProductCatalogRowDTO;
import jtelecom.dto.TariffServiceDto;
import jtelecom.services.mail.MailService;
import jtelecom.util.CollectionUtil;
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
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Resource
    OrderDao orderDao;
    @Resource
    private ProductDao productDao;
    @Resource
    private PriceDao priceDao;
    @Resource
    private UserDAO userDAO;
    @Resource
    private MailService mailService;

    /**
     * Rysakova Anna
     *
     * @param category
     * @param product
     * @return
     */
    @Override
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
    @Override
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
        // FIXME: 11.05.2017 getServiceById hash mmap
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
        List<TariffServiceDto> oldServiceList = productDao.getServicesByTariff(product.getId());
        List<TariffServiceDto> newServiceList = fillInDTOForBatchUpdate(product.getId(), servicesId);

        List<TariffServiceDto> uniqueServicesInFirstCollection = (List<TariffServiceDto>) CollectionUtil
                .getUniqueElementsInFirstCollection(oldServiceList, newServiceList);
        productDao.deleteServiceFromTariff(uniqueServicesInFirstCollection);

        uniqueServicesInFirstCollection = (List<TariffServiceDto>) CollectionUtil
                .getUniqueElementsInFirstCollection(newServiceList, oldServiceList);
        productDao.fillInTariffWithServices(uniqueServicesInFirstCollection);
    }

    /**
     * Anna Rysakova
     *
     * @param idTariff
     * @param arrayOfIdServices
     */
    @Override
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
        for (int i = 0; i < arrayOfIdServices.length; i++) {
            if (arrayOfIdServices[i] != null) {
                TariffServiceDto tariffServiceDto = new TariffServiceDto();
                tariffServiceDto.setTariffId(idTariff);
                tariffServiceDto.setServiceId(arrayOfIdServices[i]);
                products.add(tariffServiceDto);
            }
        }
        return products;
    }

    /**
     * Rysakova Anna
     *
     * @param updateProduct
     */
    @Override
    public boolean updateProduct(Product updateProduct) {
        int productId = updateProduct.getId();
        Product product = productDao.getById(productId);
        if (!updateProduct.getName().isEmpty()) {
            product.setName(updateProduct.getName());
        }
        if (!updateProduct.getDescription().isEmpty()) {
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
        if (updateProduct.getCustomerType() != product.getCustomerType()) {
            product.setCustomerType(updateProduct.getCustomerType());
        }
        return productDao.update(product);
    }

    /**
     * This method takes user and returns all products that can be applied to this user sorted
     * by categories.
     * It firstly gets all the products that can be shown to user depending on his place
     * for RESIDENTIAL user or all products for BUSINESS user.
     * For more details about user types see: {@link Role}
     * Then it gets all orders by this users customer's representatives.
     * Then it sorts all products by categories and determines status for
     * every product for current user.
     * For more details about statuses  see: {@link OperationStatus}
     * Besides statuses included in {@link OperationStatus} in current
     * business case statue can also be:
     * 'In Tariff' if user has this service in one of his tariffs
     * <code>Null</code> if user doesn't have order for this product or user's order status for this product is 'Deactivated'
     * created by Yuliya Pedash.
     *
     * @param user user
     * @return Map with Key - category name, Value - data transfer object
     * @see ProductCatalogRowDTO
     */
//    public Map<String, List<ProductCatalogRowDTO>> getCategoriesWithProductsForUser(User user) {
//        List<Order> orders = orderDao.getOrdersByCustomerId(user.getCustomerId());
//        List<Product> productWithoutStatuses = user.getRole() == Role.RESIDENTIAL ?
//                productDao.getAllAvailableServicesByPlace(user.getPlaceId()) :
//                productDao.getServicesAvailableForCustomer();
//        Map<String, List<ProductCatalogRowDTO>> categoriesWithProducts = new HashMap<>();
//        List<Product> servicesOfCurrentUserTariff = productDao.getAllServicesByCurrentUserTariff(user.getId());
//        for (Product product : productWithoutStatuses) {
//            String categoryName = productDao.getProductCategoryById(product.getCategoryId()).getCategoryName();
//            if (!categoriesWithProducts.containsKey(categoryName)) {
//                categoriesWithProducts.put(categoryName, new ArrayList<>());
//            }
//            List<ProductCatalogRowDTO> allProductCatalogRowsForCategoryDTO = categoriesWithProducts.get(categoryName);
//            String status = getStatusForProductAsString(product, orders, servicesOfCurrentUserTariff);
//            Price price;
//            if (user.getRole() == Role.RESIDENTIAL) {
//                price = priceDao.getPriceByProductIdAndPlaceId(product.getId(), user.getPlaceId());
//            } else {
//                price = new Price(null, product.getId(), product.getBasePrice());
//            }
//            ProductCatalogRowDTO productCatalogRowDTO = new ProductCatalogRowDTO(product, status);
//            allProductCatalogRowsForCategoryDTO.add(productCatalogRowDTO);
//
//        }
//        return categoriesWithProducts;
//    }

    /**
     * Yuiya Pedash
     *
     * @param start
     * @param length
     * @param sort
     * @param search
     * @param user
     * @param categoryId
     * @return
     */
    public List<ProductCatalogRowDTO> getLimitedServicesForUser(User user, Integer start, Integer length, String sort, String search, Integer categoryId) {
        Map<Integer, String> productCategories = new HashMap<>();
        List<Order> orders = orderDao.getOrdersByCustomerId(user.getCustomerId());
        List<Product> products = user.getRole() == Role.RESIDENTIAL ?
                productDao.getLimitedServicesForResidential(start, length, sort, search, categoryId, user.getPlaceId()) :
                productDao.getLimitedServicesForBusiness(start, length, sort, search, categoryId);
        List<Product> servicesOfCurrentUserTariff = productDao.getAllServicesByCurrentUserTariff(user.getId());
        List<ProductCatalogRowDTO> productCatalogRowDTOS = new ArrayList<>();
        for (Product product : products) {
            String categoryName;
            Integer productCategoryId = product.getCategoryId();
            if (productCategories.containsKey(productCategoryId)) {
                categoryName = productCategories.get(product.getCategoryId());
            } else {
                categoryName = productDao.getProductCategoryById(product.getCategoryId()).getCategoryName();
                productCategories.put(categoryId, categoryName);
            }
            String status = getStatusForProductAsString(product, orders, servicesOfCurrentUserTariff);
            ProductCatalogRowDTO productCatalogRowDTO = new ProductCatalogRowDTO(product.getId(), product.getName(), categoryName, product.getBasePrice(), status);
            productCatalogRowDTOS.add(productCatalogRowDTO);
        }
        return productCatalogRowDTOS;
    }

    /**
     * This method takes product and returns status for it as String
     * If user does not have order for this product <code>Null</code> is returned.
     * If user has this product in his tariff <code>String</code> with value "In Tariff" returned.
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

    /**
     * Yuliya Pedash
     *
     * @return
     */
    public Product getProductForUser(User currentUser, Integer productId) {
        if (currentUser.getRole() == Role.BUSINESS) {
            return productDao.getById(productId);
        }
        return productDao.findProductWithPriceSetByPlace(productId, currentUser.getPlaceId());
    }

    /**
     * Yuliya Pedash
     *
     * @param user
     * @param search
     * @param categoryId
     * @return
     */
    public Integer getCountForServicesWithSearch(User user, String search, Integer categoryId) {
        return user.getRole() == Role.RESIDENTIAL ?
                productDao.getCountForLimitedServicesForResidential(search, categoryId, user.getPlaceId()) :
                productDao.getCountForLimitedServicesForBusiness(search, categoryId);

    }

    @Override
    public Integer saveProduct(Product product) {
        Integer isSave=productDao.saveProduct(product);
        if(isSave!=null){
          List<User> users=userDAO.getUsersByCustomerType(product.getCustomerType());
          mailService.sendNewProductDispatch(users,product);
        }
        return isSave;
    }

    @Override
    public boolean disableEnableProduct(int productId) {
        Product product=productDao.getById(productId);
        boolean success=productDao.disableEnableProduct(product);
        if(success){
            if(product.getStatus().getId()==0){
                List<User> users=userDAO.getUsersByCustomerType(product.getCustomerType());
                mailService.sendNewProductDispatch(users,product);
            }
            if(product.getStatus().getId()==1){
                List<User> users=userDAO.getUsersByProductId(productId);
                mailService.sendProductDeletedDispatch(users,product);}
        }
        return success;
    }
}