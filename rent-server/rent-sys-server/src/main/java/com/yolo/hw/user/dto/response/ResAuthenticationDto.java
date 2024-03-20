package com.yolo.hw.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ResAuthenticationDto {

    @ApiModelProperty(value = "认证字符串")
    private String authenticationStr;

}
