package jtelecom.dto;

import jtelecom.dao.operationHistory.OperationHistoryRecord;

import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 15.05.2017.
 */
public class OperationHistoryDataPartitionDTO {
    private Integer amount;
    private List<OperationHistoryRecord> partOfOperations;

    public OperationHistoryDataPartitionDTO(Integer amount, List<OperationHistoryRecord> partOfOperations) {
        this.amount = amount;
        this.partOfOperations = partOfOperations;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<OperationHistoryRecord> getPartOfOperations() {
        return partOfOperations;
    }

    public void setPartOfOperations(List<OperationHistoryRecord> partOfOperations) {
        this.partOfOperations = partOfOperations;
    }

    @Override
    public String toString() {
        return "OperationHistoryDataPartitionDTO{" +
                "amount=" + amount +
                ", partOfOperations=" + partOfOperations +
                '}';
    }
}
