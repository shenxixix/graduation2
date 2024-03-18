package com.yolo.hw.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResUserLoginDto {

    @ApiModelProperty(notes = "用户token")
    private String token;

    @ApiModelProperty("用户信息")
    private ResUserInfoDto user = new ResUserInfoDto();
}
