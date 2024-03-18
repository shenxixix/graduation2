package com.yolo.hw.user.controller.platform;


import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.dto.request.ReqAddEnterpriseDto;
import com.yolo.hw.user.dto.request.ReqLoginDto;
import com.yolo.hw.user.dto.response.ResUserLoginDto;
import com.yolo.hw.user.service.IRentVEnterpriseService;
import com.yolo.hw.user.service.impl.RentVEnterpriseServiceImpl;
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
 * 企业信息表 前端控制器
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Controller
@RequestMapping("/rentVEnterprise")
public class RentVEnterpriseController {

    @Autowired
    private IRentVEnterpriseService enterpriseService;

    @ApiOperation(value = "新增企业", notes = "新增企业", response = Object.class)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody ReqAddEnterpriseDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        return Success.ok();
    }

}
