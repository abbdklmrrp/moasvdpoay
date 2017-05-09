package nc.nut.dao.product;

import nc.nut.dao.user.UserDAO;
import nc.nut.dto.TariffServiceDto;
import nc.nut.mail.Mailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rysakova Anna on 23.04.2017.
 */
@Service
public class ProductDaoImpl implements ProductDao {

    private static Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

    private final static String FIND_ALL_CATEGORIES = "SELECT * FROM PRODUCT_CATEGORIES";
    private final static String FIND_CATEGORY = "SELECT ID FROM PRODUCT_CATEGORIES WHERE NAME=:name";
    private final static String FIND_TYPES = "SELECT NAME FROM PRODUCT_TYPES";
    private final static String FIND_SERVICES = "SELECT * FROM PRODUCTS WHERE TYPE_ID=2 ORDER BY ID";
    private final static String FIND_TARIFFS = "SELECT * FROM PRODUCTS WHERE TYPE_ID=1 ORDER BY ID";
    private final static String FIND_ENABLED_TARIFFS = "SELECT * FROM PRODUCTS WHERE TYPE_ID=1 AND STATUS=1 ORDER BY ID";
    private final static String FIND_PRODUCT_BY_ID = "SELECT * FROM PRODUCTS WHERE ID=:id";

    private final static String FIND_ALL_PRODUCTS = "SELECT ID, TYPE_ID, NAME, DURATION, DESCRIPTION, BASE_PRICE, STATUS FROM PRODUCTS ORDER BY ID";

    private final static String FIND_SERVICES_BY_TARIFF = "SELECT " +
            "p.ID," +
            "p.CATEGORY_ID," +
            "p.NAME," +
            "p.DURATION," +
            "p.NEED_PROCESSING," +
            "p.DESCRIPTION," +
            "p.STATUS " +
            "FROM PRODUCTS p " +
            "JOIN TARIFF_SERVICES ts ON (p.ID=ts.SERVICE_ID) " +
            "WHERE ts.TARIFF_ID=:tariffId";

    private final static String FIND_ALL_SERVICES = "SELECT prod.ID, prod.NAME, prod.DESCRIPTION,prod.DURATION,prod.NEED_PROCESSING,\n" +
            "prod.TYPE_ID,prod.CATEGORY_ID\n" +
            "FROM PRODUCTS prod JOIN PRODUCT_TYPES pTypes ON (prod.TYPE_ID=pTypes.ID)\n" +
            "JOIN PRODUCT_CATEGORIES pCategories ON (prod.CATEGORY_ID=pCategories.ID)\n" +
            "WHERE prod.STATUS=1 AND pTypes.name='Service' AND pCategories.name=:categoryName";

    private final static String FIND_SERVICES_NOT_IN_TARIFF = "SELECT p.ID,p.CATEGORY_ID,p.NAME,p.BASE_PRICE,p.DURATION,p.NEED_PROCESSING,p.DESCRIPTION,p.STATUS " +
            "FROM PRODUCTS p" +
            " WHERE p.ID NOT IN " +
            "                  (SELECT ts.SERVICE_ID FROM TARIFF_SERVICES ts  " +
            "                  WHERE ts.TARIFF_ID=:tariffId) AND p.TYPE_ID=2";

    private final static String FIND_ALL_SERVICES_WITH_CATEGORY = "SELECT " +
            "prod.ID, " +
            "prod.NAME, " +
            "prod.CATEGORY_ID, " +
            "pCategories.NAME as Category " +
            "FROM PRODUCTS prod " +
            "JOIN PRODUCT_TYPES pTypes ON (prod.TYPE_ID=pTypes.ID) " +
            "JOIN PRODUCT_CATEGORIES pCategories ON (prod.CATEGORY_ID=pCategories.ID) " +
            "WHERE prod.STATUS=1 AND pTypes.name='Service'";

    private final static String FIND_ALL_FREE_TARIFFS = "SELECT p.ID,\n" +
            "p.NAME\n" +
            "FROM PRODUCTS p\n" +
            "JOIN PRODUCT_TYPES ptype ON(p.TYPE_ID=ptype.ID)\n" +
            "LEFT JOIN TARIFF_SERVICES ts ON(p.ID=ts.TARIFF_ID)\n" +
            "WHERE ptype.name='Tariff' AND ts.TARIFF_ID IS NULL";

