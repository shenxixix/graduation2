package com.yolo.hw.user.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @Author:yanxiao
 * @Des: 用户信息
 * @Date:2019/2/28
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    private String userName;
    private String password;
    private Integer userSex;
    private Date userExpireTime;
    private Integer userAttri;
    private String orgId;
    private String orgName;
    private String logoPath;
    private String userMobile;
    private Long userId;
    private String userEmail;
    private Integer userAccountStatus;
    private Integer manager;
    private Long dptId;
    private String dptName;
    private String userNickName;
    private String userPassword;
    private String userPath;
    Set<String> perms;
    Set<Integer> permList;
    private String token;
    private Integer changePasswordStatus;
    private Integer switchWarnFlag=0;
    private String code;
    private String notifyCars;
    private Long expireTime;
    private String zone;
    private Integer orgAuth = 0;
}
