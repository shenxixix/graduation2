package com.yolo.hw.user.service.impl;

import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.*;
import com.yolo.hw.user.domain.auto.platform.mapper.*;
import com.yolo.hw.user.dto.request.ReqBackCarDto;
import com.yolo.hw.user.dto.request.ReqPickCarDto;
import com.yolo.hw.user.service.IRentUserCarBackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * <p>
 * 还车信息表 服务实现类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Service
public class RentUserCarBackServiceImpl extends ServiceImpl<RentUserCarBackMapper, RentUserCarBack> implements IRentUserCarBackService {

    @Autowired
    private RentDispatchCarMapper dispatchCarMapper;
    @Autowired
    private RentEnterpriseDriverMapper driverMapper;
    @Autowired
    private RentEnterpriseCarMapper carMapper;
    @Autowired
    private RentUserOrderMapper orderMapper;
    @Autowired
    private RentWithdrawRuleMapper ruleMapper;

    /**
     * 还车
     * @param reqDto
     */
    @Override
    public void backCar(ReqBackCarDto reqDto) {
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
        RentEnterpriseCar car = carMapper.selectById(dispatchCar.getCarId());
        /** 设置派车单状态 **/
        // 状态0:待派车 1:已派车 2:已接车 3:已还车
        dispatchCar.setStatus(3);
        dispatchCar.setUpdateTimestamp(new Date());
        dispatchCar.updateById();
        /** 更改司机状态 **/
        // 状态0空闲 1派车中
        driver.setStatus(0);
        driver.setUpdateTimestamp(new Date());
        driver.updateById();
        /** 更改车辆状态 **/
        // 当前租赁状态0:未租 1:已租
        car.setRentStatus(0);
        car.setUpdateTimestamp(new Date());
        /** 新增取车记录 **/
        RentUserCarBack carBack = new RentUserCarBack();
        carBack.setCarId(dispatchCar.getCarId());
        carBack.setUserId(userId);
        carBack.setDriverId(driverId);
        carBack.setCarPics(reqDto.getPics());
        carBack.setBackTime(new Date());
        carBack.setRemark(reqDto.getRemark());
        carBack.insert();
        /** 整个订单结束,平台将费用转给企业,设置结算费用和结算状态 **/
        RentUserOrder order = orderMapper.selectById(dispatchCar.getOrderId());
        RentWithdrawRule rule = ruleMapper.selectById(order.getEnterpriseId());
        // 提现比例,计算转账金额
        Long ratio = rule != null ? rule.getRatio() : 95;
        Long transFee = order.getFee() * (ratio/100);
        order.setSettleFee(order.getFee());
        boolean trans = transferToEnterprise(order.getEnterpriseId(), transFee);
        if(trans) {
            //结算状态0:未结算 1:已结算
            order.setSettleStatus(1);
        }
        order.setUpdateTimestamp(new Date());
        order.updateById();
        /** 退还用户押金 **/
        backDeposit(order.getPayOrderNo(), order.getDeposit(), order.getPayWay());
    }

    private Success validateParams(ReqBackCarDto reqDto) {
        return Success.ok();
    }

    private boolean transferToEnterprise(Long enterpriseId, Long fee) {
        return true;
    }

    private boolean backDeposit(String payOrderNo, Integer deposit, Integer payWay) {
        return true;
    }
}
