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
 * 钱包表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_enterprise_wallet")
@ApiModel(value="RentEnterpriseWallet对象", description="钱包表")
public class RentEnterpriseWallet extends Model<RentEnterpriseWallet> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业id")
    @TableId("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "金额(分)")
    @TableField("money")
    private Integer money;

    @TableField("update_timestamp")
    private Date updateTimestamp;

    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.enterpriseId;
    }

}
