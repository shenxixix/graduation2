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
 * 企业提现规则信息表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_withdraw_rule")
@ApiModel(value="RentWithdrawRule对象", description="企业提现规则信息表")
public class RentWithdrawRule extends Model<RentWithdrawRule> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业ID")
    @TableId("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "提现比例")
    @TableField("ratio")
    private Long ratio;

    @TableField("operate_user")
    private Long operateUser;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.enterpriseId;
    }

}
