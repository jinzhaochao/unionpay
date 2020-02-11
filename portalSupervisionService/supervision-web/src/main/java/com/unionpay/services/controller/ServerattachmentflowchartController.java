package com.unionpay.services.controller;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.FileUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.FlowChart;
import com.unionpay.services.model.ServerAttachmentFlowChart;
import com.unionpay.services.service.ServerattachmentflowchartService;
import com.unionpay.services.service.ServerattachmentflowchartServiceImpl;
import com.unionpay.services.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author xiaopengcheng
 * 流程图附件
 */
@Api(tags = "流程图接口", description = "/serverattachmentflowchart")
@RestController
@RequestMapping("/serverattachmentflowchart")
public class ServerattachmentflowchartController {
	@Value("${servicecenter.upload-url}")
	private String savePath;
	@Value("${flow-chat.max-size}")
	private String maxFileSize;
	@Value("${flow-chat.type}")
	private String fileType;
	@Autowired
	private ServerattachmentflowchartService serverattachmentflowchartService;
	@Autowired
	private ServerattachmentflowchartServiceImpl serverattachmentflowchart_modelService;

	/**
	 * 新增流程图
	 * @param serverattachmentflowchart_model
	 * @return
	 */
	@ApiOperation(value = "add-新增流程图",notes = "新增流程图")
	@PostMapping("/add")
	@ResponseBody
	public Result add(ServerAttachmentFlowChart serverattachmentflowchart_model){
		try{
			ServerAttachmentFlowChart flowChart = serverattachmentflowchart_modelService.add(serverattachmentflowchart_model);
			return Result.success(flowChart);
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}

	/**
	 * 删除流程图
	 * @param json
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-11
	 */
	@ApiOperation(value = "delete-删除流程图",notes = "删除流程图")
	@PostMapping("/delete")
	@ResponseBody
	public Result delete(@RequestBody String json){
		Integer id = null;
		JSONObject ids = JSONObject.parseObject(json);
		if (ToolUtil.isNotEmpty(ids)){
			id = ids.getInteger("id");
		}
		try{
			serverattachmentflowchartService.delete(id);
			return Result.success();
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}

	/**
	 * 根据流程图id查看
	 * @param id
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-11
	 */
	@ApiOperation(value = "get-查看流程图",notes = "查看流程图")
	@PostMapping("/get")
	@ResponseBody
	public Result get(Integer id){
		try{
			ServerAttachmentFlowChart serverattachmentflowchart = serverattachmentflowchartService.get(id);
			if(ToolUtil.isEmpty(serverattachmentflowchart)){
				throw new RuntimeException();
			}
			return Result.success(serverattachmentflowchart);
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}

	/**
	 * 上传流程图
	 * @param request
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-11
	 */
	@ApiOperation(value = "upload-上传流程图",notes = "上传流程图")
	@PostMapping("/upload")
	@ResponseBody
	public Result uploadFlowChat(HttpServletRequest request){
		String path = getUpdataPath();
		// 创建上传文件目录
		File saveDirFile = new File(path);
		FileUtil.mkDirs(saveDirFile);
		// 获取上传文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multipartRequest.getFileNames();
		FlowChart file = null;
		String userId = SessionUtils.getUserId(request);
		Long maxFileSizes = parseSize(maxFileSize);
		while (iter.hasNext()) {
			MultipartFile multipartFile = multipartRequest.getFile(iter.next().toString());
			// 文件扩展名是否有误
			String fileName = multipartFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".")
					+ 1, fileName.length());
			if (!fileType.contains(suffix)) {
				System.out.println("[" + multipartFile + "]文件类型错误");
				return Result.failure("文件类型错误 :"+fileName);
			}
			// 判断文件大小是否超限
			if (multipartFile.getSize() > maxFileSizes) {
				System.out.println("[" + multipartFile + "]文件大小超出范围");
				return Result.failure("[" + multipartFile + "]文件大小超出范围");
			}
			file = serverattachmentflowchartService.uploadAndSaveFlowChart(multipartFile, path, userId);
			if (file == null) {
				return Result.failure("[" + multipartFile + "]文件上传失败");
			}
		}
		return Result.success(file);
	}

	/**
	 * 生成保存地址
	 * @return
	 *
	 * @author lishaung
	 * @data 2019-03-11
	 */
	private String getUpdataPath(){
		String path = null;
		path = savePath +  DateUtil.getDay(new Date()) + "/";
		return path;
	}

	/**
	 * 附件大小转换
	 * @param size
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-11
	 */
	private long parseSize(String size) {
		Assert.hasLength(size, "Size must not be empty");
		size = size.toUpperCase(Locale.ENGLISH);
		if (size.endsWith("KB")) {
			return Long.valueOf(size.substring(0, size.length() - 2)) * 1024;
		}
		if (size.endsWith("MB")) {
			return Long.valueOf(size.substring(0, size.length() - 2)) * 1024 * 1024;
		}
		return Long.valueOf(size);
	}

	@ApiOperation(value = "文件下载", notes = "文件下载接口")
	@RequestMapping(value = "/downLoadFlowChart", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Result downLoadFlowChart(@RequestParam(name = "id")Integer id){
		ServerAttachmentFlowChart downloadFlowChart =  serverattachmentflowchartService.downLoadFlowChar(id);
		if (ToolUtil.isEmpty(downloadFlowChart)){
			return Result.failure("文件不存在");
		}
		String fileName = downloadFlowChart.getName();
		HttpServletResponse response = getResponse();
		if (null != fileName){
			// 设置文件路径
			File file = new File(downloadFlowChart.getUrl());
			if (file.exists()){
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					response.setContentType("application/force-download");// 设置强制下载不打开
					response.setContentType("multipart/form-data;charset=UTF-8");// 也可以明确的设置一下UTF-8，测试中不设置也可以。
					response.setHeader("Content-Disposition", "attachment; fileName=" + downloadFlowChart.getName()
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
					return Result.success();
				} catch (Exception e) {
					e.printStackTrace();
					return Result.failure("下载失败：" + e);
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
		}
		return Result.failure("下载失败");
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

}