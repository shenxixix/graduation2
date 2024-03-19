package com.yolo.hw.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentEnterpriseDriver;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.domain.auto.platform.entity.RentVManager;
import com.yolo.hw.user.domain.auto.platform.mapper.RentEnterpriseDriverMapper;
import com.yolo.hw.user.dto.request.ReqAddCarDto;
import com.yolo.hw.user.dto.request.ReqAddDriverDto;
import com.yolo.hw.user.dto.request.ReqAddEnterpriseDto;
import com.yolo.hw.user.service.IRentEnterpriseDriverService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * <p>
 * 企业驾驶员 服务实现类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Service
public class RentEnterpriseDriverServiceImpl extends ServiceImpl<RentEnterpriseDriverMapper, RentEnterpriseDriver> implements IRentEnterpriseDriverService {

    @Autowired
    private RentEnterpriseDriverMapper driverMapper;

    /**
     * 新增司机
     * @param reqDto
     */
    @Override
    public void addDriver(ReqAddDriverDto reqDto) {
        /** 参数校验 **/
        Assert.isTrue(validateParams(reqDto).getCode() == 0, validateParams(reqDto).getMsg());
        // 效验电话号码
        String mobile = reqDto.getMobile();
        int driverNum = driverMapper.selectCount(Wrappers.<RentEnterpriseDriver>lambdaQuery().eq(RentEnterpriseDriver::getMobile, mobile).eq(RentEnterpriseDriver::getIsDel, 0));
        Assert.isTrue(driverNum <= 0, "该司机已经存在");
        /** 创建司机 **/
        RentEnterpriseDriver driver = new RentEnterpriseDriver();
        driver.setDriverId(IdWorker.getId());
        driver.setEnterpriseId(reqDto.getEnterpriseId());
        driver.setUserName(reqDto.getUserName());
        driver.setMobile(reqDto.getMobile());
        driver.setDriverLicense(reqDto.getDriverLicense());
        driver.insert();
    }

    private Success validateParams(ReqAddDriverDto reqDto) {
        return Success.ok();
    }
}
