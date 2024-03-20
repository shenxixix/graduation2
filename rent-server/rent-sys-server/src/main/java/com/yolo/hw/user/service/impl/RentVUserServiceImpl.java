package com.yolo.hw.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentVUser;
import com.yolo.hw.user.domain.auto.platform.mapper.RentVUserMapper;
import com.yolo.hw.user.dto.request.ReqAddProductDto;
import com.yolo.hw.user.dto.request.ReqAuthenticationBackDto;
import com.yolo.hw.user.dto.request.ReqAuthenticationDto;
import com.yolo.hw.user.dto.response.Authentication;
import com.yolo.hw.user.dto.response.ResAuthenticationDto;
import com.yolo.hw.user.service.IRentVUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Service
@Slf4j
public class RentVUserServiceImpl extends ServiceImpl<RentVUserMapper, RentVUser> implements IRentVUserService {

    @Autowired
    private RentVUserMapper rentVUserMapper;

    /**
     * 用户认证
     * @param reqDto
     * @return
     */
    @Override
    public ResAuthenticationDto authentication(ReqAuthenticationDto reqDto) {
        ResAuthenticationDto resDto = new ResAuthenticationDto();
        /** 参数校验 **/
        Assert.isTrue(validateParams(reqDto).getCode() == 0, validateParams(reqDto).getMsg());
        Long userId = reqDto.getUserId();
        String idCard = reqDto.getIdCard();
        String realName = reqDto.getRealName();
        RentVUser user = rentVUserMapper.selectById(userId);
        Assert.isTrue(user != null, "非法访问");
        // 调用第三方身份认证接口
        Authentication authentication = createAuthentication(idCard, realName);
        user.setAuthenticationFlowId(authentication.getFlowId());
        user.setIdCard(idCard);
        user.setUserRealName(realName);
        user.setUpdateTimestamp(new Date());
        user.updateById();
        // 返回认证支付串
        resDto.setAuthenticationStr(authentication.getAuthenticationUrl());
        return resDto;
    }

    /**
     * 认证结果回掉
     * @param reqDto
     */
    @Override
    public void authenticationCallback(ReqAuthenticationBackDto reqDto) {
        // 认证成功
        if(reqDto.getCode().intValue() == 0) {
            rentVUserMapper.update(null, Wrappers.<RentVUser>update()
                    .lambda().set(RentVUser::getAuthenticationStatus, 1)
                    .set(RentVUser::getAuthenticationFlowId, reqDto.getFlowId()));
        } else {
            log.info("用户身份认证失败:{}", reqDto);
        }
    }

    private Success validateParams(ReqAuthenticationDto reqDto) {
        return Success.ok();
    }

    private Authentication createAuthentication(String idCard, String realName) {
        return new Authentication();
    }
}
