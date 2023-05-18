package com.cqtalk.util.jwt;

import com.cqtalk.util.encrypt.UUIDUtil;
import com.cqtalk.util.returnObject.DescribeException;
import com.cqtalk.util.returnObject.ErrorCode;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    /**
     * key（按照签名算法的字节长度设置key）
     */
    private final static String SECRET_KEY = "cqtalk_skicwbvcakwerlcbkl_123456"; // 32位长度

    /**
     * 过期时间（毫秒单位）
     * 1小时
     */
    private final static long TOKEN_EXPIRE_MILLIS = 1000 * 60 * 60;

    /**
     * 创建token
     * @param claimMap 此处传入两个参数，具体使用案例请见最下面的main方法
     * @return
     */
    public static String createToken(Map<String, Object> claimMap) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUIDUtil.generateUUID())
                .setIssuedAt(new Date(currentTimeMillis))    // 设置签发时间
                .setExpiration(new Date(currentTimeMillis + TOKEN_EXPIRE_MILLIS))   // 设置过期时间
                .addClaims(claimMap)
                .signWith(generateKey())
                .compact();
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static void verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
            // return 0;
        } catch (Exception e) {
            throw new DescribeException(ErrorCode.JWT_ERROR.getCode(), ErrorCode.JWT_ERROR.getMsg());
        }


            /*throw new DescribeException(ErrorCode.JWT_TOKEN_OVERDUE_ERROR.getCode(), ErrorCode.JWT_TOKEN_OVERDUE_ERROR.getMsg());
            // 并且参考：https://juejin.cn/post/6969074624650805262
            // return 1;
        } catch (UnsupportedJwtException e) {
            throw new DescribeException(ErrorCode.JWT_TOKEN_OVERDUE_ERROR.getCode(), ErrorCode.JWT_TOKEN_OVERDUE_ERROR.getMsg());
            // return 2;
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            // return 3;
        } catch (SignatureException e) {
            e.printStackTrace();
            // return 4;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // return 5;*/
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static Map<String, Object> parseToken(String token) {
        return Jwts.parser()  // 得到DefaultJwtParser
                .setSigningKey(generateKey()) // 设置签名密钥
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 生成安全密钥
     * @return
     */
    public static Key generateKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public Integer getUserId(String token) {
        JWTUtil.verifyToken(token);
        Map<String, Object> map = JWTUtil.parseToken(token);
        return (Integer) map.get("userId");
    }

    public Integer getUserIdByRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return getUserId(token);
    }

    public String getLoginTicket(String token) {
        JWTUtil.verifyToken(token);
        Map<String, Object> map = JWTUtil.parseToken(token);
        return (String) map.get("loginTicket");
    }

    public String getLoginTicketByRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return getLoginTicket(token);
    }

    // 使用：前端传入token，
    // 后端通过：int result = JWTUtil.verifyToken(token);
    // 如果返回0则代表验证成功
    // Map<String, Object> map = JWTUtil.parseToken(token);
    // map为数据集

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 6);
        map.put("loginTicket", "cqtalk");
        String token = createToken(map);
        System.out.println(token);
        String token1 = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5YzQ0YjNkNjU1MmY0NmE2YWUyMjI1NTk5NTNkZTI4ZiIsImlhdCI6MTY4MjU3NDAxMCwiZXhwIjoxNjgyNTc3NjEwLCJsb2dpblRpY2tldCI6ImFhYjZjZDQyZmY2MTRkNjA4ODAwMzUzZGU3NGQyYmY0IiwiaWQiOjZ9.W4n7CAL1V7cEeg5gKJKTM1Jis9LnGVGqtu8zbVmEkAs";
        Map<String, Object> map1 = parseToken(token1);
        System.out.println(map1);
    }


}
