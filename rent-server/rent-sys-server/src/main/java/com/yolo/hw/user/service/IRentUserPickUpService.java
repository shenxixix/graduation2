package com.yolo.hw.user.service;

import com.yolo.hw.user.domain.auto.platform.entity.RentUserPickUp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yolo.hw.user.dto.request.ReqPickCarDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 取车信息表 服务类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
public interface IRentUserPickUpService extends IService<RentUserPickUp> {

    void pickUpCar(ReqPickCarDto reqDto);
}
