package nc.nut.dao.entity;

/**
 * Created by Yuliya Pedash on 28.04.2017.
 */
public enum OperationStatus {
    Acitve("Active"),
    Suspended("Suspended"),
    Deactivated("Deactivated"),
    InProcessing("In processing");

    private String status;

    OperationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
