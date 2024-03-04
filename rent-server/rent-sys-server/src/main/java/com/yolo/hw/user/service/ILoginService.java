package com.yolo.hw.user.service;


import com.yolo.hw.user.dto.request.ReqLoginDto;
import com.yolo.hw.user.dto.response.RespUserLoginDto;

public interface ILoginService {
    RespUserLoginDto loginOrReg(ReqLoginDto reqLoginDto);
}
