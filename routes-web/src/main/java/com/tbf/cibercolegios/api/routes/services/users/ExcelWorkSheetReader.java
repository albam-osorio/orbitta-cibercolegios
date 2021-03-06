package com.tbf.cibercolegios.api.routes.services.users;

import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import lombok.val;

@Component
public class ExcelWorkSheetReader {
	public static final char NON_BREAKING_SPACE = 160;

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private static DataFormatter formatter = new DataFormatter();

	public List<List<String>> read(InputStream in, int index, int rowStart, int colStart, int rowEnd, int colEnd,
			int maxEmptyRows) throws IOException {

		// http://stackoverflow.com/questions/4929646/how-to-get-an-excel-blank-cell-value-in-apache-poi
		List<List<String>> result = null;
		Workbook wb = null;

		try {
			wb = WorkbookFactory.create(in);
			val sheet = wb.getSheetAt(index);
			result = getData(sheet, rowStart, colStart, rowEnd, colEnd, maxEmptyRows);
		} finally {
			if (result == null) {
				result = new ArrayList<>();
			}

			if (wb != null) {
				wb.close();
			}
		}

		return result;
	}

	private List<List<String>> getData(Sheet sheet, int rowStart, int colStart, int rowEnd, int colEnd,
			int maxEmptyRows) {
		List<List<String>> result = new ArrayList<>();

		int emptyRows = 0;
		for (int rowNum = rowStart; rowNum < rowEnd + 1; rowNum++) {
			Row row = sheet.getRow(rowNum);
			boolean empty = (row == null);
			if (!empty) {
				if (rowNum < rowEnd) {
					val data = getRowData(row, colStart, colEnd);
					empty = data.isEmpty();
					
					if (!empty) {
						result.add(data);
					}
				} else {
					val msg = String.format(
							"Se ha superado el número máximo de registros a cargar por archivo.\nEl número máximo a cargar por archivo es :%d.",
							rowEnd);

					throw new RuntimeException(msg);
				}
			} 
			
			if(empty){
				// This whole row is empty
				// Handle it as needed
				emptyRows++;
				if (emptyRows > maxEmptyRows) {
					break;
				}
			}
		}
		return result;
	}

	private List<String> getRowData(Row row, int colStart, int colEnd) {
		List<String> result = new ArrayList<>();
		boolean empty = true;

		for (int cellNum = colStart; cellNum < colEnd; cellNum++) {
			String text = "";
			Cell cell = row.getCell(cellNum);
			if (cell != null) {
				empty = false;
				text = getCellText(cell);
				result.add(text);
			} else {
				result.add("");
			}
		}

		if (empty) {
			result.clear();
		}

		return result;
	}

	private String getCellText(Cell cell) {
		String text = "";

		if (cell != null) {
			text = formatter.formatCellValue(cell);
			// Alternatively, get the value and format it yourself
			switch (cell.getCellType()) {
			case STRING:
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
					text = ldt.format(dateTimeFormatter);
					text = StringUtils.remove(text, " 00:00:00");
				}
				break;
			case BOOLEAN:
				break;
			case FORMULA:
				break;
			case BLANK:
				break;
			default:
			}
		}

		text = StringUtils.replaceChars(text, "\t\n", "  ").trim();
		return text;
	}

	public boolean checkColumns(List<List<String>> data, String[] headers, List<String> errors) {
		boolean result = true;

		if (data.size() < 2) {
			throw new RuntimeException("El archivo no contiene datos");
		}

		Collator collator = Collator.getInstance();
		collator.setStrength(Collator.NO_DECOMPOSITION);

		val row = data.get(0);
		val missing = new ArrayList<String>();
		val messy = new ArrayList<String>();
		int index = 0;
		for (val header : headers) {
			boolean found = false;
			for (int i = 0; i < row.size(); i++) {
				val value = row.get(i).toUpperCase();
				if (collator.compare(header, value) == 0) {
					found = true;
					if (index != i) {
						messy.add(value);
					}
				}
			}
			if (!found) {
				missing.add(header);
				break;
			}
			index++;
		}

		if (!missing.isEmpty()) {
			val m = String.join(",", missing);
			val msg = String.format("Las siguientes columnas no se encontraron en el archivo: %s.", m);
			errors.add(msg);
		} else {
			if (!messy.isEmpty()) {
				val m = String.join(",", messy);
				val msg = String.format("Las siguientes columnas no se encontraron en el orden esperado: %s.", m);
				errors.add(msg);
			}
		}

		if (errors.isEmpty()) {
			row.clear();
			for (String header : headers) {
				row.add(header);
			}
		} else {
			result = false;
		}

		return result;
	}
}