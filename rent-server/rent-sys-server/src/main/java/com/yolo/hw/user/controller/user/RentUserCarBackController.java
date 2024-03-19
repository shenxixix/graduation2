package com.yolo.hw.user.controller.user;


import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.dto.request.ReqBackCarDto;
import com.yolo.hw.user.service.IRentUserCarBackService;
import com.yolo.hw.user.service.IRentUserOrderService;
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
 * 还车信息表 前端控制器
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Controller
@RequestMapping("/rentUserCarBack")
public class RentUserCarBackController {

    @Autowired
    private IRentUserCarBackService carBackService;

    @ApiOperation(value = "还车", notes = "还车", response = Object.class)
    @RequestMapping(value = "/backCar", method = RequestMethod.POST)
    public Object backCar(@RequestBody ReqBackCarDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        carBackService.backCar(reqDto);
        return Success.ok();
    }
}
