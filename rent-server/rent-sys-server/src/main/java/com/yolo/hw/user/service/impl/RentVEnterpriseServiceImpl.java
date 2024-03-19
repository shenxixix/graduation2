package com.yolo.hw.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.yolo.hw.user.common.Success;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.domain.auto.platform.entity.RentVManager;
import com.yolo.hw.user.domain.auto.platform.mapper.RentVEnterpriseMapper;
import com.yolo.hw.user.domain.auto.platform.mapper.RentVManagerMapper;
import com.yolo.hw.user.dto.request.ReqAddEnterpriseDto;
import com.yolo.hw.user.service.IRentVEnterpriseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * <p>
 * 企业信息表 服务实现类
 * </p>
 *
 * @author shenxi
 * @since 2024-03-18
 */
@Service
public class RentVEnterpriseServiceImpl extends ServiceImpl<RentVEnterpriseMapper, RentVEnterprise> implements IRentVEnterpriseService {

    @Autowired
    private RentVEnterpriseMapper enterpriseMapper;
    @Autowired
    private RentVManagerMapper managerMapper;

    /**
     * 新增企业
     * @param reqDto
     */
    @Override
    @Transactional
    public void addEnterprise(ReqAddEnterpriseDto reqDto) {
        /** 参数校验 **/
        Assert.isTrue(validateParams(reqDto).getCode() == 0, validateParams(reqDto).getMsg());
        // 社会信用代码
        String creditCode = reqDto.getCreditCode();
        int creditCodeNum = enterpriseMapper.selectCount(Wrappers.<RentVEnterprise>lambdaQuery().eq(RentVEnterprise::getCreditCode, creditCode));
        Assert.isTrue(creditCodeNum <= 0, "社会信用代码重复");
        int managerNum = managerMapper.selectCount(Wrappers.<RentVManager>lambdaQuery().eq(RentVManager::getUserMobile, reqDto.getContactsPhone()));
        Assert.isTrue(managerNum <= 0, "电话号码重复");
        /** 创建企业管端账号 **/
        Long enterpriseId = IdWorker.getId();
        Long managerId = IdWorker.getId();
        RentVManager manager = new RentVManager();
        manager.setEnterpriseId(enterpriseId);
        manager.setCreatorId(reqDto.getUserId());
        manager.setUserMobile(reqDto.getContactsPhone());
        manager.setUserPassword(new Sha256Hash("password123").toHex());
        manager.setUserName(reqDto.getContactsPhone());
        manager.setUserId(managerId);
        manager.insert();
        /** 创建企业 **/
        RentVEnterprise enterprise = new RentVEnterprise();
        enterprise.setKeyId(enterpriseId);
        BeanUtils.copyProperties(reqDto, enterprise);
        enterprise.insert();
    }

    private Success validateParams(ReqAddEnterpriseDto reqDto) {
        return Success.ok();
    }
}