    private final static String UPDATE_SERVICE = "UPDATE PRODUCTS SET NAME=:name," +
            "DURATION=:duration,NEED_PROCESSING=:needProcessing," +
            "DESCRIPTION=:description,STATUS=:status WHERE ID=:id";

    private final static String ADD_TARIFF_SERVICE = "INSERT INTO TARIFF_SERVICES(TARIFF_ID,SERVICE_ID) VALUES(:tariffId,:serviceId)";
    private final static String ADD_CATEGORY = "INSERT INTO PRODUCT_CATEGORIES(NAME,DESCRIPTION) VALUES(:name,:description)";
    private final static String ADD_PRODUCT = "INSERT INTO PRODUCTS(TYPE_ID,CATEGORY_ID,NAME,DURATION,CUSTOMER_TYPE_ID," +
            "NEED_PROCESSING,DESCRIPTION,STATUS,BASE_PRICE) VALUES(:typeId,:categoryId,:nameProduct,:duration,:customerTypeId," +
            ":needProcessing,:description,:status,:basePrice)";
    private final static String SELECT_SERVICES_BY_PLACE_SQL = "SELECT\n" +
            "  PRODUCTS.ID,\n" +
            "  PRODUCTS.TYPE_ID,\n" +
            "  PRODUCTS.CATEGORY_ID,\n" +
            "  PRODUCTS.NAME,\n" +
            "  PRODUCTS.DURATION,\n" +
            "  PRODUCTS.NEED_PROCESSING,\n" +
            "  PRODUCTS.STATUS,\n" +
            "PRODUCTS.DESCRIPTION, " +
            "  PRODUCTS.BASE_PRICE,\n" +
            "  PRODUCTS.CUSTOMER_TYPE_ID " +
            "FROM PRODUCTS\n" +
            "  JOIN PRICES ON PRICES.PRODUCT_ID =  products.ID\n" +
            "WHERE PRICES.PLACE_ID = :place_id AND PRODUCTS.STATUS = 1 /*active status id*/ AND PRODUCTS.TYPE_ID = 2 /*service id*/";
    private final static String SELECT_PRODUCT_CATEGORY_BY_ID_SQL = "SELECT ID, NAME, DESCRIPTION FROM PRODUCT_CATEGORIES\n" +
            "WHERE ID = :id";
    private final static String SELECT_CURRENT_USER_TARIFF_BY_USER_ID_SQL = "SELECT " +
            " id," +
            " duration," +
            " type_id," +
            " need_processing," +
            " name," +
            " description," +
            " status," +
            " base_price" +
            " FROM Products" +
            " WHERE id IN (SELECT product_id FROM Orders WHERE user_id = :userId" +
            "              AND (current_status_id = 1/* Active */ " +
            "                   OR current_status_id = 2/* Suspended */ " +
            "                   OR current_status_id = 4/* In processing */))" +
            " AND type_id = 1/* Tariff */";
    private final static String SELECT_TARIFFS_BY_PLACE_SQL = "SELECT" +
            " prod.id," +
            " prod.duration," +
            " prod.type_id," +
            " prod.need_processing," +
            " prod.name," +
            " prod.description," +
            " prod.status," +
            " prod.category_id," +
            " Prices.price base_price" +
            " FROM Products prod " +
            " JOIN Prices ON Prices.product_id = prod.id " +
            " WHERE Prices.place_id = :placeId " +
            " AND prod.type_id = 1/* Tariff */";
    private final static String SELECT_ALL_SERVICES_OF_USER_CURRENT_TERIFF_SQL = "SELECT\n" +
            "  p2.ID,\n" +
            "  p2.NAME,\n" +
            "  p2.DESCRIPTION,\n" +
            "  p2.TYPE_ID,\n" +
            "  p2.CATEGORY_ID,\n" +
            "  p2.DURATION,\n" +
            "  p2.NEED_PROCESSING,\n" +
            "  p2.DESCRIPTION,\n" +
            "  p2.STATUS,\n" +
            "  p2.BASE_PRICE\n " +
            "FROM PRODUCTS p1\n" +
            "  JOIN ORDERS ON ORDERS.PRODUCT_ID = p1.ID\n" +
            "                 AND ORDERS.USER_ID = :id\n" +
            "                 AND p1.TYPE_ID = 1\n" +
            "  JOIN TARIFF_SERVICES\n" +
            "    ON p1.ID = TARIFF_SERVICES.TARIFF_ID\n" +
            "  JOIN PRODUCTS p2 ON p2.ID = TARIFF_SERVICES.SERVICE_ID";

