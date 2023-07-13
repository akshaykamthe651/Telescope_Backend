package com.cybage.wfhreporting.util;

import com.cybage.wfhreporting.model.excel.EmployeeDetail;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Map;

@Component
public class ExcelUtil {

    public ByteArrayOutputStream exportToExcel(Map<String, EmployeeDetail> data, LocalDate attendanceDate, LocalDate startDate, LocalDate endDate) throws Exception {

        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create Attendance Sheet
        createAttendenceSheet(workbook, data, attendanceDate);

        // Create Details Sheet
        createDetailsSheet(workbook, data, startDate, endDate);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try
        {
            //Write the workbook in byte stream
            workbook.write(outputStream);
            outputStream.close();
            System.out.println("Cybage_WFHDetails.xlsx saved successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return outputStream;
    }

    private void createDetailsSheet(XSSFWorkbook workbook, Map<String, EmployeeDetail> data, LocalDate startDate, LocalDate endDate) {
        XSSFSheet sheet = workbook.createSheet("Details Sheet");
        CellStyle headerRowCellStyle = getHeaderRowCellStyle(workbook);
        CellStyle headerRowDateCellStyle = getHeaderRowDateCellStyle(workbook);
        CellStyle headerColCellStyle = getHeaderColCellStyle(workbook);
        CellStyle valueCellStyle = getValueCellStyle(workbook);

        int rownum = 0;
        Row row = sheet.createRow(rownum);

        int cellnum = 0;

        Cell cell = row.createCell(cellnum++);
        createStringCell(cell, headerRowCellStyle, "S. No");
        cell = row.createCell(cellnum++);
        createStringCell(cell, headerRowCellStyle, "Project");
        cell = row.createCell(cellnum++);
        createStringCell(cell, headerRowCellStyle,"Emp Code");
        cell = row.createCell(cellnum++);
        createStringCell(cell, headerRowCellStyle, "Employee Name");
        cell = row.createCell(cellnum++);
        createStringCell(cell, headerRowCellStyle, "Project Manager Name");
        cell = row.createCell(cellnum++);
        createStringCell(cell, headerRowCellStyle, "Work Status Planned");
        cell = row.createCell(cellnum++);
        createStringCell(cell, headerRowCellStyle, "Current Location");
        for(LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            if(!(date.getDayOfWeek().getValue() == 6) && !(date.getDayOfWeek().getValue() == 7)) {
                cell = row.createCell(cellnum++);
                createDateCell(cell, headerRowDateCellStyle, date.getYear(), date.getMonth().toString(), date.getDayOfMonth());
            }
        }

        rownum = 1;
        for(EmployeeDetail employeeDetailEntry : data.values()) {
            row = sheet.createRow(rownum++);
            cellnum = 0;

            cell = row.createCell(cellnum++);
            createIntegerCell(cell, headerColCellStyle,rownum - 1);

            cell = row.createCell(cellnum++);
            createStringCell(cell, headerColCellStyle, employeeDetailEntry.getProjectName());

            cell = row.createCell(cellnum++);
            createIntegerCell(cell, headerColCellStyle, Integer.valueOf(employeeDetailEntry.getEmployeeId()));

            cell = row.createCell(cellnum++);
            createStringCell(cell, headerColCellStyle, employeeDetailEntry.getEmployeeName());

            cell = row.createCell(cellnum++);
            createStringCell(cell, headerColCellStyle, employeeDetailEntry.getManagerName());

            cell = row.createCell(cellnum++);
            createStringCell(cell, headerColCellStyle, employeeDetailEntry.getStatusPlanned());

            cell = row.createCell(cellnum++);
            createStringCell(cell, headerColCellStyle, employeeDetailEntry.getLocation());

            for(LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                if(!(date.getDayOfWeek().getValue() == 6) && !(date.getDayOfWeek().getValue() == 7)) {
                    String status = "";
                    String taskDesc = taskDesc = employeeDetailEntry.getTaskDetails().get(date).getTaskDesc();
                    if(employeeDetailEntry.getTaskDetails().get(date).getStatus().equals("WORKING")) {
                        status = "Working";
                    } else if(employeeDetailEntry.getTaskDetails().get(date).getStatus().equals("WFO")) {
                        status = "Working (Office)";
                    } else if(employeeDetailEntry.getTaskDetails().get(date).getStatus().equals("WFH")) {
                        status = "Working (Home)";
                    } else if(employeeDetailEntry.getTaskDetails().get(date).getStatus().equals("PWFH")) {
                        status = "Working (PWFH)";
                    } else if(employeeDetailEntry.getTaskDetails().get(date).getStatus().equals("HDL")) {
                        status = "Half Day Leave";
                    } else if(employeeDetailEntry.getTaskDetails().get(date).getStatus().equals("HDL+WFO")) {
                        status = "Working (Office) + Half Day Leave";
                    } else if(employeeDetailEntry.getTaskDetails().get(date).getStatus().equals("HDL+WFH")) {
                        status = "Working (Home) + Half Day Leave";
                    } else if(employeeDetailEntry.getTaskDetails().get(date).getStatus().equals("HDL+PWFH")) {
                        status = "Working (PWFH) + Half Day Leave";
                    } else if(employeeDetailEntry.getTaskDetails().get(date).getStatus().equals("FDL")) {
                        status = "Full Day Leave";
                        taskDesc = "";
                    }

                    cell = row.createCell(cellnum++);
                    createStringCell(cell, valueCellStyle,"Status : " + status + System.lineSeparator() + System.lineSeparator() + "Task Description : " + System.lineSeparator() + taskDesc);
                }
            }
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);

        int i = 7;
        for(LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            if(!(date.getDayOfWeek().getValue() == 6) && !(date.getDayOfWeek().getValue() == 7)) {
                sheet.setColumnWidth(i, 10000);
                i++;
            }
        }

        sheet.createFreezePane(7,1);

    }

    private void createAttendenceSheet(XSSFWorkbook workbook, Map<String, EmployeeDetail> data, LocalDate attendanceDate) throws Exception {
        String day = attendanceDate.getDayOfMonth() < 10 ? "0" + attendanceDate.getDayOfMonth() : "" + attendanceDate.getDayOfMonth();
        String month = attendanceDate.getMonth().toString();

        XSSFSheet sheet = workbook.createSheet("AttendanceWFH-" + day + "-" + month);
        CellStyle valueCellstyle = getAttendanceValueCellStyle(workbook);
        CellStyle headerRowCellStyle = getAttendanceHeaderRowCellStyle(workbook);

        int rownum = 0;
        Row row = sheet.createRow(rownum);
        int cellnum = 0;

        Cell cell = row.createCell(cellnum++);
        createStringCell(cell, headerRowCellStyle, "Employee Code");
        cell = row.createCell(cellnum++);
        createStringCell(cell, headerRowCellStyle, "WFH Approved");
        cell = row.createCell(cellnum);
        createStringCell(cell, headerRowCellStyle,"%CYB WFH Work");

        rownum = 1;
        for(EmployeeDetail employeeDetailEntry : data.values()) {
            row = sheet.createRow(rownum++);
            cellnum = 0;

            cell = row.createCell(cellnum++);
            createIntegerCell(cell, valueCellstyle, Integer.valueOf(employeeDetailEntry.getEmployeeId()));

            String status;
            int percent;
            if(employeeDetailEntry.getTaskDetails().get(attendanceDate).getStatus().equals("WORKING") ||
                    employeeDetailEntry.getTaskDetails().get(attendanceDate).getStatus().equals("WFO") ||
                    employeeDetailEntry.getTaskDetails().get(attendanceDate).getStatus().equals("WFH") ||
                    employeeDetailEntry.getTaskDetails().get(attendanceDate).getStatus().equals("PWFH")) {
                status = "Yes";
                percent = 100;
            } else if (employeeDetailEntry.getTaskDetails().get(attendanceDate).getStatus().equals("HDL") ||
                    employeeDetailEntry.getTaskDetails().get(attendanceDate).getStatus().equals("HDL+WFO") ||
                    employeeDetailEntry.getTaskDetails().get(attendanceDate).getStatus().equals("HDL+WFH") ||
                    employeeDetailEntry.getTaskDetails().get(attendanceDate).getStatus().equals("HDL+PWFH")) {
                status = "Yes";
                percent = 50;
            } else if (employeeDetailEntry.getTaskDetails().get(attendanceDate).getStatus().equals("FDL")) {
                status = "No";
                percent = 0;
            } else {
                throw new Exception("IncompleteTaskSDetails");
            }
            cell = row.createCell(cellnum++);
            createStringCell(cell, valueCellstyle, status);

            cell = row.createCell(cellnum);
            createIntegerCell(cell, valueCellstyle, percent);
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.createFreezePane(0,1);
    }

    private void createStringCell(Cell cell, CellStyle cellStyle, String value) {
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    private void createIntegerCell(Cell cell, CellStyle cellStyle, Integer value) {
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    private void createDateCell(Cell cell, CellStyle cellStyle, int year, String month, int day) {
        cell.setCellValue(day + "-" + month + "-" + year);
        cell.setCellStyle(cellStyle);
    }

    private CellStyle getHeaderRowDateCellStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return  style;
    }

    private CellStyle getHeaderRowCellStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return  style;
    }

    private CellStyle getAttendanceHeaderRowCellStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return  style;
    }

    private CellStyle getHeaderColCellStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return  style;
    }

    private CellStyle getValueCellStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return  style;
    }

    private CellStyle getAttendanceValueCellStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return  style;
    }

}
