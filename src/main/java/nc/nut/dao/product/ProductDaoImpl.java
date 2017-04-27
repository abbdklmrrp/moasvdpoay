package nc.nut.dao.product;

import nc.nut.dao.user.User;
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
    private final static String ADD_SERVICE = "INSERT INTO PRODUCTS(TYPE_ID,CATEGORY_ID,NAME,DURATION," +
            "NEED_PROCESSING,DESCRIPTION,STATUS) VALUES(:typeId,:categoryId,:nameProduct,:duration," +
            ":needProcessing,:description,:status)";
    private final static String FIND_CATEGORIES = "SELECT * FROM PRODUCT_CATEGORIES";
    private final static String FIND_TYPES = "SELECT * FROM PRODUCT_TYPES";
    private final static String FIND_SERVICES = "SELECT * FROM PRODUCTS WHERE TYPE_ID=2 ORDER BY ID";
    private final static String FIND_TARIFFS = "SELECT * FROM PRODUCTS WHERE TYPE_ID=1 ORDER BY ID";
    private final static String ADD_TARIFF_SERVICE = "INSERT INTO TARIFF_SERVICES VALUES(:tariff_id,:service_id)";
    private final static String FIND_ALL_SERVICES = "SELECT prod.ID, prod.NAME, prod.DESCRIPTION,prod.DURATION,prod.NEED_PROCESSING,\n" +
            "prod.TYPE_ID,prod.CATEGORY_ID\n" +
            "FROM PRODUCTS prod JOIN PRODUCT_TYPES pTypes ON (prod.TYPE_ID=pTypes.ID)\n" +
            "JOIN PRODUCT_CATEGORIES pCategories ON (prod.CATEGORY_ID=pCategories.ID)\n" +
            "WHERE prod.STATUS=1 AND pTypes.name='Service' AND pCategories.name=:categoryName";
    private final static String FIND_ALL_FREE_TARIFFS = "SELECT * FROM PRODUCTS p join PRODUCT_TYPES ptype ON(p.TYPE_ID=ptype.ID)\n" +
            "    LEFT JOIN TARIFF_SERVICES ts ON(p.ID=ts.TARIFF_ID)\n" +
            "WHERE ptype.name='Tariff' AND ts.TARIFF_ID IS NULL";
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
        List<User> users = userDAO.getAllClient();
        mailer.sendProposal(users, product.getDescription());

    }

    @Override
    public List<ProductCategories> findProductCategories() {
        List<ProductCategories> productCategories = jdbcTemplate.query(FIND_CATEGORIES, categoriesRowMapper);
        return productCategories;
    }

    @Override
    public List<Product> getServices() {
        List<Product> services = jdbcTemplate.query(FIND_SERVICES, productRowMapper);
        return services;
    }

    @Override
    public List<Product> getTariffs() {
        List<Product> tariffs = jdbcTemplate.query(FIND_TARIFFS, productRowMapper);
        return tariffs;
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
        return productTypes;
    }

    @Override
    public List<Product> getServices(String categoryName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("categoryName", categoryName);
        List<Product> services = jdbcTemplate.query(FIND_ALL_SERVICES, params, (rs, rowNum) -> {
            Product product = new Product();
            product.setCategoryId(rs.getInt("CATEGORY_ID"));
            product.setId(rs.getInt("ID"));
            product.setTypeId(rs.getInt("TYPE_ID"));
            product.setNeedProcessing(rs.getInt("NEED_PROCESSING"));
            product.setDuration(rs.getInt("DURATION"));
            product.setName(rs.getString("NAME"));
            product.setDescription(rs.getString("DESCRIPTION"));
            return product;
        });

        return services;
    }

    @Override
    public List<Product> getAllFreeTariffs() {
        List<Product> tariffs = jdbcTemplate.query(FIND_ALL_FREE_TARIFFS, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("ID"));
            product.setTypeId(rs.getInt("TYPE_ID"));
            product.setNeedProcessing(rs.getInt("NEED_PROCESSING"));
            product.setDuration(rs.getInt("DURATION"));
            product.setName(rs.getString("NAME"));
            product.setDescription(rs.getString("DESCRIPTION"));
            return product;
        });
        return tariffs;
    }
}
