package com.zhongbin.helloworld.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//这个类的所有方法的返回数据直接给浏览器
@ResponseBody
@Api(tags = "OmsOrderReturnApplyController", description = "订单退货申请管理")
@Controller
public class HelloController {

    @ApiOperation("分页查询退货申请")
    @RequestMapping("/hello")
    public String hello(String hello){
        return hello;
    }
}
