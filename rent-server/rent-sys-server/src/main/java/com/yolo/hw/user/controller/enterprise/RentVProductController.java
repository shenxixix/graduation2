package com.yolo.hw.user.controller.enterprise;


import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.dto.request.ReqAddProductDto;
import com.yolo.hw.user.service.IRentVProductService;
import com.yolo.hw.user.service.impl.RentVProductServiceImpl;
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
 * 企业服务平台产品信息表 前端控制器
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Controller
@RequestMapping("/rentVProduct")
public class RentVProductController {

    @Autowired
    private IRentVProductService productService;

    @ApiOperation(value = "新增产品", notes = "新增产品", response = Object.class)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody ReqAddProductDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        productService.addProduct(reqDto);
        return Success.ok();
    }

}
