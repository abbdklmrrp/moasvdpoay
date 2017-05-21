package jtelecom.dao.product;

import jtelecom.dto.ServicesByCategoryDto;
import jtelecom.dto.TariffServiceDto;
import jtelecom.util.querybuilders.LimitedProductsQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    private final static String FIND_ALL_CATEGORIES = "SELECT * FROM PRODUCT_CATEGORIES";
    private final static String FIND_CATEGORY = "SELECT ID FROM PRODUCT_CATEGORIES WHERE NAME=:name";
    private final static String FIND_PRODUCT_TYPE_BY_ID = "SELECT type.NAME\n" +
            "FROM PRODUCT_TYPES type\n" +
            "  JOIN PRODUCTS product ON (type.ID = product.TYPE_ID)\n" +
            "WHERE product.ID = :productId";
    private final static String FIND_CUSTOMER_TYPE_BY_ID = "SELECT type.NAME\n" +
            "FROM CUSTOMER_TYPES type\n" +
            "  JOIN PRODUCTS product ON (type.ID = product.CUSTOMER_TYPE_ID)\n" +
            "WHERE product.ID = :productId";
    private final static String FIND_TYPES = "SELECT NAME FROM PRODUCT_TYPES";
    private final static String FIND_SERVICES = "SELECT * FROM PRODUCTS WHERE TYPE_ID=2 ORDER BY ID";
    private final static String FIND_TARIFFS = "SELECT * FROM PRODUCTS WHERE TYPE_ID=1 ORDER BY ID";
    private final static String FIND_ENABLED_TARIFFS = "SELECT * FROM PRODUCTS WHERE TYPE_ID=1 AND STATUS=1 ORDER BY ID";
    private final static String FIND_PRODUCT_BY_ID = "SELECT * FROM PRODUCTS WHERE ID=:id";
    private final static String SELECT_PRODUCT_BY_ID_BASE_PRICE_SET_BY_PLACE_SQL = "SELECT id, type_id, category_id, name, duration, need_processing, DESCRIPTION,\n" +
            "  status,customer_type_id, price AS base_price\n" +
            "  FROM PRODUCTS\n" +
            "  INNER JOIN PRICES ON PRICES.PRODUCT_ID = PRODUCTS.ID " +
            "WHERE PRICES.PLACE_ID = :place_id AND PRODUCT_ID = :product_id";
    private final static String FIND_ALL_PRODUCTS = "SELECT ID, TYPE_ID, NAME, DURATION, DESCRIPTION, BASE_PRICE, STATUS FROM PRODUCTS ORDER BY ID";
    private final static String FIND_SERVICES_BY_TARIFF = "SELECT\n" +
            "  p.ID,\n" +
            "  p.CATEGORY_ID,\n" +
            "  category.NAME CATEGORY_NAME,\n" +
            "  p.NAME\n," +
            "  ts.TARIFF_ID TARIFF_ID\n" +
            "FROM PRODUCTS p\n" +
            "  JOIN TARIFF_SERVICES ts ON (p.ID = ts.SERVICE_ID)\n" +
            "  JOIN PRODUCT_CATEGORIES CATEGORY ON (p.CATEGORY_ID = CATEGORY.ID)\n" +
            "WHERE ts.TARIFF_ID = :tariffId";
    private final static String FIND_ALL_SERVICES = "SELECT prod.ID, prod.NAME, prod.DESCRIPTION,prod.DURATION,prod.NEED_PROCESSING,\n" +
            "prod.TYPE_ID,prod.CATEGORY_ID\n" +
            "FROM PRODUCTS prod JOIN PRODUCT_TYPES pTypes ON (prod.TYPE_ID=pTypes.ID)\n" +
            "JOIN PRODUCT_CATEGORIES pCategories ON (prod.CATEGORY_ID=pCategories.ID)\n" +
            "WHERE prod.STATUS=1 AND pTypes.name='Service' AND pCategories.name=:categoryName";
    private final static String FIND_SERVICES_NOT_IN_TARIFF = "SELECT\n" +
            "  p.ID,\n" +
            "  p.CATEGORY_ID,\n" +
            "  p.NAME,\n" +
            "  p.STATUS\n" +
            "FROM PRODUCTS p\n" +
            "WHERE p.ID NOT IN (SELECT ts.SERVICE_ID\n" +
            "                   FROM TARIFF_SERVICES ts\n" +
            "                   WHERE ts.TARIFF_ID = :tariffId) AND p.TYPE_ID = 2";
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
    private final static String UPDATE_SERVICE = "UPDATE PRODUCTS SET NAME=:name,\n" +
            "DURATION=:duration,\n" +
            "NEED_PROCESSING=:needProcessing,\n" +
            "DESCRIPTION=:description,\n" +
            "STATUS=:status,\n" +
            "BASE_PRICE=:basePrice,\n" +
            "CUSTOMER_TYPE_ID=:customerType\n" +
            " WHERE ID=:id";
    private final static String ADD_TARIFF_SERVICE = "INSERT INTO TARIFF_SERVICES(TARIFF_ID,SERVICE_ID) VALUES(:tariffId,:serviceId)";
    private final static String ADD_CATEGORY = "INSERT INTO PRODUCT_CATEGORIES(NAME,DESCRIPTION) VALUES(:name,:description)";
    private final static String ADD_PRODUCT = "INSERT INTO PRODUCTS(TYPE_ID,CATEGORY_ID,NAME,DURATION,CUSTOMER_TYPE_ID," +
            "NEED_PROCESSING,DESCRIPTION,STATUS,BASE_PRICE) VALUES(:typeId,:categoryId,:nameProduct,:duration,:customerTypeId," +
            ":needProcessing,:description,:status,:basePrice)";
    private final static String SELECT_SERVICES_BY_PLACE_PRICE_DEFINED_BY_PLACE_SQL = "SELECT\n" +
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
    private final static String SELECT_INTERVAL_TARIFFS_BY_PLACE_SQL = "SELECT * FROM " +
            " (SELECT" +
            " prod.id," +
            " prod.duration," +
            " prod.type_id," +
            " prod.need_processing," +
            " prod.name," +
            " prod.description," +
            " prod.status," +
            " prod.category_id," +
            " Prices.price base_price," +
            " ROW_NUMBER() OVER (ORDER BY prod.id) Num" +
            " FROM Products prod" +
            " JOIN Prices ON Prices.product_id = prod.id " +
            " WHERE Prices.place_id = :placeId " +
            " AND prod.type_id = 1/* Tariff */)" +
            " WHERE Num > :startIndex" +
            " AND Num <= :endIndex";
    private final static String SELECT_QUANTITY_OF_AVAILABLE_TARIFFS_BY_PLACE_ID_SQL = "SELECT" +
            " COUNT(*)" +
            " FROM Products" +
            " JOIN Prices ON Prices.product_id = products.id " +
            " WHERE Prices.place_id = :placeId " +
            " AND products.type_id = 1/* Tariff */";
    private final static String SELECT_ALL_SERVICES_OF_USER_CURRENT_TARIFF_SQL = "SELECT\n" +
            "  p2.ID,\n" +
            "  p2.NAME,\n" +
            "  p2.DESCRIPTION,\n" +
            "  p2.TYPE_ID,\n" +
            "  p2.CATEGORY_ID,\n" +
            "  p2.DURATION,\n" +
            "  p2.NEED_PROCESSING,\n" +
            "  p2.DESCRIPTION,\n" +
            "  p2.STATUS,\n" +
            "  p2.BASE_PRICE,\n " +
            "  p2.CUSTOMER_TYPE_ID " +
            "FROM PRODUCTS p1\n" +
            "  JOIN ORDERS ON ORDERS.PRODUCT_ID = p1.ID\n" +
            "                 AND ORDERS.USER_ID = :id\n" +
            "                 AND p1.TYPE_ID = 1\n" +
            "  JOIN TARIFF_SERVICES\n" +
            "    ON p1.ID = TARIFF_SERVICES.TARIFF_ID\n" +
            "  JOIN PRODUCTS p2 ON p2.ID = TARIFF_SERVICES.SERVICE_ID";
    private final static String DELETE_SERVICE_FROM_TARIFF = "DELETE FROM TARIFF_SERVICES " +
            "WHERE TARIFF_ID=:idTariff AND SERVICE_ID=:idService ";
    private final static String DISABLE_ENABLE_PRODUCT = "UPDATE Products SET status=:status WHERE id=:id";
    private final static String FIND_PRODUCT_FOR_USER = "SELECT prod.ID AS ID, prod.NAME AS NAME," +
            "prod.description AS DESCRIPTION, prod.DURATION AS duration " +
            "FROM PRODUCTS prod JOIN ORDERS ord ON (prod.ID=ord.PRODUCT_ID) JOIN OPERATION_STATUS" +
            " status ON(ord.CURRENT_STATUS_ID = status.ID)" +
            "WHERE ord.USER_ID = :id AND status.NAME != 'Deactivated'";
