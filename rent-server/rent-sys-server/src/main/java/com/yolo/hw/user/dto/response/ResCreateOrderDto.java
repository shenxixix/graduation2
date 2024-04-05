package com.yolo.hw.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ResCreateOrderDto {

    @ApiModelProperty("支付串")
    private String payStr;

    @ApiModelProperty("生成合同文件")
    private String contractUrl;
}
