package com.yolo.hw.user.domain.auto.platform.entity;

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
 * 企业服务平台车辆信息表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_enterprise_car")
@ApiModel(value="RentEnterpriseCar对象", description="企业服务平台车辆信息表")
public class RentEnterpriseCar extends Model<RentEnterpriseCar> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("car_id")
    private Long carId;

    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "车牌号")
    @TableField("license")
    private String license;

    @ApiModelProperty(value = "车辆型号ID")
    @TableField("car_model_id")
    private Long carModelId;

    @ApiModelProperty(value = "注册日期")
    @TableField("register_date")
    private Date registerDate;

    @ApiModelProperty(value = "删除标志(0:正常,1:删除)")
    @TableField("delete_flag")
    private Integer deleteFlag;

    @ApiModelProperty(value = "操作人ID")
    @TableField("opt_user_id")
    private Long optUserId;

    @ApiModelProperty(value = "当前租赁状态0:未租 1:已租")
    @TableField("rent_status")
    private Integer rentStatus;

    @ApiModelProperty(value = "更新时间戳")
    @TableField("update_timestamp")
    private Date updateTimestamp;

    @ApiModelProperty(value = "插入时间戳")
    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.carId;
    }

}
