package com.yolo.hw.user.service;

import com.yolo.hw.user.domain.auto.platform.entity.RentUserCarBack;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yolo.hw.user.dto.request.ReqBackCarDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 还车信息表 服务类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
public interface IRentUserCarBackService extends IService<RentUserCarBack> {

   void backCar(@RequestBody ReqBackCarDto reqDto);
}
