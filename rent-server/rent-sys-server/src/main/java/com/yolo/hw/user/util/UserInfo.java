package com.yolo.hw.user.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @Des: 用户信息
 * @Date:2019/2/28
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

    private String userMobile;

    private Long userId;

    private Long expireTime;

    @ApiModelProperty("用户类型1:平台|企业 2:租车用户")
    private Integer userType;
}
