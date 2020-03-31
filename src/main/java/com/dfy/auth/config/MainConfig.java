package com.dfy.auth.config;

import com.dfy.auth.constants.AuthConstants;
import com.dfy.auth.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @description: 配置类
 * @author: DFY
 * @time: 2020/3/30 15:28
 */
@Configuration
public class MainConfig {

    /** 不作拦截的URL路径 */
    private String excludedURLPaths = "/index,/user/login,/user/register,/**/*.jpg,/**/*.css,/test/**";

    /** 用户登出路径 */
    private String logoutURL = "/user/logout";

    private String loginURI = "/user/login";

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setFilter(new AuthFilter());
        filterRegistrationBean.addInitParameter(AuthConstants.EXCLUDED_URI_PATHS, excludedURLPaths);
        filterRegistrationBean.addInitParameter(AuthConstants.LOGOUT_URI, logoutURL);
        filterRegistrationBean.addInitParameter(AuthConstants.LOGIN_URI, loginURI);
        return filterRegistrationBean;
    }
}
