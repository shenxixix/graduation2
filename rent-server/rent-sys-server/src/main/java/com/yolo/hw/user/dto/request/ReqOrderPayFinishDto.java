package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ReqOrderPayFinishDto {

    @ApiModelProperty("订单ID")
    private Long orderId;
}