//    private final static String FIND_ACTIVE_PRODUCTS_FOR_USER = "SELECT prod.ID AS ID, prod.NAME AS NAME " +
//            "FROM PRODUCTS prod JOIN ORDERS ord ON (prod.ID=ord.PRODUCT_ID) JOIN OPERATION_STATUS" +
//            " status ON(ord.CURRENT_STATUS_ID = status.ID)" +
//            "WHERE ord.USER_ID = :id AND status.NAME = 'Active'";

    private final static String SELECT_ACTIVE_PRODUCTS_FOR_CUSTOMER = "SELECT " +
            "Products.id id, " +
            "Products.name name " +
            "FROM Products " +
            "JOIN Orders ON Orders.product_id = Products.id " +
            "WHERE (Orders.current_status_id = 1/* Active */ " +
            "       OR Orders.current_status_id = 2/* Suspended */ " +
            "       OR Orders.current_status_id = 4/* In processing */) " +
            "AND Orders.user_id IN (SELECT id FROM Users WHERE customer_id = " +
            "                                           (SELECT customer_id FROM USERS WHERE id = :id))";
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
    private final static String SELECT_INTERVAL_TARIFFS_FOR_CUSTOMERS_SQL = "SELECT * FROM" +
            " (SELECT" +
            " prod.id," +
            " prod.duration," +
            " prod.type_id," +
            " prod.need_processing," +
            " prod.name," +
            " prod.description," +
            " prod.status," +
            " prod.category_id," +
            " prod.base_price," +
            " ROW_NUMBER() OVER (ORDER BY prod.id) Num" +
            " FROM Products prod" +
            " WHERE customer_type_id = 1 /* Business */" +
            " AND type_id = 1/* Tariff */)" +
            " WHERE Num > :startIndex" +
            " AND Num <= :endIndex";
    private final static String SELECT_QUANTITY_OF_AVAILABLE_TARIFFS_FOR_CUSTOMERS_SQL = "SELECT" +
            " COUNT(*)" +
            " FROM Products" +
            " WHERE customer_type_id = 1 /* Business */" +
            " AND type_id = 1/* Tariff */";
    private final static String SELECT_SERVICES_FOR_CUSTOMERS_SQL = "SELECT * " +
            "FROM Products " +
            "WHERE customer_type_id = 1 /* Business */ " +
            "AND TYPE_ID = 2 /*Service*/";
    private final static String SELECT_CURRENT_TARIFF_BY_CUSTOMER_ID_SQL = "SELECT " +
            "Products.id, " +
            "Products.category_id, " +
            "Products.duration, " +
            "Products.type_id, " +
            "Products.need_processing, " +
            "Products.name, " +
            "Products.description, " +
            "Products.status, " +
            "Products.base_price " +
            "FROM Products " +
            "JOIN Orders ON Orders.product_id = Products.id " +
            "WHERE Orders.user_id IN " +
            "             (SELECT id FROM Users WHERE customer_id = :customerId) " +
            "AND (Orders.current_status_id = 1/* Active */ " +
            "              OR Orders.current_status_id = 2/* Suspended */ " +
            "              OR Orders.current_status_id = 4/* In processing */)" +
            "AND Products.type_id = 1/* Tariff */";
    private final static String SELECT_SERVICES_OF_TARIFF_SQL = "SELECT " +
            " id, " +
            " category_id, " +
            " duration, " +
            " type_id, " +
            " need_processing, " +
            " name, " +
            " description, " +
            " status," +
            " base_price," +
            " customer_type_id" +
            " FROM Products " +
            " WHERE id IN (SELECT service_id FROM Tariff_services WHERE tariff_id = :tariffId)";
    private final static String SELECT_LIMITED_PRODUCTS = "select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from PRODUCTS " +
            " Where upper(name) like upper(:pattern) " +
            " OR upper(description) like upper(:pattern) " +
            " OR duration like :pattern " +
            " OR base_price like :pattern " +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";
    private final static String SELECT_LIMITED_ACTIVE_PRODUCTS = "select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from PRODUCTS " +
            " Where STATUS = 1 and (upper(name) like upper(:pattern) " +
            " OR upper(description) like upper((:pattern) " +
            " OR duration like :pattern " +
            " OR base_price like :pattern) " +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";
    private static final String SELECT_COUNT = "SELECT count(ID)\n" +
            "  FROM PRODUCTS" +
            " WHERE upper(name) LIKE upper(:pattern) " +
            " OR upper(description) LIKE upper(:pattern) " +
            " OR duration LIKE :pattern " +
            " OR base_price LIKE :pattern ";
    private static final String SELECT_ACTIVE_COUNT = "SELECT count(ID)\n" +
            "  FROM PRODUCTS" +
            " WHERE STATUS = 1 and (upper(name) LIKE upper(:pattern) " +
            " OR upper(description) LIKE upper(:pattern) " +
            " OR duration LIKE :pattern " +
            " OR base_price LIKE :pattern) ";
    private final static String FIND_PRODUCT_RESEDENTIAL_WITHOUT_PRICE = "SELECT\n" +
            "  product.ID,\n" +
            "  product.NAME\n" +
            "FROM PRODUCTS product\n" +
            "WHERE product.ID NOT IN (SELECT price.PRODUCT_ID\n" +
            "                         FROM PRICES price)\n" +
            "      AND product.CUSTOMER_TYPE_ID = 2";
    private final static String FIND_PRODUCT_PRICE_BY_REGION = "SELECT\n" +
            "  product.ID,\n" +
            "  product.NAME,\n" +
            "  product.DESCRIPTION,\n" +
            "  place.NAME PLACE,\n" +
            "  price.PRICE\n" +
            "FROM PRODUCTS product\n" +
            "  JOIN PRICES price ON (product.ID = price.PRODUCT_ID)\n" +
            "  JOIN PLACES place ON (price.PLACE_ID = place.ID)";
    private final static String SELECT_LIMITED_SERVICES_FOR_BUSINESS_SQL = "SELECT " +
            "  products.*, " +
            "  rownum " +
            "FROM products " +
            "WHERE rownum <= :length AND rownum > :start AND TYPE_ID = 2 /*Service*/ AND status = 1 /*Active*/ AND customer_type_id = 1 /*Business*/";
    private final static String SELECT_LIMITED_SERVICES_FOR_RESIDENTIAL_SQL = "SELECT\n" +
            "  id,\n" +
            "  type_id,\n" +
            "  category_id,\n" +
            "  name,\n" +
            "  duration,\n" +
            "  need_processing,\n" +
            "  description,\n" +
            "  status,\n" +
            "  CUSTOMER_TYPE_ID,\n" +
            "  price AS base_price,\n" +
            "  rownum\n" +
            "FROM products\n" +
            "  INNER JOIN PRICES ON PRICES.PRODUCT_ID = PRODUCTS.ID\n" +
            "WHERE rownum <= :length AND rownum > :start AND TYPE_ID = 2 /*Service*/ AND status =1 /*Active*/\n" +
            "  AND place_id = :place_id";
    private final static String SELECT_COUNT_FOR_SERVICES_FOR_BUSINESS_SQL = "\n" +
            "SELECT" +
            "  COUNT(*) " +
            "FROM products WHERE " +
            "   TYPE_ID = 2 /*Service*/ AND STATUS = 1 /*Active*/ AND customer_type_id = 1";
    private final static String SELECT_COUNT_FOR_SERVICES_FOR_RESIDENT_SQL = "SELECT COUNT(*)\n" +
            "FROM products\n" +
            "  INNER JOIN PRICES ON PRICES.PRODUCT_ID = PRODUCTS.ID\n" +
            "WHERE TYPE_ID = 2\n AND STATUS = 1 /*Active*/ " +
            " AND place_id = :place_id";

    private static final String FIND_SERVICES_BY_CATEGORY_ID = "SELECT\n" +
            "  ID,\n" +
            "  NAME\n" +
            "FROM PRODUCTS\n" +
            "WHERE CATEGORY_ID = :categoryId";

    private static final String GET_PRODUCT_BY_ORDER_ID = "SELECT * " +
            " FROM PRODUCTS WHERE ID=( " +
            " SELECT PRODUCT_ID FROM ORDERS WHERE ID=:orderId)";
    private static Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);
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

    @Override
    public Integer saveProduct(Product product) {
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
        KeyHolder key = new GeneratedKeyHolder();
        Integer productID = jdbcTemplate.update(ADD_PRODUCT, params, key, new String[]{"ID"});
        return key.getKey().intValue();
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
    public void fillInTariffWithServices(List<TariffServiceDto> tariffServiceDtos) {

        List<Map<String, Object>> batchValues = new ArrayList<>(tariffServiceDtos.size());
        for (TariffServiceDto person : tariffServiceDtos) {
            batchValues.add(
                    new MapSqlParameterSource("tariffId", person.getTariffId())
                            .addValue("serviceId", person.getServiceId())
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
        params.addValue("status", product.getStatus().getId());
        params.addValue("basePrice", product.getBasePrice());
        params.addValue("customerType", product.getCustomerType().getId());
        params.addValue("id", product.getId());
        int isUpdate = jdbcTemplate.update(UPDATE_SERVICE, params);
        return isUpdate > 0;
    }

    @Override
    public List<Product> getAllAvailableServicesByPlace(Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("place_id", placeId);
        return jdbcTemplate.query(SELECT_SERVICES_BY_PLACE_PRICE_DEFINED_BY_PLACE_SQL, params, new ProductRowMapper());
    }

    @Override
    public ProductCategories getProductCategoryById(Integer categoryId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", categoryId);
        return jdbcTemplate.queryForObject(SELECT_PRODUCT_CATEGORY_BY_ID_SQL, params, new ProductCategoriesRowMapper());
    }

    /**
     * Bulgakov Anton
     * {@inheritDoc}
     */
    @Override
    public List<Product> getAvailableTariffsByPlace(Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("placeId", placeId);
        try {
            return jdbcTemplate.query(SELECT_TARIFFS_BY_PLACE_SQL, params, tariffRowMapper);
        } catch (Exception e) {
            logger.debug("There are no tariffs in place with id {}", placeId);
            return new ArrayList<>();
        }
    }

    /**
     * Bulgakov Anton
     * {@inheritDoc}
     */
    @Override
    public List<Product> getIntervalOfTariffsByPlace(Integer placeId, Integer startIndex, Integer endIndex) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("placeId", placeId);
        params.addValue("startIndex", startIndex);
        params.addValue("endIndex", endIndex);
        try {
            return jdbcTemplate.query(SELECT_INTERVAL_TARIFFS_BY_PLACE_SQL, params, tariffRowMapper);
        } catch (Exception e) {
            logger.debug("There are no tariffs in place with id {}", placeId);
            return new ArrayList<>();
        }
    }

    /**
     * Bulgakov Anton
     * {@inheritDoc}
     */
    @Override
    public Integer getQuantityOfAllAvailableTariffsByPlaceId(Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("placeId", placeId);
        try {
            return jdbcTemplate.queryForObject(SELECT_QUANTITY_OF_AVAILABLE_TARIFFS_BY_PLACE_ID_SQL, params, Integer.class);
        } catch (Exception e) {
            logger.debug("There are no available tariffs for user.");
            return null;
        }
    }

    /**
     * Bulgakov Anton
     * {@inheritDoc}
     */
    @Override
    public Integer getQuantityOfAllAvailableTariffsForCustomers() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        try {
            return jdbcTemplate.queryForObject(SELECT_QUANTITY_OF_AVAILABLE_TARIFFS_FOR_CUSTOMERS_SQL, params, Integer.class);
        } catch (Exception e) {
            logger.debug("There are no available tariffs for customers.");
            return null;
        }
    }

    /**
     * Bulgakov Anton
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
     * Bulgakov Anton
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
     * Bulgakov Anton
     * {@inheritDoc}
     */
    @Override
    public List<Product> getIntervalOfTariffsForCustomers(Integer startIndex, Integer endIndex) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startIndex", startIndex);
        params.addValue("endIndex", endIndex);
        try {
            return jdbcTemplate.query(SELECT_INTERVAL_TARIFFS_FOR_CUSTOMERS_SQL, params, tariffRowMapper);
        } catch (Exception e) {
            logger.debug("There are no tariffs for customers.");
            return new ArrayList<>();
        }
    }


    /**
     * Bulgakov Anton
     * {@inheritDoc}
     */
    @Override
    public List<Product> getServicesAvailableForCustomer() {
        return jdbcTemplate.query(SELECT_SERVICES_FOR_CUSTOMERS_SQL, productRowMapper);
    }

    /**
     * Bulgakov Anton
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
     * Bulgakov Anton
     * {@inheritDoc}
     */
    @Override
    public Product getCurrentCustomerTariff(Integer customerId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        try {
            return jdbcTemplate.queryForObject(SELECT_CURRENT_TARIFF_BY_CUSTOMER_ID_SQL, params, tariffRowMapper);
        } catch (Exception e) {
            logger.debug("User doesn`t have tariff.");
            return null;
        }
    }

    /**
     * Bulgakov Anton
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
     * @param tariffId
     * @return
     */
    @Override
    public List<TariffServiceDto> getServicesByTariff(Integer tariffId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tariffId", tariffId);
        List<TariffServiceDto> productList = jdbcTemplate.query(FIND_SERVICES_BY_TARIFF, params, (rs, rowNum) -> {
            TariffServiceDto productTmp = new TariffServiceDto();
            productTmp.setServiceId(rs.getInt("ID"));
            productTmp.setServiceName(rs.getString("NAME"));
            productTmp.setCategoryId(rs.getInt("CATEGORY_ID"));
            productTmp.setTariffId(rs.getInt("TARIFF_ID"));
            productTmp.setCategoryName(rs.getString("CATEGORY_NAME"));
            return productTmp;
        });
        return productList;
    }

