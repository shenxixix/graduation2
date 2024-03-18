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
 * 用户角色
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_v_user_role")
@ApiModel(value="RentVUserRole对象", description="用户角色")
public class RentVUserRole extends Model<RentVUserRole> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "功能ID")
    @TableId("key_id")
    private Long keyId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Long roleId;

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
