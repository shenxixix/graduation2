package com.yolo.hw.user.controller.user;


import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.dto.request.ReqPickCarDto;
import com.yolo.hw.user.service.IRentUserPickUpService;
import com.yolo.hw.user.service.IRentVProductService;
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
 * 取车信息表 前端控制器
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Controller
@RequestMapping("/rentUserPickUp")
public class RentUserPickUpController {

    @Autowired
    private IRentUserPickUpService pickUpService;

    @ApiOperation(value = "取车", notes = "取车", response = Object.class)
    @RequestMapping(value = "/pickUpCar", method = RequestMethod.POST)
    public Object pickUpCar(@RequestBody ReqPickCarDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        pickUpService.pickUpCar(reqDto);
        return Success.ok();
    }
}
