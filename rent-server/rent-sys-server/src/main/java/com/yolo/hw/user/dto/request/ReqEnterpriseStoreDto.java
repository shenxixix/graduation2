package com.yolo.hw.user.dto.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 企业下的门店
 * </p>
 *
 * @author shenxi
 * @since 2024-04-05
 */
@Data
public class ReqEnterpriseStoreDto {

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "站点名")
    private String name;

    @ApiModelProperty(value = "联系人")
    private String contractUser;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "所在地城市编码")
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

}
