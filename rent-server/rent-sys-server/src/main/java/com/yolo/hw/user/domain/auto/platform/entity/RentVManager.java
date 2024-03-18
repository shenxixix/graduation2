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
 * 
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_v_manager")
@ApiModel(value="RentVManager对象", description="")
public class RentVManager extends Model<RentVManager> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId("user_id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "密码")
    @TableField("user_password")
    private String userPassword;

    @ApiModelProperty(value = "手机号")
    @TableField("user_mobile")
    private String userMobile;

    @ApiModelProperty(value = "性别")
    @TableField("user_sex")
    private Integer userSex;

    @ApiModelProperty(value = "车企id")
    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "1:平台管理员; 2:车企管理员")
    @TableField("user_role")
    private Integer userRole;

    @ApiModelProperty(value = "1:启用;0:停用")
    @TableField("user_account_status")
    private Integer userAccountStatus;

    @ApiModelProperty(value = "创建者id")
    @TableField("creator_id")
    private Long creatorId;

    @ApiModelProperty(value = "更新时间戳")
    @TableField("update_timestamp")
    private Date updateTimestamp;

    @ApiModelProperty(value = "插入时间戳")
    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
