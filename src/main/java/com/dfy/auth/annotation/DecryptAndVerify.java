package com.dfy.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 该注解表明请求中的数据是加密的，需要进行解密
 * @author: DFY
 * @time: 2020/3/30 10:58
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DecryptAndVerify {

    /** 解密后的参数类型 */
    Class<?> decryptedClass();

}
