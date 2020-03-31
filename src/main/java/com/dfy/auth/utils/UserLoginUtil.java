package com.dfy.auth.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: 生成用户登录唯一token认证
 * @author: DFY
 * @time: 2020/3/31 10:38
 */
public class UserLoginUtil {

    /** 用户名，token缓存映射 */
    private static Cache<String,String> usernameAuthTokenCache = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();

    /**
     * 为保证token的唯一性，这里使用用户名+UUID+时间戳，最后通过SHA1算法进行加密生成唯一token
     * @param username
     * @return
     */
    public static String generate(String username) {
        StringBuilder sb = new StringBuilder();
        sb.append(username)
                .append(UUID.randomUUID().toString().replaceAll("-", ""))
                .append(System.currentTimeMillis());
        String authToken = CodecUtil.sha1Encrypt(sb.toString());
        usernameAuthTokenCache.put(username, authToken);
        return authToken;
    }

    /**
     * 验证用户token是否存在或是否过期
     * @param username
     * @param authToken
     * @return
     */
    public static boolean isValid(String username, String authToken) {
        if (username == null || authToken == null) return false;
        String findAuthToken = usernameAuthTokenCache.getIfPresent(username);
        return findAuthToken != null && authToken.equals(findAuthToken);
    }

    /**
     * 验证用户当前登录是否登录（先从cookie中获取，再从header中获取）
     * @param request
     * @return
     */
    public static boolean verify(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("authToken") && isValid(username, cookie.getValue())) {
                return true;
            }
        }
        String findAuthToken = request.getHeader("authToken");
        if (isValid(username, findAuthToken)) {
            return true;
        }
        return false;
    }

    /**
     * 用户登出时删除缓存中的数据
     * @param request
     */
    public static void logout(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        usernameAuthTokenCache.invalidate(username);
    }
}
