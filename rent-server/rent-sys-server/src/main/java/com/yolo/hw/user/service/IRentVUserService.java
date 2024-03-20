package com.yolo.hw.user.service;

import com.yolo.hw.user.domain.auto.platform.entity.RentVUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yolo.hw.user.dto.request.ReqAuthenticationBackDto;
import com.yolo.hw.user.dto.request.ReqAuthenticationDto;
import com.yolo.hw.user.dto.response.ResAuthenticationDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
public interface IRentVUserService extends IService<RentVUser> {

    ResAuthenticationDto authentication(ReqAuthenticationDto reqDto);

    void authenticationCallback(ReqAuthenticationBackDto reqDto);
}