    private final static String DELETE_SERVICE_FROM_TARIFF = "DELETE FROM TARIFF_SERVICES " +
            "WHERE TARIFF_ID=:idTariff AND SERVICE_ID=:idService ";

    private final static String DISABLE_PRODUCT = "UPDATE Products SET status=0 WHERE id=:id";

    private final static String FIND_PRODUCT_FOR_USER = "SELECT prod.ID AS ID, prod.NAME AS NAME," +
            "prod.description AS DESCRIPTION, prod.DURATION AS duration " +
            "FROM PRODUCTS prod JOIN ORDERS ord ON (prod.ID=ord.PRODUCT_ID) JOIN OPERATION_STATUS" +
            " status ON(ord.CURRENT_STATUS_ID = status.ID)" +
            "WHERE ord.USER_ID = :id AND status.NAME != 'Deactivated'";
    private final static String FIND_ACTIVE_PRODUCTS_FOR_USER = "SELECT prod.ID AS ID, prod.NAME AS NAME " +
            "FROM PRODUCTS prod JOIN ORDERS ord ON (prod.ID=ord.PRODUCT_ID) JOIN OPERATION_STATUS" +
            " status ON(ord.CURRENT_STATUS_ID = status.ID)" +
            "WHERE ord.USER_ID = :id AND status.NAME = 'Active'";
    private final static String DEACTIVATE_TARIFF_OF_USER_SQL = "UPDATE Orders SET current_status_id = 3/* Deactivated */" +
            "WHERE user_id IN (SELECT id FROM Users WHERE customer_id = (SELECT customer_id FROM Users WHERE id = :userId )) " +
            "AND product_id = :tariffId " +
            "AND (current_status_id = 1/* Active */ " +
            "     OR current_status_id = 2/* Suspended */ " +
            "     OR current_status_id = 4/* In processing */)";
    private final static String SELECT_TARIFFS_FOR_CUSTOMERS_SQL = "SELECT " +
            "id, " +
            "category_id, " +
            "duration, " +
            "type_id, " +
            "need_processing, " +
            "name, " +
            "description, " +
            "status, " +
            "base_price " +
            "FROM Products " +
            "WHERE customer_type_id = 1 /* Business */";
    private final static String SELECT_CURRENT_CUSTOMER_TARIFF_BY_CUSTOMER_ID_SQL = "SELECT " +
            "id, " +
            "category_id, " +
            "duration, " +
            "type_id, " +
            "need_processing, " +
            "name, " +
            "description, " +
            "status FROM Products " +
            "WHERE type_id = 1/* Tariff */ " +
            "AND id IN (SELECT product_id FROM Orders " +
            "             WHERE user_id IN " +
            "             (SELECT id FROM Users WHERE customer_id = :customerId AND role_id = 5) " +
            "              AND (current_status_id = 1/* Active */ " +
            "              OR current_status_id = 2/* Suspended */ " +
            "              OR current_status_id = 4/* In processing */))";
    private final static String SELECT_SERVICES_OF_TARIFF_SQL = "SELECT " +
            " id, " +
            " category_id, " +
            " duration, " +
            " type_id, " +
            " need_processing, " +
            " name, " +
            " description, " +
            " status," +
            " base_price FROM Products " +
            " WHERE id IN (SELECT service_id FROM Tariff_services WHERE tariff_id = :tariffId)";

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Resource
    private ProductCategoriesRowMapper categoriesRowMapper;
    @Resource
    private ProductRowMapper productRowMapper;
    @Resource
    private UserDAO userDAO;
    @Resource
    private Mailer mailer;
    @Resource
    private TariffRowMapper tariffRowMapper;

