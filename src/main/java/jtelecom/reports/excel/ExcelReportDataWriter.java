package jtelecom.reports.excel;

import jtelecom.reports.ReportData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.List;

/**
 * ExcelReportDataWriter class consists exclusively of static methods that operate
 * on writing report data and drawing graphics on excel sheet.
 * {@link #writeTableBody(short, Sheet, List)} method writes body of table for report on excel sheet.
 * {@link #writeReportData(XSSFSheet, List)} method draws graphic on excel sheet.
 * Created by Yuliya Pedash on 16.04.2017.
 */
public class ExcelReportDataWriter {
    /**
     * This method defines beginning of the report
     * {@link #writeTableBody(short, Sheet, List)}
     *
     * @param excelSheet sheet on which report will be written
     * @param reportData data which will be used for creating report
     */
    public static void writeReportData(XSSFSheet excelSheet, List<ReportData> reportData) {
        short rowIndex = 3;
        writeTableBody(rowIndex, excelSheet, reportData);
        //       drawChart(excelSheet, reportData.size());
    }

    /**
     * This method writes body of table with three colomns(time period, complains count, orders count).
     *
     * @param rowIndex   index of table where the first row locates
     * @param excelSheet sheet to write data to
     * @param reportData data for report
     */
    private static void writeTableBody(short rowIndex, Sheet excelSheet, List<ReportData> reportData) {
        for (ReportData reportDataObject : reportData) {
            Row row = getRow(rowIndex++, excelSheet);
            int cellIndex = 0;
            Cell cell = row.createCell(cellIndex++);
            cell.setCellValue(reportDataObject.getTimePeriod());
            cell = row.createCell(cellIndex++);
            cell.setCellValue(reportDataObject.getAmount());
        }
    }

//    /**
//     * This method draws graph. The values for graph are taken
//     * from table columns that were written with {@link #writeTableBody(short, Sheet, List)}
//     *
//     * @param excelSheet     sheet to draw graph to
//     * @param reportDataSize size of report data
//     */
//    private static void drawChart(Sheet excelSheet, int reportDataSize) {
//        Drawing drawing = excelSheet.createDrawingPatriarch();
//        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, reportDataSize + 2, 10, reportDataSize + 32);
//        Chart chart = drawing.createChart(anchor);
//        ChartLegend legend = chart.getOrCreateLegend();
//        legend.setPosition(LegendPosition.TOP_RIGHT);
//        ScatterChartData data = chart.getChartDataFactory().createScatterChartData();
//        ValueAxis bottomAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.BOTTOM);
//        ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
//        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
//        ChartDataSource<String> xAxisData = DataSources.fromStringCellRange(excelSheet, new CellRangeAddress(2, reportDataSize + 1, 0, 0));
//        ChartDataSource<Number> yAxisData = DataSources.fromNumericCellRange(excelSheet, new CellRangeAddress(2, reportDataSize + 1, 1, 1));
////        ChartDataSource<Number> yAxisData2 = DataSources.fromNumericCellRange(excelSheet, new CellRangeAddress(2, reportDataSize + 1, 2, 2));
////        data.addSerie(xAxisData, yAxisData2).setTitle("Reports");
//        data.addSerie(xAxisData, yAxisData).setTitle("Data");
//        chart.plot(data, bottomAxis, leftAxis);
//    }


    /**
     * Gets row from Excel sheet
     * if row has not existed yet, method creates it and returns it
     *
     * @param rowIndex index of the row to get
     * @return row
     */
    private static Row getRow(short rowIndex, Sheet excelSheet) {
        return (excelSheet.getRow(rowIndex) == null) ?
                excelSheet.createRow(rowIndex) :
                excelSheet.getRow(rowIndex);
    }
}
