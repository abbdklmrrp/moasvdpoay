package nc.nut.dao.product;

/**
 * Created by Yuliya Pedash on 28.04.2017.
 */
public enum ProductType {
    Tariff("Tariff"),
    Service("Service");
    private String productType;

    ProductType(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }
}
