package com.yolo.hw.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentEnterpriseCar;
import com.yolo.hw.user.domain.auto.platform.entity.RentVProduct;
import com.yolo.hw.user.domain.auto.platform.mapper.RentVProductMapper;
import com.yolo.hw.user.dto.request.ReqAddCarDto;
import com.yolo.hw.user.dto.request.ReqAddProductDto;
import com.yolo.hw.user.service.IRentVProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 企业服务平台产品信息表 服务实现类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Service
public class RentVProductServiceImpl extends ServiceImpl<RentVProductMapper, RentVProduct> implements IRentVProductService {

    @Autowired
    private RentVProductMapper productMapper;

    /**
     * 新增产品
     * @param reqDto
     */
    @Override
    public void addProduct(ReqAddProductDto reqDto) {
        /** 参数校验 **/
        Assert.isTrue(validateParams(reqDto).getCode() == 0, validateParams(reqDto).getMsg());
        /** 创建产品 **/
        RentVProduct product = new RentVProduct();
        product.setKeyId(IdWorker.getId());
        product.setPic(reqDto.getPic());
        product.setConfigParams(reqDto.getConfigParams());
        product.setDayFee(reqDto.getDayFee());
        product.setEnterpriseId(reqDto.getEnterpriseId());
        product.setCarModelId(reqDto.getCarModelId());
        product.insert();
    }

    private Success validateParams(ReqAddProductDto reqDto) {
        return Success.ok();
    }
}
