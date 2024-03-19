package com.yolo.hw.user.service;

import com.yolo.hw.user.domain.auto.platform.entity.RentEnterpriseCar;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yolo.hw.user.dto.request.ReqAddCarDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 企业服务平台车辆信息表 服务类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
public interface IRentEnterpriseCarService extends IService<RentEnterpriseCar> {

    void addCar(ReqAddCarDto reqDto);
}
