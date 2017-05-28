package jtelecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtelecom.dao.product.Product;

/**
 * @author Anna Rysakova
 */
public class ProductWithTypeNameDTO extends Product {
    @JsonProperty("product_type_name")
    private String productTypeName;

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }
}
