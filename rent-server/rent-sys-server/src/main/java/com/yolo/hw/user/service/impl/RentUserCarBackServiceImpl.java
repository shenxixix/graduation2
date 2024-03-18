package com.yolo.hw.user.service.impl;

import com.yolo.hw.user.domain.auto.platform.entity.RentUserCarBack;
import com.yolo.hw.user.domain.auto.platform.mapper.RentUserCarBackMapper;
import com.yolo.hw.user.dto.request.ReqBackCarDto;
import com.yolo.hw.user.service.IRentUserCarBackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    /**
     * 还车
     * @param reqDto
     */
    @Override
    public void backCar(ReqBackCarDto reqDto) {

    }
}
