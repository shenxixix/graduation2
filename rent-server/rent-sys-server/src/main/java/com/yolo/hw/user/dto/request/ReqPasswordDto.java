package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReqPasswordDto {
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "密码")
    private String userPassword;
}
