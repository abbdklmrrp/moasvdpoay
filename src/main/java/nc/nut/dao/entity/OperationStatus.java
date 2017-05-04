package nc.nut.dao.entity;

import java.util.Objects;

/**
 * Created by Yuliya Pedash on 28.04.2017.
 */
public enum OperationStatus {
    Active(1, "Active"),
    Suspended(2, "Suspended"),
    Deactivated(3, "Deactivated"),
    InProcessing(4, "In Processing"),
    InTariff(5, "In Tariff");

    private Integer id;
    private String name;

    OperationStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method gets <code>OperationStatus</code> object by id
     *
     * @param id id operation status
     * @return OperationStatus object  or <code>null</code> if object by this id is not found
     */
    public static OperationStatus getOperationStatusById(Integer id) {
        OperationStatus[] operationStatuses = values();
        for (OperationStatus operationStatus : operationStatuses) {
            if (Objects.equals(operationStatus.getId(), id)) {
                return operationStatus;
            }
        }
        return null;
    }


}


