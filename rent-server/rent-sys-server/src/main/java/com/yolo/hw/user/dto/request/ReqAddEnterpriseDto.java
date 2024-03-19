package com.yolo.hw.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ReqAddEnterpriseDto {

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty(value = "企业名称")
    private String epName;

    @ApiModelProperty(value = "社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "企业类型1:合资企业 2:独资企业 3:国有企业 4:私营企业 5:股份制企业")
    private Integer epType;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系电话")
    private String contactsPhone;

    @ApiModelProperty(value = "营业时间")
    private String openingTime;

    @ApiModelProperty(value = "所在地区")
    private Integer areaCode;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "企业简介")
    private String introduce;

    @ApiModelProperty(value = "营业执照照片,多张逗号隔开")
    private String epPics;

    @ApiModelProperty(value = "提现银行卡")
    private String withdrawBankNo;

    @ApiModelProperty(value = "提现银行名称")
    private String withdrawBankName;

    @ApiModelProperty(value = "提现用户名")
    private String withdrawBankUser;

    @ApiModelProperty(value = "提现手机号")
    private String withdrawBankMobile;
}
