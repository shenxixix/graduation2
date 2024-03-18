package com.yolo.hw.user.controller.user;


import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.dto.request.ReqCreateOrderDto;
import com.yolo.hw.user.dto.request.ReqOrderPayFinishDto;
import com.yolo.hw.user.dto.response.ResCreateOrderDto;
import com.yolo.hw.user.service.IRentUserOrderService;
import com.yolo.hw.user.service.IRentUserPickUpService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 租赁订单表 前端控制器
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Controller
@RequestMapping("/rentUserOrder")
public class RentUserOrderController {

    @Autowired
    private IRentUserOrderService orderService;

    @ApiOperation(value = "生成订单", notes = "生成订单", response = ResCreateOrderDto.class)
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public Object createOrder(@RequestBody ReqCreateOrderDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        return Success.ok();
    }

    @ApiOperation(value = "订单支付完成回调", notes = "订单支付完成回调", response = Object.class)
    @RequestMapping(value = "/payFinish", method = RequestMethod.POST)
    public Object payFinish(@RequestBody ReqOrderPayFinishDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        return Success.ok();
    }
}
