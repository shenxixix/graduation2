package com.yolo.hw.user.service;


import com.yolo.hw.user.dto.request.*;
import com.yolo.hw.user.dto.response.ResUserLoginDto;
import com.yolo.hw.user.dto.response.RespManagerLoginDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface ILoginService {

    RespManagerLoginDto managerLogin(ReqManagerLoginDto reqDto);

    ResUserLoginDto userLogin(ReqLoginDto reqDto);

    ResUserLoginDto register(ReqRegisterDto reqDto);

    void changePassword(ReqPasswordDto reqDto);

    void authCode(ReqAuthCodeDto reqDto);
}
