package jtelecom.services.product;


import jtelecom.dao.entity.CustomerType;
import jtelecom.dao.order.Order;
import jtelecom.dao.order.OrderDAO;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.product.ProductDAO;
import jtelecom.dao.user.Role;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.ServicesCatalogRowDTO;
import jtelecom.dto.TariffServiceDTO;
import jtelecom.services.mail.MailService;
import jtelecom.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Anna Rysakova
 */
@Component
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Resource
    private OrderDAO orderDAO;
    @Resource
    private ProductDAO productDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private MailService mailService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Product isValidProduct(Integer productId) throws EmptyResultDataAccessException {
        try {
            logger.debug("Received product ID {} ", productId);
            return productDAO.getById(productId);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Product doesn't exist ", ex);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmptyFieldOfProduct(Product product) {
        logger.debug("Received product {} ", product.toString());
        return (product.getName().isEmpty() || product.getDescription().isEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmptyFieldsOfNewCategory(ProductCategories categories) {
        logger.debug("Received product category {} ", categories.toString());
        return (categories.getCategoryName().isEmpty() ^ categories.getCategoryDescription().isEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateBasePriceByCustomerType(Product product) {
        logger.debug("Received product {} ", product.toString());
        if (product.getCustomerType() == CustomerType.Residential) {
            logger.debug("Product customer type {} ", product.getCustomerType());
            product.setBasePrice(null);
            logger.debug("Product base price {} ", product.getBasePrice());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product getCategory(ProductCategories category, Product product) {
        logger.debug("Received product {} with category {}", product.toString(), category.toString());
        if (!category.getCategoryName().isEmpty()) {
            Integer categoryId = productDAO.saveCategory(category);
            logger.debug("Received ID of saved category {} ", categoryId);
            product.setCategoryId(categoryId);
        }
        return product;
    }

    /**
     * Method remove comma from string if comma is last character
     *
     * @param str {@code String} field of object
     * @return {@code String} without comma
     */
    private String removeComma(String str) {
        if (str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.replaceAll(",", "");
        }
        return str;
    }

    /**
     * Method remove comma from {@code Product} name and description
     *
     * @param product {@code Product} object
     * @return {@code Product} object without comma at last character
     */
    private Product validNameDescription(Product product) {
        String productName = removeComma(product.getName());
        String productDescription = removeComma(product.getDescription());
        product.setName(productName);
        product.setDescription(productDescription);
        return product;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateProduct(Product updateProduct) {
        int productId = updateProduct.getId();
        Product product = productDAO.getById(productId);
        logger.debug("Found product from database {} ", product.toString());
        updateProduct = validNameDescription(updateProduct);
        if (!updateProduct.getName().isEmpty()) {
            logger.debug("Change product name to {} ", updateProduct.getName());
            product.setName(updateProduct.getName());
        }
        if (!updateProduct.getDescription().isEmpty()) {
            logger.debug("Change product description to {} ", updateProduct.getDescription());
            product.setDescription(updateProduct.getDescription());
        }
        if (!Objects.equals(updateProduct.getDurationInDays(), product.getDurationInDays())) {
            logger.debug("Change product duration in days to {} ", updateProduct.getDurationInDays());
            product.setDurationInDays(updateProduct.getDurationInDays());
        }
        if (updateProduct.getProcessingStrategy() != product.getProcessingStrategy()) {
            logger.debug("Change product processing status to {} ", updateProduct.getProcessingStrategy());
            product.setProcessingStrategy(updateProduct.getProcessingStrategy());
        }
        if (updateProduct.getStatus() != product.getStatus()) {
            logger.debug("Change product status to {} ", updateProduct.getStatus());
            product.setStatus(updateProduct.getStatus());
        }
        if (!Objects.equals(updateProduct.getBasePrice(), product.getBasePrice())) {
            logger.debug("Change product base price to {} ", updateProduct.getBasePrice());
            product.setBasePrice(updateProduct.getBasePrice());
        }
        if (updateProduct.getCustomerType() != product.getCustomerType()) {
            logger.debug("Change product customer type to {} ", updateProduct.getCustomerType());
            product.setCustomerType(updateProduct.getCustomerType());
        }
        return productDAO.update(product);
    }

    /**
     * This method convert received ID tariff, array of ID services to {@link TariffServiceDTO}
     *
     * @param idTariff          tariff ID
     * @param arrayOfIdServices array of ID services
     * @return {@code ArrayList} of {@code TariffServiceDTO}
     * which contains tariff ID and array of ID services.
     * @see ArrayList
     * @see TariffServiceDTO
     */
    private ArrayList<TariffServiceDTO> fillInDTOForBatchUpdate(Integer idTariff, Integer[] arrayOfIdServices) {
        ArrayList<TariffServiceDTO> products = new ArrayList<>();
        for (Integer arrayOfIdService : arrayOfIdServices) {
            if (arrayOfIdService != null) {
                TariffServiceDTO tariffServiceDTO = new TariffServiceDTO();
                tariffServiceDTO.setTariffId(idTariff);
                tariffServiceDTO.setServiceId(arrayOfIdService);
                products.add(tariffServiceDTO);
                logger.debug("to list was add object TariffServiceDTO {} ", tariffServiceDTO);

            }
        }
        return products;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillInTariffWithServices(Integer idTariff, Integer[] arrayOfIdServices) {
        logger.debug("Tariff ID {}, services ID", idTariff, arrayOfIdServices);
        ArrayList<TariffServiceDTO> products = fillInDTOForBatchUpdate(idTariff, arrayOfIdServices);
        productDAO.fillInTariffWithServices(products);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateFillingOfTariffsWithServices(Integer[] servicesId, Integer productId) {
        List<TariffServiceDTO> oldServiceList = productDAO.getServicesIDByTariff(productId);
        logger.debug("Old services by tariff ID {}", oldServiceList.toString());

        List<TariffServiceDTO> newServiceList = fillInDTOForBatchUpdate(productId, servicesId);
        logger.debug("New services by tariff ID {}", newServiceList.toString());

        List<TariffServiceDTO> uniqueServicesInFirstCollection = (List<TariffServiceDTO>) CollectionUtil
                .firstCollectionMinusSecondCollection(oldServiceList, newServiceList);
        logger.debug("Unique elements in oldServiceList {}", uniqueServicesInFirstCollection.toString());
        productDAO.deleteServiceFromTariff(uniqueServicesInFirstCollection);

        uniqueServicesInFirstCollection = (List<TariffServiceDTO>) CollectionUtil
                .firstCollectionMinusSecondCollection(newServiceList, oldServiceList);
        logger.debug("Unique elements in newServiceList {}", uniqueServicesInFirstCollection.toString());

        productDAO.fillInTariffWithServices(uniqueServicesInFirstCollection);
    }

    /**
     * @autor Yuliya Pedash
     * {@inheritDoc}
     */
    public List<ServicesCatalogRowDTO> getLimitedServicesForUser(User user, Integer start, Integer length, String sort, String search, Integer categoryId) {
        Map<Integer, String> productCategories = new HashMap<>();
        List<Order> orders = orderDAO.getOrdersByCustomerId(user.getCustomerId());
        List<Product> products = user.getRole() == Role.RESIDENTIAL ?
                productDAO.getLimitedServicesForResidential(start, length, sort, search, categoryId, user.getPlaceId()) :
                productDAO.getLimitedServicesForBusiness(start, length, sort, search, categoryId);
        List<Product> servicesOfCurrentUserTariff = productDAO.getAllServicesByCurrentUserTariff(user.getId());
        List<ServicesCatalogRowDTO> servicesCatalogRowDTOS = new ArrayList<>();
        for (Product product : products) {
            String categoryName;
            Integer productCategoryId = product.getCategoryId();
            if (productCategories.containsKey(productCategoryId)) {
                categoryName = productCategories.get(product.getCategoryId());
            } else {
                categoryName = productDAO.getProductCategoryById(product.getCategoryId()).getCategoryName();
                productCategories.put(categoryId, categoryName);
            }
            String status = getStatusForProductAsString(product, orders, servicesOfCurrentUserTariff);
            ServicesCatalogRowDTO servicesCatalogRowDTO = new ServicesCatalogRowDTO(product.getId(), product.getName(), categoryName, product.getBasePrice(), status);
            servicesCatalogRowDTOS.add(servicesCatalogRowDTO);
        }
        return servicesCatalogRowDTOS;
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
     * @autor Yuliya Pedash
     * {@inheritDoc}
     */
    public Product getProductForUser(User currentUser, Integer productId) {
        if (currentUser.getRole() == Role.RESIDENTIAL) {
            return productDAO.findProductWithPriceSetByPlace(productId, currentUser.getPlaceId());
        }
        return productDAO.getById(productId);

    }

    /**
     * @autor Yuliya Pedash
     * {@inheritDoc}
     */
    public Integer getCountForServicesWithSearch(User user, String search, Integer categoryId) {
        return user.getRole() == Role.RESIDENTIAL ?
                productDAO.getCountForLimitedServicesForResidential(search, categoryId, user.getPlaceId()) :
                productDAO.getCountForLimitedServicesForBusiness(search, categoryId);

    }

    @Override
    public Integer saveProduct(Product product) {
        product = validNameDescription(product);
        Integer isSave = productDAO.saveProduct(product);
        if (isSave != null) {
            List<User> users = userDAO.getUsersByCustomerType(product.getCustomerType());
            mailService.sendNewProductDispatch(users, product);
        }
        return isSave;
    }

    @Override
    public boolean disableEnableProduct(int productId) {
        Product product = productDAO.getById(productId);
        boolean success = productDAO.disableEnableProduct(product);
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