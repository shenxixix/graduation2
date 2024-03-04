package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReqPwdDto {
    @ApiModelProperty("手机号")
    private String userMobile;
    @ApiModelProperty("密码")
    private String password;
}
