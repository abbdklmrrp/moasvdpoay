package jtelecom.services;

import jtelecom.dao.plannedTask.PlannedTask;
import jtelecom.dao.plannedTask.PlannedTaskDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Yuliya Pedash on 21.05.2017.
 */
@Service
public class PlannedTaskService {
    private static Logger logger = LoggerFactory.getLogger(PlannedTaskService.class);

    @Resource
    PlannedTaskDao plannedTaskDao;

    /**
     * @param suspensePlannedTaskId
     * @return
     */
    @Transactional
    public boolean canselSuspense(Integer suspensePlannedTaskId) {
        PlannedTask suspensePlannedTask = plannedTaskDao.getById(suspensePlannedTaskId);
        boolean wasSuspendedPlannedTaskDeleted = plannedTaskDao.deletePlannedTaskById(suspensePlannedTaskId);
        if (!wasSuspendedPlannedTaskDeleted) {
            logger.error("Unable to delete planned task with id {} from database", suspensePlannedTask);
            return false;
        }
        return plannedTaskDao.deletePlannedTaskForActivationOfThisSuspense(suspensePlannedTask);
    }
}
