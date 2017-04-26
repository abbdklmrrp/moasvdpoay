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

    public TestGraphStatisticData(int day, int month, int year, int countOfOrders, int countOfComplaints) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.countOfOrders = countOfOrders;
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
