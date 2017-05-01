package nc.nut.reports;

/**
 * Created by Yuliya Pedash on 16.04.2017.
 */
public class ReportData {
    private String timePeriod;
    private int complaintsCount;
    private int ordersCount;


    public ReportData(String timePeriod, int complaintsCount, int ordersCount) {
        this.timePeriod = timePeriod;
        this.complaintsCount = complaintsCount;
        this.ordersCount = ordersCount;
    }

    public ReportData() {
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public int getComplaintsCount() {
        return complaintsCount;
    }

    public void setComplaintsCount(int complaintsCount) {
        this.complaintsCount = complaintsCount;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    @Override
    public String toString() {
        return "ReportData{" +
                "timePeriod='" + timePeriod + '\'' +
                ", complaintsCount=" + complaintsCount +
                ", ordersCount=" + ordersCount +
                '}';
    }
}
