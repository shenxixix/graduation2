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
 * 企业钱包历史表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_enterprise_wallet_log")
@ApiModel(value="RentEnterpriseWalletLog对象", description="企业钱包历史表")
public class RentEnterpriseWalletLog extends Model<RentEnterpriseWalletLog> {

    private static final long serialVersionUID = 1L;

    @TableId("key_id")
    private Long keyId;

    @ApiModelProperty(value = "企业id")
    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "类型1:订单费用 2:提现费用")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "金额(分),大于0转入小于0转出")
    @TableField("money")
    private Integer money;

    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "业务id,type=1订单id,type=2提现申请id")
    @TableField("buss_id")
    private Long bussId;

    @TableField("update_timestamp")
    private Date updateTimestamp;

    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.keyId;
    }

}
