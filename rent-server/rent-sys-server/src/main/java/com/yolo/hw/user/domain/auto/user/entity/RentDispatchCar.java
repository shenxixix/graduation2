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
 * 派车单
 * </p>
 *
 * @author shenxi
 * @since 2024-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_dispatch_car")
@ApiModel(value="RentDispatchCar对象", description="派车单")
public class RentDispatchCar extends Model<RentDispatchCar> {

    private static final long serialVersionUID = 1L;

    @TableId("dispatch_id")
    private Long dispatchId;

    @ApiModelProperty(value = "派车企业id")
    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "用车人ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "用车人名")
    @TableField("user_name")
    private String userName;

    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "租车开始时间")
    @TableField("start_time")
    private Date startTime;

    @ApiModelProperty(value = "租车结束时间")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty(value = "取车地址")
    @TableField("start_address")
    private String startAddress;

    @ApiModelProperty(value = "还车地址")
    @TableField("end_address")
    private String endAddress;

    @ApiModelProperty(value = "车辆ID")
    @TableField("car_id")
    private Long carId;

    @ApiModelProperty(value = "车牌号")
    @TableField("license")
    private String license;

    @ApiModelProperty(value = "状态0:待派车 1:已派车 2:已接车 3:已还车")
    @TableField("status")
    private Integer status;

    @TableField("update_timestamp")
    private Date updateTimestamp;

    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.dispatchId;
    }

}
