package com.yolo.hw.user.controller.login;


import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.config.exception.ErrorEnum;
import com.yolo.hw.user.controller.AbstractController;
import com.yolo.hw.user.dto.request.*;
import com.yolo.hw.user.dto.response.ResUserLoginDto;
import com.yolo.hw.user.dto.response.RespManagerLoginDto;
import com.yolo.hw.user.service.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "用户登陆", tags = "用户登陆")
@RestController
@Log4j2
public class LogController extends AbstractController {
    @Autowired
    private ILoginService loginService;

    @ApiOperation(value = "平台|商家登录", notes = "平台|商家登录", response = RespManagerLoginDto.class)
    @RequestMapping(value = "/manager/login", method = RequestMethod.POST)
    public Object managerLogin(@RequestBody ReqManagerLoginDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        RespManagerLoginDto resLoginDto = loginService.managerLogin(reqDto);
        return resLoginDto;
    }

    @ApiOperation(value = "用户登录", notes = "登录注册", response = ResUserLoginDto.class)
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Object userLogin(@RequestBody ReqLoginDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        ResUserLoginDto resLoginDto = loginService.userLogin(reqDto);
        return resLoginDto;
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", response = ResUserLoginDto.class)
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public Object register(@RequestBody ReqRegisterDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        ResUserLoginDto resLoginDto = loginService.register(reqDto);
        return resLoginDto;
    }

    @ApiOperation(value = "用户修改密码", notes = "用户修改密码", response = Object.class)
    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
    public Object changePassword(@RequestBody ReqPasswordDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        Long userId = getUserId(request);
        Assert.isTrue(userId != null, ErrorEnum.AUTH_ERR.getInfo());
        reqDto.setUserId(userId);
        reqDto.setUserType(getUserType(request));
        loginService.changePassword(reqDto);
        return Success.ok();
    }

    @ApiOperation(value = "发送验证码", notes = "发送验证码", response = Object.class)
    @RequestMapping(value = "/user/authCode", method = RequestMethod.POST)
    public Object authCode(@RequestBody ReqAuthCodeDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        loginService.authCode(reqDto);
        return Success.ok();
    }

    @ApiOperation(value = "用户退出", notes = "用户退出", response = String.class)
    @PostMapping(value = "/logout")
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        Long userId = this.getUserId(request);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                c.setMaxAge(0);
                c.setPath("/");
                c.setValue("");
                response.addCookie(c);
            }
        }
        return Success.ok();
    }
}