    /**
     * Rysakova Anna
     *
     * @param product
     * @return
     */
    @Override
    public boolean save(Product product) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("typeId", product.getProductType().getId());
        params.addValue("categoryId", product.getCategoryId());
        params.addValue("nameProduct", product.getName());
        params.addValue("duration", product.getDurationInDays());
        params.addValue("customerTypeId", product.getCustomerType().getId());
        params.addValue("needProcessing", product.getProcessingStrategy().getId());
        params.addValue("description", product.getDescription());
        params.addValue("status", product.getStatus().getId());
        params.addValue("basePrice", product.getBasePrice());
        int isUpdate = jdbcTemplate.update(ADD_PRODUCT, params);
        return isUpdate > 0;

    }

    /**
     * Rysakova Anna
     *
     * @return
     */
    @Override
    public List<ProductCategories> findProductCategories() {
        return jdbcTemplate.query(FIND_ALL_CATEGORIES, categoriesRowMapper);
    }

    /**
     * Rysakova Anna
     *
     * @return
     */
    @Override
    public List<Product> getAllServices() {
        return jdbcTemplate.query(FIND_SERVICES, productRowMapper);
    }

    /**
     * Rysakova Anna
     *
     * @return
     */
    @Override
    public List<Product> getAllTariffs() {
        return jdbcTemplate.query(FIND_TARIFFS, productRowMapper);
    }

    @Override
    public List<Product> getAllEnabledTariffs() {
        return jdbcTemplate.query(FIND_ENABLED_TARIFFS, productRowMapper);
    }

    @Override
    public void fillInTariffWithServices(ArrayList<TariffServiceDto> tariffServiceDtos) {

        List<Map<String, Object>> batchValues = new ArrayList<>(tariffServiceDtos.size());
        for (TariffServiceDto person : tariffServiceDtos) {
            batchValues.add(
                    new MapSqlParameterSource("tariffId", person.getIdTariff())
                            .addValue("serviceId", person.getIdService())
                            .getValues());
        }
        jdbcTemplate.batchUpdate(ADD_TARIFF_SERVICE, batchValues.toArray(new Map[tariffServiceDtos.size()]));
    }

    /**
     * Rysakova Anna
     *
     * @param categories
     * @return
     */
    @Override
    public boolean addCategory(ProductCategories categories) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", categories.getCategoryName());
        params.addValue("description", categories.getCategoryDescription());
        int isUpdate = jdbcTemplate.update(ADD_CATEGORY, params);
        return isUpdate > 0;
    }

    /**
     * Rysakova Anna
     *
     * @param categories
     * @return
     */
    @Override
    public int findIdCategory(ProductCategories categories) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", categories.getCategoryName());
        ProductCategories categoriesList = jdbcTemplate.queryForObject(FIND_CATEGORY, params, (rs, rowNum) -> {
            ProductCategories productCategories = new ProductCategories();
            productCategories.setId(rs.getInt("ID"));
            return productCategories;
        });
        return categoriesList.getId();
    }

    /**
     * Rysakova Anna
     *
     * @return
     */
    @Override
    public List<String> findProductTypes() {
        return jdbcTemplate.query(FIND_TYPES, (rs, rowNum) -> {
            String type = rs.getString("NAME");
            return type;
        });
    }

    @Override
    public List<Product> getAllServices(String categoryName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("categoryName", categoryName);
        List<Product> services = jdbcTemplate.query(FIND_ALL_SERVICES, params, (rs, rowNum) -> {
            Product product = new Product();
            product.setCategoryId(rs.getInt("CATEGORY_ID"));
            product.setId(rs.getInt("ID"));
            Integer productType = rs.getInt("type_id");
            product.setProductType(ProductType.getProductTypeFromId(rs.getInt("type_id")));
            product.setProductType(ProductType.getProductTypeFromId(rs.getInt("TYPE_ID")));
            product.setProcessingStrategy(ProcessingStrategy.getProcessingStrategyFromId(rs.getInt("NEED_PROCESSING")));
            product.setDurationInDays(rs.getInt("DURATION"));
            product.setName(rs.getString("NAME"));
            product.setDescription(rs.getString("DESCRIPTION"));
            Integer processingStrategyId = rs.getInt("NEED_PROCESSING");
            product.setProcessingStrategy(ProcessingStrategy.getProcessingStrategyFromId(processingStrategyId));
            Integer statusId = rs.getInt("STATUS");
            product.setStatus(ProductStatus.getProductStatusFromId(statusId));
            return product;
        });

        return services;
    }

    @Override
    public List<Product> getAllFreeTariffs() {
        List<Product> tariffs = jdbcTemplate.query(FIND_ALL_FREE_TARIFFS, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("ID"));
//            Integer productType = rs.getInt("type_id");
//            product.setProductType(ProductType.getProductTypeFromId(rs.getInt("type_id")));
//            product.setDurationInDays(rs.getInt("DURATION"));
            product.setName(rs.getString("NAME"));
//            product.setCategoryDescription(rs.getString("DESCRIPTION"));
//            Integer processingStrategyId = rs.getInt("NEED_PROCESSING");
//            product.setProcessingStrategy(ProcessingStrategy.getProcessingStrategyFromId(processingStrategyId));
//            Integer statusId = rs.getInt("STATUS");
//            product.setStatus(ProductStatus.getProductStatusFromId(statusId));
            return product;
        });
        return tariffs;
    }

    @Override
    public boolean delete(Product product) {
        return false;
    }

    /**
     * Rysakova Anna
     *
     * @param id
     * @return
     */
    @Override
    public Product getById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.queryForObject(FIND_PRODUCT_BY_ID, params, productRowMapper);
    }

    @Override
    public List<Product> getByTypeId(int id) {
        return null;
    }

    @Override
    public List<Product> getByCategoryId(int id) {
        return null;
    }

    @Override
    public List<Product> getByProcessingStatus(int id) {
        return null;
    }

    /**
     * Rysakova Anna
     *
     * @param product
     * @return
     */
    @Override
    public boolean update(Product product) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", product.getName());
        params.addValue("duration", product.getDurationInDays());
        params.addValue("needProcessing", product.getProcessingStrategy().getId());
        params.addValue("description", product.getDescription());
        params.addValue("status", product.getStatus());
        params.addValue("id", product.getId());
        int isUpdate = jdbcTemplate.update(UPDATE_SERVICE, params);
        return isUpdate > 0;
    }

    @Override
    public List<Product> getAllAvailableServicesByPlace(Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("place_id", placeId);
        return jdbcTemplate.query(SELECT_SERVICES_BY_PLACE_SQL, params, new ProductRowMapper());
    }

    @Override
    public ProductCategories getProductCategoryById(Integer categoryId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", categoryId);
        return jdbcTemplate.queryForObject(SELECT_PRODUCT_CATEGORY_BY_ID_SQL, params, new ProductCategoriesRowMapper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product getCurrentUserTariff(Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        try {
            return jdbcTemplate.queryForObject(SELECT_CURRENT_USER_TARIFF_BY_USER_ID_SQL, params, tariffRowMapper);
        } catch (Exception e) {
            logger.debug("User doesn`t have tariff.", userId);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> getAvailableTariffsByPlace(Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("placeId", placeId);
        try {
            return jdbcTemplate.query(SELECT_TARIFFS_BY_PLACE_SQL, params, tariffRowMapper);
        } catch (Exception e) {
            logger.debug("There are no tariffs in pce with id {}", placeId);
            return new ArrayList<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deactivateTariff(Integer userId, Integer tariffId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("tariffId", tariffId);
        return (jdbcTemplate.update(DEACTIVATE_TARIFF_OF_USER_SQL, params) != 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> getAvailableTariffsForCustomers() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        try {
            return jdbcTemplate.query(SELECT_TARIFFS_FOR_CUSTOMERS_SQL, params, tariffRowMapper);
        } catch (Exception e) {
            logger.debug("There are no tariffs for customers.");
            return new ArrayList<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean activateTariff(Integer userId, Integer tariffId) {
        try {
            Connection conn = dataSource.getConnection();
            CallableStatement proc = conn.prepareCall("{ call activateTariff(?,?,?) }");
            proc.setInt(1, userId);
            proc.setInt(2, tariffId);
            proc.registerOutParameter(3, Types.VARCHAR);
            proc.execute();
            return "success".equals(proc.getString(3));
        } catch (SQLException e) {
            logger.error("Exception during activation tariff", e);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product getCurrentCustomerTariff(Integer customerId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        try {
            return jdbcTemplate.queryForObject(SELECT_CURRENT_CUSTOMER_TARIFF_BY_CUSTOMER_ID_SQL, params, tariffRowMapper);
        } catch (Exception e) {
            logger.debug("User doesn`t have tariff.");
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> getServicesOfTariff(Integer tariffId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tariffId", tariffId);
        try {
            return jdbcTemplate.query(SELECT_SERVICES_OF_TARIFF_SQL, params, productRowMapper);
        } catch (Exception e) {
            logger.debug("There are no services in tariff with id = {}", tariffId);
            return new ArrayList<>();
        }
    }

    @Override
    public Map<String, List<Product>> getAllServicesWithCategory() {
        Map<String, List<Product>> serviceMap = new HashMap<>();
        List<Product> services = jdbcTemplate.query(FIND_ALL_SERVICES_WITH_CATEGORY, (rs, rowNum) -> {
            Product product = new Product();
            product.setCategoryId(rs.getInt("CATEGORY_ID"));
            product.setId(rs.getInt("ID"));
            product.setName(rs.getString("NAME"));
            String category = rs.getString("CATEGORY");
            if (serviceMap.containsKey(category)) {
                List<Product> serv = serviceMap.get(category);
                serv.add(product);
            } else {
                List<Product> serv = new ArrayList<>();
                serv.add(product);
                serviceMap.put(category, serv);
            }

            return product;
        });

        return serviceMap;
    }

    /**
     * Rysakova Anna
     *
     * @return
     */
    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = jdbcTemplate.query(FIND_ALL_PRODUCTS, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("ID"));
            product.setProductType(ProductType.getProductTypeFromId(rs.getInt("TYPE_ID")));
            product.setDurationInDays(rs.getInt("DURATION"));
            product.setName(rs.getString("NAME"));
            product.setDescription(rs.getString("DESCRIPTION"));
            product.setBasePrice(rs.getBigDecimal("BASE_PRICE"));
            product.setStatus(ProductStatus.getProductStatusFromId(rs.getInt("STATUS")));
            return product;
        });
        return productList;
    }

    /**
     * Rysakova Anna
     *
     * @param product
     * @return
     */
    @Override
    public List<Product> getServicesByTariff(Product product) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tariffId", product.getId());
        List<Product> productList = jdbcTemplate.query(FIND_SERVICES_BY_TARIFF, params, (rs, rowNum) -> {
            Product p = new Product();
            p.setId(rs.getInt("ID"));
            p.setProductType(ProductType.Service);
            p.setCategoryId(rs.getInt("CATEGORY_ID"));
            p.setDurationInDays(rs.getInt("DURATION"));
            p.setName(rs.getString("NAME"));
            p.setDescription(rs.getString("DESCRIPTION"));
            Integer processingStrategyId = rs.getInt("NEED_PROCESSING");
            p.setProcessingStrategy(ProcessingStrategy.getProcessingStrategyFromId(processingStrategyId));
            Integer statusId = rs.getInt("STATUS");
            p.setStatus(ProductStatus.getProductStatusFromId(statusId));
            return p;
        });
        return productList;
    }

    /**
     * Rysakova Anna
     *
     * @param product
     * @return
     */
    @Override
    public List<Product> getServicesNotInTariff(Product product) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tariffId", product.getId());
        return jdbcTemplate.query(FIND_SERVICES_NOT_IN_TARIFF, params, productRowMapper);
    }

    /**
     * Rysakova Anna
     *
     * @param idTariff
     * @return
     */
    @Override
    public boolean deleteServiceFromTariff(int idTariff, Integer[] idServicesArray) {
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(idServicesArray);
        int[] batchUpdate = jdbcTemplate.batchUpdate(ADD_TARIFF_SERVICE, params);
        return batchUpdate.length != 0;
    }

    /**
     * @param productID
     * @return status of updating
     * @author Nikita Alistratenko
     */
    @Override
    public boolean disableProductByID(int productID) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", productID);
        return jdbcTemplate.update(DISABLE_PRODUCT, params) > 0;
    }

    /**
     * @param id
     * @return
     * @author Moiseienko Petro
     */
    @Override
    public List<Product> getProductsByUserId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Product> products = jdbcTemplate.query(FIND_PRODUCT_FOR_USER, params, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("ID"));
            product.setName(rs.getString("NAME"));
            product.setDescription(rs.getString("DESCRIPTION"));
            product.setDurationInDays(rs.getInt("DURATION"));
            return product;
        });
        return products;
    }

    /**
     * @param id
     * @return
     * @author Moiseienko Petro
     */
    @Override
    public List<Product> getActiveProductsByUserId(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Product> products = jdbcTemplate.query(FIND_ACTIVE_PRODUCTS_FOR_USER, params, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("ID"));
            product.setName(rs.getString("NAME"));
            return product;
        });
        return products;
    }

    public List<Product> getAllServicesByCurrentUserTariff(Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", userId);
        return jdbcTemplate.query(SELECT_ALL_SERVICES_OF_USER_CURRENT_TERIFF_SQL, params, new ProductRowMapper());
    }
}
