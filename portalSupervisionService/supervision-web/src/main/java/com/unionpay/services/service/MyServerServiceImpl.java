package com.unionpay.services.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.HttpClientUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.business.SendNotice;
import com.unionpay.services.entity.*;
import com.unionpay.services.model.CheckConditionModel;
import com.unionpay.services.model.CountDeptModel;
import com.unionpay.services.model.SendNoticeModelDto;
import com.unionpay.services.model.ServerApplyModel;
import com.unionpay.services.repository.*;
import com.unionpay.services.util.CalendarUtil;
import com.unionpay.services.util.Result;
import com.unionpay.supervision.domain.OmOrganization;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 我的服务 查询流程列表 service
 * @author lishuang
 * @date 2019/10/10
 */
@Service
public class MyServerServiceImpl implements MyServerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ServerApplyInfoRepository serverApplyInfoRepository;
    @Autowired
    private ServerApplyUserRepository serverApplyUserRepository;
    @Autowired
    private ServerApplyCountRepository serverApplyCountRepository;
    @Autowired
    private ServerApplyLogRepository serverApplyLogRepository;
    @Autowired
    private ServerApplyFlowRepository serverApplyFlowRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ServerSuggestService serverSuggestService;
    @Autowired
    private SendNotice sendNotice;

    @Value("${http.liucheng.underwayflow}")
    private String underWayServerUrl;
    @Value("${http.liucheng.completeflow}")
    private String completeServerUrl;
    @Value("${http.liucheng.servercount}")
    private String serverCountUrl;
    @Value("${http.service.account}")
    private String account;
    @Value("${http.service.password}")
    private String password;
    @Value("${http.server.givescore}")
    private String giveScoreUrl;
    @Value("${http.server.givescore.complete}")
    private String completeGiveScoreUrl;

    /**
     * 分页查询进行中流程列表
     * @return
     */
    public Result getUnderWayServerUseGet(String classify, String launchTimeEnd, String launchTimeStart, String userid, String size, String currentPage,String serviceInfoName){
        Map map = new HashMap();
        //添加默认时间
        Calendar c = Calendar.getInstance();
        map.put("classify",classify);
        //添加默认开始时间
        if ("".equals(launchTimeStart)||null==launchTimeStart){
            c.setTime(new Date());
            c.add(Calendar.MONTH,-3);
            Date y = c.getTime();
            launchTimeStart = DateUtil.format(y,"yyyy-MM-dd HH:mm:ss");
            map.put("launchTimeStart",launchTimeStart);
        }else {
            map.put("launchTimeStart",launchTimeStart+" 00:00:00");
        }
        //添加默认结束时间
        if ("".equals(launchTimeEnd)||null ==launchTimeEnd){
            map.put("launchTimeEnd",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }else {
            map.put("launchTimeEnd",launchTimeEnd+" 23:59:59");
        }
        //构建流程平台需要的格式
        map.put("userid",userid);
        map.put("size",size);
        map.put("currentPage",currentPage);
        if (ToolUtil.isNotEmpty(serviceInfoName)) {
            map.put("serviceInfoName", serviceInfoName);
        }
        System.out.println(map);
        String str = HttpClientUtils.doGet(underWayServerUrl,map);
        logger.info(str);
        List<ServerApplyModel> modelList = null;
        Pager pager = new Pager();
        if (ToolUtil.isNotEmpty(str)){
            JSONObject object = JSONObject.parseObject(str);
            if (ToolUtil.isNotEmpty(object)){
                JSONArray array = object.getJSONArray("data");
                if (ToolUtil.isNotEmpty(array)){
                    // 进行中流程接口数据转换成ServerApplyModel集合
                    modelList = changeArrayToModel(array);
                }
                pager = getArrayToPager(object);
            }
        }
        return Result.success(modelList,pager);
    }

    private Pager getArrayToPager(JSONObject jsonObject){
        Pager pager = new Pager();
        System.out.println(jsonObject.get("paper"));
        JSONObject page = JSONObject.parseObject(jsonObject.get("paper").toString());
        if (ToolUtil.isNotEmpty(page)){
                pager.setTotal(page.getInteger("total"));
                pager.setSize(page.getInteger("size"));
                pager.setCurrentPage(page.getInteger("currentPage"));
        }
        return pager;
    }

    private List<ServerApplyModel> changeArrayToModel(JSONArray array){
        List<ServerApplyModel> modelList = new ArrayList<>();
        for (int i=0;i<array.size();i++){
            JSONObject object = JSONObject.parseObject(array.get(i).toString());
            if (ToolUtil.isEmpty(object)){
                continue;
            }
            ServerApplyModel model = new ServerApplyModel();
            model.setId(new BigInteger(object.getString("processid")));
            model.setServiceInfoName(object.getString("serviceInfoName"));
            model.setLaunchUsername(object.getString("launchUsername"));
            model.setLaunchTime(object.getString("launchTime"));
            model.setCurrentNode(object.getString("currentNode"));
            model.setCurrentOrgname(object.getString("currentOrgname"));
            model.setCurrentUsername(object.getString("currentUsername"));
            model.setQueryUrl(object.getString("queryUrl"));
            modelList.add(model);
        }
        return modelList;
    }

    /**
     * 分页查询已完成服务列表
     * @param checkConditionModel
     * @return
     */
    public List<ServerApplyModel> getCompleteServer(CheckConditionModel checkConditionModel,String userid){
        String sql = "SELECT s.id,s.service_info_name serviceInfoName,DATE_FORMAT(s.launch_time, '%Y-%m-%d') launchTime,"
                   + "DATE_FORMAT(s.end_time, '%Y-%m-%d') endTime,TIMESTAMPDIFF(DAY,s.launch_time,s.end_time) serverDuration,"
                   + "s.ser_orgname serOrgname,s.ser_username flowUsername,s.score,s.query_url queryUrl FROM server_apply_info s ";
        sql += getConditionSql(checkConditionModel,userid);
        Query query = entityManager.createNativeQuery(sql);
        query.setFirstResult((checkConditionModel.getCurrentPage()-1)*checkConditionModel.getSize());
        query.setMaxResults(checkConditionModel.getSize());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerApplyModel.class));
        List<ServerApplyModel> modelList = query.getResultList();
        return modelList;
    }

    /**
     * 查询已完成服务总数
     * @param checkConditionModel
     * @param userid
     * @return
     */
    public int count(CheckConditionModel checkConditionModel,String userid){
        int count = 0;
        String sql = "SELECT count(s.id) FROM server_apply_info s ";
        sql += getConditionSql(checkConditionModel,userid);
        Query query = entityManager.createNativeQuery(sql);
        List<BigInteger> counts = query.getResultList();
        if (ToolUtil.isNotEmpty(counts)){
            count = counts.get(0).intValue();
        }
        return count;
    }

    private String getConditionSql(CheckConditionModel checkConditionModel,String userid){
        String conditionSql = "";
        if (checkConditionModel.getClassify()==0){
            conditionSql += "WHERE s.launch_userid ='"+userid+"'";
        }else if (checkConditionModel.getClassify()==1){
            //conditionSql += "LEFT JOIN server_apply_user u ON s.process_id = u.process_id WHERE u.current_userid ='"+userid+"'";
            conditionSql += "INNER JOIN (SELECT process_id FROM server_apply_user WHERE current_userid = '"+userid+"' " +
                    "GROUP BY process_id) a ON s.process_id = a.process_id  WHERE 1=1 ";
        }
        if (ToolUtil.isNotEmpty(checkConditionModel.getSerOrgid())){
            conditionSql += "AND s.ser_orgid ='"+checkConditionModel.getSerOrgid()+"'";
        }
        if (ToolUtil.isNotEmpty(checkConditionModel.getLaunchTimeStart())){
            conditionSql += "AND s.launch_time >='"+checkConditionModel.getLaunchTimeStart()+" 00:00:00'";
        }
        if (ToolUtil.isNotEmpty(checkConditionModel.getLaunchTimeEnd())){
            conditionSql += "AND s.launch_time <='"+checkConditionModel.getLaunchTimeEnd()+" 23:59:59'";
        }
        if (ToolUtil.isNotEmpty(checkConditionModel.getServiceInfoName())){
            conditionSql += "AND s.service_info_name LIKE '%"+checkConditionModel.getServiceInfoName()+"%'";
        }
        if (ToolUtil.isNotEmpty(checkConditionModel.getScore())){
            conditionSql += "AND s.score ='"+checkConditionModel.getScore()+"'";
        }
        conditionSql += " ORDER BY s.launch_time DESC";
        return conditionSql;
    }

    /**
     * 查看详情页
     * @param id
     * @return
     */
    public ServerApplyModel get(BigInteger id){
        String sql = "SELECT s.id,s.service_info_name serviceInfoName,s.launch_username launchUsername,"
                   + "DATE_FORMAT(s.launch_time, '%Y-%m-%d') launchTime,DATE_FORMAT(s.end_time, '%Y-%m-%d') endTime,"
                   + "TIMESTAMPDIFF(DAY,s.launch_time,s.end_time) serverDuration,s.ser_orgname serOrgname,s.ser_username "
                   + "flowUsername,s.score FROM server_apply_info s WHERE s.id=:id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id",id);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerApplyModel.class));
        ServerApplyModel serverApplyModel = (ServerApplyModel)query.getSingleResult();
        return serverApplyModel;
    }

    /**
     * 获取服务统计
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public void getServerCount(){
        /*
         * 先删除，再保存新拉取的数据
         */
        serverApplyCountRepository.deleteAll();
        ServerApplyCount serverApplyCount = null;
        // 本周数据
        String weekStartTime = DateUtil.getStrTime(CalendarUtil.getCurrentWeekStartTime(Calendar.getInstance()));
        String weekEndTime = DateUtil.getStrTime(CalendarUtil.getCurrentWeekEndTime(Calendar.getInstance()));
        serverApplyCount = calcCount(weekStartTime,weekEndTime);
        serverApplyCount.setType(1);
        String weekServerIncrease = serverIncrease(1);
        serverApplyCount.setServerIncrements(weekServerIncrease);
        serverApplyCountRepository.save(serverApplyCount);
        //本月数据
        String monthStartTime = DateUtil.getStrTime(CalendarUtil.getCurrentMonthStartTime(Calendar.getInstance()));
        String monthEndTime = DateUtil.getStrTime(CalendarUtil.getCurrentMonthEndTime(Calendar.getInstance()));
        serverApplyCount = calcCount(monthStartTime,monthEndTime);
        serverApplyCount.setType(2);
        String monthServerIncrease = serverIncrease(2);
        serverApplyCount.setServerIncrements(monthServerIncrease);
        serverApplyCountRepository.save(serverApplyCount);
        //本季数据
        String quarterStartTime = DateUtil.getStrTime(CalendarUtil.getCurrentQuarterStartTime(Calendar.getInstance()));
        String quarterEndTime = DateUtil.getStrTime(CalendarUtil.getCurrentQuarterEndTime(Calendar.getInstance()));
        serverApplyCount = calcCount(quarterStartTime,quarterEndTime);
        serverApplyCount.setType(3);
        String quarterServerIncrease = serverIncrease(3);
        serverApplyCount.setServerIncrements(quarterServerIncrease);
        serverApplyCountRepository.save(serverApplyCount);
    }

    /**
     * 获取已完成流程
     * @param endTimeStart
     * @param endTimeEnd
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public List<ServerApplyInfo> getCompleteServer(String endTimeStart,String endTimeEnd){
        List<ServerApplyInfo> list = null;
        Map<String,String> map = new LinkedHashMap<>();
        map.put("endTimeStart",endTimeStart);
        map.put("endTimeEnd",endTimeEnd);
        String str = HttpClientUtils.doGet(completeServerUrl,map);
        logger.info(str);
        if (ToolUtil.isNotEmpty(str)){
            JSONObject object = JSONObject.parseObject(str);
            if (ToolUtil.isNotEmpty(object)){
                Pager pager = (Pager)object.get("pager");
                JSONArray array = object.getJSONArray("data");
                if (ToolUtil.isNotEmpty(array)){
                    // 流程信息保存
                    List<ServerApplyInfo> infoList = changeToServerApplyInfo(array);
                    list = serverApplyInfoRepository.saveAll(infoList);
                    // 当前节点操作人信息保存
                    List<ServerApplyUser> userList = changeToServerApplyUser(array);
                    serverApplyUserRepository.saveAll(userList);
                }
            }
//            if (ToolUtil.isNotEmpty(list)){
//                // 处理完成服务，发送通知给发起人，进行评价
//                for (ServerApplyInfo info:list){
//                    SendNoticeModelDto modelDto = new SendNoticeModelDto();
//                    modelDto.setTitle("您发起的【"+info.getServiceInfoName()+"】已完成，请及时评价哦");
//                    modelDto.setUrl(giveScoreUrl+info.getId());
//                    modelDto.setTypeId(5);
//                    modelDto.setAccount(account);
//                    modelDto.setPassword(password);
//                    // 接收人
//                    String userid = info.getLaunchUserid();
//                    List<Integer> receivers = new ArrayList<>();
//                    Integer empid = omUserService.findByUserid(userid).getEmpid();
//                    receivers.add(empid);
//                    modelDto.setReceivers(receivers);
////                    sendNotice.sendNotice(modelDto);
//                }
//            }
        }
        return list;
    }

    private void sendNotice(List<ServerApplyInfo> list){

    }

    private ServerApplyCount calcCount(String endTimeStart,String endTimeEnd){
        ServerApplyCount serverApplyCount = new ServerApplyCount();
        String str = getServerCount(endTimeStart,endTimeEnd);
        int serverTotal = Integer.parseInt(str.substring(0,str.indexOf(",")));
        int underwayCount = Integer.parseInt(str.substring(str.indexOf(",")+1));
        serverApplyCount.setServerTotal(serverTotal);
        serverApplyCount.setUnderwayCount(underwayCount);
        //已完成的服务数
        int completeCount = serverTotal -underwayCount;
        //已完成服务总时长
        List<BigInteger> list = serverApplyInfoRepository.findAllDiff(endTimeStart,endTimeEnd);
        Long time = 0L;
        for (BigInteger i:list){
            if (i.compareTo(BigInteger.ZERO)==0){
                continue;
            }else {
                time += i.longValue();
            }
        }
        //平均时效
        int averageTime = 0;
        if (ToolUtil.isNotEmpty(completeCount)||0!=completeCount) {
            if (time != null) {
                averageTime = Long.bitCount(time / completeCount);
            }
        }
        //投诉总数
        int complaintTotal = serverSuggestService.getComplaintTotal(endTimeStart,endTimeEnd);
        //服务满意个数
        int satisfiedCount = serverApplyInfoRepository.findByScore(1,endTimeStart,endTimeEnd);
        //服务不满意个数
        int unsatisfiedCount = serverApplyInfoRepository.findByScore(0,endTimeStart,endTimeEnd);
        serverApplyCount.setAverageTime(averageTime);
        serverApplyCount.setComplaintCount(complaintTotal);
        serverApplyCount.setSatisfiedCount(satisfiedCount);
        serverApplyCount.setUnSatisfiedCount(unsatisfiedCount);
        serverApplyCount.setCreateTime(DateUtil.getStrTime(new Date()));
        return serverApplyCount;
    }

    /**
     * 服务增量统计
     * @param type
     * @return
     */
    private String serverIncrease(Integer type){
        String serverIncrease = "";
        // 1本周；2本月；3本季
        if (type == 1){
            // 上周起始时间
            String weekEndTimeStart = DateUtil.getStrTime(CalendarUtil.getLastWeekStartTime(Calendar.getInstance()));
            String weekEndTimeEnd = DateUtil.getStrTime(CalendarUtil.getCurrentWeekStartTime(Calendar.getInstance()));
            String week = getServerCount(weekEndTimeStart,weekEndTimeEnd);
            int lastWeekServerCount = Integer.parseInt(week.substring(0,week.indexOf(",")));
            // 上上周起始时间
            String lastWeekEndTimeStart = DateUtil.getStrTime(CalendarUtil.getBeforeLastWeekStartTime(Calendar.getInstance()));
            String lastWeekEndTimeEnd = DateUtil.getStrTime(CalendarUtil.getLastWeekStartTime(Calendar.getInstance()));
            String lastWeek = getServerCount(lastWeekEndTimeStart,lastWeekEndTimeEnd);
            int beforeLastWeekServerCount = Integer.parseInt(lastWeek.substring(0,lastWeek.indexOf(",")));
            serverIncrease = Integer.parseInt(new DecimalFormat("0").
                    format((lastWeekServerCount-beforeLastWeekServerCount)/beforeLastWeekServerCount*100))+"%";
        }else if (type == 2){
            // 上月起始时间
            String monthEndTimeStart = DateUtil.getStrTime(CalendarUtil.getLastMonthStartTime(Calendar.getInstance()));
            String monthEndTimeEnd = DateUtil.getStrTime(CalendarUtil.getCurrentMonthStartTime(Calendar.getInstance()));
            String month = getServerCount(monthEndTimeStart,monthEndTimeEnd);
            int lastMonthServerCount = Integer.parseInt(month.substring(0,month.indexOf(",")));
            // 上上月起始时间
            String lastMonthEndTimeStart = DateUtil.getStrTime(CalendarUtil.getBeforeLastMonthStartTime(Calendar.getInstance()));
            String lastMonthEndTimeEnd = DateUtil.getStrTime(CalendarUtil.getLastMonthStartTime(Calendar.getInstance()));
            String lastMonth = getServerCount(lastMonthEndTimeStart,lastMonthEndTimeEnd);
            int beforeLastMonthServerCount = Integer.parseInt(lastMonth.substring(0,lastMonth.indexOf(",")));
            serverIncrease = Integer.parseInt(new DecimalFormat("0").
                    format((lastMonthServerCount-beforeLastMonthServerCount)/beforeLastMonthServerCount*100))+"%";
        }else if (type == 3){
            // 上季起始时间
            String quarterEndTimeStart = DateUtil.getStrTime(CalendarUtil.getLastQuarterStartTime(Calendar.getInstance()));
            String quarterEndTimeEnd = DateUtil.getStrTime(CalendarUtil.getCurrentQuarterStartTime(Calendar.getInstance()));
            String quarter = getServerCount(quarterEndTimeStart,quarterEndTimeEnd);
            int lastQuarterServerCount = Integer.parseInt(quarter.substring(0,quarter.indexOf(",")));
            // 上上季起始时间
            String lastQuarterEndTimeStart = DateUtil.getStrTime(CalendarUtil.getBeforeLastQuarterStartTime(Calendar.getInstance()));
            String lastQuarterEndTimeEnd = DateUtil.getStrTime(CalendarUtil.getLastQuarterStartTime(Calendar.getInstance()));
            String lastQuarter = getServerCount(lastQuarterEndTimeStart,lastQuarterEndTimeEnd);
            int beforeLastQuarterServerCount = Integer.parseInt(lastQuarter.substring(0,lastQuarter.indexOf(",")));
            serverIncrease = Integer.parseInt(new DecimalFormat("0").
                    format((lastQuarterServerCount-beforeLastQuarterServerCount)/beforeLastQuarterServerCount*100))+"%";
        }
        return serverIncrease;
    }

    /**
     * 从流程平台获取服务统计总量
     * @param endTimeStart
     * @param endTimeEnd
     * @return
     */
    private String getServerCount(String endTimeStart,String endTimeEnd){
        int serverTotal = 0;
        int underwayCount = 0;
        Map<String,String> map = new LinkedHashMap<>();
        map.put("launchTimeStart",endTimeStart);
        map.put("launchTimeEnd",endTimeEnd);
        String str = HttpClientUtils.doGet(serverCountUrl,map);
        logger.info(str);
        if (ToolUtil.isNotEmpty(str)){
            JSONObject object = JSONObject.parseObject(str);
            if (ToolUtil.isNotEmpty(object)){
                JSONObject data  = JSONObject.parseObject(object.get("data").toString());
                if (ToolUtil.isNotEmpty(data)){
                    //服务总量
                    serverTotal = data.getInteger("total");
                    //进行中的服务数
                    if (ToolUtil.isNotEmpty(data.getInteger("underway"))) {
                        underwayCount = data.getInteger("underway");
                    }
                }
            }
        }
        return serverTotal+","+underwayCount;
    }

    /**
     * 根据类型查询统计数据
     * @param type 1本周；2本月；3本季
     * @return
     */
    public ServerApplyCount getDate(Integer type){
        ServerApplyCount count = null;
        String sql = "SELECT server_total serverTotal,underway_count underwayCount,average_time averageTime,"
                   + "complaint_count complaintCount,server_increments serverIncrements,satisfied_count "
                   + "satisfiedCount,unsatisfied_count unSatisfiedCount FROM server_apply_count WHERE type =:type";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerApplyCount.class));
        query.setParameter("type",type);
        List<ServerApplyCount> counts = query.getResultList();
        if (ToolUtil.isNotEmpty(counts)){
            count = counts.get(0);
        }
        return count;
    }

    /**
     * 根据类型查询部门满意度排行
     * @param type 1本周；2本月；3本季
     * @return
     */
    public List sort(Integer type){
        List list = null;
        // 本周部门满意度排行
        if (type ==1){
            String weekStartTime = DateUtil.getStrTime(CalendarUtil.getCurrentWeekStartTime(Calendar.getInstance()));
            String weekEndTime = DateUtil.getStrTime(CalendarUtil.getCurrentWeekEndTime(Calendar.getInstance()));
            list = deptSort(weekStartTime,weekEndTime);
        }
        if (type == 2){
            String monthStartTime = DateUtil.getStrTime(CalendarUtil.getCurrentMonthStartTime(Calendar.getInstance()));
            String monthEndTime = DateUtil.getStrTime(CalendarUtil.getCurrentMonthEndTime(Calendar.getInstance()));
            list = deptSort(monthStartTime,monthEndTime);
        }
        if (type == 3){
            String quarterStartTime = DateUtil.getStrTime(CalendarUtil.getCurrentQuarterStartTime(Calendar.getInstance()));
            String quarterEndTime = DateUtil.getStrTime(CalendarUtil.getCurrentQuarterEndTime(Calendar.getInstance()));
            list = deptSort(quarterStartTime,quarterEndTime);
        }
        return list;
    }
    private List deptSort(String weekStartTime,String weekEndTime){
        List list = new ArrayList();
        // 部门满意数
        String sql1 = "SELECT s.ser_orgid orgid,o.ORGNAME orgname,COUNT(id) num FROM server_apply_info s "
                   + "LEFT JOIN om_organization o ON s.ser_orgid = o.ORGID WHERE score = '1' "
                   + "AND launch_time >=:weekStartTime AND end_time <=:weekEndTime "
                   + "GROUP BY ser_orgid ORDER BY o.SORTNO";
        Query query1 = entityManager.createNativeQuery(sql1);
        query1.setParameter("weekStartTime",weekStartTime);
        query1.setParameter("weekEndTime",weekEndTime);
        query1.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(CountDeptModel.class));
        List<CountDeptModel> satisfiedDeptList = query1.getResultList();
        // 部门投票数
        String sql2 = "SELECT s.ser_orgid orgid,o.ORGNAME orgname,COUNT(id) num FROM server_apply_info s "
                    + "LEFT JOIN om_organization o ON s.ser_orgid = o.ORGID WHERE score = '1' OR score = '0' "
                    + "AND launch_time >=:weekStartTime AND end_time <=:weekEndTime "
                    + "GROUP BY ser_orgid ORDER BY o.SORTNO";
        Query query2 = entityManager.createNativeQuery(sql2);
        query2.setParameter("weekStartTime",weekStartTime);
        query2.setParameter("weekEndTime",weekEndTime);
        query2.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(CountDeptModel.class));
        List<CountDeptModel> voteDeptList = query2.getResultList();
        if (ToolUtil.isNotEmpty(satisfiedDeptList)&&ToolUtil.isNotEmpty(voteDeptList)){
            for (CountDeptModel model:satisfiedDeptList){
                for (CountDeptModel deptModel:voteDeptList){
                    if (!deptModel.getOrgid().equals(model.getOrgid())){
                       continue;
                    }
                    Map map = new LinkedHashMap<>();
                    map.put("orgname",model.getOrgname());
                    double num = model.getNum().intValue();
                    double total = deptModel.getNum().intValue();
                    map.put("satisfaction",Integer.parseInt(new DecimalFormat("0").format(num/total*100))+"%");
                    list.add(map);
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 流程平台接口数据转换至ServerApplyInfo
     * @param array
     * @return
     */
    private List<ServerApplyInfo> changeToServerApplyInfo(JSONArray array){
        List<ServerApplyInfo> infoList = new ArrayList<>();
        for(int i=0;i<array.size();i++){
            JSONObject jsonObject = JSONObject.parseObject(array.get(i).toString());
            if (ToolUtil.isEmpty(jsonObject)){
                continue;
            }
            // 日志记录
            ServerApplyLog log = new ServerApplyLog();
            log.setBizcode(jsonObject.getString("bizcode"));
            log.setChgData(array.get(i).toString());
            log.setClientType(1);
            log.setStatus(1);
            log.setCreateTime(DateUtil.getStrTime(new Date()));
            serverApplyLogRepository.save(log);
            String processid = jsonObject.getString("processid");
            String bizcode = jsonObject.getString("bizcode");
            ServerApplyInfo info = serverApplyInfoRepository.findByProcessIdAndAndBizcode(processid,bizcode);
            if (ToolUtil.isNotEmpty(info)){
                continue;
            }
            // 流程信息保存
            ServerApplyInfo serverApplyInfo = new ServerApplyInfo();
            serverApplyInfo.setProcessId(processid);
            serverApplyInfo.setBizcode(bizcode);
            serverApplyInfo.setServiceInfoName(jsonObject.getString("serviceInfoName"));
            serverApplyInfo.setStatus(jsonObject.getString("status"));
            serverApplyInfo.setLaunchUserid(jsonObject.getString("launchUserid"));
            serverApplyInfo.setLaunchUsername(jsonObject.getString("launchUsername"));
            serverApplyInfo.setLaunchTime(jsonObject.getString("launchTime"));
            serverApplyInfo.setEndTime(jsonObject.getString("endTime"));
            String flowNameId = jsonObject.getString("flowNameid");
            serverApplyInfo.setFlowNameId(flowNameId);
            serverApplyInfo.setFlowName(jsonObject.getString("flowName"));
            String serverItemId = jsonObject.getString("serveritemId");
            serverApplyInfo.setServerItemId(serverItemId);
            serverApplyInfo.setServerItemName(jsonObject.getString("serveritemName"));
            ServerApplyFlow serverApplyFlow = serverApplyFlowRepository.findByFlowNameIdAndServerItemId(flowNameId,serverItemId);
            Integer serId = null;
            if (ToolUtil.isNotEmpty(serverApplyFlow)) {
                serId = serverApplyFlow.getSerId();
                serverApplyInfo.setSerId(serId);
            }
            if (ToolUtil.isNotEmpty(serId)){
                // 设置服务所属部门
                String dept = getDeptOrgidAndName(serId);
                String orgid = dept.substring(0,dept.indexOf("-"));
                String orgname = dept.substring(dept.indexOf("-")+1);
                serverApplyInfo.setSerOrgid(orgid);
                serverApplyInfo.setSerOrgname(orgname);
                // 设置服务经理
                String user = getUserEmpidAndUsername(serId);
                String userid = user.substring(0,user.indexOf("-"));
                String username = user.substring(user.indexOf("-")+1);
                serverApplyInfo.setSerUserid(userid);
                serverApplyInfo.setSerUsername(username);
            }
            serverApplyInfo.setQueryUrl(jsonObject.getString("queryUrl"));
            serverApplyInfo.setCreateTime(DateUtil.getStrTime(new Date()));
            infoList.add(serverApplyInfo);
        }
        return infoList;
    }

    /**
     * 根据服务id，获取服务所属部门编号即名称
     * @param serId
     * @return
     */
    private String getDeptOrgidAndName(Integer serId){
        String orgId = "";
        String orgName = "";
        String sql = "SELECT d.org_id orgid,o.ORGNAME orgname FROM server_info s LEFT JOIN server_acceptance_dept d ON s.id = d.server_id "
                   + "LEFT JOIN om_organization o ON d.org_id = o.ORGID WHERE s.id =:serId";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("serId",serId);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(OmOrganization.class));
        List<OmOrganization> organizations = query.getResultList();
        if (ToolUtil.isNotEmpty(organizations)){
            for (int i=0;i<organizations.size();i++){
                if (i<organizations.size()-1){
                    orgId += organizations.get(i).getOrgid()+",";
                    orgName += organizations.get(i).getOrgname()+",";
                }else {
                    orgId += organizations.get(i).getOrgid();
                    orgName += organizations.get(i).getOrgname();
                }
            }
        }
        return orgId+"-"+orgName;
    }

    /**
     * 根据服务id，获取服务受理人（即服务经理）编号及名称
     * @param serId
     * @return
     */
    private String getUserEmpidAndUsername(Integer serId){
        String empid = "";
        String username = "";
        String sql = "SELECT u.user_id empid,u.user_name empname FROM server_acceptance_user u WHERE u.server_id =:serId";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("serId",serId);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(OmUser.class));
        List<OmUser> users = query.getResultList();
        if (ToolUtil.isNotEmpty(users)){
            for (int i=0;i<users.size();i++){
                if (i<users.size()-1){
                    empid += users.get(i).getEmpid()+",";
                    username += users.get(i).getEmpname()+",";
                }else {
                    empid += users.get(i).getEmpid();
                    username += users.get(i).getEmpname();
                }
            }
        }
        return empid+"-"+username;
    }

    /**
     * 流程平台接口数据转换至ServerApplyUser
     * @param array
     * @return
     */
    private List<ServerApplyUser> changeToServerApplyUser(JSONArray array){
        List<ServerApplyUser> userList = new ArrayList<>();
        for(int i=0;i<array.size();i++){
            JSONObject jsonObject = JSONObject.parseObject(array.get(i).toString());
            if (ToolUtil.isEmpty(jsonObject)){
                continue;
            }
            JSONArray userArray = jsonObject.getJSONArray("currentUser");
            if (ToolUtil.isNotEmpty(userArray)){
                for (int j=0;j<userArray.size();j++){
                    JSONObject object = JSONObject.parseObject(userArray.get(j).toString());
                    if (ToolUtil.isEmpty(object)){
                        continue;
                    }
                    ServerApplyUser user = new ServerApplyUser();
                    user.setProcessId(jsonObject.getString("processid"));
                    user.setCurrentNode(object.getString("currentNode"));
                    user.setCurrentUserid(object.getString("currentUserid"));
                    user.setCurrentUsername(object.getString("currentUsername"));
                    user.setCurrentOrgid(object.getString("currentOrgid"));
                    user.setCurrentOrgname(object.getString("currentOrgname"));
                    user.setNodeTime(object.getString("nodeTime"));
                    user.setCreateTime(DateUtil.getStrTime(new Date()));
                    userList.add(user);
                }
            }
        }
        return userList;
    }

    /**
     * 评价已完成流程
     * @param id 流程编号ID
     * @param score 评价（1满意；0不满意）
     * @return
     */
    public boolean giveScore(BigInteger id, Integer score){
        ServerApplyInfo serverApplyInfo = serverApplyInfoRepository.getOne(id);
        serverApplyInfo.setScore(score);
        serverApplyInfo = serverApplyInfoRepository.save(serverApplyInfo);
        if (ToolUtil.isNotEmpty(serverApplyInfo)){
            // 评价完成，发送通知给服务经理
            SendNoticeModelDto modelDto = new SendNoticeModelDto();
            String userid = serverApplyInfo.getSerUserid();
            if (ToolUtil.isNotEmpty(userid)){
                // 接收人
                List<Integer> receivers = new ArrayList<>();
                String[] empids = userid.split(",");
                for (String str:empids){
                    receivers.add(Integer.parseInt(str));
                }
                if (ToolUtil.isNotEmpty(receivers)&&receivers.size()>0){
                    modelDto.setTitle("您处理的【"+serverApplyInfo.getServiceInfoName()+"】收到了1条新评价");
                    modelDto.setTypeId(5);
                    modelDto.setAccount(account);
                    modelDto.setPassword(password);
                    //modelDto.setUrl(completeServerUrl+serverApplyInfo.getId());
                    sendNotice.sendNotice(modelDto);
                }
            }
            return true;
        }else {
            return false;
        }
    }

}
