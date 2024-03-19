package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ReqPickCarDto {

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty("派车单ID")
    private Long dispatchId;

    @ApiModelProperty("车身检测照片")
    private String pics;

    @ApiModelProperty("司机id")
    private Long driverId;

    @ApiModelProperty("备注")
    private String remark;
}
