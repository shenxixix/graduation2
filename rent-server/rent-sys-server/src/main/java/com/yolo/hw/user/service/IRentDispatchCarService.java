package com.yolo.hw.user.service;

import com.yolo.hw.user.domain.auto.platform.entity.RentDispatchCar;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yolo.hw.user.dto.request.ReqDispatchCarDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 派车单 服务类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
public interface IRentDispatchCarService extends IService<RentDispatchCar> {

    void dispatchCar(ReqDispatchCarDto reqDto);
}
