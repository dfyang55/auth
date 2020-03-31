package com.dfy.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 用户登录响应
 * @author: DFY
 * @time: 2020/3/31 10:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRes {

    /** 用户登录唯一token认证 */
    private String authToken;

}
