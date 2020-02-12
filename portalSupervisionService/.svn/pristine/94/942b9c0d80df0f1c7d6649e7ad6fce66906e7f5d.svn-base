package com.unionpay.supervision.web.major;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.supervision.model.StatisticalDto;
import com.unionpay.supervision.model.StatisticsModel;
import com.unionpay.supervision.service.StatisticsService;
import com.unionpay.supervision.supportController.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  督办统计Controller
 * </p>
 *
 * @author xiongym
 * @since 2018-12-25
 */
@Api(value = "StatisticsController", description = "督办统计Controller")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/statistics")
public class StatisticsController extends BaseController{
	
	@Autowired
    private StatisticsService statisticsService;
	
	/**
	 * 督办类型统计
	*/
    @ApiOperation(value="督办类型统计")
	@GetMapping("/getOverSeeStatistics")
	@ResponseBody
	public RESTResultBean getOverSeeStatistics(){   
		RESTResultBean result = new RESTResultBean(200,"suceess");
		List<StatisticsModel> list = statisticsService.getOverSeeStatistics();
		result.setData(list);
		return result;
	}
    
    /**
	 * 事项类型统计
	*/
    @ApiOperation(value="事项类型统计")
	@GetMapping("/getServiceTypeStatistics")
	@ResponseBody
	public RESTResultBean getServiceTypeStatistics(StatisticalDto statisticalDto){   
		RESTResultBean result = new RESTResultBean(200,"suceess");
		List<StatisticsModel> list = null;
		if(StatisticalDto.isEmpty(statisticalDto)){
			list = statisticsService.getServiceTypeStatistics();
		}else{
			list = statisticsService.getServiceTypeStatistics(statisticalDto);
		}
		result.setData(list);
		return result;
	}

}
