package com.unionpay.common.easyexcel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.unionpay.common.exception.MyException;

public class ExcelUtil {
	
	/**
     * 读取 Excel(多个 sheet)
     *
     * @param excel  文件
     * @param object 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
	 * @throws MyException 
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel object) throws MyException {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return null;
        }
        for (Sheet sheet : reader.getSheets()) {
            if (object != null) {
                sheet.setClazz(object.getClass());
            }
            reader.read(sheet);
        }
        return excelListener.getData();
    }
    
    /**
     * 返回 ExcelReader
     *
     * @param excel         需要解析的 Excel 文件
     * @param excelListener new ExcelListener()
     * @throws MyException 
     */
    private static ExcelReader getReader(MultipartFile excel,ExcelListener excelListener) throws MyException {
        String filename = excel.getOriginalFilename();
        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
            throw new MyException("文件格式错误！");
        }
//        ExcelTypeEnum excelTypeEnum = ExcelTypeEnum.XLSX;
//        if (filename.toLowerCase().endsWith(".xls")) {
//            excelTypeEnum = ExcelTypeEnum.XLS;
//        }
        InputStream inputStream;
        try {
            inputStream = excel.getInputStream();
            return new ExcelReader(inputStream, null, excelListener);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
