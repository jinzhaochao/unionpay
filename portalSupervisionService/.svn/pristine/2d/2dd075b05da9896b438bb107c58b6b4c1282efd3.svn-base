package com.unionpay.common.util;

import java.text.NumberFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public final class ExcelReadUtil {
	private ExcelReadUtil() {
		throw new AssertionError("不能实例化工具类");
	}

	public static String convertToString(Cell cell) {
		String value = "";
		if (cell != null) {
			if(cell.getCellTypeEnum() == CellType.NUMERIC){
				NumberFormat nf = NumberFormat.getInstance();
				nf.setGroupingUsed(false);
				nf.setParseIntegerOnly(true);
				value = nf.format(cell.getNumericCellValue());
			}else if(cell.getCellTypeEnum() == CellType.STRING){
				value = cell.getStringCellValue();
			}
		}
		return value;
	}

	public static Long convertToLong(Cell cell) {
		Long value = -1L;
		if (cell != null) {
			if(cell.getCellTypeEnum() == CellType.NUMERIC){
				value = Double.valueOf(cell.getNumericCellValue()).longValue();
			}else if(cell.getCellTypeEnum() == CellType.STRING){
				value = Long.valueOf(cell.getStringCellValue());
			}
		}
		return value;
	}

	public static Integer convertToInteger(Cell cell) {
		Integer value = -1;
		if (cell != null) {
			if(cell.getCellTypeEnum() == CellType.NUMERIC){
				value = Double.valueOf(cell.getNumericCellValue()).intValue();
			}else if(cell.getCellTypeEnum() == CellType.STRING){
				value = Integer.valueOf(cell.getStringCellValue());
			}
		}
		return value;
	}
}

