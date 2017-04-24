package nc.nut.product;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Anna on 23.04.2017.
 */
@Service
public class ProductDaoImpl implements ProductDao {
    private final static String ADD_SERVICE = "INSERT INTO PRODUCTS(TYPE_ID,CATEGORY_ID,NAME,DURATION," +
            "NEED_PROCESSING,DESCRIPTION,STATUS) VALUES(:typeId,:categoryId,:nameProduct,:duration," +
            ":needProcessing,:description,:status)";
    private final static String FIND_CATEGORIES = "SELECT * FROM PRODUCT_CATEGORIES";
    private final static String FIND_TYPES = "SELECT * FROM PRODUCT_TYPES";
    private final static String FIND_SERVICES = "SELECT * FROM PRODUCTS WHERE TYPE_ID=2 ORDER BY ID";
    private final static String FIND_TARIFFS = "SELECT * FROM PRODUCTS WHERE TYPE_ID=1 ORDER BY ID";
    private final static String ADD_TARIFF_SERVICE = "INSERT INTO TARIFF_SERVICES VALUES(:tariff_id,:service_id)";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Resource
    private ProductCategoriesRowMapper categoriesRowMapper;
    @Resource
    private ProductTypesRowMapper typesRowMapper;
    @Resource
    private ProductRowMapper productRowMapper;

    @Override
    public void addProduct(Product product) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", product.getId());
        params.addValue("typeId", product.getTypeId());
        params.addValue("categoryId", product.getCategoryId());
        params.addValue("nameProduct", product.getName());
        params.addValue("duration", product.getDuration());
        params.addValue("needProcessing", product.getNeedProcessing());
        params.addValue("description", product.getDescription());
        params.addValue("status", product.getStatus());

        jdbcTemplate.update(ADD_SERVICE, params);
    }

    @Override
    public List<ProductCategories> findProductCategories() {
        List<ProductCategories> productCategories = jdbcTemplate.query(FIND_CATEGORIES, categoriesRowMapper);
        return productCategories.isEmpty() ? null : productCategories;
    }

    @Override
    public List<Product> getServices() {
        List<Product> services = jdbcTemplate.query(FIND_SERVICES, productRowMapper);
        return services.isEmpty() ? null : services;
    }

    @Override
    public List<Product> getTariffs() {
        List<Product> tariffs = jdbcTemplate.query(FIND_TARIFFS, productRowMapper);
        return tariffs.isEmpty() ? null : tariffs;
    }

    @Override
    public void identifyTariff(int idTariff, int idService) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tariff_id", idTariff);
        params.addValue("service_id", idService);
        jdbcTemplate.update(ADD_TARIFF_SERVICE, params);
    }

    @Override
    public List<ProductTypes> findProductTypes() {
        List<ProductTypes> productTypes = jdbcTemplate.query(FIND_TYPES, typesRowMapper);
        return productTypes.isEmpty() ? null : productTypes;
    }
}
