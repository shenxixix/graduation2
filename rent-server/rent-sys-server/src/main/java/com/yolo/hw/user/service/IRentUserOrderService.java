package com.yolo.hw.user.service;

import com.yolo.hw.user.domain.auto.platform.entity.RentUserOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yolo.hw.user.dto.request.ReqCreateOrderDto;
import com.yolo.hw.user.dto.request.ReqOrderPayFinishDto;
import com.yolo.hw.user.dto.response.ResCreateOrderDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 租赁订单表 服务类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
public interface IRentUserOrderService extends IService<RentUserOrder> {

    ResCreateOrderDto createOrder(ReqCreateOrderDto reqDto);

    void payFinishBack(ReqOrderPayFinishDto reqDto);
}
