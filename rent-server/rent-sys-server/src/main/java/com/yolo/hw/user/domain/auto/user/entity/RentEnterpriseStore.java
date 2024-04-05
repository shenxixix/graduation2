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
 * 企业下的门店
 * </p>
 *
 * @author shenxi
 * @since 2024-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_enterprise_store")
@ApiModel(value="RentEnterpriseStore对象", description="企业下的门店")
public class RentEnterpriseStore extends Model<RentEnterpriseStore> {

    private static final long serialVersionUID = 1L;

    @TableId("store_id")
    private Long storeId;

    @ApiModelProperty(value = "企业ID")
    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "站点名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "联系电话")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "所在地城市编码")
    @TableField("city_code")
    private String cityCode;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private Double latitude;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private Double longitude;

    @ApiModelProperty(value = "地址")
    @TableField("location")
    private String location;

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
        return this.storeId;
    }

}
