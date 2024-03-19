package com.yolo.hw.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentEnterpriseCar;
import com.yolo.hw.user.domain.auto.platform.entity.RentEnterpriseDriver;
import com.yolo.hw.user.domain.auto.platform.mapper.RentEnterpriseCarMapper;
import com.yolo.hw.user.dto.request.ReqAddCarDto;
import com.yolo.hw.user.dto.request.ReqAddDriverDto;
import com.yolo.hw.user.service.IRentEnterpriseCarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 企业服务平台车辆信息表 服务实现类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Service
public class RentEnterpriseCarServiceImpl extends ServiceImpl<RentEnterpriseCarMapper, RentEnterpriseCar> implements IRentEnterpriseCarService {

    @Autowired
    private RentEnterpriseCarMapper carMapper;

    /**
     * 添加车辆
     * @param reqDto
     */
    @Override
    public void addCar(ReqAddCarDto reqDto) {
        /** 参数校验 **/
        Assert.isTrue(validateParams(reqDto).getCode() == 0, validateParams(reqDto).getMsg());
        // 效验车牌号
        String license = reqDto.getLicense();
        int carNum = carMapper.selectCount(Wrappers.<RentEnterpriseCar>lambdaQuery().eq(RentEnterpriseCar::getLicense, license).eq(RentEnterpriseCar::getDeleteFlag, 0));
        Assert.isTrue(carNum <= 0, "该司机已经存在");
        /** 创建司机 **/
        RentEnterpriseCar driver = new RentEnterpriseCar();
        driver.setCarId(IdWorker.getId());
        driver.setEnterpriseId(reqDto.getEnterpriseId());
        driver.setLicense(reqDto.getLicense());
        driver.setCarModelId(reqDto.getCarModelId());
        driver.insert();
    }

    private Success validateParams(ReqAddCarDto reqDto) {
        return Success.ok();
    }
}
