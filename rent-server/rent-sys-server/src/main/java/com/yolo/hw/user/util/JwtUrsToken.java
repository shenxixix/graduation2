package com.yolo.hw.user.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.hw.user.config.exception.ErrorEnum;
import com.yolo.hw.user.config.exception.SysAppException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @Author:yanxiao
 * @Des:jwt工具类
 * @Date:2017/11/10
 */
@Slf4j
public class JwtUrsToken {
    //密码。用于加密token
    private final String key = "fleet.yolo.cn-xxx12345678";
    //签发者
    private final String ISSUER = "app.yolo.cn";
    /**
     * 用户信息串
     */
    private String subInfo;
    /**
     * 过期时间
     */
    private Date expTime;

    private String token;

    /**
     * 签发时间
     */
    private Date iat;
    /**
     * 接收token标识，可以用于app校验，如包名等,使用者
     */
    private String audience;

    public Date getExpTime() {
        return expTime;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Date getIat() {
        return iat;
    }

    protected String getToken() {
        return token;
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    private SecretKey generalKey() {
        String stringKey = key;
        byte[] encodedKey = java.util.Base64.getMimeDecoder().decode(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 生成jwt
     *
     * @param subject   主题对象
     * @param ttlMillis 过期时间（秒）
     * @return 返回jwt
     */
    public String genertator(Object subject, Long ttlMillis) {
        if (subject == null) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            String val = mapper.writeValueAsString(subject);
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            Date now = new Date();
            long nowMillis = now.getTime();
            SecretKey key = generalKey();
            JwtBuilder builder = Jwts.builder().setIssuer(ISSUER).setAudience(audience).setIssuedAt(now).setSubject(val).signWith(signatureAlgorithm, key);
            if (ttlMillis != null && ttlMillis >= 0) {
                long expMillis = nowMillis + (ttlMillis * 1000);
                Date exp = new Date(expMillis);
                //设置过期时间，暂时不过期
                this.expTime = exp;
                builder.setExpiration(exp);
            }
            this.token = builder.compact();
            this.subInfo = val;
            //此处由于jwt签发时间的bug，时间去掉了毫秒。在业务获取iat时间时毫米部分为000；
            this.iat = new Date((now.getTime() / 1000L) * 1000L);
            return token;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 解密jwt，解析后，可以获取该jwt过期时间、主题json（用于业务解析）、jwt生成时间、接收者标识
     *
     * @param jwt
     */
    public void parseJWT(String jwt) {
        try {
            SecretKey key = generalKey();
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
            this.audience = claims.getAudience();
            this.expTime = claims.getExpiration();
            this.subInfo = claims.getSubject();
            this.iat = claims.getIssuedAt();
            this.token = jwt;
        } catch (ExpiredJwtException e) {
            //过期
            e.printStackTrace();
            throw new SysAppException(ErrorEnum.TOKEN_ERR);
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            throw new SysAppException(ErrorEnum.TOKEN_ERR);
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            throw new SysAppException(ErrorEnum.TOKEN_ERR);
        } catch (SignatureException e) {
            e.printStackTrace();
            throw new SysAppException(ErrorEnum.TOKEN_ERR);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new SysAppException(ErrorEnum.TOKEN_ERR);
        }
    }

    public String getSubInfo() {
        return subInfo;
    }

    public static JwtUrsToken getToken(String token) {
        try {
            JwtUrsToken jwtUrsToken = new JwtUrsToken();
            jwtUrsToken.parseJWT(token);
            return jwtUrsToken;
        } catch (SysAppException e) {
            log.debug("AppGateWayException 网关-用户无效,x_access_token={}", token);
            return null;
        } catch (Exception e) {
            log.debug("网关-用户无效,x_access_token={}", token);
            return null;
        }
    }
}


