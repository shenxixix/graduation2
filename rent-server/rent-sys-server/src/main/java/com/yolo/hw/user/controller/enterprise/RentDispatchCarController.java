package com.yolo.hw.user.controller.enterprise;


import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.controller.AbstractController;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.dto.request.ReqDispatchCarDto;
import com.yolo.hw.user.service.IRentDispatchCarService;
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
 * 派车单 前端控制器
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Controller
@RequestMapping("/rentDispatchCar")
public class RentDispatchCarController {

    @Autowired
    private IRentDispatchCarService dispatchCarService;

    @ApiOperation(value = "派车", notes = "派车", response = Object.class)
    @RequestMapping(value = "/dispatchCar", method = RequestMethod.POST)
    public Object dispatchCar(@RequestBody ReqDispatchCarDto reqDto, HttpServletRequest request, HttpServletResponse response) {

        return Success.ok();
    }
}
