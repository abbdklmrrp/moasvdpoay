package nc.nut.dao.product;

import nc.nut.dao.user.UserDAO;
import nc.nut.mail.Mailer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rysakova Anna on 23.04.2017.
 */
@Service
public class ProductDaoImpl implements ProductDao {

    private final static String FIND_ALL_CATEGORIES = "SELECT * FROM PRODUCT_CATEGORIES";
    private final static String FIND_CATEGORY = "SELECT * FROM PRODUCT_CATEGORIES WHERE NAME=:name";
    private final static String FIND_TYPES = "SELECT * FROM PRODUCT_TYPES";
    private final static String FIND_SERVICES = "SELECT * FROM PRODUCTS WHERE TYPE_ID=2 ORDER BY ID";
    private final static String FIND_TARIFFS = "SELECT * FROM PRODUCTS WHERE TYPE_ID=1 ORDER BY ID";

    private final static String FIND_ALL_SERVICES = "SELECT prod.ID, prod.NAME, prod.DESCRIPTION,prod.DURATION,prod.NEED_PROCESSING,\n" +
            "prod.TYPE_ID,prod.CATEGORY_ID\n" +
            "FROM PRODUCTS prod JOIN PRODUCT_TYPES pTypes ON (prod.TYPE_ID=pTypes.ID)\n" +
            "JOIN PRODUCT_CATEGORIES pCategories ON (prod.CATEGORY_ID=pCategories.ID)\n" +
            "WHERE prod.STATUS=1 AND pTypes.name='Service' AND pCategories.name=:categoryName";

    private final static String FIND_ALL_FREE_TARIFFS = "SELECT * FROM PRODUCTS p join PRODUCT_TYPES ptype ON(p.TYPE_ID=ptype.ID)\n" +
            "    LEFT JOIN TARIFF_SERVICES ts ON(p.ID=ts.TARIFF_ID)\n" +
            "WHERE ptype.name='Tariff' AND ts.TARIFF_ID IS NULL";

    private final static String ADD_TARIFF_SERVICE = "INSERT INTO TARIFF_SERVICES VALUES(:tariff_id,:service_id)";
    private final static String ADD_CATEGORY = "INSERT INTO PRODUCT_CATEGORIES(NAME,DESCRIPTION) VALUES(:name,:description)";
    private final static String ADD_PRODUCT = "INSERT INTO PRODUCTS(TYPE_ID,CATEGORY_ID,NAME,DURATION," +
            "NEED_PROCESSING,DESCRIPTION,STATUS) VALUES(:typeId,:categoryId,:nameProduct,:duration," +
            ":needProcessing,:description,:status)";
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
    private final static String GET_CURRENT_USER_TARIFF_BY_USER_ID_SQL = "SELECT " +
            "id, " +
            "category_id, " +
            "duration, " +
            "type_id, " +
            "need_processing, " +
            "name, " +
            "description, " +
            "status FROM Products " +
            "WHERE id = (SELECT product_id FROM Orders WHERE user_id = :userId AND current_status_id = 1/* id = 1 - active status */)";
    private final static String GET_TARIFFS_BY_PLACE_SQL = "SELECT " +
            "id, " +
            "category_id, " +
            "duration, " +
            "type_id, " +
            "need_processing, " +
            "name, " +
            "description, " +
            "status FROM Products " +
            "WHERE id IN (SELECT place_id FROM Prices WHERE product_id = :placeId) AND type_id = 1/*id = 1 - Tariff*/";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Resource
    private ProductCategoriesRowMapper categoriesRowMapper;
    @Resource
    private ProductTypesRowMapper typesRowMapper;
    @Resource
    private ProductRowMapper productRowMapper;
    @Resource
    private UserDAO userDAO;
    @Resource
    private Mailer mailer;

    @Override
    public boolean save(Product product) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("typeId", product.getProductType());
        params.addValue("categoryId", product.getCategoryId());
        params.addValue("nameProduct", product.getName());
        params.addValue("duration", product.getDurationInDays());
        params.addValue("needProcessing", product.getNeedProcessing());
        params.addValue("description", product.getDescription());
        params.addValue("status", product.getStatus());
        params.addValue("type_id", product.getProductType());
        int isUpdate = jdbcTemplate.update(ADD_PRODUCT, params);

