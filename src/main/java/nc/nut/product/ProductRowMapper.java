package nc.nut.product;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Anna on 23.04.2017.
 */
@Component
class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("ID"));
        product.setCategoryId(rs.getInt("CATEGORY_ID"));
        product.setDuration(rs.getInt("DURATION"));
        product.setTypeId(rs.getInt("TYPE_ID"));
        product.setNeedProcessing(rs.getInt("NEED_PROCESSING"));
        product.setName(rs.getString("NAME"));
        product.setDescription(rs.getString("DESCRIPTION"));
        product.setStatus(rs.getInt("STATUS"));

        return product;
    }
}
