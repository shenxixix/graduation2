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
 * 企业服务平台车辆类型表
 * </p>
 *
 * @author shenxi
 * @since 2024-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_car_type")
@ApiModel(value="RentCarType对象", description="企业服务平台车辆类型表")
public class RentCarType extends Model<RentCarType> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("key_id")
    private Long keyId;

    @ApiModelProperty(value = "车辆类型")
    @TableField("car_type_name")
    private String carTypeName;

    @ApiModelProperty(value = "描述")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "删除标志(0:正常,1:删除)")
    @TableField("delete_flag")
    private Integer deleteFlag;

    @ApiModelProperty(value = "操作人ID")
    @TableField("opt_user_id")
    private Long optUserId;

    @ApiModelProperty(value = "更新时间戳")
    @TableField("update_timestamp")
    private Date updateTimestamp;

    @ApiModelProperty(value = "插入时间戳")
    @TableField("insert_timestamp")
    private Date insertTimestamp;

    @ApiModelProperty(value = "排序")
    @TableField("sort_num")
    private Integer sortNum;


    @Override
    protected Serializable pkVal() {
        return this.keyId;
    }

}
