package com.yolo.hw.user.service.impl;

import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentDispatchCar;
import com.yolo.hw.user.domain.auto.platform.entity.RentEnterpriseDriver;
import com.yolo.hw.user.domain.auto.platform.entity.RentUserPickUp;
import com.yolo.hw.user.domain.auto.platform.mapper.RentDispatchCarMapper;
import com.yolo.hw.user.domain.auto.platform.mapper.RentEnterpriseDriverMapper;
import com.yolo.hw.user.domain.auto.platform.mapper.RentUserPickUpMapper;
import com.yolo.hw.user.dto.request.ReqDispatchCarDto;
import com.yolo.hw.user.dto.request.ReqPickCarDto;
import com.yolo.hw.user.service.IRentUserPickUpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

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

    @Autowired
    private RentDispatchCarMapper dispatchCarMapper;
    @Autowired
    private RentEnterpriseDriverMapper driverMapper;

    /**
     * 取车
     * @param reqDto
     */
    @Override
    @Transactional
    public void pickUpCar(ReqPickCarDto reqDto) {
        /** 参数校验 **/
        Assert.isTrue(validateParams(reqDto).getCode() == 0, validateParams(reqDto).getMsg());
        Long userId = reqDto.getUserId();
        Long dispatchId = reqDto.getDispatchId();
        Long driverId = reqDto.getDriverId();
        RentDispatchCar dispatchCar = dispatchCarMapper.selectById(dispatchId);
        Assert.isTrue(dispatchCar != null, "派车单不存在");
        Assert.isTrue(dispatchCar.getUserId().equals(userId), "该派车单不属于你");
        RentEnterpriseDriver driver = driverMapper.selectById(driverId);
        Assert.isTrue(driver != null, "司机不存在");
        /** 设置派车单状态 **/
        // 状态0:待派车 1:已派车 2:已接车 3:已还车
        dispatchCar.setStatus(2);
        dispatchCar.setUpdateTimestamp(new Date());
        dispatchCar.updateById();
        /** 新增取车记录 **/
        RentUserPickUp pickUp = new RentUserPickUp();
        pickUp.setCarId(dispatchCar.getCarId());
        pickUp.setUserId(userId);
        pickUp.setDriverId(driverId);
        pickUp.setCarPics(reqDto.getPics());
        pickUp.setGetTime(new Date());
        pickUp.setRemark(reqDto.getRemark());
        pickUp.insert();
    }

    private Success validateParams(ReqPickCarDto reqDto) {
        return Success.ok();
    }
}
