package com.yolo.hw.user.service.impl;

import com.yolo.hw.user.domain.auto.platform.entity.RentEnterpriseCar;
import com.yolo.hw.user.domain.auto.platform.mapper.RentEnterpriseCarMapper;
import com.yolo.hw.user.dto.request.ReqAddCarDto;
import com.yolo.hw.user.service.IRentEnterpriseCarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    /**
     * 添加车辆
     * @param reqDto
     */
    @Override
    public void add(ReqAddCarDto reqDto) {

    }
}
