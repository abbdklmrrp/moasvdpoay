package nc.nut.reports.excel;

import nc.nut.reports.ReportData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.List;

/**
 * Created by Yuliya Pedash on 16.04.2017.
 */
public class XLSReportDataWriter {
    private XSSFSheet excelSheet;
    private List<ReportData> reportData;

    public XLSReportDataWriter(XSSFSheet excelSheet, List<ReportData> reportData) {
        this.excelSheet = excelSheet;
        this.reportData = reportData;
    }


    public void writeReportData() {
        Row row;
        short rowIndex = 2;
//        short cellIndex = 0;
//        row = getRow(cellIndex);
//        row.createCell(0).setCellValue("Complaints & Orders data");
//        row = getRow(rowIndex++);
//        row.createCell(cellIndex).setCellValue("Time period");
//        row.createCell(++cellIndex).setCellValue("Complaints");
//        row.createCell(++cellIndex).setCellValue("Orders");
        writeTableBody(rowIndex);
        drawChart();
    }

    private <T> short writeTableBody(short rowIndex) {
        for (int i = 0; i < reportData.size(); i++) {
            Row row = getRow(rowIndex++);
            int cellIndex = 0;
            Cell cell = row.createCell(cellIndex++);
            cell.setCellValue(reportData.get(i).getTimePeriod());
            cell = row.createCell(cellIndex++);
            cell.setCellValue(reportData.get(i).getComplaintsCount());
            cell = row.createCell(cellIndex++);
            cell.setCellValue(reportData.get(i).getOrdersCount());
        }
        return rowIndex;
    }

    private void drawChart() {
        Drawing drawing = excelSheet.createDrawingPatriarch();
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, reportData.size() + 2, 10, reportData.size() + 32);
        Chart chart = drawing.createChart(anchor);
        ChartLegend legend = chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);
        LineChartData data = chart.getChartDataFactory().createLineChartData();
        ValueAxis bottomAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        ChartDataSource<String> xAxisData = DataSources.fromStringCellRange(excelSheet, new CellRangeAddress(2, reportData.size() - 1, 0, 0));
        ChartDataSource<Number> yAxisData1 = DataSources.fromNumericCellRange(excelSheet, new CellRangeAddress(2, reportData.size() - 1, 1, 1));
        ChartDataSource<Number> yAxisData2 = DataSources.fromNumericCellRange(excelSheet, new CellRangeAddress(2, reportData.size() - 1, 2, 2));
        data.addSeries(xAxisData, yAxisData1).setTitle("Complaints");
        data.addSeries(xAxisData, yAxisData2).setTitle("Reports");
        chart.plot(data, bottomAxis, leftAxis);
    }


    /**
     * Gets row from Excel sheet
     * if row has not existed yet, method creates it and returns it
     *
     * @param rowIndex index of the row to get
     * @return row
     */
    private Row getRow(short rowIndex) {
        if (excelSheet.getRow(rowIndex) == null) {
            return excelSheet.createRow(rowIndex);
        } else {
            return excelSheet.getRow(rowIndex);
        }
    }
}
