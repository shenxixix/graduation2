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
 * 企业信息表
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_v_enterprise")
@ApiModel(value="RentVEnterprise对象", description="企业信息表")
public class RentVEnterprise extends Model<RentVEnterprise> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("key_id")
    private Long keyId;

    @ApiModelProperty(value = "企业名称")
    @TableField("ep_name")
    private String epName;

    @ApiModelProperty(value = "社会信用代码")
    @TableField("credit_code")
    private String creditCode;

    @ApiModelProperty(value = "企业类型")
    @TableField("ep_type")
    private Integer epType;

    @ApiModelProperty(value = "联系人")
    @TableField("contacts")
    private String contacts;

    @ApiModelProperty(value = "联系电话")
    @TableField("contacts_phone")
    private String contactsPhone;

    @ApiModelProperty(value = "营业时间")
    @TableField("opening_time")
    private String openingTime;

    @ApiModelProperty(value = "所在地区")
    @TableField("area_code")
    private Integer areaCode;

    @ApiModelProperty(value = "详细地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "企业简介")
    @TableField("introduce")
    private String introduce;

    @ApiModelProperty(value = "营业执照照片")
    @TableField("ep_pics")
    private String epPics;

    @ApiModelProperty(value = "承诺书")
    @TableField("promise_pdf")
    private String promisePdf;

    @ApiModelProperty(value = "状态(0:正常,1:停用)")
    @TableField("ep_status")
    private Integer epStatus;

    @ApiModelProperty(value = "删除标志(0:正常,1:删除)")
    @TableField("delete_flag")
    private Integer deleteFlag;

    @ApiModelProperty(value = "总体得分")
    @TableField("total_star")
    private Double totalStar;

    @ApiModelProperty(value = "评价条数")
    @TableField("evaluate_num")
    private Integer evaluateNum;

    @ApiModelProperty(value = "添加人ID")
    @TableField("opt_user_id")
    private Long optUserId;

    @ApiModelProperty(value = "添加单位ID")
    @TableField("opt_org_id")
    private String optOrgId;

    @ApiModelProperty(value = "服务开始时间")
    @TableField("service_start_time")
    private Date serviceStartTime;

    @ApiModelProperty(value = "服务结束时间")
    @TableField("service_end_time")
    private Date serviceEndTime;

    @ApiModelProperty(value = "管理员用户ID")
    @TableField("admin_user_id")
    private Long adminUserId;

    @ApiModelProperty(value = "提现银行卡")
    @TableField("withdraw_bank_no")
    private String withdrawBankNo;

    @ApiModelProperty(value = "提现银行名称")
    @TableField("withdraw_bank_name")
    private String withdrawBankName;

    @ApiModelProperty(value = "提现用户名")
    @TableField("withdraw_bank_user")
    private String withdrawBankUser;

    @ApiModelProperty(value = "提现手机号")
    @TableField("withdraw_bank_mobile")
    private String withdrawBankMobile;

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
