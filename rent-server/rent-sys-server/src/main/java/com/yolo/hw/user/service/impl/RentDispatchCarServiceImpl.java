package com.yolo.hw.user.service.impl;

import com.yolo.hw.user.domain.auto.platform.entity.RentDispatchCar;
import com.yolo.hw.user.domain.auto.platform.mapper.RentDispatchCarMapper;
import com.yolo.hw.user.dto.request.ReqDispatchCarDto;
import com.yolo.hw.user.service.IRentDispatchCarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 派车单 服务实现类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Service
public class RentDispatchCarServiceImpl extends ServiceImpl<RentDispatchCarMapper, RentDispatchCar> implements IRentDispatchCarService {

    /**
     * 派车
     * @param reqDto
     */
    @Override
    public void dispatchCar(ReqDispatchCarDto reqDto) {

    }
}
