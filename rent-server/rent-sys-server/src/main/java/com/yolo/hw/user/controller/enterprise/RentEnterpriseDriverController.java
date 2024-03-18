package com.yolo.hw.user.controller.enterprise;


import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.dto.request.ReqAddCarDto;
import com.yolo.hw.user.service.IRentEnterpriseDriverService;
import com.yolo.hw.user.service.impl.RentEnterpriseCarServiceImpl;
import com.yolo.hw.user.service.impl.RentEnterpriseDriverServiceImpl;
import com.yolo.hw.user.service.impl.RentVProductServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 企业服务平台司机信息表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Controller
@RequestMapping("/rentEnterpriseDriver")
public class RentEnterpriseDriverController {

    @Autowired
    private IRentEnterpriseDriverService driverService;

    @ApiOperation(value = "新增司机", notes = "新增司机", response = Object.class)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody ReqAddCarDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        return Success.ok();
    }
}
