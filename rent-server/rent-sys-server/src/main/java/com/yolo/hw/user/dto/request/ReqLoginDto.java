package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
public class ReqLoginDto {

    @ApiModelProperty(value = "手机号", required = true)
    @NotEmpty(message = "手机号为空")
    private String userMobile;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码为空")
    private String password;

}
