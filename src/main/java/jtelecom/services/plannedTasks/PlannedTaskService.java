package jtelecom.services.plannedTasks;

/**
 * Created by Yuliya Pedash on 21.05.2017.
 */
public interface PlannedTaskService {

    /**
     *
     * @param suspensePlannedTaskId
     * @return
     */
    boolean cancelSuspense(Integer suspensePlannedTaskId);
}
