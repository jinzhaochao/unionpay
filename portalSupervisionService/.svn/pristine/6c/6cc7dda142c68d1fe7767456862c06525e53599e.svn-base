package com.unionpay.voice.extra;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.log.LogManager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.FileUtil;
import com.unionpay.common.util.MD5Util;
import com.unionpay.supervision.bussniss.FeedBackOperator;
import com.unionpay.supervision.domain.SuperClient;
import com.unionpay.supervision.log.LogTaskFactory;
import com.unionpay.supervision.service.SuperClientService;
import com.unionpay.voice.domain.VoiceInfo;
import com.unionpay.voice.domain.VoiceLog;
import com.unionpay.voice.model.FileModel;
import com.unionpay.voice.model.LcVoiceInfoModel;
import com.unionpay.voice.service.VoiceFileService;
import com.unionpay.voice.service.VoiceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.unionpay.supervision.supportController.BaseController.verify;

/**
 * 客户之声-第三方调用接口信息
 *
 * @author lishuang
 * @date 2019-05-08 10:16:30
 */
@Api(tags = "第三方调用接口",description = "第三方调用接口")
@Controller
@RequestMapping("/voiceOperator")
public class VoiceOperator {
    @Autowired
    private VoiceFileService voiceFileService;
    @Autowired
    private VoiceInfoService voiceInfoService;
    @Autowired
    private SuperClientService superClientService;
    @Value("${http.voice.upload.path}")
    private String savePath;
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;
    @Value("${file.type}")
    private String fileType;

    @ApiOperation(value = "第三方新增接口")
    @ResponseBody
    @PostMapping("/addVoiceInfo")
    @Transactional(rollbackFor = Exception.class)
    public RESTResultBean addVoiceInfo(@RequestParam("appKey") String appKey, @RequestParam("requestTime") String requestTime,
            @RequestParam("sign") String sign, HttpServletRequest request, @Valid @RequestBody LcVoiceInfoModel params,
            BindingResult bindingResult){
        RESTResultBean result = new RESTResultBean(200 ,"success");
        System.out.println("推送的参数params--"+params);
        //基本信息处理
        VoiceInfo voiceInfo = new VoiceInfo();
        try {
            //对传入的参数params中的字段进行校验，并对错误的字段进行提示
            verify(bindingResult, request, params);
            // 时间戳验证
            if (!FeedBackOperator.isTrueTime(requestTime)) {
                result.setCode(500);
                result.setMessage("requestTime is inValid");
                return result;
            }
            //appKey验证
            SuperClient client = superClientService.findByAppkey(appKey);
            if (client == null) {
                result.setCode(500);
                result.setMessage("appKey is error");
                return result;
            }
            // 签名验证
            String appSecret = client.getAppSecret();
            if (!sign.equals(getSign(appKey, appSecret, requestTime))) {
                result.setCode(500);
                result.setMessage("sign is error");
                return result;
            }
            voiceInfo = voiceInfoService.addVoiceInfo(params);
            Map map = new HashMap();
            map.put("unid",voiceInfo.getUnid());
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //日志记录
            VoiceLog log = new VoiceLog();
            log.setVoiceInfoUnid(voiceInfo.getUnid());
            log.setClientType("others");
            log.setInterfaceType("新增信息");
            log.setChgData(JSONObject.toJSONString(params));
            if (200 == result.getCode()){
                log.setResultStatus("success");
            }else {
                log.setResultStatus("false");
            }
            log.setResultData(result.toString());
            log.setCreateTime(DateUtil.getStrTime(new Date()));
            log.setStatus(1);
            try {
                LogManager.me().executeLog(LogTaskFactory.voiceLog(log));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 附件上传接口
     * @return
     */
    @ApiOperation(value = "附件上传接口")
    @ResponseBody
    @PostMapping("/uploadFile")
    @Transactional
    public RESTResultBean uploadFile(@RequestParam("appKey") String appKey, @RequestParam("requestTime") String requestTime,
            @RequestParam("sign") String sign, HttpServletRequest request,@RequestParam("punid") String punid,
            @RequestParam("fileType") Integer type,@RequestParam("fileName") String fileName,MultipartFile file){
        RESTResultBean result = new RESTResultBean(200 ,"success");
        // 时间戳验证
        if (!FeedBackOperator.isTrueTime(requestTime)) {
            result.setCode(500);
            result.setMessage("requestTime is inValid");
            return result;
        }
        //appKey验证
        SuperClient client = superClientService.findByAppkey(appKey);
        if (null == client) {
            result.setCode(500);
            result.setMessage("appKey is error");
            return result;
        }
        // 签名验证
        String appSecret = client.getAppSecret();
        if (!sign.equals(getSign(appKey, appSecret, requestTime))) {
            result.setCode(500);
            result.setMessage("sign is error");
            return result;
        }
        String path = getUpdataPath();
        // 创建上传文件目录
        File saveDirFile = new File(path);
        FileUtil.mkDirs(saveDirFile);
        // 获取上传文件
        FileModel fileModel = null;
        String userId = SessionUtils.getUserId(request);
        Long maxFileSizes = parseSize(maxFileSize);
        // 文件扩展名是否有误
        //String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")
                + 1, fileName.length());
        try {
            if (!fileType.contains(suffix)) {
                System.out.println("[" + file + "]文件类型错误");
                result.setCode(500);
                result.setMessage("\"文件类型错误 :\" "+  fileName);
                return result;
            }
            // 判断文件大小是否超限
            if (file.getSize() > maxFileSizes) {
                System.out.println("[" + file + "]文件大小超出范围");
                result.setCode(500);
                result.setMessage("文件大小超出范围 :" + fileName);
                return result;
            }
            fileModel = voiceFileService.uploadFile(file,path,userId,punid,type);
            if (fileModel == null) {
                result.setCode(500);
                result.setMessage("文件上传失败 :"+fileName);
                return result;
            }
            result.setData(fileModel);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            //日志记录
            VoiceLog log = new VoiceLog();
            log.setClientType("others");
            log.setInterfaceType("上传附件");
            if (200 == result.getCode()){
                log.setResultStatus("success");
            }else {
                log.setResultStatus("false");
            }
            if (null != punid){
                log.setVoiceInfoUnid(punid);
            }
            log.setResultData(result.toString());
            log.setCreateTime(DateUtil.getStrTime(new Date()));
            log.setStatus(1);
            try {
                LogManager.me().executeLog(LogTaskFactory.voiceLog(log));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    private String getUpdataPath(){
        String path = null;
        path = savePath +  DateUtil.getDay(new Date()) + "/";
        return path;
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

    /*
     * 签名生成
     */
    private static String getSign(String appkey,String appSecret,String requestTime ){
        String sign = null;
        sign = MD5Util.encrypt(appkey + appSecret + requestTime);
        return sign;
    }

    @GetMapping("/sign")
    @ResponseBody
    public Map sign(){
        Long requestTime = System.currentTimeMillis();
        String sign = getSign("liucheng","password@123",requestTime.toString());
        Map map = new HashMap();
        map.put("sign",sign);
        map.put("requestTime",requestTime);
        map.put("appKey","liucheng");
        return map;
    }

}
