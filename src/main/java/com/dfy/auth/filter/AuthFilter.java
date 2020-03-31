package com.dfy.auth.filter;

import com.dfy.auth.constants.AuthConstants;
import com.dfy.auth.utils.ExcludedURIUtil;
import com.dfy.auth.utils.UserLoginUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 登录认证拦截器
 * @author: DFY
 * @time: 2020/3/30 15:26
 */
public class AuthFilter implements Filter {

    private static String[] excludedURLPaths;

    private static String loginURI;

    private static String logoutURI;

    private static ExcludedURIUtil excludedURIUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedURLPaths = filterConfig.getInitParameter(AuthConstants.EXCLUDED_URI_PATHS).split(",");
        excludedURIUtil = ExcludedURIUtil.getInstance(excludedURLPaths);
        logoutURI = filterConfig.getInitParameter(AuthConstants.LOGOUT_URI);
        loginURI = filterConfig.getInitParameter(AuthConstants.LOGIN_URI);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if (excludedURIUtil.match(requestURI)) { // 如果请求URI不需要进行拦截
            filterChain.doFilter(request, response);
            return;
        }
        if (!UserLoginUtil.verify(request)) { // 如果用户未登录
            response.sendRedirect(loginURI);
        } else {
            if (requestURI.equals(logoutURI)) { // 用户登出时删除相关数据
                UserLoginUtil.logout(request);
            }
            filterChain.doFilter(request, response);
        }
    }

}
