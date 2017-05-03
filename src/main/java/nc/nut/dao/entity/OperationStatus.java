package nc.nut.dao.entity;

/**
 * Created by Yuliya Pedash on 28.04.2017.
 */
public enum OperationStatus {
    Active("Active"),
    Suspended("Suspended"),
    Deactivated("Deactivated"),
    InProcessing("In processing"),
    InTariff("In tariff");

    private String status;

    OperationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Integer getIdByStatus(OperationStatus operationStatus) {
        switch (operationStatus) {
            case Acitve:
                return 1;
            case Suspended:
                return 2;
            case InProcessing:
                return 3;
            default:
                return null;
        }
    }

    public static OperationStatus getOperationStatusByID(Integer id) {
        switch (id) {
            case 1:
                return Acitve;

            case 2:
                return Suspended;

            case 3:
                return Deactivated;

            case 4:
                return InProcessing;

        }
        return null;
    }
}


