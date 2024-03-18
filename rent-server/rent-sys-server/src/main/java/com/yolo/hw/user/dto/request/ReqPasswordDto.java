package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Validated
public class ReqPasswordDto {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "验证码")
    @NotEmpty(message = "验证码为空")
    private String code;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码为空")
    private String userPassword;

    @ApiModelProperty(hidden = true, value = "用户类型1:平台|企业 2:租车用户")
    private Integer userType;
}
