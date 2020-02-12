package com.unionpay.supervision.log;

import com.unionpay.common.exception.BussinessException;
import com.unionpay.sms.model.SmsLog;
import com.unionpay.sms.service.SmsLogService;
import com.unionpay.voice.domain.VoiceLog;
import com.unionpay.voice.service.VoiceLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unionpay.common.frame.SpringContextHolder;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.HttpUtil;
import com.unionpay.supervision.domain.SuperLog;
import com.unionpay.supervision.service.SuperLogService;
import com.unionpay.supervision.supportController.BaseController;

import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 *
 */
public class LogTaskFactory {

	private static Logger logger = LoggerFactory.getLogger(LogTaskFactory.class);

	private static SuperLogService superLogService = SpringContextHolder.getBean(SuperLogService.class);

	private static SmsLogService smsLogService = SpringContextHolder.getBean(SmsLogService.class);

	private static VoiceLogService voiceLogService = SpringContextHolder.getBean(VoiceLogService.class);

	private static String userId = SessionUtils.getUserId(HttpUtil.getRequest());

	/**
	 * 创建系统管理相关操作日志
	 *
	 * @param log
	 * @return
	 */
	public static TimerTask systemLog(SuperLog log) {
		return new TimerTask() {
			@Override
			public void run() {
				try {
					superLogService.add(log);
				} catch (Exception e) {
					logger.error("创建系统管理日志异常!", e);
				}
			}
		};
	}

	/**
	 * 创建系统管理相关操作日志
	 *
	 * @param sponsorUnid
	 *            督办部门id
	 * @param serviceUnid
	 *            督办事项id
	 * @param histData
	 *            历史数据
	 * @param chgData
	 *            变更数据
	 * @param clientType
	 *            操作平台类型（1，督办平台；2流程平台）
	 * @param changeType
	 *            日志类型（1.立项，2.督办，3.变更，4.延期，5.完成，6.中止，-1.删除）
	 * @param sponsorId
	 *            主办人
	 * @param overseeUserid
	 *            督办人id
	 * @return
	 */
	public static TimerTask systemLog(String sponsorUnid, String serviceUnid, String histData, String chgData,
			Integer clientType, Integer changeType, String sponsorId, String overseeUserid) {
		return new TimerTask() {
			@Override
			public void run() {
				try {
					SuperLog log = getCreateLog(sponsorUnid, serviceUnid, histData, chgData, clientType,
							changeType, sponsorId, overseeUserid);
					superLogService.add(log);
				} catch (Exception e) {
					logger.error("创建系统管理日志异常!", e);
				}
			}
		};
	}

	public static TimerTask errorLog(final Exception e) {
		return new TimerTask() {
			@Override
			public void run() {
				logger.error("运行异常：", e);
			}
		};
	}

	public static TimerTask warnLog(final String message, final Exception e) {
		return new TimerTask() {
			@Override
			public void run() {
				logger.warn("{}|{}", message, "操作用户ID：" + userId, e);
			}
		};
	}

	public static TimerTask infoLog(final String message) {
		return new TimerTask() {
			@Override
			public void run() {
				logger.info("{}|{}", message);
			}
		};
	}

	public static TimerTask debugLog(final String message) {
		return new TimerTask() {
			@Override
			public void run() {
				logger.debug("{}|{}", message);
			}
		};
	}

	private static SuperLog getCreateLog(String sponsorUnid, String serviceUnid, String histData, String chgData,
										  Integer clientType, Integer changeType, String sponsorId, String overseeUnid) {
		// 日志记录
		SuperLog log = new SuperLog();
		log.setSponsorUnid(sponsorUnid);
		log.setServiceUnid(serviceUnid);
		log.setHistData(histData);
		log.setChgData(chgData);
		log.setClientType(clientType);
		log.setChangeType(changeType);
		log.setOverseeUserid(overseeUnid);
		log.setSponsorId(sponsorId);
		log.setStatus(1);
		log.setCreateTime(DateUtil.getTime());

		return log;
	}

	/**
	 * 短信日志记录
	 * @param unid
	 * 			短信申请主键unid
	 * @param clientType
	 * 			操作平台类型（1、短信平台；2、流程平台）
	 * @param chgData
	 * 			历史数据
	 * @return
	 * @author lishuang
	 * @date 2019-04-25
	 */
	public static TimerTask smsLog(String unid,Integer clientType,String chgData) {
		return new TimerTask() {
			@Override
			public void run() {
				try {
					SmsLog log = createLog(unid,clientType,chgData);
					smsLogService.add(log);
				} catch (Exception e) {
					logger.error("创建日志异常!", e);
					throw new BussinessException(500,"创建日志异常");
				}
			}
		};
	}

	/**
	 * 短信日志记录
	 * @param log
	 * @return
	 * @author lishuang
	 * @date 2019-04-25
	 */
	public static TimerTask smsLog(SmsLog log) {
		return new TimerTask() {
			@Override
			public void run() {
				try {
					smsLogService.add(log);
				} catch (Exception e) {
					logger.error("创建日志异常!", e);
					throw new BussinessException(500,"创建日志异常!");
				}
			}
		};
	}

	private static SmsLog createLog(String unid,Integer clientType,String chgData) {
		// 日志记录
		SmsLog log = new SmsLog();
		log.setUnid(unid);
		log.setClientType(clientType);
		log.setChgData(chgData);
		log.setStatus(1);
		log.setCreateTime(DateUtil.getTime());
		return log;
	}

	/**
	 * 客户之声日志记录
	 * @param log
	 * @return
	 * @author lishuang
	 * @date 2019-05-16
	 */
	public static TimerTask voiceLog(VoiceLog log){
		return new TimerTask() {
			@Override
			public void run() {
				try {
					voiceLogService.add(log);
				} catch (Exception e) {
					logger.error("创建日志异常!", e);
					throw new BussinessException(500,"创建日志异常!");
				}
			}
		};
	}

}