        return isUpdate > 0;

    }

    @Override
    public List<ProductCategories> findProductCategories() {
        return jdbcTemplate.query(FIND_ALL_CATEGORIES, categoriesRowMapper);
    }

    @Override
    public List<Product> getAllServices() {
        return jdbcTemplate.query(FIND_SERVICES, productRowMapper);
    }

    @Override
    public List<Product> getAllTariffs() {
        return jdbcTemplate.query(FIND_TARIFFS, productRowMapper);
    }

    //TODO boolean
    @Override
    public void fillTariff(int idTariff, int idService) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tariff_id", idTariff);
        params.addValue("service_id", idService);
        int update = jdbcTemplate.update(ADD_TARIFF_SERVICE, params);

    }

    @Override
    public boolean addCategory(ProductCategories categories) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", categories.getName());
        params.addValue("description", categories.getDescription());
        int isUpdate = jdbcTemplate.update(ADD_CATEGORY, params);
        return isUpdate > 0;
    }

    @Override
    public List<ProductCategories> findIdCategory(ProductCategories categories) {
        List<ProductCategories> categoriesList = jdbcTemplate.query(FIND_CATEGORY, categoriesRowMapper);
        return categoriesList;
    }

    @Override
    public List<ProductTypes> findProductTypes() {
        List<ProductTypes> productTypes = jdbcTemplate.query(FIND_TYPES, typesRowMapper);
        return productTypes;
    }

    //TODO почему бы не использовать внешний класс ProductRowMapper?
    @Override
    public List<Product> getAllServices(String categoryName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("categoryName", categoryName);
        List<Product> services = jdbcTemplate.query(FIND_ALL_SERVICES, params, (rs, rowNum) -> {
            Product product = new Product();
            product.setCategoryId(rs.getInt("CATEGORY_ID"));
            product.setId(rs.getInt("ID"));
            Integer productType = rs.getInt("type_id");
            product.setProductType(rs.getInt("type_id"));
            product.setNeedProcessing(rs.getInt("NEED_PROCESSING"));
            product.setDurationInDays(rs.getInt("DURATION"));
            product.setName(rs.getString("NAME"));
            product.setDescription(rs.getString("DESCRIPTION"));
            return product;
        });

        return services;
    }

    //TODO почему бы не использовать внешний класс ProductRowMapper
    @Override
    public List<Product> getAllFreeTariffs() {
        List<Product> tariffs = jdbcTemplate.query(FIND_ALL_FREE_TARIFFS, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("ID"));
            Integer productType = rs.getInt("type_id");
            product.setProductType(rs.getInt("type_id"));
            product.setNeedProcessing(rs.getInt("NEED_PROCESSING"));
            product.setDurationInDays(rs.getInt("DURATION"));
            product.setName(rs.getString("NAME"));
            product.setDescription(rs.getString("DESCRIPTION"));
            return product;
        });
        return tariffs;
    }

    @Override
    public boolean delete(Product product) {
        return false;
    }

    @Override
    public Product getById(int id) {
        return null;
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

    @Override
    public boolean update(Product product) {
        return false;
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
     * Method returns current user`s tariff. If user hasn`t got tariff, method returns null.
     *
     * @param userId user Id.
     * @return current user`s tariff.
     */
    @Override
    public Product getCurrentUserTariff(Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        return jdbcTemplate.queryForObject(GET_CURRENT_USER_TARIFF_BY_USER_ID_SQL, params, new ProductRowMapper());
    }

    /**
     * Method returns all tariffs are available in place with id from params. If there are no tariffs in this place, method returns empty list.
     *
     * @param placeId id of place.
     * @return list of tariffs.
     */
    @Override
    public List<Product> getAvailableTariffsByPlace(Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("placeId", placeId);
        return jdbcTemplate.query(GET_TARIFFS_BY_PLACE_SQL, params, new ProductRowMapper());
    }
}
