package com.unionpay.services.controller;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.FileUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.FlowChart;
import com.unionpay.services.model.HandMaterialPrquest;
import com.unionpay.services.model.HandlingMaterialRequest;
import com.unionpay.services.model.ServerAttachment;
import com.unionpay.services.service.ServerattachmentServiceImpl;
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
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author xiaopengcheng
 * 办理材料附件
 */
@RestController
@RequestMapping("/serverattachment")
@Api(value = "/serverattachment", tags = "材料材料附件", description = "材料材料附件")
public class ServerattachmentController {
	@Value("${servicecenter.upload-url}")
	private String savePath;
	@Value("${flow-chat.max-size}")
	private String maxFileSize;
	@Value("${flow-chat.type}")
	private String fileType;
	@Autowired
	private ServerattachmentServiceImpl serverattachment_modelService;


	@ApiOperation(value = "upload-办理材料附件",notes = "办理材料附件")
	@PostMapping("/upload")
	@ResponseBody
	public Result uploadAttachment(Integer materId,Integer type, HttpServletRequest request){
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
			file = serverattachment_modelService.uploadAndSaveAttachment(multipartFile, path, materId,type);
			if (file == null) {
				return Result.failure("[" + multipartFile + "]文件上传失败");
			}
		}
		return Result.success(file);
	}

	/**
	 * 办理材料附件文件下载
	 */
	@ApiOperation(value = "办理材料附件文件下载", notes = "办理材料附件文件下载接口")
	@RequestMapping(value = "/downLoadFile", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Result downLoadFile(@RequestParam(name = "id")Integer id) {
		//Integer fileId = handlingMaterialRequest.getId();
		ServerAttachment serverAttachment = serverattachment_modelService.get(id);
		if (serverAttachment == null) {
			return Result.build(500,"文件不存在","");
		}
		String fileName = serverAttachment.getName();// 设置文件名，根据业务需要替换成要下载的文件名
		HttpServletResponse response = getResponse();
		if (fileName != null) {
			// 设置文件路径
			File file = new File(serverAttachment.getUrl());
			if (file.exists()) {
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					response.setContentType("application/force-download");// 设置强制下载不打开
					response.setContentType("multipart/form-data;charset=UTF-8");// 也可以明确的设置一下UTF-8，测试中不设置也可以。
					response.setHeader("Content-Disposition", "attachment; fileName=" + serverAttachment.getName()
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
					System.out.println("success");
					return Result.build(200,"success","");
				} catch (Exception e) {
					e.printStackTrace();
					return Result.build(500,"下载失败" + e,"");
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
		return null;
	}

	/**
	 * 文件删除
	 */
	@ApiOperation(value = "文件删除")
	@DeleteMapping(value = "/deleteFile")
	@ResponseBody
	public Result deleteFilePath(@Valid @RequestBody HandlingMaterialRequest handlingMaterialRequest) {
		Integer fileId = handlingMaterialRequest.getId();
		ServerAttachment deleteFile = serverattachment_modelService.get(fileId);
		if (deleteFile == null) {
			return Result.build(500,"文件不存在","");
		}
		serverattachment_modelService.delete(fileId);
		return Result.build(200,"success","");
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



	@PostMapping("/add")
	public Result add(ServerAttachment serverattachment_model){
		try{
			return Result.success(serverattachment_modelService.add(serverattachment_model));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}
	@PostMapping("/delete")
	public Result delete(Integer id){
		try{
			serverattachment_modelService.delete(id);
			return Result.success();
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}
	@PostMapping("/get")
	public Result get(Integer id){
		try{
			ServerAttachment serverattachment_model = serverattachment_modelService.get(id);
			if(serverattachment_model==null){throw new RuntimeException();}
			return Result.success(serverattachment_model);
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}
}