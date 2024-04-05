package com.yolo.hw.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.*;
import com.yolo.hw.user.domain.auto.platform.mapper.*;
import com.yolo.hw.user.domain.auto.user.entity.RentContractTemplate;
import com.yolo.hw.user.domain.auto.user.entity.RentUserEvaluate;
import com.yolo.hw.user.domain.auto.user.mapper.RentContractTemplateMapper;
import com.yolo.hw.user.domain.auto.user.mapper.RentUserEvaluateMapper;
import com.yolo.hw.user.dto.request.ReqCreateOrderDto;
import com.yolo.hw.user.dto.request.ReqOrderPayFinishDto;
import com.yolo.hw.user.dto.request.ReqUserEvaluateDto;
import com.yolo.hw.user.dto.response.ResCreateOrderDto;
import com.yolo.hw.user.service.IRentUserOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

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

    @Autowired
    private RentVProductMapper productMapper;
    @Autowired
    private RentVUserMapper userMapper;
    @Autowired
    private RentCarModelMapper carModelMapper;
    @Autowired
    private RentUserOrderMapper orderMapper;
    @Autowired
    private RentVEnterpriseMapper enterpriseMapper;
    @Autowired
    private RentContractTemplateMapper templateMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 下单
     * @param reqDto
     * @return
     */
    @Override
    @Transactional
    public ResCreateOrderDto createOrder(ReqCreateOrderDto reqDto) {
        ResCreateOrderDto resDto = new ResCreateOrderDto();
        /** 参数校验 **/
        Assert.isTrue(validateParams(reqDto).getCode() == 0, validateParams(reqDto).getMsg());
        Long productId = reqDto.getProductId();
        // 获取产品详情
        RentVProduct product = productMapper.selectById(productId);
        Assert.isTrue(product != null && product.getDeleteFlag().intValue() == 0, "产品不存在");
        Assert.isTrue(product.getUpStatus().intValue() == 1, "产品已下架");
        // 获取车型
        RentCarModel carModel = carModelMapper.selectById(product.getCarModelId());
        // 获取租赁天数
        int days = days(reqDto.getStartTime(), reqDto.getEndTime());
        Long fee = Long.valueOf(days*product.getDayFee());
        RentUserOrder order = new RentUserOrder();
        BeanUtils.copyProperties(reqDto, order);
        order.setOrderId(IdWorker.getId());
        order.setFee(fee);
        order.setProductName(product.getProductName());
        order.setProductPic(product.getPic());
        order.setCarBrandId(carModel.getCarBrandId());
        order.setCarTypeId(carModel.getCarTypeId());
        order.setProductDetail(createProductDetail(product));
        // 生成第三方支付单号
        String payOrderNo = String.valueOf(IdWorker.getId());
        order.setPayOrderNo(payOrderNo);
        String payStr = createPayStr(reqDto.getPayWay(), order, payOrderNo);
        Assert.isTrue(!StringUtils.isBlank(payStr), "生成字符串异常");
        resDto.setPayStr(payStr);
        // 生成合文件
        String contractUrl = createContract(reqDto);
        order.setContractUrl(contractUrl);
        resDto.setContractUrl(contractUrl);
        order.insert();
        return resDto;
    }

    /**
     * 支付完成回调
     * @param reqDto
     */
    @Override
    @Transactional
    public void payFinishBack(ReqOrderPayFinishDto reqDto) {
        Long orderId = reqDto.getOrderId();
        RentUserOrder order = orderMapper.selectById(orderId);
        Assert.isTrue(order != null, "订单不存在");
        order.setStatus(1);  // 状态0:待支付 1:已支付 2:已取消
        order.updateById();
        /** 创建待派车派车单 **/
        RentDispatchCar dispatchCar = new RentDispatchCar();
        dispatchCar.setOrderId(orderId);
        dispatchCar.setEnterpriseId(order.getEnterpriseId());
        dispatchCar.setStartTime(order.getStartTime());
        dispatchCar.setEndTime(order.getEndTime());
        dispatchCar.setStartAddress(order.getStartAddress());
        dispatchCar.setEndAddress(order.getEndAddress());
        dispatchCar.setStartLatitude(order.getStartLatitude());
        dispatchCar.setStartLongitude(order.getStartLongitude());
        dispatchCar.setEndLatitude(order.getEndLatitude());
        dispatchCar.setEndLongitude(order.getEndLongitude());
        dispatchCar.setUserId(order.getUserId());
        dispatchCar.insert();
    }

    /**
     * 评价
     * @param reqDto
     */
    @Override
    @Transactional
    public void evaluate(ReqUserEvaluateDto reqDto) {
        Long enterpriseId = reqDto.getEnterpriseId();
        RentVEnterprise enterprise = enterpriseMapper.selectById(enterpriseId);
        Assert.isTrue(enterprise != null, "企业不存在");
        RentUserEvaluate evaluate = new RentUserEvaluate();
        BeanUtils.copyProperties(evaluate, reqDto);
        evaluate.setKeyId(IdWorker.getId());
        evaluate.insert();
        /** 修改企业评分 **/
        enterprise.setEvaluateNum(enterprise.getEvaluateNum() + 1);
        // 评论分计算规则：总得分/总条数
        enterprise.setTotalStar((enterprise.getTotalStar() * enterprise.getEvaluateNum() + reqDto.getTotalScore()) / (enterprise.getEvaluateNum() + 1));
        enterprise.setUpdateTimestamp(new Date());
        enterprise.updateById();
    }

    private Success validateParams(ReqCreateOrderDto reqDto) {
        return Success.ok();
    }

    private Integer days(Date startTime, Date endTime) {
        return 10;
    }

    private String createPayStr(Integer payWay, RentUserOrder order, String payOrderNo) {
        return "";
    }

    private String createProductDetail(RentVProduct product) {
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String createContract(ReqCreateOrderDto reqDto) {
        return "";
    }
}
