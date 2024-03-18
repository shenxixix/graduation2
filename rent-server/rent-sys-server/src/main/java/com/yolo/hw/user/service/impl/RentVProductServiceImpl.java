package com.yolo.hw.user.service.impl;

import com.yolo.hw.user.domain.auto.platform.entity.RentVProduct;
import com.yolo.hw.user.domain.auto.platform.mapper.RentVProductMapper;
import com.yolo.hw.user.dto.request.ReqAddProductDto;
import com.yolo.hw.user.service.IRentVProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    /**
     * 新增产品
     * @param reqDto
     */
    @Override
    public void add(ReqAddProductDto reqDto) {

    }
}
