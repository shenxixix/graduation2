package com.yolo.hw.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Preconditions;
import com.yolo.hw.user.config.exception.ErrorEnum;
import com.yolo.hw.user.config.exception.SysAppException;
import com.yolo.hw.user.domain.auto.platform.entity.RentVEnterprise;
import com.yolo.hw.user.domain.auto.platform.entity.RentVManager;
import com.yolo.hw.user.domain.auto.platform.entity.RentVMenu;
import com.yolo.hw.user.domain.auto.platform.entity.RentVUser;
import com.yolo.hw.user.domain.auto.platform.mapper.RentVEnterpriseMapper;
import com.yolo.hw.user.domain.auto.platform.mapper.RentVManagerMapper;
import com.yolo.hw.user.domain.auto.platform.mapper.RentVUserMapper;
import com.yolo.hw.user.domain.custom.dao.MenuDao;
import com.yolo.hw.user.dto.request.*;
import com.yolo.hw.user.dto.response.ResUserInfoDto;
import com.yolo.hw.user.dto.response.ResUserLoginDto;
import com.yolo.hw.user.dto.response.RespManagerLoginDto;
import com.yolo.hw.user.service.ILoginService;
import com.yolo.hw.user.util.JwtUrsToken;
import com.yolo.hw.user.util.UserInfo;
import javafx.scene.control.Alert;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoginService implements ILoginService {

    @Autowired
    private RentVManagerMapper managerMapper;
    @Autowired
    private RentVUserMapper userMapper;
    @Autowired
    private RentVEnterpriseMapper enterpriseMapper;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RedisTemplate redisTemplate;

    private static String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    /**
     * 管理端登录
     * @param reqDto
     * @return
     */
    @Override
    public RespManagerLoginDto managerLogin(ReqManagerLoginDto reqDto) {
        RespManagerLoginDto resDto = new RespManagerLoginDto();
        String userMobile = reqDto.getUserMobile();
        String password = reqDto.getPassword();
        RentVManager platformUser = managerMapper.selectOne(Wrappers.<RentVManager>lambdaQuery().eq(RentVManager::getUserMobile, userMobile).last(" limit 1"));
        Assert.isTrue(platformUser != null, "用户名或密码错误");
        Assert.isTrue(platformUser.getUserAccountStatus().equals(1), "用户已被停用");
        Assert.isTrue(platformUser.getUserPassword().equals(password), "用户名或密码错误");
        JwtUrsToken jwtUrsToken = new JwtUrsToken();
        RespManagerLoginDto.User user = new RespManagerLoginDto.User();
        user.setUserId(platformUser.getUserId());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(platformUser.getUserId());
        userInfo.setUserMobile(userMobile);
        // 有效期暂定10天
        userInfo.setExpireTime(System.currentTimeMillis() + 10*24*60*60*1000);
        // 用户类型1:平台|企业 2:租车用户
        userInfo.setUserType(1);
        String token = jwtUrsToken.genertator(userInfo, null);
        if(platformUser.getEnterpriseId() != null) {
            RentVEnterprise enterprise = enterpriseMapper.selectById(platformUser.getEnterpriseId());
            user.setEpName(enterprise != null ? enterprise.getEpName() : "");
        }
        resDto.setToken(token);
        /** 查询菜单权限 **/
        List<RentVMenu> menus = menuDao.findUserMenus(platformUser.getUserId());
        if(menus != null) {
            Map<Long, List<RentVMenu>> parentMenuMap = menus.stream().collect(Collectors.groupingBy(RentVMenu::getParentId));
            List<RentVMenu> topMenuList = parentMenuMap.get(0l);
            for(RentVMenu menu : topMenuList) {
                RespManagerLoginDto.ResRentMenu resRentMenu = new RespManagerLoginDto.ResRentMenu();
                BeanUtils.copyProperties(menu, resRentMenu);
                resRentMenu.setChildMenus(findChildMenu(parentMenuMap, menu));
                resDto.getUserInfo().getMenus().add(resRentMenu);
            }
        }
        return resDto;
    }

    /**
     * 租车用户登录
     * @param reqDto
     * @return
     */
    @Override
    public ResUserLoginDto userLogin(ReqLoginDto reqDto) {
        ResUserLoginDto resDto = new ResUserLoginDto();
        String mobile = reqDto.getUserMobile();
        String password = reqDto.getPassword();
        RentVUser rentVUser = userMapper.selectOne(Wrappers.<RentVUser>lambdaQuery().eq(RentVUser::getUserMobile, mobile).last(" limit 1"));
        Assert.isTrue(rentVUser != null, "用户名或密码错误");
        Assert.isTrue(rentVUser.getUserAccountStatus().equals(1), "无效用户");
        Assert.isTrue(rentVUser.getUserPassword().equals(password), "用户名或密码错误");
        JwtUrsToken jwtUrsToken = new JwtUrsToken();
        ResUserInfoDto user = new ResUserInfoDto();
        BeanUtils.copyProperties(rentVUser, user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserId());
        userInfo.setUserMobile(mobile);
        // 有效期暂定10天
        userInfo.setExpireTime(System.currentTimeMillis() + 10*24*60*60*1000);
        // 用户类型1:平台|企业 2:租车用户
        userInfo.setUserType(2);
        String token = jwtUrsToken.genertator(userInfo, null);
        resDto.setToken(token);
        return resDto;
    }

    /**
     * 租车用户注册
     * @param reqDto
     * @return
     */
    @Override
    public ResUserLoginDto register(ReqRegisterDto reqDto) {
        ResUserLoginDto resDto = new ResUserLoginDto();
        String mobile = reqDto.getUserMobile();
        String code = reqDto.getCode();
        String password = reqDto.getPassword();
        Assert.isTrue(validatePassword(password), "密码格式不正确");
        validateCode(code, String.format("user:code:%d:%s", 1, mobile));
        RentVUser rentVUser = userMapper.selectOne(Wrappers.<RentVUser>lambdaQuery().eq(RentVUser::getUserMobile, mobile).last(" limit 1"));
        Assert.isTrue(rentVUser != null, "无效用户");
        rentVUser.setUserPassword(password);
        rentVUser.setUpdateTimestamp(new Date());
        rentVUser.updateById();
        return resDto;
    }

    /**
     * 修改密码（管端和租车用户共用）
     * @param reqDto
     */
    @Override
    public void changePassword(ReqPasswordDto reqDto) {
        log.info("发起修改密码操作:{}", reqDto);
        Long userId = reqDto.getUserId();
        Integer userType = reqDto.getUserType();
        String code = reqDto.getCode();
        String password = reqDto.getUserPassword();
        if(!validatePassword(password)) {
            throw new SysAppException(ErrorEnum.BUSINESS_ERR.getCode(), "密码规则有误");
        }
        // 户类型1:平台|企业 2:租车用户
        if(userType == 1) {
            RentVManager manager = managerMapper.selectById(userId);
            Assert.isTrue(manager != null, "无效用户");
            // 验证验证码是否正确
            validateCode(code, String.format("user:code:%d:%s", 2, manager.getUserMobile()));
            manager.setUserPassword(password);
            manager.setUpdateTimestamp(new Date());
            manager.updateById();
        } else {
            RentVUser user = userMapper.selectById(userId);
            Assert.isTrue(user != null, "无效用户");
            // 验证验证码是否正确
            validateCode(code, String.format("user:code:%d:%s", 3, user.getUserMobile()));
            user.setUserPassword(password);
            user.setUpdateTimestamp(new Date());
            user.updateById();
        }
    }

    /**
     * 发送验证码
     * @param reqDto
     */
    @Override
    public void authCode(ReqAuthCodeDto reqDto) {
        String mobile = reqDto.getMobile();
        String key = String.format("user:code:%d:%s", reqDto.getType(), mobile);
        String code = (String)redisTemplate.opsForValue().get(key);
        if(code == null) {
            // 生成4位有效数
            code = generateCode(4);
        }
        // 设置5分钟有效期
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
    }

    private String generateCode(int length){
        // 生成验证码
        StringBuilder randNum = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            randNum.append(rand);
        }
        return randNum.toString();
    }

    /**
     * 验证密码规则
     * @param password
     * @return
     */
    private boolean validatePassword(String password) {
        // 编译正则表达式
        Pattern pattern = Pattern.compile(passwordRegex);
        // 将字符串与正则表达式匹配
        Matcher matcher = pattern.matcher(password);
        // 返回是否匹配的结果
        return matcher.matches();
    }

    /**
     * 验证验证码
     * @param code
     * @param redisKey
     */
    private void validateCode(String code, String redisKey) {
        String redisCode = (String)redisTemplate.opsForValue().get(redisKey);
        if(redisCode == null || !redisCode.equals(code)) {
            throw new SysAppException(ErrorEnum.BUSINESS_ERR.getCode(), "验证码异常");
        }
    }

    /**
     * 查找子菜单
     * @param parentMenuMap 按父菜单ID分组的菜单列表
     * @param menu 当前菜单
     * @return
     */
    private List<RespManagerLoginDto.ResRentMenu> findChildMenu(Map<Long, List<RentVMenu>> parentMenuMap, RentVMenu menu) {
        List<RespManagerLoginDto.ResRentMenu> childMenus = new ArrayList<>();
        List<RentVMenu> cldList = parentMenuMap.get(menu.getMenuId());
        if(CollectionUtils.isNotEmpty(cldList)) {
            for(RentVMenu mu : cldList) {
                RespManagerLoginDto.ResRentMenu resRentMenu = new RespManagerLoginDto.ResRentMenu();
                BeanUtils.copyProperties(mu, resRentMenu);
                resRentMenu.setChildMenus(findChildMenu(parentMenuMap, mu));
                childMenus.add(resRentMenu);
            }
        }
        return childMenus;
    }


    public static void main(String[] args) {
        String password = new Sha256Hash("123456").toHex();
        System.out.println(password);
        String d = null;
        Assert.isTrue(d != null, "空异常");
    }
}
