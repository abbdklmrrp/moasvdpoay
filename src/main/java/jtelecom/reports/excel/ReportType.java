package jtelecom.reports.excel;

import java.io.File;

/**
 * Created by Yuliya Pedash on 20.05.2017.
 */
public enum ReportType {
    OrdersStatistics(File.separator + "excel" + File.separator + "orders_template.xlsx"),
    ComplaintsStatistics(File.separator + "excel" + File.separator + "comp_template.xlsx");
    private final String templateFileAddress;

    ReportType(String templateFileAddress) {

        this.templateFileAddress = templateFileAddress;
    }

    public String getTemplateFileAddress() {
        return templateFileAddress;
    }
}
