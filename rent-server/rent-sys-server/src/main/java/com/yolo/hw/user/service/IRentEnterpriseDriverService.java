package com.yolo.hw.user.service;

import com.yolo.hw.user.domain.auto.platform.entity.RentEnterpriseDriver;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yolo.hw.user.dto.request.ReqAddCarDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 企业驾驶员 服务类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
public interface IRentEnterpriseDriverService extends IService<RentEnterpriseDriver> {

    void add(ReqAddCarDto reqDto);
}
