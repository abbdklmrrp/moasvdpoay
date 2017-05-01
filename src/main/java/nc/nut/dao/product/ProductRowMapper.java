package nc.nut.dao.product;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Rysakova Anna on 23.04.2017.
 */
@Component
class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("ID"));
        product.setNeedProcessing(rs.getInt("NEED_PROCESSING"));
        product.setName(rs.getString("NAME"));
        product.setDescription(rs.getString("DESCRIPTION"));
        product.setStatus(rs.getInt("STATUS"));
        product.setBasePrice(rs.getBigDecimal("BASE_PRICE"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setDurationInDays(rs.getInt("duration"));
        product.setProductType(rs.getInt("type_id"));
        return product;
    }
}
