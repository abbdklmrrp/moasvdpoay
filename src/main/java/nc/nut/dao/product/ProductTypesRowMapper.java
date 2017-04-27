package nc.nut.dao.product;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Rysakova Anna on 24.04.2017.
 */
@Component
class ProductTypesRowMapper implements RowMapper<ProductTypes> {
    @Override
    public ProductTypes mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductTypes productTypes = new ProductTypes();
        productTypes.setId(rs.getInt("ID"));
        productTypes.setName(rs.getString("NAME"));

        return productTypes;
    }
}