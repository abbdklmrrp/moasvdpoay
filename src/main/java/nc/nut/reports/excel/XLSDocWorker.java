package nc.nut.reports.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;

/**
 * Created by Yuliya Pedash on 16.04.2017.
 */
public class XLSDocWorker {
    private XSSFWorkbook excelWorkbook;
    private String fileName;

    public XSSFWorkbook getExcelWorkbook() {
        return excelWorkbook;
    }


    public XLSDocWorker(String fileName) {
        final String templateFileAdress = File.separator + "excel" + File.separator + "template.xlsx";
        try {
            excelWorkbook = new XSSFWorkbook(this.getClass().getClassLoader().getResourceAsStream(templateFileAdress));

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileName = fileName;

    }

}
