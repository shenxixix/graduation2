package com.yolo.hw.user.controller.enterprise;


import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.dto.request.ReqAddCarDto;
import com.yolo.hw.user.service.IRentEnterpriseCarService;
import com.yolo.hw.user.service.impl.RentEnterpriseCarServiceImpl;
import com.yolo.hw.user.service.impl.RentEnterpriseDriverServiceImpl;
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
 * 企业服务平台车辆信息表 前端控制器
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Controller
@RequestMapping("/rentEnterpriseCar")
public class RentEnterpriseCarController {

    @Autowired
    private IRentEnterpriseCarService carService;

    @ApiOperation(value = "新增车辆", notes = "新增车辆", response = Object.class)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody ReqAddCarDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        carService.addCar(reqDto);
        return Success.ok();
    }
}
