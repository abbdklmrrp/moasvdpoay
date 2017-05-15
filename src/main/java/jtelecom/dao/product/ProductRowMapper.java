package jtelecom.dao.product;

import jtelecom.dao.entity.CustomerType;
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
        product.setName(rs.getString("NAME"));
        product.setDescription(rs.getString("DESCRIPTION"));
        Integer processingStrategyId = rs.getInt("NEED_PROCESSING");
        product.setProcessingStrategy(ProcessingStrategy.getProcessingStrategyFromId(processingStrategyId));
        Integer statusId = rs.getInt("STATUS");
        product.setStatus(ProductStatus.getProductStatusFromId(statusId));
        product.setBasePrice(rs.getBigDecimal("BASE_PRICE"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setDurationInDays(rs.getInt("duration"));
        product.setProductType(ProductType.getProductTypeFromId(rs.getInt("type_id")));
        product.setCustomerType(CustomerType.getCustomerTypeFromId(rs.getInt("customer_type_id")));
        return product;
    }
}
