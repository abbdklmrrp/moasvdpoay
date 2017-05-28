package jtelecom.services.plannedTasks;

/**
 * @author Yuliya Pedash
 */
public interface PlannedTaskService {

    /**
     * This method deletes planned task, which goal was to activate this order after
     * Suspense and also sets this order status to 'Active'
     * @param suspensePlannedTaskId planned task for order suspense
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise
     * @see jtelecom.dao.entity.OperationStatus
     */
    boolean cancelSuspense(Integer suspensePlannedTaskId);
}
