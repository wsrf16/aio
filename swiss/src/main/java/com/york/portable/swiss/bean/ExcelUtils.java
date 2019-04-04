//package com.york.portable.swiss.bean;
//
//import cn.afterturn.easypoi.excel.ExcelExportUtil;
//import cn.afterturn.easypoi.excel.entity.ExportParams;
//import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
//import org.apache.poi.ss.usermodel.Workbook;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.List;
//import java.util.Map;
//
//public class ExcelUtils {
//
//    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
//        ExportParams exportParams = new ExportParams(title, sheetName);
//        exportParams.setCreateHeadRows(isCreateHeader);
//        defaultExport(list, pojoClass, fileName, response, exportParams);
//    }
//
//    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
//        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
//    }
//
//    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
//        defaultExport(list, fileName, response);
//    }
//
//    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
//        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
//        if (workbook != null) ;
//        downLoadExcel(fileName, response, workbook);
//    }
//
//    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
//        try {
//            response.setCharacterEncoding("UTF-8");
//            response.setHeader("content-Type", "application/vnd.ms-excel");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//            workbook.write(response.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
//        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
//        if (workbook != null) ;
//        downLoadExcel(fileName, response, workbook);
//    }
//
//}