//    /**
//     * Rysakova Anna
//     *
//     * @param product
//     * @return
//     */
//    @Override
//    public List<Product> getServicesNotInTariff(Product product) {
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("tariffId", product.getId());
//        List<Product> productList = jdbcTemplate.query(FIND_SERVICES_NOT_IN_TARIFF, params, (rs, rowNum) -> {
//            Product productTmp = new Product();
//            productTmp.setId(rs.getInt("ID"));
//            productTmp.setName(rs.getString("NAME"));
//            productTmp.setStatus(ProductStatus.getProductStatusFromId(rs.getInt("STATUS")));
//            productTmp.setCategoryId(rs.getInt("CATEGORY_ID"));
//            return productTmp;
//        });
//        return productList;
//    }

    /**
     * Rysakova Anna
     *
     * @param
     * @return
     */
    @Override
    public void deleteServiceFromTariff(List<TariffServiceDto> tariffServiceDtos) {
        List<Map<String, Object>> batchValues = new ArrayList<>(tariffServiceDtos.size());
        for (TariffServiceDto person : tariffServiceDtos) {
            batchValues.add(
                    new MapSqlParameterSource("idTariff", person.getTariffId())
                            .addValue("idService", person.getServiceId())
                            .getValues());
        }
        jdbcTemplate.batchUpdate(DELETE_SERVICE_FROM_TARIFF, batchValues.toArray(new Map[tariffServiceDtos.size()]));
    }

    /**
     * @param product
     * @return status of updating
     * @author Nikita Alistratenko
     */
    @Override
    public boolean disableEnableProduct(Product product) {
        logger.debug("Product sent to get status changed, id = {} ", product.getId());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", product.getId());
        if (product.getStatus().getId() == 1) {
            params.addValue("status", 0);
        } else {
            params.addValue("status", 1);
        }
        return jdbcTemplate.update(DISABLE_ENABLE_PRODUCT, params) > 0;
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
     * @author Moiseienko Petro, Anton Bulgakov
     */
    @Override
    public List<Product> getActiveProductsByUserId(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Product> products = jdbcTemplate.query(SELECT_ACTIVE_PRODUCTS_FOR_CUSTOMER, params, (rs, rowNum) -> {
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
        return jdbcTemplate.query(SELECT_ALL_SERVICES_OF_USER_CURRENT_TARIFF_SQL, params, new ProductRowMapper());
    }

    @Override
    public List<Product> getLimitedQuantityProduct(int start, int length, String sort, String search) {
        int rownum = start + length;
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_LIMITED_PRODUCTS, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", rownum);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.query(sql, params, new ProductRowMapper());
    }

    @Override
    public Integer getCountProductsWithSearch(String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_COUNT, params, Integer.class);
    }

    /**
     * Anna
     *
     * @return
     */
    @Override
    public List<Product> getProductForResidentialCustomerWithoutPrice() {
        List<Product> products = jdbcTemplate.query(FIND_PRODUCT_RESEDENTIAL_WITHOUT_PRICE, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("ID"));
            product.setName(rs.getString("NAME"));
            return product;
        });
        return products;
    }

    @Override
    public Product findProductWithPriceSetByPlace(Integer productId, Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId);
        params.addValue("place_id", placeId);
        return jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_ID_BASE_PRICE_SET_BY_PLACE_SQL, params, productRowMapper);

    }

    @Override
    public List<ServicesByCategoryDto> findServicesByCategoryId(Integer categoryId) {
        MapSqlParameterSource params = new MapSqlParameterSource("categoryId", categoryId);
        List<ServicesByCategoryDto> servicesByCategoryDtos = jdbcTemplate.query(FIND_SERVICES_BY_CATEGORY_ID, params, (rs, rowNum) -> {
            ServicesByCategoryDto service = new ServicesByCategoryDto();
            service.setId(rs.getInt("ID"));
            service.setName(rs.getString("NAME"));
            return service;
        });
        return servicesByCategoryDtos;
    }

    @Override
    public List<Product> getLimitedServicesForBusiness(Integer start, Integer length, String sort, String search, Integer categoryId) {
        String query = LimitedProductsQueryBuilder.getQuery(SELECT_LIMITED_SERVICES_FOR_BUSINESS_SQL, sort, search, categoryId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", length);
        return jdbcTemplate.query(query, params, productRowMapper);
    }

    @Override
    public List<Product> getLimitedServicesForResidential(Integer start, Integer length, String sort, String search, Integer categoryId, Integer placeId) {
        String query = LimitedProductsQueryBuilder.getQuery(SELECT_LIMITED_SERVICES_FOR_RESIDENTIAL_SQL, sort, search, categoryId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("place_id", placeId);
        return jdbcTemplate.query(query, params, productRowMapper);
    }

    @Override
    public Integer getCountForLimitedServicesForBusiness(String search, Integer categoryId) {
        String query = LimitedProductsQueryBuilder.getQuery(SELECT_COUNT_FOR_SERVICES_FOR_BUSINESS_SQL, "", search, categoryId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        return jdbcTemplate.queryForObject(query, params, Integer.class);

    }

    @Override
    public Integer getCountForLimitedServicesForResidential(String search, Integer categoryId, Integer placeId) {
        String query = LimitedProductsQueryBuilder.getQuery(SELECT_COUNT_FOR_SERVICES_FOR_RESIDENT_SQL, "", search, categoryId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("place_id", placeId);
        return jdbcTemplate.queryForObject(query, params, Integer.class);
    }

    @Override
    public Integer getCountActiveProductsWithSearch(String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_ACTIVE_COUNT, params, Integer.class);
    }

    @Override
    public List<Product> getLimitedQuantityActiveProduct(int start, int length, String sort, String search) {
        int rownum = start + length;
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_LIMITED_ACTIVE_PRODUCTS, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", rownum);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.query(sql, params, new ProductRowMapper());
    }

    @Override
    public String getProductTypeByProductId(Integer productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("productId", productId);
        return jdbcTemplate.queryForObject(FIND_PRODUCT_TYPE_BY_ID, params, String.class);
    }

    @Override
    public String getCustomerTypeByProductId(Integer productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("productId", productId);
        return jdbcTemplate.queryForObject(FIND_CUSTOMER_TYPE_BY_ID, params, String.class);
    }

    @Override
    public Product getProductByOrderId(int orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource("orderId", orderId);
        return jdbcTemplate.queryForObject(GET_PRODUCT_BY_ORDER_ID, params, new ProductRowMapper());
    }
}
