package com.unionpay.sms.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.unionpay.common.easyexcel.EasyExcelFactory;
import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.ExcelReadUtil;
import com.unionpay.sms.model.SmsReceiverToDoModel;
import com.unionpay.sms.service.ExcelServiceSms;
import com.unionpay.sms.service.SmsReceiverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Excel的controller
 * 
 * @author wangyue
 * @date 2018-12-12
 */
@Api(value = "ExcelController",description = "Excel模版下载与导入")
@Controller
@RequestMapping("smsExcel")
public class SmsExcelController {
	@Autowired
	private ExcelServiceSms excelServiceSms;
	@Autowired
	private SmsReceiverService smsReceiverService;
	@Value("${http.sms.download}")
	private String downLoadUrl;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 接收人Excel导入
	 * 
	 * @param file
	 * @return
	 *
	 * 已测  OK
	 */
	@ApiOperation(value = "申请发送—>导入模版")
	@PostMapping("/addSmsByExcel")
	@ResponseBody
	@Transactional
	public RESTResultBean addSuperServiceByExcel(@RequestParam(name = "file") MultipartFile file,
												 @RequestParam(name = "unid")String unid) throws IOException{
		System.out.println(file);
		RESTResultBean result = new RESTResultBean(200, "操作成功");
		Workbook workbook = null;
		InputStream inputStream = null;
		inputStream = file.getInputStream();
		String filename = file.getOriginalFilename();
		if (filename != null && (filename.lastIndexOf(".xlsx") == (filename.length() - 5))) {
			workbook = new XSSFWorkbook(inputStream);
		} else {
			workbook = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		if (rowCount>=1){
			Row row1 = sheet.getRow(1);
			String st1 = ExcelReadUtil.convertToString(row1.getCell(0));
			String st2 = ExcelReadUtil.convertToString(row1.getCell(1));
			String st3 = ExcelReadUtil.convertToString(row1.getCell(2));
			if(!st1.equals("接收人")|| !st2.equals("接收人手机") || !st3.equals("部门/单位")){
				throw new BussinessException(500,"外部接收人导入模板错误");
			}
			String receiverName = "";
			for (int i = 3; i <= rowCount; i++) {
				try {
					Row row = sheet.getRow(i);
					if (row.getLastCellNum() > 1) {
						receiverName += excelServiceSms.importServiceAndSms(row,unid);
						if (receiverName.length()>1){
							receiverName = receiverName.substring(0,receiverName.lastIndexOf(","));
							throw new BussinessException(500,"用户'"+receiverName+"'手机号码不能重复");
						}
					}
				} catch (Exception e) {
					logger.debug("第 " + (i+1) + "行接收人信息导入失败：" + e);
					String error = e.getMessage();
					if(!error.contains("不")){
						error = "";
					}
					throw new BussinessException(500,"第" + (i+1) + "行异常:" +error);
				}
			}
		}
		inputStream.close();
		return result;
	}

	/**
	 * 短信外部接收人模版下载
	 * @return
	 * @author lishuang
	 * @date 2019-04-26
	 */
	@ApiOperation(value = "文件下载", notes = "文件下载接口")
	@RequestMapping(value = "/downLoadFile", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public RESTResultBean downLoadFile() {
		RESTResultBean result = new RESTResultBean();
		//String fileName = "smsTemplet.xlsx";// 设置文件名，根据业务需要替换成要下载的文件名
		String fileName = new String(("外部接收人" + new SimpleDateFormat("yy-MM-dd HH_mm_ss").format(new Date())))+".xlsx";
		HttpServletResponse response = getResponse();
		// 设置文件路径
		File file = new File(downLoadUrl+"smsTemplet.xlsx");
		if (file.exists()) {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.setContentType("multipart/form-data;charset=UTF-8");// 也可以明确的设置一下UTF-8，测试中不设置也可以。
				response.setHeader("Content-Disposition", "attachment; fileName=" + fileName
						+ ";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8"));
				byte[] buffer = new byte[1024];
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
				result.setCode(200);
				result.setMessage("success");
			} catch (Exception e) {
				e.printStackTrace();
				result.setCode(500);
				result.setMessage("下载失败：" + e);
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取 HttpServletRequest
	 *
	 * @return the response
	 */
	public static HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		return response;
	}

	/**
	 * 下载模版
	 * 
	 * @author wangyue
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "申请发送—>下载模版")
	@RequestMapping(value = "/exportSmsToDoByExcel", method = RequestMethod.GET)
	public void exportSuperServiceReadyToDoByExcel(HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
		try {
			String fileName = new String(("外部接收人" + new SimpleDateFormat("yy-MM-dd HH_mm_ss").format(new Date())));
			com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 0,
					SmsReceiverToDoModel.class);
			sheet.setSheetName("外部接收人");
			List<SmsReceiverToDoModel> list = smsReceiverService.SuperServiceReadyToDo(1, 3);
			writer.write(list, sheet);
			response.setContentType("multipart/form-data");
			response.setCharacterEncoding("utf-8");
			fileName = getFileName(request, fileName);
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.finish();
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @author wangyue
	 * @param request
	 * @param fileName
	 * @return
	 * @date 2018-12-12
	 */
	public String getFileName(HttpServletRequest request, String fileName) {
		try {
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes(), "iso-8859-1");// firefox浏览器
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
}
