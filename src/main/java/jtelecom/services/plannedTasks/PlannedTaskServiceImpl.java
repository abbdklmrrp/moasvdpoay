package jtelecom.services.plannedTasks;

import jtelecom.dao.plannedTask.PlannedTask;
import jtelecom.dao.plannedTask.PlannedTaskDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Yuliya Pedash on 24.05.2017.
 */
@Service
public class PlannedTaskServiceImpl implements PlannedTaskService {
    private static Logger logger = LoggerFactory.getLogger(PlannedTaskService.class);

    @Resource
    private PlannedTaskDAO plannedTaskDAO;


    /**
     * {@inheritDoc}
     */
    @Transactional
    public boolean cancelSuspense(Integer suspensePlannedTaskId) {
        PlannedTask suspensePlannedTask = plannedTaskDAO.getById(suspensePlannedTaskId);
        boolean wasSuspendedPlannedTaskDeleted = plannedTaskDAO.deletePlannedTaskById(suspensePlannedTaskId);
        if (!wasSuspendedPlannedTaskDeleted) {
            logger.error("Unable to delete planned task with id {} from database", suspensePlannedTask);
            return false;
        }
        return plannedTaskDAO.deletePlannedTaskForActivationOfThisSuspense(suspensePlannedTask);
    }
}
