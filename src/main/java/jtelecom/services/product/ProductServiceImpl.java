package jtelecom.services.product;


import jtelecom.dao.entity.CustomerType;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Rysakova Anna on 26.04.2017.
 */
@Component
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

    @Override
    public Product foundProduct(Integer productId) {
        return productDao.getById(productId);
    }

    /**
     * If <code>ProductCategories</code> was created, this method add
     * it to database and return <code>ID</code> of new <code>ProductCategories</code>.
     * If new <code>ProductCategories</code> was created,
     * received key of new <code>ProductCategories</code> set to <code>Product</code> if
     *
     * @param category <code>Product</code> category
     * @param product incoming object of <code>Product</code>
     * @return <code>Product</code>
     */
    @Override
    public Product getCategory(ProductCategories category, Product product) {
        if (!category.getCategoryName().isEmpty()) {
            Integer categoryId = productDao.addCategory(category);
            product.setCategoryId(categoryId);
        }
        return product;
    }

    /**
     * Returns {@code true} if, and only if, fields {@code categoryName} and {@code categoryDescription}
     * of {@link ProductCategories} is {@code 0}.
     *
     * @param categories incoming object of {@link ProductCategories}
     * @return {@code true} if fields of{@link ProductCategories} is {@code 0}, otherwise
     * {@code false}
     */
    @Override
    public boolean isEmptyFieldsOfNewCategory(ProductCategories categories) {
        return (categories.getCategoryName().isEmpty() ^ categories.getCategoryDescription().isEmpty());
    }

    /**
     * Returns {@code true} if, and only if, fields {@code name} and {@code description}of {@link Product} is {@code 0}.
     *
     * @param product incoming object of {@link Product}
     * @return {@code true} if fields of{@link Product} is {@code 0}, otherwise
     * {@code false}
     */
    @Override
    public boolean isEmptyFieldOfProduct(Product product) {
        return (product.getName().isEmpty() || product.getDescription().isEmpty());
    }

    /**
     * If {@code Product} customer type is {@code Residential},
     * this method set {@code basePrice}  to {@code null}
     *
     * @param product {@code Product}
     */
    @Override
    public void validateBasePriceByCustomerType(Product product) {
        if (product.getCustomerType() == CustomerType.Residential) {
            product.setBasePrice(null);
        }
    }

    /**
     * <p>This method update services in tariff. For this, the method takes out old services from the database to {@link List}
     * and compare with {@link List} of new services. </p>
     * <p>First, services that are not included in the new list are deleted
     * Then new services are inserted into the database</p>
     *
     * @param servicesId {@code Array} of ID services
     * @param product {@code Product}
     */
    public void updateFillingOfTariffsWithServices(Integer[] servicesId, Product product) throws DataIntegrityViolationException {
        List<TariffServiceDto> oldServiceList = productDao.getServicesByTariff(product.getId());
        List<TariffServiceDto> newServiceList = fillInDTOForBatchUpdate(product.getId(), servicesId);

        List<TariffServiceDto> uniqueServicesInFirstCollection = (List<TariffServiceDto>) CollectionUtil
                .firstCollectionMinusSecondCollection(oldServiceList, newServiceList);
        productDao.deleteServiceFromTariff(uniqueServicesInFirstCollection);

        uniqueServicesInFirstCollection = (List<TariffServiceDto>) CollectionUtil
                .firstCollectionMinusSecondCollection(newServiceList, oldServiceList);
        productDao.fillInTariffWithServices(uniqueServicesInFirstCollection);
    }

    /**
     * This method insert to database received {@link ArrayList} of {@link TariffServiceDto}
     * which contains tariff ID and array of ID services.
     *
     * @param idTariff tariff ID
     * @param arrayOfIdServices array of ID services
     */
    @Override
    public void fillInTariffWithServices(Integer idTariff, Integer[] arrayOfIdServices) {
        ArrayList<TariffServiceDto> products = fillInDTOForBatchUpdate(idTariff, arrayOfIdServices);
        productDao.fillInTariffWithServices(products);
    }

    /**
     * This method convert received ID tariff, array of ID services to {@link TariffServiceDto}
     *
     * @param idTariff tariff ID
     * @param arrayOfIdServices array of ID services
     * @return {@link ArrayList} of {@link TariffServiceDto}
     * which contains tariff ID and array of ID services.
     */
    private ArrayList<TariffServiceDto> fillInDTOForBatchUpdate(Integer idTariff, Integer[] arrayOfIdServices) throws NumberFormatException {
        ArrayList<TariffServiceDto> products = new ArrayList<>();
        for (Integer arrayOfIdService : arrayOfIdServices) {
            if (arrayOfIdService != null) {
                TariffServiceDto tariffServiceDto = new TariffServiceDto();
                tariffServiceDto.setTariffId(idTariff);
                tariffServiceDto.setServiceId(arrayOfIdService);
                products.add(tariffServiceDto);
            }
        }
        return products;
    }

    /**
     * This method compare new received {@link Product} with existing in database.
     * If this {@link Product} exist, method compare these fields and rewrite {@link Product}.
     * Received object of {@code Product} update to database.
     *
     * @param updateProduct {@link Product} for update
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
        if (currentUser.getRole() == Role.RESIDENTIAL) {
            return productDao.findProductWithPriceSetByPlace(productId, currentUser.getPlaceId());
        }
        return productDao.getById(productId);

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