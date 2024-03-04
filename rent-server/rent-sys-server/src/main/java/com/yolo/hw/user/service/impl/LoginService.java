package com.yolo.hw.user.service.impl;

import com.yolo.hw.user.domain.auto.platform.entity.RentVManager;
import com.yolo.hw.user.domain.auto.platform.mapper.RentVManagerMapper;
import com.yolo.hw.user.domain.custom.dao.OrderDao;
import com.yolo.hw.user.dto.request.ReqLoginDto;
import com.yolo.hw.user.dto.response.RespUserLoginDto;
import com.yolo.hw.user.service.ILoginService;
import com.yolo.hw.user.util.JwtUrsToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService implements ILoginService {

    @Autowired
    private RentVManagerMapper managerMapper;
    @Autowired
    private OrderDao orderDao;

    @Override
    public RespUserLoginDto loginOrReg(ReqLoginDto reqLoginDto) {
        RespUserLoginDto resDto = new RespUserLoginDto();
        RentVManager platformUser = managerMapper.selectById(1l);
        JwtUrsToken jwtUrsToken = new JwtUrsToken();
        RespUserLoginDto.User user = new RespUserLoginDto.User();
        user.setUserId(platformUser.getUserId());
        String token = jwtUrsToken.genertator(user, null);
        resDto.setToken(token);
        RentVManager platformUser2 =  orderDao.findUser(1l);
        return resDto;
    }

    public static void main(String[] args) {
        String password = new Sha256Hash("123456").toHex();
        System.out.println(password);
    }
}
