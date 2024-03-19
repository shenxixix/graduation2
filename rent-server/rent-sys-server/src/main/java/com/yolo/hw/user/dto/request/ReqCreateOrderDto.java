package com.yolo.hw.user.dto.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yolo.hw.user.domain.auto.user.entity.RentUserOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@Validated
public class ReqCreateOrderDto {

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(hidden = true, value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "取车地址")
    private String startAddress;

    @ApiModelProperty(value = "取车地址纬度")
    private Double startLatitude;

    @ApiModelProperty(value = "取车地址经度")
    private Double startLongitude;

    @ApiModelProperty(value = "还车地址")
    private String endAddress;

    @ApiModelProperty(value = "还车地址纬度")
    private Double endLatitude;

    @ApiModelProperty(value = "还车地址经度")
    private Double endLongitude;

    @ApiModelProperty(value = "费用(分)")
    private Long fee;

    @ApiModelProperty(value = "支付方式1:支付宝 2:微信 3:银行")
    @TableField("pay_way")
    private Integer payWay;
}
