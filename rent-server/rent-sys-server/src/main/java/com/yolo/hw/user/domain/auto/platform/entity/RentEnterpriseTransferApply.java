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
 * 企业提现申请表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_enterprise_transfer_apply")
@ApiModel(value="RentEnterpriseTransferApply对象", description="企业提现申请表")
public class RentEnterpriseTransferApply extends Model<RentEnterpriseTransferApply> {

    private static final long serialVersionUID = 1L;

    @TableId("key_id")
    private Long keyId;

    @ApiModelProperty(value = "申请人用户id")
    @TableField("apply_user_id")
    private Long applyUserId;

    @ApiModelProperty(value = "企业id")
    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "金额(分), 包含了手续费的")
    @TableField("money")
    private Integer money;

    @ApiModelProperty(value = "提现账号")
    @TableField("transfer_account")
    private String transferAccount;

    @ApiModelProperty(value = "收款人")
    @TableField("transfer_user_name")
    private String transferUserName;

    @ApiModelProperty(value = "手续费")
    @TableField("commission")
    private Integer commission;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "转账凭证")
    @TableField("transfer_pic")
    private String transferPic;

    @ApiModelProperty(value = "处理人id")
    @TableField("handle_user_id")
    private Long handleUserId;

    @ApiModelProperty(value = "审核状态(1:待转账,2:成功,3:失败)")
    @TableField("apply_status")
    private Integer applyStatus;

    @ApiModelProperty(value = "处理时间")
    @TableField("handle_timestamp")
    private Date handleTimestamp;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_timestamp")
    private Date updateTimestamp;

    @ApiModelProperty(value = "添加时间")
    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.keyId;
    }

}
