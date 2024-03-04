package com.yolo.hw.user.domain.auto.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 租赁订单表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_user_order")
@ApiModel(value="RentUserOrder对象", description="租赁订单表")
public class RentUserOrder extends Model<RentUserOrder> {

    private static final long serialVersionUID = 1L;

    @TableId("order_id")
    private Long orderId;

    @ApiModelProperty(value = "法人身份证")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "法人身份证")
    @TableField("user_id_card")
    private String userIdCard;

    @ApiModelProperty(value = "法人姓名")
    @TableField("user_real_name")
    private String userRealName;

    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "产品id")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty(value = "产品图片")
    @TableField("product_pic")
    private String productPic;

    @ApiModelProperty(value = "开始时间")
    @TableField("start_time")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty(value = "取车地址")
    @TableField("start_address")
    private String startAddress;

    @ApiModelProperty(value = "取车地址纬度")
    @TableField("start_latitude")
    private Double startLatitude;

    @ApiModelProperty(value = "取车地址经度")
    @TableField("start_longitude")
    private Double startLongitude;

    @ApiModelProperty(value = "还车地址")
    @TableField("end_address")
    private String endAddress;

    @ApiModelProperty(value = "还车地址纬度")
    @TableField("end_latitude")
    private Double endLatitude;

    @ApiModelProperty(value = "还车地址经度")
    @TableField("end_longitude")
    private Double endLongitude;

    @ApiModelProperty(value = "费用(分)")
    @TableField("fee")
    private Long fee;

    @TableField("car_brand_id")
    private Long carBrandId;

    @ApiModelProperty(value = "车品牌")
    @TableField("car_brand")
    private String carBrand;

    @TableField("car_model_id")
    private Long carModelId;

    @ApiModelProperty(value = "车型")
    @TableField("car_model")
    private String carModel;

    @TableField("car_type_id")
    private Long carTypeId;

    @ApiModelProperty(value = "车类型")
    @TableField("car_type")
    private String carType;

    @ApiModelProperty(value = "产品详情")
    @TableField("product_detail")
    private String productDetail;

    @ApiModelProperty(value = "支付方式1:支付宝 2:微信 3:银行")
    @TableField("pay_way")
    private Integer payWay;

    @ApiModelProperty(value = "状态0待支付 1:已支付 2:已取消")
    @TableField("status")
    private Integer status;

    @TableField("update_timestamp")
    private Date updateTimestamp;

    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
