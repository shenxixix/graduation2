package com.yolo.hw.user.config.normal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.hw.user.util.JwtUrsToken;
import com.yolo.hw.user.util.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class FilterInterceptor extends HandlerInterceptorAdapter {

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开始拦截url={}", request.getRequestURI());
        String token = request.getHeader("X-Access-Token");
        //用户token解析
        UserInfo userInfo = new UserInfo();
        if(!StringUtils.isEmpty(token)) {
            JwtUrsToken jwtUrsToken = JwtUrsToken.getToken(token);
            // 从header或是cookie中获取用户token
            String subInfo = jwtUrsToken.getSubInfo();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                userInfo = objectMapper.readValue(subInfo, UserInfo.class);
                if (userInfo != null && userInfo.getUserId() != null) {
                    request.setAttribute("userId", userInfo.getUserId());
                }
            } catch (IOException e) {
                log.error("网关-解析用户token错误：", e);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    private Long getUserId(HttpServletRequest request) {
        return getUserId(null, request);
    }

    private Long getUserId(Long userIdIn, HttpServletRequest request) {
        Long userId;
        if (userIdIn != null && userIdIn > 0) {
            return userIdIn;
        }
        String userIdStr = this.getHeaderBeforCookie(request, "userId");
        if (userIdStr != null && !"".equals(userIdStr.trim())) {
            userId = Long.valueOf(userIdStr);
        } else {
            userId = userIdIn;
        }
        return userId;
    }

    private String getHeaderBeforCookie(HttpServletRequest request, String key) {
        String val = null;
        val = request.getHeader(key);
        if (val != null) {
            return val;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equalsIgnoreCase(cookie.getName())) {
                    val = cookie.getValue();
                    //logger.warn("获取cookie值>>name:" + cookie.getName() + ",value:" + cookie.getValue());
                    break;
                }
            }
        }
        return val;
    }
}
