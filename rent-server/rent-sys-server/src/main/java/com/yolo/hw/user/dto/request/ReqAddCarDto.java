package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@Validated
public class ReqAddCarDto {

    @ApiModelProperty("企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "车牌号")
    private String license;

    @ApiModelProperty(value = "车辆型号ID")
    private Long carModelId;

    @ApiModelProperty(value = "注册日期")
    private Date registerDate;

}
