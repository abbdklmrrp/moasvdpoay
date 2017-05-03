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

    public static ProductType getProductTypeByID(Integer id) {
        switch (id) {
            case (1):
                return ProductType.Tariff;

            case (2):
                return ProductType.Service;
            default:
                return null;

        }
    }
}
