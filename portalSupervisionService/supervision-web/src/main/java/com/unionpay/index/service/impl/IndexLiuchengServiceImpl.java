package com.unionpay.index.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.HttpClientUtils;
import com.unionpay.index.service.IndexLiuchengService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IndexLiuchengServiceImpl implements IndexLiuchengService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static Map<String,String> cachaMap=new HashMap<String,String>();
    static{
        //获取未签批和已签批的接口已经可以请求到了  这里就不再进行操作了

        //
        cachaMap.put("http://uatworkflownew.oa.unionpay.com/default/oawf/httptrs/officedoc!getDeptSponsorAverageTime.action","{\"isSuccess\": \"true\",\"jsonData\": [{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"409\",\"orgname\": \"基本建设管理办公室\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"413\",\"orgname\": \"客户服务中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"419\",\"orgname\": \"集中采购管理办公室\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"269\",\"orgname\": \"北京代表处\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"371\",\"orgname\": \"北京信息中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1200\",\"orgname\": \"联银创投\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1199\",\"orgname\": \"其他\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"347\",\"orgname\": \"电子支付研究院\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"362\",\"orgname\": \"信息总中心\",\"efficiency\": \"84939.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"402\",\"orgname\": \"财务部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1295\",\"orgname\": \"总监或顾问\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1294\",\"orgname\": \"移动支付部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"377\",\"orgname\": \"人力资源部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"388\",\"orgname\": \"监察审计部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"394\",\"orgname\": \"风险管理部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"277\",\"orgname\": \"市场拓展部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"284\",\"orgname\": \"银联国际\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"250\",\"orgname\": \"产品与创新部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1134\",\"orgname\": \"互联网部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"227\",\"orgname\": \"业务部\",\"efficiency\": \"1195.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"235\",\"orgname\": \"技术部\",\"efficiency\": \"90950.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1375\",\"orgname\": \"公司发展及重大项目部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1494\",\"orgname\": \"技术开发中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1654\",\"orgname\": \"风险监控服务中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1675\",\"orgname\": \"市场部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1764\",\"orgname\": \"业务运营中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"207\",\"orgname\": \"公司领导\",\"efficiency\": \"732.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"208\",\"orgname\": \"办公室\",\"efficiency\": \"89810.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"216\",\"orgname\": \"战略发展部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2435\",\"orgname\": \"000888斯蒂芬\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2196\",\"orgname\": \"测试部门9990io我放\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2255\",\"orgname\": \"测试部门17\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2475\",\"orgname\": \"测试部门222\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2218\",\"orgname\": \"是士大夫士大夫\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2335\",\"orgname\": \"888000\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2219\",\"orgname\": \"测试部门01\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2103\",\"orgname\": \"风险部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2059\",\"orgname\": \"法律合规部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2054\",\"orgname\": \"群工部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2535\",\"orgname\": \"测试名称\",\"efficiency\": \"0.0\"}],\"errorMessage\": \"\"}");
        //各部门会签公文处理效率接口
        cachaMap.put("http://uatworkflownew.oa.unionpay.com/default/oawf/httptrs/officedoc!getDeptSignAverageTime.action","{\"isSuccess\": \"true\",\"jsonData\": [{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"409\",\"orgname\": \"基本建设管理办公室\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"413\",\"orgname\": \"客户服务中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"419\",\"orgname\": \"集中采购管理办公室\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"269\",\"orgname\": \"北京代表处\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"371\",\"orgname\": \"北京信息中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1200\",\"orgname\": \"联银创投\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1199\",\"orgname\": \"其他\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"347\",\"orgname\": \"电子支付研究院\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"362\",\"orgname\": \"信息总中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"402\",\"orgname\": \"财务部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1295\",\"orgname\": \"总监或顾问\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1294\",\"orgname\": \"移动支付部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"377\",\"orgname\": \"人力资源部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"388\",\"orgname\": \"监察审计部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"394\",\"orgname\": \"风险管理部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"277\",\"orgname\": \"市场拓展部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"284\",\"orgname\": \"银联国际\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"250\",\"orgname\": \"产品与创新部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1134\",\"orgname\": \"互联网部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"227\",\"orgname\": \"业务部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"235\",\"orgname\": \"技术部\",\"efficiency\": \"26.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1375\",\"orgname\": \"公司发展及重大项目部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1494\",\"orgname\": \"技术开发中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1654\",\"orgname\": \"风险监控服务中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1675\",\"orgname\": \"市场部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"1764\",\"orgname\": \"业务运营中心\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"207\",\"orgname\": \"公司领导\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"208\",\"orgname\": \"办公室\",\"efficiency\": \"26.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"216\",\"orgname\": \"战略发展部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2435\",\"orgname\": \"000888斯蒂芬\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2196\",\"orgname\": \"测试部门9990io我放\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2255\",\"orgname\": \"测试部门17\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2475\",\"orgname\": \"测试部门222\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2218\",\"orgname\": \"是士大夫士大夫\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2335\",\"orgname\": \"888000\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2219\",\"orgname\": \"测试部门01\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2103\",\"orgname\": \"风险部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2059\",\"orgname\": \"法律合规部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2054\",\"orgname\": \"群工部\",\"efficiency\": \"0.0\"},{\"createtime\": \"2019-12-15 00:00:00\",\"orgid\": \"2535\",\"orgname\": \"测试名称\",\"efficiency\": \"0.0\"}],\"errorMessage\": \"\"}");
        //各部门主办公文总时长超期数量统计表接口 未完善
        cachaMap.put("http://uatworkflownew.oa.unionpay.com/default/oawf/httptrs/officedoc!countDeptSponsorOvertime.action","");
        //各部门当前处理公文超期数量统计表接口 未完善
        cachaMap.put("http://uatworkflownew.oa.unionpay.com/default/oawf/httptrs/officedoc!countDeptCurrentHandleOvertime.action","");
        //超期公文查看接口 未完善
        cachaMap.put("http://uatworkflownew.oa.unionpay.com/default/oawf/httptrs/officedoc!getOverTimeOfficeDoc.action","");
        //部门账号超期公文表接口 未完善
        cachaMap.put("http://uatworkflownew.oa.unionpay.com/default/oawf/httptrs/officedoc!getOverTimeOfficeDocByDept.action","");

    }
    @Override
    public JSONObject LiuChengReq(Map map) {
        String url=map.get("url")+"";
        String str = HttpClientUtils.doGet(url,map);
        String result="";
        if(StringUtils.isEmpty(str)){
            result=cachaMap.get(url);
        }else{
            JSONObject object = JSONObject.parseObject(str);
            boolean isSuccess =object.getBoolean("isSuccess");
            if (isSuccess){
                result=str;
            }else {
                result=cachaMap.get(url);
            }
        }
        return JSON.parseObject(result);
    }

    public Integer getOrgId(){
        String userId=SessionUtils.getUserId();
        Map map=jdbcTemplate.queryForMap("select deptorgid from om_user where userid='"+userId+"'");
        return Integer.parseInt(map.get("deptorgid")+"");
    }
}
