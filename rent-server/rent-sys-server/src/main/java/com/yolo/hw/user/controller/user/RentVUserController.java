package com.yolo.hw.user.controller.user;


import com.yolo.hw.user.dto.request.ReqAuthenticationDto;
import com.yolo.hw.user.dto.request.ReqRegisterDto;
import com.yolo.hw.user.dto.response.ResAuthenticationDto;
import com.yolo.hw.user.dto.response.ResUserLoginDto;
import com.yolo.hw.user.service.IRentVUserService;
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
 *  前端控制器
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Controller
@RequestMapping("/rentVUser")
public class RentVUserController {

    @Autowired
    private IRentVUserService userService;

    @ApiOperation(value = "用户身份认证", notes = "用户身份认证", response = ResUserLoginDto.class)
    @RequestMapping(value = "/user/authentication", method = RequestMethod.POST)
    public Object authentication(@RequestBody ReqAuthenticationDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        ResAuthenticationDto resDto = userService.authentication(reqDto);
        return resDto;
    }
}
