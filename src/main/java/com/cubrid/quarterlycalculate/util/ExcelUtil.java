package com.cubrid.quarterlycalculate.util;

import com.cubrid.quarterlycalculate.controller.excel.WorkTimeInfoDto;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    public static List<WorkTimeInfoDto> loadExcelData(MultipartFile file, int startRowNum, int columnLength) {

        List<WorkTimeInfoDto> excelList = new ArrayList<WorkTimeInfoDto>();

        try {
            OPCPackage opcPackage = OPCPackage.open(file.getInputStream());

            @SuppressWarnings("resource")
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);

            // 첫번째 시트
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rowIndex = 0;
            int columnIndex = 0;

            // 첫번째 행(0)은 컬럼 명이기 때문에 두번째 행(1) 부터 검색
            for (rowIndex = startRowNum; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);

                WorkTimeInfoDto loadDto = new WorkTimeInfoDto();

                loadDto.setDate(LocalDate.parse(getCellValue(row.getCell(0)), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                loadDto.setDayOfTheWeek(getCellValue(row.getCell(1)));
                loadDto.setName(getCellValue(row.getCell(2)));
                loadDto.setBeginWork(getCellValue(row.getCell(7)));
                loadDto.setEndWork(getCellValue(row.getCell(8)));
                loadDto.setTotalWork(getCellValue(row.getCell(14)));
                loadDto.setNightWork(getCellValue(row.getCell(16)));
                loadDto.setHolidayWork(getCellValue(row.getCell(17)));
                loadDto.setLeave(getCellValue(row.getCell(23)));
                loadDto.setHoliday(getCellValue(row.getCell(26)));
                excelList.add(loadDto);
            }

        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelList;
    }

    public static String getCellValue(XSSFCell cell) {

        String value = "";

        if (cell == null) {
            return value;
        }

        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                value = (int) cell.getNumericCellValue() + "";
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }


}
