package jtelecom.dto;

import jtelecom.dao.price.Price;
import jtelecom.dao.product.Product;

/**
 * Created by Yuliya Pedash on 29.04.2017.
 */
public class ProductCatalogRowDTO {

    private final Product product;
    private final String status;
    private final Price price;

    public ProductCatalogRowDTO(Product product, String status, Price price) {
        this.product = product;
        this.status = status;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public String getStatus() {
        return status;
    }

    public Price getPrice() {
        return price;
    }
}