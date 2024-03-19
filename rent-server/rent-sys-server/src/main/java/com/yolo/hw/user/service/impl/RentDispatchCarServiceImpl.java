package com.yolo.hw.user.service.impl;

import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentDispatchCar;
import com.yolo.hw.user.domain.auto.platform.entity.RentEnterpriseCar;
import com.yolo.hw.user.domain.auto.platform.entity.RentEnterpriseDriver;
import com.yolo.hw.user.domain.auto.platform.mapper.RentDispatchCarMapper;
import com.yolo.hw.user.domain.auto.platform.mapper.RentEnterpriseCarMapper;
import com.yolo.hw.user.domain.auto.platform.mapper.RentEnterpriseDriverMapper;
import com.yolo.hw.user.dto.request.ReqAddProductDto;
import com.yolo.hw.user.dto.request.ReqDispatchCarDto;
import com.yolo.hw.user.service.IRentDispatchCarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import zipkin2.internal.DateUtil;

import java.util.Date;

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

    @Autowired
    private RentDispatchCarMapper dispatchCarMapper;
    @Autowired
    private RentEnterpriseDriverMapper driverMapper;
    @Autowired
    private RentEnterpriseCarMapper carMapper;

    /**
     * 派车
     * @param reqDto
     */
    @Override
    @Transactional
    public void dispatchCar(ReqDispatchCarDto reqDto) {
        /** 参数校验 **/
        Assert.isTrue(validateParams(reqDto).getCode() == 0, validateParams(reqDto).getMsg());
        Long dispatchId = reqDto.getDispatchId();
        Long driverId = reqDto.getDriverId();
        Long carId = reqDto.getCarId();
        RentDispatchCar dispatchCar = dispatchCarMapper.selectById(dispatchId);
        Assert.isTrue(dispatchCar.getStatus().intValue() == 0, "派车单状态异常");
        RentEnterpriseDriver driver = driverMapper.selectById(driverId);
        Assert.isTrue(driver.getStatus().intValue() == 0, "司机目前已处于派车中");
        RentEnterpriseCar car = carMapper.selectById(carId);
        Assert.isTrue(car.getRentStatus().intValue() == 0, "车辆处于租赁中");
        /** 更改司机状态 **/
        // 状态0空闲 1派车中
        driver.setStatus(1);
        /** 更改车辆状态 **/
        // 当前租赁状态0:未租 1:已租
        car.setRentStatus(1);
        car.updateById();
        /** 更改派车单信息 **/
        dispatchCar.setDriverId(driverId);
        dispatchCar.setCarId(carId);
        dispatchCar.setLicense(car.getLicense());
        // 状态0:待派车 1:已派车 2:已接车 3:已还车
        dispatchCar.setStatus(1);
        dispatchCar.setUpdateTimestamp(new Date());
        dispatchCar.insert();
        /** 给司机推送派车信息 **/
        sendMsgToDriver(driverId, String.format("%你好,你有派车单任务,派车地点:{} 到达派车点时间:{}", driver.getUserName(), dispatchCar.getStartAddress(), dispatchCar.getStartTime()));
    }

    private Success validateParams(ReqDispatchCarDto reqDto) {
        return Success.ok();
    }

    private void sendMsgToDriver(Long driverId, String msg) {

    }
}
