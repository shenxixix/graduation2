package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ReqAddProductDto {

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "车辆型号ID")
    private Long carModelId;

    @ApiModelProperty(value = "日租费(单位:分)")
    private Integer dayFee;

    @ApiModelProperty(value = "产品图片")
    private String pic;

    @ApiModelProperty(value = "产品特色")
    private String configParams;

    @ApiModelProperty(value = "备注")
    private String remark;
}
