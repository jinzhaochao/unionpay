package com.unionpay.services.business;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.util.HttpClientUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.SendNoticeModelDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 发送通知
 * @author lishuang
 * @date 2019/11/08
 */
@Component
public class SendNotice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${http.notice.send}")
    private String sendNoticeUrl;

    public boolean sendNotice(SendNoticeModelDto sendNoticeModelDto){
        boolean result = false;
        JSONObject params = new JSONObject();
        params.put("title",sendNoticeModelDto.getTitle());
        params.put("url",sendNoticeModelDto.getUrl());
        params.put("account",sendNoticeModelDto.getAccount());
        params.put("password",sendNoticeModelDto.getPassword());
        params.put("typeId",sendNoticeModelDto.getTypeId());
        params.put("receivers",sendNoticeModelDto.getReceivers());
        String str = HttpClientUtils.doPostJson(sendNoticeUrl, params.toString());
        if (ToolUtil.isNotEmpty(str)){
            JSONObject json = JSONObject.parseObject(str);
            if (json.getInteger("code")==200 && json.getString("message").contains("成功")){
                result = true;
                logger.info("发送通知成功。推送消息中心：结果" + result + " 返回值：" + str + " params = " + params);
            }else {
                logger.info("推送消息中心：结果" + result + " 返回值：" + str + " params = " + params);
            }
        }else {
            logger.info("推送消息中心：结果" + result + " 返回值：" + str + " params = " + params);
        }
        return result;
    }
}
