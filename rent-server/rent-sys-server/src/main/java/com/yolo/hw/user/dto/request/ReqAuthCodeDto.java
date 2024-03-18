package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Validated
public class ReqAuthCodeDto {

    @ApiModelProperty("类型1:注册 2:平台修改密码 3:租车用户修改密码")
    @NotNull(message = "类型为空")
    private Integer type;

    @ApiModelProperty("手机号")
    @NotEmpty(message = "手机号为空")
    private String mobile;
}
