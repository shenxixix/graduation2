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
 * 用户评价表
 * </p>
 *
 * @author shenxi
 * @since 2024-04-05
 */
@Data
public class ReqUserEvaluateDto {

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "企业id")
    private Long enterpriseId;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "总分")
    private Integer totalScore;

    @ApiModelProperty(value = "时效评分")
    private Integer timeScore;

    @ApiModelProperty(value = "技能评分")
    private Integer skillScore;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "评价图片多个逗号隔开")
    private String photos;

}
