package com.sexyuncle.springboot.scp.utils;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loserico.commons.utils.StringUtils;

public class ExcelUtils {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2003csv = ".csv";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel

	//去除包含cell内容的""
	private static Pattern pattern = Pattern.compile("\"(.+)\"");
	private static Pattern moneyDoublePattern = Pattern.compile("\\$(.+)");
	
    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);  //2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr);  //2007+
        }else if(excel2003csv.equals(fileType)){
            wb = new HSSFWorkbook(inStr);  //2003-
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

	public static String stringVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				String cellValue = cell.getStringCellValue();
				Matcher matcher = pattern.matcher(cellValue);
				if (matcher.matches()) {
					return matcher.group(1).trim();
				}
				if (isNotBlank(cellValue)) {
					return cellValue.trim();
				}
			} catch (Exception e) {
				logger.error("get string value failed with row[" + row.getRowNum() + "], cell[" + cellIndex + "]", e);
			}
		}
		return null;
	}

	public static LocalDateTime dateVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				String dateStr = stringVal(row, cellIndex);
				if(isBlank(dateStr)) {
					return null;
				}
				return LocalDateTime.parse(dateStr, ofPattern("yyyy-MM-dd HH:mm:ss"));
			} catch (Exception e) {
				logger.error("get date value failed with row[" + row.getRowNum() + "], cell[" + cellIndex + "]", e);
			}
		}
		return null;
	}

	public static Double doubleVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				String value = cell.getStringCellValue();
				if (isBlank(value)) {
					return 0d;
				}
				Matcher matcher = moneyDoublePattern.matcher(value);
				if (matcher.matches()) {
					return Double.valueOf(matcher.group(1).replaceAll(",", ""));
				}
				return Double.valueOf(value.replaceAll(",", ""));
			} catch (Exception e) {
				logger.error("get double value failed with row[" + row.getRowNum() + "], cell[" + cellIndex + "]", e);
			}
		}
		return 0d;
	}
	
	public static Long longVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				String value = cell.getStringCellValue();
				if (isBlank(value)) {
					return 0L;
				}
				return Long.valueOf(value);
			} catch (Exception e) {
				logger.error("get double value failed with row[" + row.getRowNum() + "], cell[" + cellIndex + "]", e);
			}
		}
		return 0L;
	}
	
	public static Integer intVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				String value = cell.getStringCellValue();
				if (isBlank(value)) {
					return 0;
				}
				return Integer.valueOf(value);
			} catch (Exception e) {
				logger.error("get double value failed with row[" + row.getRowNum() + "], cell[" + cellIndex + "]", e);
			}
		}
		return 0;
	}
	
	public static BigDecimal bigDecimalVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				String value = cell.getStringCellValue();
				if (isBlank(value)) {
					return BigDecimal.ZERO;
				}
				return new BigDecimal(value);
			} catch (Exception e) {
				logger.error("get double value failed with row[" + row.getRowNum() + "], cell[" + cellIndex + "]", e);
			}
		}
		return BigDecimal.ZERO;
	}

}