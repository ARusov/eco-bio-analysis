package org.vsu.eco.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.vsu.eco.dao.TaxonDao;
import org.vsu.eco.model.Taxon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by arusov on 10/18/14.
 */

@Component
public class TaxonsXLSToDB {

    @Autowired
    private TaxonDao taxonDao;

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        TaxonsXLSToDB taxonsXLSToDB = (TaxonsXLSToDB) applicationContext.getBean("taxonsXLSToDB");
//        taxonsXLSToDB.imporExcel();
    }

    private void imporExcel() throws IOException {
        InputStream fis = TaxonsXLSToDB.class.getResourceAsStream("/taxons.xlsx");
        Workbook wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            Taxon taxon = new Taxon();
            Row row = (Row) rows.next();
            System.out.println("row#=" + row.getRowNum() + "");
            Cell cell = row.getCell(0);
            taxon.setName(getCellValue(cell));

            cell = row.getCell(1);
            taxon.setValues(getCellValue(cell));

            cell = row.getCell(2);
            taxon.setValuex(getCellValue(cell));


            cell = row.getCell(3);
            taxon.setValueo(getCellValue(cell));

            cell = row.getCell(4);
            taxon.setValueb(getCellValue(cell));

            cell = row.getCell(5);
            taxon.setValuea(getCellValue(cell));

            cell = row.getCell(6);
            taxon.setValuep(getCellValue(cell));

            cell = row.getCell(7);
            taxon.setValueG(getCellValue(cell));

            cell = row.getCell(8);
            taxon.setValue_S(getCellValue(cell));

            cell = row.getCell(9);
            taxon.setComment(getCellValue(cell));

            System.out.print(taxon.getName() + "  " + taxon.getValues() + "  " + taxon.getValuex() + "  " + taxon.getValueo() + "  " + taxon.getValueb()
                    + "  " + taxon.getValuea() + "  " + taxon.getValuep() + "  " + taxon.getValueG() + "  " + taxon.getValue_S()
                    + "  " + taxon.getComment());
            System.out.println("");
            System.out.println("_____________________");

            taxonDao.createTaxon(taxon);
        }
    }

    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return String.valueOf(cell.getDateCellValue());
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            default: {
                return "";
            }
        }

    }
}
