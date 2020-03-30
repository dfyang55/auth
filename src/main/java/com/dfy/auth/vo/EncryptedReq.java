package com.dfy.auth.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 加密请求
 * @author: DFY
 * @time: 2020/3/30 10:16
 */
@Data
public class EncryptedReq<T> {

    /** 签名 */
    @NotBlank(message = "用户签名不能为空")
    private String sign;

    /** 加密请求数据 */
    @NotBlank(message = "加密请求不能为空")
    private String encryptedData;

    /** 原始请求数据（解密后回填到对象） */
    private T data;

    /** 请求的时间戳 */
    @NotNull(message = "时间戳不能为空")
    private Long timestamp;

}
