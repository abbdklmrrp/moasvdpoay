package nc.nut.dao.price;

import nc.nut.dao.entity.Price;
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
        price.setPlace_id(resultSet.getInt("place_id"));
        price.setProduct_id(resultSet.getInt("product_id"));
        price.setPrice(resultSet.getBigDecimal("price"));
        return price;
    }
}