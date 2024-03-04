package com.yolo.hw.user.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RespUserLoginDto {
    @ApiModelProperty(notes = "用户token")
    private String token;
    @ApiModelProperty(notes = "用户信息")
    private User userInfo = new User();
    @ApiModelProperty(notes = "权限信息")
    private AuthDto auth = new AuthDto();
    @ApiModelProperty(notes = "状态值 1 正常 0 账号密码错误 2 该用户已经停用 3 手机号格式错误 4 您输入的验证码不正确，请重新输入 5 帐号已经被禁用 6 已连续5次密码验证错误，请5分钟后再登录 7 账号不存在")
    private Integer status = 1;
    @ApiModelProperty(notes = "错误次数")
    private Integer errNum = 0;
    @Data
    @ApiModel("用户信息")
    public static class User {
        @ApiModelProperty(notes = "用户ID")
        private Long userId;
        @ApiModelProperty(notes = "手机号")
        private String userMobile;
        @ApiModelProperty(notes = "男1;女2")
        private Integer userSex;
        @ApiModelProperty(notes = "用户名昵称")
        private String userNickName;
        @ApiModelProperty(notes = "头像")
        private String headImg;
        @ApiModelProperty(notes = "是否初始密码 1 是 2 不是")
        private Integer hasPwd = 2;
        @ApiModelProperty(value = "企业名称")
        private String companyName;

    }

    @Data
    @ApiModel("权限信息")
    public static class AuthDto {
        @ApiModelProperty(notes = "菜单列表")
        private Set<Long> menuIds = new HashSet<>();
        @ApiModelProperty(notes = "资源列表")
        private Set<Long> resIds = new HashSet<>();
        @ApiModelProperty(notes = "Api列表")
        private Set<Long> apiIds = new HashSet<>();
    }
}
