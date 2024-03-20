package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
public class ReqAuthenticationBackDto {

    @ApiModelProperty(value = "认证流号", required = true)
    private String flowId;

    @ApiModelProperty("0成功 其他失败")
    private Integer code;
}
