package nc.nut.dao.product;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Anton on 08.05.2017.
 */
@Component
class TariffRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("ID"));
        product.setName(rs.getString("NAME"));
        product.setDescription(rs.getString("DESCRIPTION"));
        Integer processingStrategyId = rs.getInt("NEED_PROCESSING");
        product.setProcessingStrategy(ProcessingStrategy.getProcessingStrategyFromId(processingStrategyId));
        Integer statusId = rs.getInt("STATUS");
        product.setStatus(ProductStatus.getProductStatusFromId(statusId));
        product.setDurationInDays(rs.getInt("duration"));
        product.setProductType(ProductType.getProductTypeFromId(rs.getInt("type_id")));
        product.setBasePrice(rs.getBigDecimal("base_price"));
        return product;
    }
}

