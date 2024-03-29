package com.yolo.hw.user.service;

import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yolo.hw.user.dto.request.ReqAddEnterpriseDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 企业信息表 服务类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
public interface IRentVEnterpriseService extends IService<RentVEnterprise> {

    void addEnterprise(ReqAddEnterpriseDto reqDto);
}
