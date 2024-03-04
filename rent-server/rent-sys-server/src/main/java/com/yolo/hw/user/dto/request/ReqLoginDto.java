package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReqLoginDto {
    @ApiModelProperty(value = "手机号", required = true)
    private String userMobile;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("验证码登陆")
    private String code;
    @ApiModelProperty(notes = "推送push")
    private String pushToken;
    @ApiModelProperty(value = "手机型号")
    private String mobileType;
    @ApiModelProperty(value = "手机系统版本")
    private String mobileSystemVersion;
    @ApiModelProperty(value = "APP软件版本")
    private String appVersion;

}
