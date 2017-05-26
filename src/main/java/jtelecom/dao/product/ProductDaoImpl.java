package jtelecom.dao.product;

import jtelecom.dto.TariffServiceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @author Anna Rysakova
 * @author Yuliya Pedash
 * @author Anton Bulgakov
 * @author Moiseienko Petro
 * @author Alistratenko Nikita
 */
@Service
public class ProductDaoImpl implements ProductDao {

    private final static String INSERT_PRODUCT_SQL = "INSERT INTO PRODUCTS(" +
            " TYPE_ID," +
            " CATEGORY_ID," +
            " NAME,DURATION," +
            " CUSTOMER_TYPE_ID," +
            " NEED_PROCESSING," +
            " DESCRIPTION," +
            " STATUS," +
            " BASE_PRICE) VALUES(:typeId," +
            "                   :categoryId," +
            "                   :nameProduct," +
            "                   :duration," +
            "                   :customerTypeId," +
            "                   :needProcessing," +
            "                   :description," +
            "                   :status," +
            "                   :basePrice)";
    private final static String SELECT_PRODUCT_BY_ID_SQL = "SELECT * FROM PRODUCTS WHERE ID=:id";
    private final static String UPDATE_SERVICE_SQL = "UPDATE PRODUCTS SET " +
            " NAME=:name,\n" +
            " DURATION=:duration,\n" +
            " NEED_PROCESSING=:needProcessing,\n" +
            " DESCRIPTION=:description,\n" +
            " STATUS=:status,\n" +
            " BASE_PRICE=:basePrice,\n" +
            " CUSTOMER_TYPE_ID=:customerType\n" +
            "   WHERE ID=:id";
    private final static String INSERT_CATEGORY_SQL = "INSERT INTO PRODUCT_CATEGORIES(NAME,DESCRIPTION)" +
            " VALUES(:name,:description)";
    private final static String SELECT_PRODUCT_TYPE_BY_PRODUCT_ID_SQL = "SELECT " +
            " type.NAME\n" +
            "  FROM PRODUCT_TYPES type\n" +
            "  JOIN PRODUCTS product ON (type.ID = product.TYPE_ID)\n" +
            "WHERE product.ID = :productId";
    private final static String SELECT_CUSTOMER_TYPE_BY_PRODUCT_ID_SQL = "SELECT " +
            " type.NAME\n" +
            "  FROM CUSTOMER_TYPES type\n" +
            "  JOIN PRODUCTS product ON (type.ID = product.CUSTOMER_TYPE_ID)\n" +
            "   WHERE product.ID = :productId";
    private final static String SELECT_ALL_CATEGORIES_SQL = "SELECT * FROM PRODUCT_CATEGORIES";
    private final static String SELECT_ALL_SERVICES_WITH_CATEGORY_SQL = "SELECT \n" +
            " prod.ID, \n" +
            " prod.NAME, \n" +
            " prod.CATEGORY_ID, \n" +
            " pCategories.NAME as Category \n" +
            "  FROM PRODUCTS prod \n" +
            "  JOIN PRODUCT_TYPES pTypes ON (prod.TYPE_ID=pTypes.ID) \n" +
            "  JOIN PRODUCT_CATEGORIES pCategories ON (prod.CATEGORY_ID=pCategories.ID) \n" +
            "   WHERE prod.STATUS=1 /*active status id*/ AND pTypes.name='Service'";
    private final static String SELECT_SERVICES_BY_TARIFF_SQL = "SELECT\n" +
            " p.ID,\n" +
            " p.CATEGORY_ID,\n" +
            " category.NAME CATEGORY_NAME,\n" +
            " p.NAME\n," +
            " ts.TARIFF_ID TARIFF_ID\n" +
            "  FROM PRODUCTS p\n" +
            "   JOIN TARIFF_SERVICES ts ON (p.ID = ts.SERVICE_ID)\n" +
            "   JOIN PRODUCT_CATEGORIES CATEGORY ON (p.CATEGORY_ID = CATEGORY.ID)\n" +
            "    WHERE ts.TARIFF_ID = :tariffId";
    private final static String SELECT_SERVICES_NOT_IN_TARIFF_SQL = "SELECT \n" +
            " p.ID, \n" +
            " p.CATEGORY_ID, \n" +
            " p.NAME, \n" +
            " pCategories.NAME AS Category \n" +
            "  FROM PRODUCT_CATEGORIES pCategories LEFT JOIN ( \n" +
            "   SELECT * FROM products WHERE id NOT IN (SELECT ts.SERVICE_ID \n" +
            "    FROM TARIFF_SERVICES ts \n" +
            "     WHERE ts.TARIFF_ID = :tariffId) \n" +
            "     AND TYPE_ID=2 /*service product type id*/\n" +
            "     ) p ON pCategories.id=p.CATEGORY_ID";
    private static final String SELECT_COUNT = "SELECT count(ID)\n" +
            "  FROM PRODUCTS" +
            " WHERE upper(name) LIKE upper(:pattern) " +
            " OR upper(description) LIKE upper(:pattern) " +
            " OR duration LIKE :pattern " +
            " OR base_price LIKE :pattern ";
    private final static String SELECT_LIMITED_PRODUCTS_SQL = "SELECT *\n" +
            " FROM ( SELECT a.*, rownum rnum\n" +
            "       FROM ( SELECT * FROM PRODUCTS " +
            "        WHERE upper(name) like upper(:pattern) " +
            "        OR upper(description) like upper(:pattern) " +
            "        OR type_id like :pattern " +
            "        OR customer_type_id like :pattern " +
            "        OR duration like :pattern " +
            "        OR base_price like :pattern " +
            "        ORDER BY %s) a\n" +
            "       WHERE rownum <= :length )\n" +
            "       WHERE rnum > :start";
    private final static String INSERT_TARIFF_SERVICE_SQL = "INSERT INTO TARIFF_SERVICES(TARIFF_ID,SERVICE_ID) " +
            " VALUES(:tariffId,:serviceId)";
    private final static String DELETE_SERVICE_FROM_TARIFF_SQL = "DELETE FROM TARIFF_SERVICES" +
            " WHERE TARIFF_ID=:idTariff AND SERVICE_ID=:idService ";
    private final static String SELECT_PRODUCT_BY_ID_BASE_PRICE_SET_BY_PLACE_SQL = "SELECT id, type_id, category_id, name, duration, need_processing, DESCRIPTION,\n" +
            "  status,customer_type_id, price AS base_price\n" +
            "  FROM PRODUCTS\n" +
            "  INNER JOIN PRICES ON PRICES.PRODUCT_ID = PRODUCTS.ID " +
            "WHERE PRICES.PLACE_ID = :place_id AND PRODUCT_ID = :product_id";
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
            " AND ORDERS.CURRENT_STATUS_ID <> 3 /*Deactivated*/" +
            "  JOIN TARIFF_SERVICES\n" +
            "    ON p1.ID = TARIFF_SERVICES.TARIFF_ID\n" +
            "  JOIN PRODUCTS p2 ON p2.ID = TARIFF_SERVICES.SERVICE_ID ";
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
    private final static String DELETE_PLANNED_TASKS_FOR_TARIFF_SQL = "DELETE FROM planned_tasks" +
            " WHERE order_id = (SELECT" +
            "                    id FROM Orders WHERE" +
            "                    user_id = :userId" +
            "                    AND product_id = :tariffId" +
            "                    AND (current_status_id = 1/* Active */ " +
            "                         OR current_status_id = 2/* Suspended */ " +
            "                         OR current_status_id = 4/* In processing */))";
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
    private final static String SELECT_LIMITED_ACTIVE_PRODUCTS_FOR_BUSINESS = "select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from PRODUCTS " +
            " Where status = 1 and customer_type_id = 1 and (upper(name) like upper(:pattern) " +
            " OR upper(description) like upper(:pattern) " +
            " OR type_id like :pattern " +
            " OR customer_type_id like :pattern " +
            " OR duration like :pattern " +
            " OR base_price like :pattern) " +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";
    private final static String SELECT_LIMITED_ACTIVE_PRODUCTS_FOR_RESEDINTIAL = "select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from PRODUCTS " +
            " Where status = 1 and customer_type_id = 2 and (upper(name) like upper(:pattern) " +
            " OR upper(description) like upper(:pattern) " +
            " OR type_id like :pattern " +
            " OR customer_type_id like :pattern " +
            " OR duration like :pattern " +
            " OR base_price like :pattern) " +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";
    private final static String FIND_ENABLED_TARIFFS = "SELECT * FROM PRODUCTS WHERE TYPE_ID=1 AND STATUS=1 ORDER BY ID";
    private static final String SELECT_ACTIVE_PRODUCT_FOR_BUSINESS_COUNT = "SELECT count(ID)\n" +
            "  FROM PRODUCTS" +
            " Where status = 1 and customer_type_id = 1 and (upper(name) LIKE upper(:pattern) " +
            " OR upper(description) LIKE upper(:pattern) " +
            " OR duration LIKE :pattern " +
            " OR base_price LIKE :pattern) ";
    private static final String SELECT_ACTIVE_PRODUCT_FOR_RESEDENTIAL_COUNT = "SELECT count(ID)\n" +
            "  FROM PRODUCTS" +
            " Where status = 1 and customer_type_id = 2 and (upper(name) LIKE upper(:pattern) " +
            " OR upper(description) LIKE upper(:pattern) " +
            " OR duration LIKE :pattern " +
            " OR base_price LIKE :pattern) ";
    private final static String SELECT_LIMITED_SERVICES_FOR_BUSINESS_SQL = "SELECT *\n" +
            "FROM (SELECT\n" +
            "        products.*,\n" +
            "        ROW_NUMBER() OVER(ORDER BY %s) rnum\n" +
            "      FROM products\n" +
            "      WHERE TYPE_ID = 2 /*Service*/ AND status = 1 /*Active*/ AND customer_type_id = 1 /*Business*/ " +
            " AND LOWER(name) LIKE LOWER(:pattern) || '%%' %s)\n" +
            "WHERE rnum <= :length AND rnum > :start";
    private final static String SELECT_LIMITED_SERVICES_FOR_RESIDENTIAL_SQL = "SELECT *\n" +
            "FROM (SELECT\n" +
            "        id,\n" +
            "        type_id,\n" +
            "        category_id,\n" +
            "        name,\n" +
            "        duration,\n" +
            "        need_processing,\n" +
            "        description,\n" +
            "        status,\n" +
            "        CUSTOMER_TYPE_ID,\n" +
            "        price AS base_price, \n" +
            "        ROW_NUMBER() OVER (ORDER BY %s) rnum\n" +
            "      FROM products\n" +
            "        INNER JOIN PRICES ON PRICES.PRODUCT_ID = PRODUCTS.ID\n" +
            "      WHERE TYPE_ID = 2 /*Service*/ AND status = 1 /*Active*/\n" +
            "            AND place_id = :place_id AND LOWER(name) LIKE LOWER(:pattern" +
            ") || '%%'  %s) \n" +
            "WHERE rnum <= :length AND rnum > :start";
    private final static String SELECT_COUNT_FOR_SERVICES_FOR_BUSINESS_SQL = "\n" +
            "SELECT" +
            "  COUNT(*) " +
            "FROM products WHERE " +
            "   TYPE_ID = 2 /*Service*/ AND STATUS = 1 /*Active*/ AND customer_type_id = 1 " +
            "AND LOWER(name) LIKE LOWER(:pattern) || '%%' %s";
    private final static String SELECT_COUNT_FOR_SERVICES_FOR_RESIDENT_SQL = "SELECT COUNT(*)\n" +
            "FROM products\n" +
            "  INNER JOIN PRICES ON PRICES.PRODUCT_ID = PRODUCTS.ID\n" +
            "WHERE TYPE_ID = 2\n AND STATUS = 1 /*Active*/ " +
            " AND place_id = :place_id " +
            " AND LOWER(name) LIKE LOWER(:pattern) || '%%' %s";
    private static final String GET_PRODUCT_BY_ORDER_ID = "SELECT * " +
            " FROM PRODUCTS WHERE ID=( " +
            " SELECT PRODUCT_ID FROM ORDERS WHERE ID=:orderId)";
    final static String AND_CATEGORY_ID_SQL = "  AND category_id = :category_id ";
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
     * {@inheritDoc}
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
        int isUpdate = jdbcTemplate.update(INSERT_PRODUCT_SQL, params);
        return isUpdate > 0;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product getById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_ID_SQL, params, productRowMapper);
    }

    /**
     * {@inheritDoc}
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
        int isUpdate = jdbcTemplate.update(UPDATE_SERVICE_SQL, params);
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
        jdbcTemplate.update(INSERT_PRODUCT_SQL, params, key, new String[]{"ID"});
        return key.getKey().intValue();
    }

    /**
     * Rysakova Anna
     *
     * {@inheritDoc}
     */
    @Override
    public Integer saveCategory(ProductCategories categories) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", categories.getCategoryName());
        params.addValue("description", categories.getCategoryDescription());
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_CATEGORY_SQL, params, key, new String[]{"ID"});
        return key.getKey().intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProductTypeByProductId(int productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("productId", productId);
        return jdbcTemplate.queryForObject(SELECT_PRODUCT_TYPE_BY_PRODUCT_ID_SQL, params, String.class);
    }

    /**
     * Rysakova Anna
     *
     * {@inheritDoc}
     */
    @Override
    public String getCustomerTypeByProductId(int productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("productId", productId);
        return jdbcTemplate.queryForObject(SELECT_CUSTOMER_TYPE_BY_PRODUCT_ID_SQL, params, String.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductCategories> getProductCategories() {
        return jdbcTemplate.query(SELECT_ALL_CATEGORIES_SQL, categoriesRowMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<Product>> getAllServicesWithCategory() {
        Map<String, List<Product>> serviceMap = new HashMap<>();
        jdbcTemplate.query(SELECT_ALL_SERVICES_WITH_CATEGORY_SQL, (rs, rowNum) -> {
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
     * {@inheritDoc}
     */
    @Override
    public List<TariffServiceDto> getServicesInfoByTariff(int tariffId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tariffId", tariffId);
        return jdbcTemplate.query(SELECT_SERVICES_BY_TARIFF_SQL, params, (rs, rowNum) -> {
            TariffServiceDto productTmp = new TariffServiceDto();
            productTmp.setServiceId(rs.getInt("ID"));
            productTmp.setServiceName(rs.getString("NAME"));
            productTmp.setCategoryId(rs.getInt("CATEGORY_ID"));
            productTmp.setTariffId(rs.getInt("TARIFF_ID"));
            productTmp.setCategoryName(rs.getString("CATEGORY_NAME"));
            return productTmp;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TariffServiceDto> getServicesIDByTariff(int tariffId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tariffId", tariffId);
        return jdbcTemplate.query(SELECT_SERVICES_BY_TARIFF_SQL, params, (rs, rowNum) -> {
            TariffServiceDto productTmp = new TariffServiceDto();
            productTmp.setServiceId(rs.getInt("ID"));
            productTmp.setTariffId(rs.getInt("TARIFF_ID"));
            return productTmp;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<Product>> getServicesNotInTariff(int tariffId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tariffId", tariffId);
        Map<String, List<Product>> serviceMap = new HashMap<>();
        jdbcTemplate.query(SELECT_SERVICES_NOT_IN_TARIFF_SQL, params, (rs, rowNum) -> {
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
     * {@inheritDoc}
     */
    @Override
    public Integer getCountProductsWithSearch(String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_COUNT, params, Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> getLimitedQuantityProduct(int start, int length, String sort, String search) {
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_LIMITED_PRODUCTS_SQL, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.query(sql, params, new ProductRowMapper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void fillInTariffWithServices(List<TariffServiceDto> tariffServiceDtos) {

        List<Map<String, Object>> batchValues = new ArrayList<>(tariffServiceDtos.size());
        for (TariffServiceDto person : tariffServiceDtos) {
            batchValues.add(
                    new MapSqlParameterSource("tariffId", person.getTariffId())
                            .addValue("serviceId", person.getServiceId())
                            .getValues());
        }
        jdbcTemplate.batchUpdate(INSERT_TARIFF_SERVICE_SQL, batchValues.toArray(new Map[tariffServiceDtos.size()]));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void deleteServiceFromTariff(List<TariffServiceDto> tariffServiceDtos) {
        List<Map<String, Object>> batchValues = new ArrayList<>(tariffServiceDtos.size());
        for (TariffServiceDto person : tariffServiceDtos) {
            batchValues.add(
                    new MapSqlParameterSource("idTariff", person.getTariffId())
                            .addValue("idService", person.getServiceId())
                            .getValues());
        }
        jdbcTemplate.batchUpdate(DELETE_SERVICE_FROM_TARIFF_SQL, batchValues.toArray(new Map[tariffServiceDtos.size()]));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAllEnabledTariffs() {
        return jdbcTemplate.query(FIND_ENABLED_TARIFFS, productRowMapper);
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
    @Transactional
    public boolean deactivateTariff(Integer userId, Integer tariffId) {
        deletePlannedTasks(userId, tariffId);
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
    public boolean deletePlannedTasks(Integer userId, Integer tariffId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("tariffId", tariffId);
        return (jdbcTemplate.update(DELETE_PLANNED_TASKS_FOR_TARIFF_SQL, params) != 0);
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
    public Product findProductWithPriceSetByPlace(Integer productId, Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId);
        params.addValue("place_id", placeId);
        return jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_ID_BASE_PRICE_SET_BY_PLACE_SQL, params, productRowMapper);

    }

    @Override
    public List<Product> getLimitedServicesForBusiness(Integer start, Integer length, String sort, String search, Integer categoryId) {
        String query;
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (sort.isEmpty()) {
            sort = "name";
        }
        if (categoryId != null) {
            query = String.format(SELECT_LIMITED_SERVICES_FOR_BUSINESS_SQL, sort, AND_CATEGORY_ID_SQL);
            params.addValue("category_id", categoryId);
        } else {
            query = String.format(SELECT_LIMITED_SERVICES_FOR_BUSINESS_SQL, sort, "");
        }
        params.addValue("pattern", "%" + search + "%");
        params.addValue("start", start);
        params.addValue("length", length + 1);
        return jdbcTemplate.query(query, params, productRowMapper);
    }

    @Override
    public List<Product> getLimitedServicesForResidential(Integer start, Integer length, String sort, String search, Integer categoryId, Integer placeId) {
        // String query = LimitedQueryBuilder.getQuery(SELECT_LIMITED_SERVICES_FOR_RESIDENTIAL_SQL, sort, search, categoryId);
        String query;
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (sort.isEmpty()) {
            sort = "name";
        }
        if (categoryId != null) {
            query = String.format(SELECT_LIMITED_SERVICES_FOR_RESIDENTIAL_SQL, sort, AND_CATEGORY_ID_SQL);
            params.addValue("category_id", categoryId);
        } else {
            query = String.format(SELECT_LIMITED_SERVICES_FOR_RESIDENTIAL_SQL, sort, "");
        }
        params.addValue("pattern", "%" + search + "%");
        params.addValue("start", start);
        params.addValue("length", length + 1);
        params.addValue("place_id", placeId);
        return jdbcTemplate.query(query, params, productRowMapper);
    }

    @Override
    public Integer getCountForLimitedServicesForBusiness(String search, Integer categoryId) {
        String query;
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (categoryId != null) {
            query = String.format(SELECT_COUNT_FOR_SERVICES_FOR_BUSINESS_SQL, AND_CATEGORY_ID_SQL);
            params.addValue("category_id", categoryId);
        } else {
            query = String.format(SELECT_COUNT_FOR_SERVICES_FOR_BUSINESS_SQL, "");
        }
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(query, params, Integer.class);

    }

    @Override
    public Integer getCountForLimitedServicesForResidential(String search, Integer categoryId, Integer placeId) {
        String query;
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (categoryId != null) {
            query = String.format(SELECT_COUNT_FOR_SERVICES_FOR_RESIDENT_SQL, AND_CATEGORY_ID_SQL);
            params.addValue("category_id", categoryId);
        } else {
            query = String.format(SELECT_COUNT_FOR_SERVICES_FOR_RESIDENT_SQL, "");
        }
        params.addValue("place_id", placeId);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(query, params, Integer.class);
    }

    @Override
    public List<Product> getLimitedActiveProductsForBusiness(Integer start, Integer length, String sort, String search) {
        int rownum = start + length;
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_LIMITED_ACTIVE_PRODUCTS_FOR_BUSINESS, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", rownum);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.query(sql, params, new ProductRowMapper());
    }

    @Override
    public List<Product> getLimitedActiveProductsForResidential(Integer start, Integer length, String sort, String search) {
        int rownum = start + length;
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_LIMITED_ACTIVE_PRODUCTS_FOR_RESEDINTIAL, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", rownum);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.query(sql, params, new ProductRowMapper());
    }

    @Override
    public Integer getCountForLimitedActiveProductsForBusiness(String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_ACTIVE_PRODUCT_FOR_BUSINESS_COUNT, params, Integer.class);
    }

    @Override
    public Integer getCountForLimitedActiveProductsForResidential(String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_ACTIVE_PRODUCT_FOR_RESEDENTIAL_COUNT, params, Integer.class);
    }

    @Override
    public Product getProductByOrderId(int orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource("orderId", orderId);
        return jdbcTemplate.queryForObject(GET_PRODUCT_BY_ORDER_ID, params, new ProductRowMapper());
    }

    @Override
    public boolean delete(Product product) {
        return false;
    }
}