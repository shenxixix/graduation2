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
 * 模版配置表
 * </p>
 *
 * @author shenxi
 * @since 2024-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_contract_template")
@ApiModel(value="RentContractTemplate对象", description="模版配置表")
public class RentContractTemplate extends Model<RentContractTemplate> {

    private static final long serialVersionUID = 1L;

    @TableId("key_id")
    private Long keyId;

    @ApiModelProperty(value = "模板地址")
    @TableField("template_url")
    private String templateUrl;

    @TableField("opt_user_id")
    private Long optUserId;

    @TableField("update_timestamp")
    private Date updateTimestamp;

    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.keyId;
    }

}
