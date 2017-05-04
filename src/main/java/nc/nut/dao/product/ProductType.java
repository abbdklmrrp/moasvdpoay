package nc.nut.dao.product;

import java.util.Objects;

/**
 * Created by Yuliya Pedash on 28.04.2017.
 */
public enum ProductType {
    Tariff(1, "Tariff"),
    Service(2, "Service");
    private Integer id;
    private String name;

    ProductType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * This method gets <code>ProductType</code> object by id
     *
     * @param id id of product type
     * @return ProductType object  or <code>null</code> if object by this id is not found
     */
    public static ProductType getProductTypeById(Integer id) {
        ProductType[] productTypes = values();
        for (ProductType productType : productTypes) {
            if (Objects.equals(productType.getId(), id)) {
                return productType;
            }
        }
        return null;
    }
}
