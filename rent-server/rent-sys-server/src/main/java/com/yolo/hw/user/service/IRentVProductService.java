package com.yolo.hw.user.service;

import com.yolo.hw.user.domain.auto.platform.entity.RentVProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yolo.hw.user.dto.request.ReqAddProductDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 企业服务平台产品信息表 服务类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
public interface IRentVProductService extends IService<RentVProduct> {

    void addProduct(ReqAddProductDto reqDto);
}
