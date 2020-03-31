package com.dfy.auth.constants;

/**
 * @description: 登录认证静态变量
 * @author: DFY
 * @time: 2020/3/30 15:34
 */
public interface AuthConstants {

    /** 不作拦截的URL请求路径 */
    String EXCLUDED_URI_PATHS = "EXCLUDED_URI_PATHS";

    /** 登出地址 */
    String LOGOUT_URI = "LOGOUT_URI";

    /** 登录地址 */
    String LOGIN_URI = "LOGIN_URI";
}
