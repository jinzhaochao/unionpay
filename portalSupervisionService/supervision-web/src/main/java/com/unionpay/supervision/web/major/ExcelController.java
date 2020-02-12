package com.unionpay.supervision.web.major;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.unionpay.common.easyexcel.EasyExcelFactory;
import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.supervision.bussniss.ImportExcelOperator;
import com.unionpay.supervision.bussniss.LiuchengOperator;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.domain.SuperTypeOversee;
import com.unionpay.supervision.filter.submit.NoRepeatSubmit;
import com.unionpay.supervision.model.*;
import com.unionpay.supervision.service.*;
import com.unionpay.supervision.supportController.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Excel导入导出Controller
 * </p>
 *
 * @author xiongym
 * @since 2018-11-13
 */
@Api(value = "ExcelController")
@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/excel")
public class ExcelController extends BaseController {

	@Autowired
	private OmUserService omUserService;
	@Autowired
	private SponsorServiceDetailService superObjectService;
	@Autowired
	private SuperTypeOverseeService superTypeOverseeService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private SponsorSearchService sponsorSearchService;
	@Autowired
	private LiuchengOperator liuchengOperator;
	@Autowired
	private ImportExcelOperator importExcelOperator;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 督办事项批量导入
	 * 
	 * @author xiongym 
	 * 2018-12-20  type 改为督办类型id
	 * @throws IOException 
	 */
	@ApiOperation(value = "督办事项批量导入", notes = "新建录入接口")
	@PostMapping("/addSuperServiceByExcel")
	@ResponseBody
	@NoRepeatSubmit
	public RESTResultBean addSuperServiceByExcel(MultipartFile file, HttpServletRequest request, Integer type) throws IOException {
		RESTResultBean result = new RESTResultBean(200, "success");
		if (type == null || type < 1) {
			result.setCode(500);
			result.setMessage("督办类型模板需要指定");
			return result;
		}
		// 查询用户信息
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (omUser == null) {
			result.setCode(100);
			result.setMessage("未登录");
			return result;
		}
		SuperTypeOversee superTypeOversee = new SuperTypeOversee();
		superTypeOversee.setUnid(type.toString());
		superTypeOversee = superTypeOverseeService.getFindById(superTypeOversee);
		if(superTypeOversee == null){
			result.setCode(500);
			result.setMessage("type is error");
			return result;
		}
		importExcelOperator.addSuperServiceByExcel(superTypeOversee, file, omUser, type);
		return result;
	}

