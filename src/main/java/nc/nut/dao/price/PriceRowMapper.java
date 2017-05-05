package nc.nut.dao.price;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
class PriceRowMapper implements RowMapper<Price> {
    @Override
    public Price mapRow(ResultSet resultSet, int i) throws SQLException {
        Price price = new Price();
        price.setPlaceId(resultSet.getInt("place_id"));
        price.setProductId(resultSet.getInt("product_id"));
        price.setPrice(resultSet.getBigDecimal("price"));
        return price;
    }
}