package com.dfy.auth.aspect;

import com.alibaba.fastjson.JSON;
import com.dfy.auth.annotation.DecryptAndVerify;
import com.dfy.auth.exception.DecryptAndVerifyException;
import com.dfy.auth.utils.CodecUtil;
import com.dfy.auth.vo.EncryptedReq;
import com.dfy.auth.vo.UserLoginReq;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @description: 加密请求解密及验证切面
 * @author: DFY
 * @time: 2020/3/30 11:18
 */
@Slf4j
@Aspect
@Component
public class DecryptAndVerifyAspect {

    @Pointcut("@annotation(com.dfy.auth.annotation.DecryptAndVerify)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            throw new DecryptAndVerifyException(joinPoint.getSignature().getName() + "，参数为空");
        }
        EncryptedReq encryptedReq = null;
        for (Object obj : args) {
            if (obj instanceof EncryptedReq) {
                encryptedReq = (EncryptedReq) obj;
                break;
            }
        }
        if (encryptedReq == null) {
            throw new DecryptAndVerifyException(joinPoint.getSignature().getName() + "，参数中无待解密类");
        }
        String decryptedData = decryptAndVerify(encryptedReq);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DecryptAndVerify annotation = methodSignature.getMethod().getAnnotation(DecryptAndVerify.class);
        if (annotation == null || annotation.decryptedClass() == null) {
            throw new DecryptAndVerifyException(joinPoint.getSignature().getName() + "，未指定解密类型");
        }
        encryptedReq.setData(JSON.parseObject(decryptedData, annotation.decryptedClass()));
        return joinPoint.proceed();
    }

    private String decryptAndVerify(EncryptedReq encryptedReq) {
        String sign = CodecUtil.sha1Encrypt(encryptedReq.getEncryptedData() + encryptedReq.getTimestamp());
        if (sign.equals(encryptedReq.getSign())) {
            return CodecUtil.aesDecrypt(encryptedReq.getEncryptedData());
        } else {
            throw new DecryptAndVerifyException("验签失败：" + JSON.toJSONString(encryptedReq));
        }
    }
}
