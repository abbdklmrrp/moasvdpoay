package nc.nut.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;

/**
 * Created by Yuliya Pedash on 09.05.2017.
 */
public class SuspendFormDTO {
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    Calendar beginDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    Calendar endDate;
    Integer orderId;

    public Calendar getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Calendar beginDate) {
        this.beginDate = beginDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
