package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ReqDispatchCarDto {

    @ApiModelProperty("派车单ID")
    private Long dispatchId;

    @ApiModelProperty("派车司机")
    private Long driverId;

    @ApiModelProperty("派车车辆")
    private Long carId;
}
