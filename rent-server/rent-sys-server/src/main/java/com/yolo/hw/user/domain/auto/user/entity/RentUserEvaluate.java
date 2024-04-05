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
 * 用户评价表
 * </p>
 *
 * @author shenxi
 * @since 2024-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_user_evaluate")
@ApiModel(value="RentUserEvaluate对象", description="用户评价表")
public class RentUserEvaluate extends Model<RentUserEvaluate> {

    private static final long serialVersionUID = 1L;

    @TableId("key_id")
    private Long keyId;

    @ApiModelProperty(value = "订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "企业id")
    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "产品id")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty(value = "总分")
    @TableField("total_score")
    private Integer totalScore;

    @ApiModelProperty(value = "时效评分")
    @TableField("time_score")
    private Integer timeScore;

    @ApiModelProperty(value = "模板地址")
    @TableField("skill_score")
    private Integer skillScore;

    @ApiModelProperty(value = "技能评分")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "评价图片多个逗号隔开")
    @TableField("photos")
    private String photos;

    @TableField("update_timestamp")
    private Date updateTimestamp;

    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.keyId;
    }

}
