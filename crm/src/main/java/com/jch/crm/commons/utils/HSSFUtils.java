package com.jch.crm.commons.utils;

import com.jch.crm.workbench.domain.Activity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *  excel文件操作的工具类
 */
public class HSSFUtils {

    /**
     * 导出市场活动
     * @param response
     * @param activityList
     */
    public static void fileDownloadActivityList(HttpServletResponse response, List<Activity> activityList) throws IOException {
        // 创建excel文件, 并且把activityList写入excel文件中
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("市场活动名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");

        // 遍历activityList, 创建HSSFRow对象, 生成所有的数据行
        Activity activity = null;
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);

                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }

        // 把excel文件下载到客户端
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=activityList.xls");
        ServletOutputStream os = response.getOutputStream();

        wb.write(os);
        wb.close();
        os.flush();
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
