package com.unionpay.supervision.web.major;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.FileUtil;
import com.unionpay.supervision.domain.SuperFile;
import com.unionpay.supervision.service.SuperFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "FileController", description = "文件操作Controller")
@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/file")
public class FileController {

	@Autowired
	private SuperFileService superFileService;

	@Value("${upload-path}")
	private String savePath;

	@Value("${spring.servlet.multipart.max-file-size}")
	private String maxFileSize;
	
	@Value("${file.mine.type}")
	private String fileType;

	/**
	 * 附近上传
	 */
	@ApiOperation(value = "文件上传", notes = "文件上传接口")
	@PostMapping("/add")
	@ResponseBody
	public RESTResultBean uploadFile(HttpServletRequest request) {
		RESTResultBean result = new RESTResultBean(200, "success");
		String path = getUpdataPath();
		// 创建上传文件目录
		File saveDirFile = new File(path);
		FileUtil.mkDirs(saveDirFile);
		// 获取上传文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multipartRequest.getFileNames();
		SuperFile file = null;
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
			file = superFileService.uploadFile(multipartFile, path, userId);
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
	 * excel模板下载，9911HY（会议议定事项.xlsx）；9912PS（公司领导批示.xlsx）；
	 * 9913CF（公司领导境外出访布置工作.xlsx）；1000AA（自定义项目.xlsx）；
	 */
	@ApiOperation(value = "文件下载", notes = "文件下载接口")
	@RequestMapping(value = "/downLoadFile", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public RESTResultBean downLoadFile(@RequestParam("fileId") String fileId) {
		RESTResultBean result = new RESTResultBean();
		SuperFile downloadfile = superFileService.selectByFileId(fileId);
		if (downloadfile == null) {
			result.setCode(500);
			result.setMessage("文件不存在");
			return result;
		}
		String fileName = downloadfile.getFileName();// 设置文件名，根据业务需要替换成要下载的文件名
		HttpServletResponse response = getResponse();
		if (fileName != null) {
			// 设置文件路径
			File file = new File(downloadfile.getUrl());
			if (file.exists()) {
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					response.setContentType("application/force-download");// 设置强制下载不打开
					response.setContentType("multipart/form-data;charset=UTF-8");// 也可以明确的设置一下UTF-8，测试中不设置也可以。
					response.setHeader("Content-Disposition", "attachment; fileName=" + downloadfile.getFileName()
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
		return null;
	}
	
	/**
	 * 文件删除
	 */
	@ApiOperation(value = "文件删除")
	@DeleteMapping(value = "/deleteFile")
    @ResponseBody
    public RESTResultBean deleteFilePath(@RequestParam("fileId") String fileId) {
       RESTResultBean result = new RESTResultBean(200, "success");
        SuperFile deleteFile = superFileService.selectByFileId(fileId);
		if (deleteFile == null) {
			result.setCode(500);
			result.setMessage("文件不存在");
			return result;
		}
		superFileService.deleteByFileId(fileId);

		return result;
    }

	/**
	 * 根据fileId查找文件
     * @param fileId
	 */
	@ApiOperation(value = "查找文件")
	@RequestMapping(value = "/findFile",method = RequestMethod.GET)
	@ResponseBody
	public RESTResultBean findFile(@RequestParam("fileId") String fileId){
		RESTResultBean result = new RESTResultBean(200, "success");
		SuperFile superFile = superFileService.selectByFileId(fileId);
		if (superFile == null){
			result.setCode(500);
			result.setMessage("文件不存在");
			return result;
		}
		result.setData(superFile);
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
