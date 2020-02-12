package com.unionpay.supervision.web.major;

import com.alibaba.excel.annotation.ExcelProperty;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.domain.SuperTag;
import com.unionpay.supervision.service.SuperTagService;
import com.unionpay.supervision.supportController.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * jinzhao
 * 2019-11-19
 * 事项标签
 */

@Api(value = "事项标签查询", description = "事项标签查询")
@RestController
@RequestMapping("/superTag")
public class SuperTagController extends BaseController {

    @Autowired
    private SuperTagService superTagService;

    @GetMapping("/selectAll")
    public RESTResultBean selectAll() {
        RESTResultBean resultBean = new RESTResultBean(200, "SUCCESS");
        try {
            List<SuperTag> list = superTagService.findAll();
            resultBean.setData(list);
            if (ToolUtil.isNotEmpty(list)) {
            } else if (ToolUtil.isEmpty(list)) {
                resultBean.setCode(200);
                resultBean.setMessage("查询成功，但没有信息");
            }
        } catch (Exception e) {
            resultBean.setCode(500);
            resultBean.setMessage("查询失败");
        }
        return resultBean;
    }


}
