package com.yolo.hw.user.controller.login;


import com.yolo.hw.user.controller.AbstractController;
import com.yolo.hw.user.dto.request.ReqLoginDto;
import com.yolo.hw.user.dto.response.RespUserLoginDto;
import com.yolo.hw.user.service.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "用户登陆", tags = "用户登陆")
@RestController
@Log4j2
public class LogController extends AbstractController {
    @Autowired
    private ILoginService loginService;
    @ApiOperation(value = "平台|商家登录", notes = "平台|商家登录", response = RespUserLoginDto.class)
    @RequestMapping(value = "/manager/login", method = RequestMethod.POST)
    public Object login(@RequestBody ReqLoginDto loginOrRegDto, HttpServletRequest request, HttpServletResponse response) {
        RespUserLoginDto resLoginDto = loginService.loginOrReg(loginOrRegDto);
        Long userId = getUserId(request);
        return resLoginDto;
    }

    @ApiOperation(value = "用户登录", notes = "登录注册", response = RespUserLoginDto.class)
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Object loginOrReg(@RequestBody ReqLoginDto loginOrRegDto, HttpServletRequest request, HttpServletResponse response) {
        RespUserLoginDto resLoginDto = loginService.loginOrReg(loginOrRegDto);
        return resLoginDto;
    }
}
