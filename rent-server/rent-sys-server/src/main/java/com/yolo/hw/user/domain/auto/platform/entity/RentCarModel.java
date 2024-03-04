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
 * 企业服务平台车辆型号表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_car_model")
@ApiModel(value="RentCarModel对象", description="企业服务平台车辆型号表")
public class RentCarModel extends Model<RentCarModel> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("key_id")
    private Long keyId;

    @ApiModelProperty(value = "车辆型号")
    @TableField("car_model_name")
    private String carModelName;

    @ApiModelProperty(value = "车辆品牌ID")
    @TableField("car_brand_id")
    private Long carBrandId;

    @ApiModelProperty(value = "车辆类型ID")
    @TableField("car_type_id")
    private Long carTypeId;

    @ApiModelProperty(value = "车辆参考样图")
    @TableField("car_pics")
    private String carPics;

    @ApiModelProperty(value = "基本参数")
    @TableField("car_params")
    private String carParams;

    @ApiModelProperty(value = "配置参数")
    @TableField("config_params")
    private String configParams;

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

    @ApiModelProperty(value = "能量类型")
    @TableField("energy_type")
    private Integer energyType;


    @Override
    protected Serializable pkVal() {
        return this.keyId;
    }

}
