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
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private ProductCategoriesRowMapper categoriesRowMapper;
    @Resource
    private ProductTypesRowMapper typesRowMapper;

    @Override
    public void addProduct(Product product) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("typeId", product.getTypeId());
        params.addValue("categoryId", product.getCategoryId());
        params.addValue("nameProduct", product.getName());
        params.addValue("duration", product.getDuration());
        params.addValue("needProcessing", product.getNeedProcessing());
        params.addValue("description", product.getDescription());
        params.addValue("status", product.getStatus());

        namedParameterJdbcTemplate.update(ADD_SERVICE, params);
    }

    @Override
    public List<ProductCategories> findProductCategories() {
        List<ProductCategories> productCategories = namedParameterJdbcTemplate.query(FIND_CATEGORIES, categoriesRowMapper);
        return productCategories.isEmpty() ? null : productCategories;
    }

    @Override
    public List<ProductTypes> findProductTypes() {
        List<ProductTypes> productTypes = namedParameterJdbcTemplate.query(FIND_TYPES, typesRowMapper);
        return productTypes.isEmpty() ? null : productTypes;
    }
}
