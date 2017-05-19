package jtelecom.reports;

/**
 * Created by Yuliya Pedash on 16.04.2017.
 */
public class ReportData {
    private String timePeriod;
    private int amount;

    public ReportData() {
    }

    public ReportData(String timePeriod, int amount) {
        this.timePeriod = timePeriod;
        this.amount = amount;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ReportData{" +
                "timePeriod='" + timePeriod + '\'' +
                ", amount=" + amount +
                '}';
    }
}
