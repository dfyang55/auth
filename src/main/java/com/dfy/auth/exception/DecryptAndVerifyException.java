package com.dfy.auth.exception;

/**
 * @description: 解密验证异常
 * @author: DFY
 * @time: 2020/3/30 13:58
 */
public class DecryptAndVerifyException extends RuntimeException {

    public DecryptAndVerifyException(String message) {
        super(message);
    }

}
