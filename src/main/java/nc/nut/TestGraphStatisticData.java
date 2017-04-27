package nc.nut;

/**
 * @author Revniuk Aleksandr
 */
public class TestGraphStatisticData {
    private int day;
    private int month;
    private int year;
    private int countOfOrders;
    private int countOfComplaints;

    public TestGraphStatisticData() {
    }

    public TestGraphStatisticData(int day, int month, int year, int countOfOrders, int countOfComplaints) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.countOfOrders = countOfOrders;
        this.countOfComplaints = countOfComplaints;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCountOfOrders() {
        return countOfOrders;
    }

    public void setCountOfOrders(int countOfOrders) {
        this.countOfOrders = countOfOrders;
    }

    public int getCountOfComplaints() {
        return countOfComplaints;
    }

    public void setCountOfComplaints(int countOfComplaints) {
        this.countOfComplaints = countOfComplaints;
    }

    @Override
    public String toString() {
        return "TestGraphStatisticData{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", countOfOrders=" + countOfOrders +
                ", countOfComplaints=" + countOfComplaints +
                '}';
    }
}
