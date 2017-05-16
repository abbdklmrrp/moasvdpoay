package jtelecom.dto;

import jtelecom.dao.product.Product;

import java.util.List;

/**
 * Created by Anton on 16.05.2017.
 */
public class TariffsDataPartitionDTO {
    private Product currentTariff;
    private Integer quantityOfAllTariffs;
    private List<Product> partOfTariffs;
    private Integer userId = -1;

    public TariffsDataPartitionDTO() {
    }

    public TariffsDataPartitionDTO(Integer quantityOfAllTariffs, List<Product> partOfTariffs, Product currentTariff, Integer userId) {
        this.quantityOfAllTariffs = quantityOfAllTariffs;
        this.partOfTariffs = partOfTariffs;
        this.currentTariff = currentTariff;
        this.userId = userId;
    }

    public Integer getQuantityOfAllTariffs() {
        return quantityOfAllTariffs;
    }

    public void setQuantityOfAllTariffs(Integer quantityOfAlltariffs) {
        this.quantityOfAllTariffs = quantityOfAlltariffs;
    }

    public List<Product> getPartOfTariffs() {
        return partOfTariffs;
    }

    public void setPartOfTariffs(List<Product> partOfTariffs) {
        this.partOfTariffs = partOfTariffs;
    }

    public Product getCurrentTariff() {
        return currentTariff;
    }

    public void setCurrentTariff(Product currentTariff) {
        this.currentTariff = currentTariff;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TariffsDataPartitionDTO{" +
                "currentTariff=" + currentTariff +
                ", quantityOfAllTariffs=" + quantityOfAllTariffs +
                ", partOfTariffs=" + partOfTariffs +
                ", userId=" + userId +
                '}';
    }
}
