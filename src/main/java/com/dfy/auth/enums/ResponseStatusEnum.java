package com.dfy.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 响应状态枚举
 * @author: DFY
 * @time: 2020/3/30 11:04
 */
@Getter
@AllArgsConstructor
public enum ResponseStatusEnum {
    SUCCESS(200, "成功"),
    USER_AUTH_FAILURE(300, "用户认证失败"),
    ;

    private int status;

    private String msg;

}