	/**
	 * 我的待办已办Excel导出
	 * 
	 * @author xiongym
	 */
	@ApiOperation(value = "我的待办已办Excel导出")
	@RequestMapping(value = "/exportSuperServiceReadyToDoByExcel", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportSuperServiceReadyToDoByExcel(SuperCondition superCondition, HttpServletRequest request,
			HttpServletResponse response,Integer top) {
		ServletOutputStream out = null;
		// 查询用户信息
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (omUser == null) {
			throw new BussinessException(100,"未登录");
		}

		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
		try {
			String fileName = new String(("我的待办已办" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 0,
					SponsorServiceReadyToDoModel.class);
			sheet.setSheetName("我的待办已办");
			superCondition.setUserId(omUser.getUserid());
			if(top != null && (top == 1 || top == 5)) {
				//待确认事项;待回复
				// 流程平台获取数据
				List<SponsorServiceReadyToDoModel> list = liuchengOperator.geQueryTodoTaskList( userId, superCondition, top);
				writer.write(list, sheet);
				response.setContentType("multipart/form-data");
				response.setCharacterEncoding("utf-8");
				fileName = getFileName(request, fileName);
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
				out.flush();
			} else {
				List<SponsorServiceReadyToDoModel> list = superObjectService.getSuperServiceReadyToDo(1, 100000, superCondition,top);
				writer.write(list, sheet);
				response.setContentType("multipart/form-data");
				response.setCharacterEncoding("utf-8");
				fileName = getFileName(request, fileName);
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
				out.flush();
			}

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
	 * 事项综合查询Excel导出
	 * 
	 * @author xiongym
	 */
	@ApiOperation(value = "事项综合查询Excel导出")
	@RequestMapping(value = "/exportSuperServiceItemSynthesisByExcel", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportSuperServiceItemSynthesisByExcel(SuperCondition condition, HttpServletRequest request,
			HttpServletResponse response,Integer top,Integer type) {
		ServletOutputStream out = null;
		// 查询用户信息
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (omUser == null) {
			throw new BussinessException(100,"未登录");
		}

		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
		try {
			String fileName = new String(("事项综合" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			condition.setUserId(omUser.getUserid());
			condition.setOrgId(omUser.getDeptorgid());// 部门id
			condition.setOfficeOrgId(omUser.getOrgid());// 科室id

				List<ServiceModel> list = superObjectService.getSuperServic(1, 100000, condition, top, type);
				Sheet sheet = new Sheet(1, 0,ServiceModel.class);
				writer.write(list, sheet);
			sheet.setSheetName("事项综合查询");
//				List<SponsorServiceItemModel> list = superObjectService.getSuperServiceItemSynthesis(1, 100000, condition, top, type);
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
	 * 统计报表导出 (事项类型统计)
	 * 
	 * @author xiongym
	 */
	@ApiOperation(value = "统计报表Excel导出")
	@RequestMapping(value = "/exportSupervisionStatisticsByExcel", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportSupervisionStatisticsByExcel(StatisticalDto statisticalDto, HttpServletRequest request,
			HttpServletResponse response,Integer top,Integer type) {
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
		try {
			String fileName = new String(("统计报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 0,
					StatisticsModel.class);
			sheet.setSheetName("事项类别统计");
			List<StatisticsModel> list = null;
			if(StatisticalDto.isEmpty(statisticalDto)){
				list = statisticsService.getServiceTypeStatistics();
			}else{
				list = statisticsService.getServiceTypeStatistics(statisticalDto);
			}
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
	 * 统计报表导出 (主办单位统计)
	 *
	 * @author jinzhao
	 */
	@ApiOperation(value = "统计报表Excel导出")
	@RequestMapping(value = "/exportOrgNameStatisticsByExcel", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportOrgNameStatisticsByExcel(StatisticalDto statisticalDto, HttpServletRequest request, HttpServletResponse response,Integer top,Integer type) {
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
		try {
			String fileName = new String(("统计报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 0, StatisticsOrgNameModel.class);
			sheet.setSheetName("主办单位统计");
			List<StatisticsOrgNameModel> list = null;
			if(StatisticalDto.isEmpty(statisticalDto)){
				list = statisticsService.getOrgNameStatistics();
			}else{
				list = statisticsService.getOrgNameStatistics(statisticalDto);
			}
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
	 * 统计报表导出 (任务来源统计)
	 *
	 * @author jinzhao
	 */
	@ApiOperation(value = "统计报表Excel导出")
	@RequestMapping(value = "/exportOverseeNameStatisticsByExcel", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportOverseeNameStatisticsByExcel(StatisticalDto statisticalDto, HttpServletRequest request,HttpServletResponse response,Integer top,Integer type) {
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
		try {
			String fileName = new String(("统计报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 0,
					StatisticsOverseeNameModel.class);
			sheet.setSheetName("任务来源统计");
			List<StatisticsOverseeNameModel> list = null;
			if(StatisticalDto.isEmpty(statisticalDto)){
				list = statisticsService.getOverseeNameStatistics();
			}else{
				list = statisticsService.getOverseeNameStatistics(statisticalDto);
			}
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
	 * 事项搜索查询导出
	 * 
	 * @author xiongym
	 */
	@ApiOperation(value = "事项搜索查询Excel导出")
	@RequestMapping(value = "/exportSupervisionByExcel", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportSupervisionByExcel(SponsorSearchDto sponsorSearch, String unids, HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
		try {
			String fileName = new String(("督办事项" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			Sheet sheet = new Sheet(1, 0,
					ServiceModel.class);
			sheet.setSheetName("督办事项");
			List<ServiceModel> list =sponsorSearchService.findAndExportSuperVisionlist(1, 100000, sponsorSearch,unids);
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
	 * 我的督办事项Excel导出
	 * 
	 * @author xiongym
	 */
	@ApiOperation(value = "我的督办事项Excel导出")
	@RequestMapping(value = "/exportMySuperService", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportSuperServiceItemSynthesisByExcel(SuperCondition condition, HttpServletRequest request,
			HttpServletResponse response,Integer top) {
		ServletOutputStream out = null;
		// 查询用户信息
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (omUser == null) {
			throw new BussinessException(100,"未登录");
		}

		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
		try {
			String fileName = new String(("我的督办事项" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 0,
					SponsorServiceItemModel.class);
			sheet.setSheetName("我的督办事项");
			condition.setUserId(omUser.getUserid());
			List<SponsorServiceItemModel> list = superObjectService.getMySuperService(1,100000,condition,top);
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
