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
 * 企业服务平台产品信息表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_v_product")
@ApiModel(value="RentVProduct对象", description="企业服务平台产品信息表")
public class RentVProduct extends Model<RentVProduct> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("key_id")
    private Long keyId;

    @ApiModelProperty(value = "企业ID")
    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "车辆型号ID")
    @TableField("car_model_id")
    private Long carModelId;

    @ApiModelProperty(value = "日租费(单位:分)")
    @TableField("day_fee")
    private Integer dayFee;

    @ApiModelProperty(value = "产品图片")
    @TableField("pic")
    private String pic;

    @ApiModelProperty(value = "产品特色")
    @TableField("config_params")
    private String configParams;

    @ApiModelProperty(value = "上架状态(0:待处理,1:已上架,2:已下架)")
    @TableField("up_status")
    private Integer upStatus;

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


    @Override
    protected Serializable pkVal() {
        return this.keyId;
    }

}
