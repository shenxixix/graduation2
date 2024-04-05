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
 * 企业驾驶员
 * </p>
 *
 * @author shenxi
 * @since 2024-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_enterprise_driver")
@ApiModel(value="RentEnterpriseDriver对象", description="企业驾驶员")
public class RentEnterpriseDriver extends Model<RentEnterpriseDriver> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "司机ID")
    @TableId("driver_id")
    private Long driverId;

    @ApiModelProperty(value = "企业ID")
    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "联系电话")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "状态0空闲 1派车中")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "删除状态 0 正常 1 删除")
    @TableField("is_del")
    private Integer isDel;

    @ApiModelProperty(value = "插入时间戳")
    @TableField("insert_timestamp")
    private Date insertTimestamp;

    @ApiModelProperty(value = "更新时间戳")
    @TableField("update_timestamp")
    private Date updateTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.driverId;
    }

}
