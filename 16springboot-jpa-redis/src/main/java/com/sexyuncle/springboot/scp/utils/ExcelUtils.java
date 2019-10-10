package com.sexyuncle.springboot.scp.utils;

import static com.loserico.io.utils.IOUtils.GBK;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.loserico.commons.utils.DateUtils;
import com.loserico.io.utils.IOUtils;

public class ExcelUtils {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
	private final static String excel2003L = ".xls"; //2003- 版本的excel
	private final static String excel2003csv = ".csv"; //2003- 版本的excel
	private final static String excel2007U = ".xlsx"; //2007+ 版本的excel

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
	public static Workbook getWorkbook(MultipartFile file) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());
		String fileName = file.getOriginalFilename();
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (excel2003L.equals(fileType)) {
			wb = new HSSFWorkbook(bais); //2003-
		} else if (excel2007U.equals(fileType)) {
			wb = new XSSFWorkbook(bais); //2007+
		} else if (excel2003csv.equals(fileType)) {
			File xlsxFile = csvToXLS(bais);
			wb = new XSSFWorkbook(IOUtils.toByteArrayInputStream(xlsxFile)); //2003-
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
	}

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
			wb = new HSSFWorkbook(inStr); //2003-
		} else if (excel2007U.equals(fileType)) {
			wb = new XSSFWorkbook(inStr); //2007+
		} else if (excel2003csv.equals(fileType)) {
			wb = new HSSFWorkbook(inStr); //2003-
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
	}

	public static String stringVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				if (cell.getCellTypeEnum() == CellType.STRING) {
					String cellValue = cell.getStringCellValue();
					Matcher matcher = pattern.matcher(cellValue);
					if (matcher.matches()) {
						return matcher.group(1).trim();
					}
					if (isNotBlank(cellValue)) {
						return cellValue.trim();
					}
				} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					return new BigDecimal(cell.getNumericCellValue()).toPlainString();
				}
			} catch (Exception e) {
				logger.info("get string value failed with row[" + row.getRowNum() + "], cell[" + cellIndex + "]", e);
			}
		}
		return null;
	}

	public static LocalDateTime dateTimeVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				if (cell.getCellTypeEnum() == CellType.STRING) {
					String dateStr = stringVal(row, cellIndex);
					if (isBlank(dateStr)) {
						return null;
					}
					return DateUtils.toLocalDateTime(dateStr);
				}
				return DateUtils.toLocalDateTime(cell.getDateCellValue());
			} catch (Exception e) {
				logger.info("调用 cell.getDateCellValue() 失败，改用 cell.getStringCellValue()");
			}
		}
		return null;
	}

	public static LocalDate dateVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				if (cell.getCellTypeEnum() == CellType.STRING) {
					String dateStr = stringVal(row, cellIndex);
					if (isBlank(dateStr)) {
						return null;
					}
					return DateUtils.toLocalDate(dateStr);
				}
				return DateUtils.toLocalDate(cell.getDateCellValue());
			} catch (Exception e) {
				logger.info("调用 cell.getDateCellValue() 失败，改用 cell.getStringCellValue()");
			}
		}
		return null;
	}

	public static Double doubleVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					return cell.getNumericCellValue();
				}
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
				logger.info("调用 cell.getNumericCellValue()， 改用 cell.getStringCellValue()");
			}
		}
		return 0d;
	}

	public static Long longVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					return (long) cell.getNumericCellValue();
				}
				String value = cell.getStringCellValue();
				if (isBlank(value)) {
					return 0L;
				}
				return Long.parseLong(value);
			} catch (Exception e) {
				logger.info("调用cell.getNumericCellValue()失败，改用cell.getStringCellValue()");
			}
		}
		return 0L;
	}

	public static Integer intVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					return (int) cell.getNumericCellValue();
				}
				String value = cell.getStringCellValue();
				if (isBlank(value)) {
					return 0;
				}
				return Integer.valueOf(value);
			} catch (Exception e) {
				logger.info("调用cell.getNumericCellValue()失败，改用cell.getStringCellValue()");
			}
		}
		return 0;
	}

	public static BigDecimal bigDecimalVal(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		if (cell != null) {
			try {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					double value = cell.getNumericCellValue();
					return new BigDecimal(value);
				}
				String value = cell.getStringCellValue();
				if (isNotBlank(value)) {
					return new BigDecimal(value.trim());
				}
				return null;
			} catch (Exception e) {
				logger.error("调用 cell.getNumericCellValue() 失败，改用 cell.getStringCellValue()", e);
			}
		}
		return BigDecimal.ZERO;
	}

	public static File csvToXLS(InputStream inputStream) {
		try {
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("sheet1");
			String currentLine = null;
			int RowNum = 0;
			BufferedReader br = IOUtils.toBufferedReader(inputStream, GBK);
			while ((currentLine = br.readLine()) != null) {
				String str[] = currentLine.split(",");
				RowNum++;
				XSSFRow currentRow = sheet.createRow(RowNum);
				for (int i = 0; i < str.length; i++) {
					currentRow.createCell(i).setCellValue(str[i]);
				}
			}

			File tempFile = IOUtils.tempFile("xls");
			FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
			workBook.write(fileOutputStream);
			fileOutputStream.close();
			workBook.close();
			return tempFile;
		} catch (Exception ex) {
			logger.error("转换CSV-XLS 失败", ex);
		}
		return null;
	}
}