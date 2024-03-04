package com.yolo.hw.user.domain.auto.enterprise.entity;

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
 * 企业站点表(用于取还车)
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_enterprise_station")
@ApiModel(value="RentEnterpriseStation对象", description="企业站点表(用于取还车)")
public class RentEnterpriseStation extends Model<RentEnterpriseStation> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业id")
    @TableId("station_id")
    private Long stationId;

    @ApiModelProperty(value = "企业名称")
    @TableField("station_name")
    private String stationName;

    @ApiModelProperty(value = "租赁企业id")
    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "联系电话")
    @TableField("phone_num")
    private String phoneNum;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private Double latitude;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private Double longitude;

    @ApiModelProperty(value = "位置信息")
    @TableField("location")
    private String location;

    @ApiModelProperty(value = "所属城市代码")
    @TableField("city_code")
    private Integer cityCode;

    @ApiModelProperty(value = "所属城市")
    @TableField("city_name")
    private String cityName;

    @ApiModelProperty(value = "门脸照片")
    @TableField("station_pics")
    private String stationPics;

    @ApiModelProperty(value = "删除状态，0：未删除，1：已删除")
    @TableField("del_flag")
    private Integer delFlag;

    @ApiModelProperty(value = "关闭操作人ID")
    @TableField("opt_user_id")
    private Long optUserId;

    @ApiModelProperty(value = "操作时间")
    @TableField("opt_timestamp")
    private Date optTimestamp;

    @TableField("update_timestamp")
    private Date updateTimestamp;

    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.stationId;
    }

}
