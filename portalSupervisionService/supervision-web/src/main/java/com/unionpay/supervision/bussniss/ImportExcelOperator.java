package com.unionpay.supervision.bussniss;

import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.util.ExcelReadUtil;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.domain.SuperTypeOversee;
import com.unionpay.supervision.service.ExcelService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * excel导入处理
 * </p>
 *
 * @author xiongym
 * @since 2019-04-40
 */

@Component
public class ImportExcelOperator {

	@Autowired
	private ExcelService excelService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("resource")
	@Transactional
	public void addSuperServiceByExcel(SuperTypeOversee superTypeOversee, MultipartFile file,OmUser omUser,Integer type) throws IOException {
		Workbook workbook = null;
		InputStream inputStream = file.getInputStream();
		String filename = file.getOriginalFilename();
		if (filename != null && (filename.lastIndexOf(".xlsx") == (filename.length() - 5))) {
			workbook = new XSSFWorkbook(inputStream);
		} else {
			workbook = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		//批量导入，督办类型模板判断
		if(rowCount >= 1){
			Row row1 = sheet.getRow(1);	
			String st1 = ExcelReadUtil.convertToString(row1.getCell(0));
			String st2 = ExcelReadUtil.convertToString(row1.getCell(1));
			String st3 = ExcelReadUtil.convertToString(row1.getCell(2));
			if(type.equals(9911)){
				//会议议定事项
				if(!st1.equals("会议名称")|| !st2.equals("任务要求") || !st3.equals("备注")){
					throw new BussinessException(500,"会议议定事项导入模板错误");
				}
				for (int i = 3; i <= rowCount; i++) {
					try {
						Row row = sheet.getRow(i);
						if (row.getLastCellNum() > 1) {
							excelService.importServiceAndSponsorForMeeting(row, omUser, superTypeOversee);
							
						}
					} catch (Exception e) {
						logger.debug("第 " +(i+1) + "行督办事项批量导入异常：" + e);
						String error = e.getMessage();
						if(!error.contains("不")){
							error = "";
						}
						throw new BussinessException(500,"第" + (i+1) + "行异常:" +error);
					}
				}
			}else if(type.equals(9912)){
				//公司领导批示
				if(!st1.equals("批文名称")|| !st2.equals("批文来源") || !st3.equals("批示领导")){
					throw new BussinessException(500,"公司领导批示导入模板错误");
				}
				for (int i = 3; i <= rowCount; i++) {
					try {
						Row row = sheet.getRow(i);
						if (row.getLastCellNum() > 1) {
							excelService.importServiceAndSponsorForWritten(row, omUser, superTypeOversee);
							
						}
					} catch (Exception e) {
						logger.debug("第 " + (i+1) + "行异常：" + e);
						String error = e.getMessage();
						if(!error.contains("不")){
							error = "";
						}
						throw new BussinessException(500,"第 " + (i+1) + "行异常:" +error);
					}
				}
			}else if(type.equals(9913)){
				//公司领导境外出访布置工作
				if(!st1.equals("出访活动名称")|| !st2.equals("出访领导") || !st3.equals("任务要求")){
					throw new BussinessException(500,"公司领导境外出访布置工作模板错误");
				}
				for (int i = 3; i <= rowCount; i++) {
					try {
						Row row = sheet.getRow(i);
						if (row.getLastCellNum() > 1) {
							excelService.importServiceAndSponsorForAssign(row, omUser, superTypeOversee);
							
						}
					} catch (Exception e) {
						logger.debug("第 " + (i+1) + "行督办事项批量导入异常：" + e);
						String error = e.getMessage();
						if(!error.contains("不")){
							error = "";
						}
						throw new BussinessException(500,"第 " + (i+1) + "行督办事项导入异常:" +error);
					}
				}
			}else{
				//自定义
				if(!st1.equals("事项名称")|| !st2.equals("任务要求") || !st3.equals("备注")){
					throw new BussinessException(500,"自定义导入模板错误");
				}
				
				for (int i = 3; i <= rowCount; i++) {
					try {
						Row row = sheet.getRow(i);
						if (row.getLastCellNum() > 1) {
							excelService.importServiceAndSponsorForDefined(row, omUser, superTypeOversee);
							
						}
					} catch (Exception e) {
						logger.debug("第 " + (i+1) + "行督办事项批量导入异常：" + e);
						String error = e.getMessage();
						if(!error.contains("不")){
							error = "";
						}
						throw new BussinessException(500,"第 " + (i+1) + "行异常:" +error);
					}
				}
			}
		}
		
		inputStream.close();
		
		
	}

}
