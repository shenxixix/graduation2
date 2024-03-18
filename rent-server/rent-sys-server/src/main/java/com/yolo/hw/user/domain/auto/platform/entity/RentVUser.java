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
@TableName("rent_v_user")
@ApiModel(value="RentVUser对象", description="")
public class RentVUser extends Model<RentVUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
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

    @ApiModelProperty(value = "男1;女2")
    @TableField("user_sex")
    private Integer userSex;

    @TableField("user_nick_name")
    private String userNickName;

    @TableField("user_path")
    private String userPath;

    @TableField("birthday")
    private Date birthday;

    @TableField("user_real_name")
    private String userRealName;

    @TableField("id_card")
    private String idCard;

    @ApiModelProperty(value = "1:启用;0:停用")
    @TableField("user_account_status")
    private Integer userAccountStatus;

    @TableField("update_timestamp")
    private Date updateTimestamp;

    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
