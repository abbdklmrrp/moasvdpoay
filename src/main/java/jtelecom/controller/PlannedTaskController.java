package jtelecom.controller;

import jtelecom.controller.user.UsersOrdersController;
import jtelecom.dao.plannedTask.PlannedTaskDao;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.PlannedTaskDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.PlannedTaskService;
import jtelecom.util.SharedVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yuliya Pedash on 20.05.2017.
 */
@Controller
@RequestMapping({"residential", "business", "csr"})
public class PlannedTaskController {
    @Resource
    PlannedTaskDao plannedTaskDao;
    @Resource
    private UserDAO userDAO;
    @Resource
    PlannedTaskService plannedTaskService;
    private static Logger logger = LoggerFactory.getLogger(UsersOrdersController.class);
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;

    @RequestMapping(value = {"getPlannedTasks"}, method = RequestMethod.GET)
    @ResponseBody
    public ListHolder showPlannedTasks(@ModelAttribute GridRequestDto request) {
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user {} ", user);
        List<PlannedTaskDTO> plannedTaskDTOS = plannedTaskDao.getLimitedPlannedTasksForUsersOrders(user.getId(), start, length);
        int size = plannedTaskDao.getCountPlannedTasksForUserOrders(user.getId());
        logger.debug("Got Planned Tasks {} ", plannedTaskDTOS);
        return ListHolder.create(plannedTaskDTOS, size);
    }

    @RequestMapping(value = {"cancelSuspense"}, method = RequestMethod.POST)
    @ResponseBody
    public String cancelSuspense(@RequestParam Integer plannedTaskId) {
        logger.debug("Request for cancelling suspense with id  {}", plannedTaskId);
        boolean wasSuspenseCancelled = plannedTaskService.canselSuspense(plannedTaskId);
        if (wasSuspenseCancelled) {
            logger.info("Suspense with id  {}  was cancelled", plannedTaskId);
            return SharedVariables.SUCCESS;
        }
        logger.error("Unable to cancel suspense with id {}", plannedTaskId);
        return SharedVariables.FAIL;
    }

}
