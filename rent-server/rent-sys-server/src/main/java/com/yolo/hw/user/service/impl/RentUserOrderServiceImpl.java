package com.yolo.hw.user.service.impl;

import com.yolo.hw.user.domain.auto.platform.entity.RentUserOrder;
import com.yolo.hw.user.domain.auto.platform.mapper.RentUserOrderMapper;
import com.yolo.hw.user.dto.request.ReqCreateOrderDto;
import com.yolo.hw.user.dto.request.ReqOrderPayFinishDto;
import com.yolo.hw.user.dto.response.ResCreateOrderDto;
import com.yolo.hw.user.service.IRentUserOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租赁订单表 服务实现类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Service
public class RentUserOrderServiceImpl extends ServiceImpl<RentUserOrderMapper, RentUserOrder> implements IRentUserOrderService {

    /**
     * 下单
     * @param reqDto
     * @return
     */
    @Override
    public ResCreateOrderDto createOrder(ReqCreateOrderDto reqDto) {
        return null;
    }

    /**
     * 支付完成回调
     * @param reqDto
     */
    @Override
    public void payFinishBack(ReqOrderPayFinishDto reqDto) {

    }
}
