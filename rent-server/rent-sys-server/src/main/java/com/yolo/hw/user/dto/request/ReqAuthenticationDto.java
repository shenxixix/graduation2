package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
public class ReqAuthenticationDto {

    @ApiModelProperty(value = "手机号", required = true)
    @NotEmpty(message = "手机号为空")
    private Long userId;

    @ApiModelProperty("身份证号")
    @NotEmpty(message = "身份证号为空")
    private String idCard;

    @ApiModelProperty(value = "用户名")
    @NotEmpty(message = "用户名为空")
    private String realName;
}
