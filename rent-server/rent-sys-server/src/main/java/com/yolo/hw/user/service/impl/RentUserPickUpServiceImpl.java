package com.yolo.hw.user.service.impl;

import com.yolo.hw.user.domain.auto.platform.entity.RentUserPickUp;
import com.yolo.hw.user.domain.auto.platform.mapper.RentUserPickUpMapper;
import com.yolo.hw.user.dto.request.ReqPickCarDto;
import com.yolo.hw.user.service.IRentUserPickUpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 取车信息表 服务实现类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Service
public class RentUserPickUpServiceImpl extends ServiceImpl<RentUserPickUpMapper, RentUserPickUp> implements IRentUserPickUpService {

    /**
     * 取车
     * @param reqDto
     */
    @Override
    public void pickUpCar(ReqPickCarDto reqDto) {

    }
}
