package com.unionpay.voice.controller;


import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.FileUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.voice.domain.VoiceFile;
import com.unionpay.voice.model.FileModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.unionpay.voice.service.VoiceFileService;
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
 * 客户之声附件信息接口
 * 
 * @author lishuang
 * @date 2019-05-08 10:16:29
 */
@Api(tags = "客户之声附件信息",description = "客户之声附件信息")
@Controller
@RequestMapping("/voiceFile")
public class VoiceFileController {
	@Autowired
	private VoiceFileService voiceFileService;

	@Value("${http.voice.upload.path}")
	private String savePath;

	@Value("${spring.servlet.multipart.max-file-size}")
	private String maxFileSize;

	@Value("${file.type}")
	private String fileType;

	/**
	 * 附件上传
	 */
	@ApiOperation(value = "附件上传", notes = "附件上传接口")
	@PostMapping("/uploadFile")
	@ResponseBody
	public RESTResultBean uploadFile(HttpServletRequest request) {
		RESTResultBean result = new RESTResultBean(200, "上传成功");
		String path = getUpdataPath();
		// 创建上传文件目录
		File saveDirFile = new File(path);
		FileUtil.mkDirs(saveDirFile);
		// 获取上传文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multipartRequest.getFileNames();
		FileModel file = null;
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
				return new RESTResultBean(500, "文件类型错误 :" + fileName);
			}

			// 判断文件大小是否超限
			if (multipartFile.getSize() > maxFileSizes) {
				System.out.println("[" + multipartFile + "]文件大小超出范围");
				return new RESTResultBean(500, "[" + multipartFile + "]文件大小超出范围");
			}
			file = voiceFileService.uploadFile(multipartFile,path,userId,null,null);
			if (file == null) {
				result.setCode(500);
				result.setMessage("[" + multipartFile + "]文件上传失败");
				return result;
			}
		}
		result.setData(file);
		return result;
	}

	/**
	 * 附件下载
	 * @param unid
	 * @return
	 */
	@ApiOperation(value = "附件下载", notes = "附件下载接口")
	@RequestMapping(value = "/downLoadFile",method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public RESTResultBean downLoadFile(@RequestParam String unid){
		RESTResultBean result = new RESTResultBean(200, "success");
		VoiceFile voiceFile = voiceFileService.get(unid);
		if (ToolUtil.isEmpty(voiceFile)){
			result.setCode(500);
			result.setMessage("文件不存在");
			return result;
		}
		String fileName = voiceFile.getFileStoreName();
		HttpServletResponse response = getResponse();
		if (fileName != null) {
			// 设置文件路径
			File file = new File(voiceFile.getFilePath());
			if (file.exists()) {
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					response.setContentType("application/force-download");// 设置强制下载不打开
					response.setContentType("multipart/form-data;charset=UTF-8");// 也可以明确的设置一下UTF-8，测试中不设置也可以。
					response.setHeader("Content-Disposition", "attachment; fileName=" + voiceFile.getFileName()
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
		}
		return result;
	}

	/**
	 * 附件删除
	 * @param unid
	 * @return
	 */
	@ApiOperation(value = "附件删除", notes = "附件删除接口")
	@ResponseBody
	@PostMapping("/deleteFile")
	public RESTResultBean deleteFile(@RequestParam String unid){
		RESTResultBean result = new RESTResultBean(200, "删除成功");
		VoiceFile voiceFile = voiceFileService.get(unid);
		if (ToolUtil.isEmpty(voiceFile)){
			result.setCode(500);
			result.setMessage("文件不存在");
			return result;
		}
		boolean bool = voiceFileService.delete(voiceFile);
		if (!bool){
			result.setCode(500);
			result.setMessage("删除失败");
			return result;
		}
		return result;
	}

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

	private String getUpdataPath(){
		String path = null;
		path = savePath +  DateUtil.getDay(new Date()) + "/";
		return path;
	}

}
