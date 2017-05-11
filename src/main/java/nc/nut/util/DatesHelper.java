package nc.nut.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * Created by Yuliya Pedash on 08.05.2017.
 */
public class DatesHelper {
    private static Logger logger = LoggerFactory.getLogger(DatesHelper.class);

    /**
     * This method checks if dates for superdense of order are correct.
     * End date should be after end date of Superdense.
     * Begin date of superdense should not be before current date.
     * @param beginDate begin date of superdense
     * @param endDate end date of superdense
     * @return
     */
    public static boolean areDatesCorrectForOrderSuspendence(Calendar beginDate, Calendar endDate){
        Calendar currentDate = getCurrentDate();
        if (endDate.before(beginDate)){
            logger.error("Begin date is after start date");
            return false;
        }
        if (currentDate.after(beginDate)){
            logger.error("Begin date is after current date");
            return false;
        }
        return true;
    }

    /**
     * This method returns Calendar for current date.
     * Values for <code>HOUR_OF_DAY</code>, <code>AM_PM</code>,
     * <code>MINUTE</code>, <code>SECOND</code>  and <code>MILLISECOND</code> are omitted
     * and don't play role in final result.
     * @return current date
     */
    public static Calendar getCurrentDate(){
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        return currentDate;
    }
}
