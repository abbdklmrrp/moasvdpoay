package nc.nut.dao.price;

import nc.nut.dao.entity.Price;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
@Service
public class PriceDaoImp implements PriceDao {
    private static String SELECT_PRICE = "SELECT PRICES.PLACE_ID, PRICES.PRODUCT_ID, PRICES.PRICE" +
            "  FROM PRICES PRODUCT_ID = :product_id AND PLACE_ID = :place_id";
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
    public Price getPriceByProductIdAndPlaceId(int productId, long placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId);
        params.addValue("place_id", placeId);
        return jdbcTemplate.queryForObject(SELECT_PRICE, params, new PriceRowMapper());
    }
}
