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
 * 取车信息表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_user_pick_up")
@ApiModel(value="RentUserPickUp对象", description="取车信息表")
public class RentUserPickUp extends Model<RentUserPickUp> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "派车单id")
    @TableId("dispatch_id")
    private Long dispatchId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "车id")
    @TableField("car_id")
    private Long carId;

    @ApiModelProperty(value = "取车时的车辆图片信息")
    @TableField("car_pics")
    private String carPics;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "派车司机")
    @TableField("driver_id")
    private Long driverId;

    @ApiModelProperty(value = "取车时间")
    @TableField("get_time")
    private Date getTime;

    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.dispatchId;
    }

}
