package nc.nut.dao.price;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
@Service
public class PriceDaoImp implements PriceDao {
    private static String SELECT_PRICE_BY_PRODUCT_AND_PLACE_SQL = "SELECT PRICES.PLACE_ID, PRICES.PRODUCT_ID, PRICES.PRICE" +
            "  FROM PRICES WHERE PRODUCT_ID = :product_id AND PLACE_ID = :place_id";

    private final static String ADD_PRICE_OF_PRODUCT_BY_REGION = "INSERT INTO PRICES\n " +
            "VALUES(:productId,:placeId,:price)";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Price getById(int id) {
        return null;
    }

    @Override
    public boolean update(Price object) {
        return false;
    }

    @Override
    public boolean save(Price object) {
        return false;
    }

    @Override
    public boolean delete(Price object) {
        return false;
    }

    @Override
    public Price getPriceByProductIdAndPlaceId(Integer productId, Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId);
        params.addValue("place_id", placeId);
        return jdbcTemplate.queryForObject(SELECT_PRICE_BY_PRODUCT_AND_PLACE_SQL, params, new PriceRowMapper());
    }

    @Override
    public boolean fillPriceOfProductByRegion(ArrayList<Price> priceByRegion) {
        List<Map<String, Object>> batchValues = new ArrayList<>(priceByRegion.size());
        for (Price price : priceByRegion) {
            batchValues.add(
                    new MapSqlParameterSource("productId", price.getProductId())
                            .addValue("placeId", price.getPlaceId())
                            .addValue("price", price.getPrice())
                            .getValues());
        }
        int[] isInsert = jdbcTemplate.batchUpdate(ADD_PRICE_OF_PRODUCT_BY_REGION, batchValues.toArray(new Map[priceByRegion.size()]));
        return isInsert.length != 0;
    }
}
