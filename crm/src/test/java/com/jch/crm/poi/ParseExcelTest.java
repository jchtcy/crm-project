package com.jch.crm.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 使用apache-poi 解析excel文件
 */
public class ParseExcelTest {
    public static void main(String[] args) throws IOException {
        // 根据指定的excel生成HSSFWorkbook对象, 封装了excel文件的所有信息
        FileInputStream fis = new FileInputStream("D:\\work\\serve\\studentList.xls");
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        // 获取页
        HSSFSheet sheet = wb.getSheetAt(0);
        // 获取行
        HSSFRow row = null;
        HSSFCell cell = null;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);

                System.out.print(getCellValueForStr(cell) + " ");
            }
            System.out.println();
        }

    }

    /**
     * 从指定的HSSFCell对象中获取列的值
     * @return
     */
    public static String getCellValueForStr(HSSFCell cell) {
        String res = "";
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            res = cell.getStringCellValue();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            res = cell.getNumericCellValue() + "";
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
            res = cell.getBooleanCellValue() + "";
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
            res = cell.getCellFormula() + "";
        } else {
            res = "";
        }
        return res;
    }
}
