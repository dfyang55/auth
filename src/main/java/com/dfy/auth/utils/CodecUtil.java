package com.dfy.auth.utils;

import com.alibaba.fastjson.JSON;
import com.dfy.auth.vo.EncryptedReq;
import com.dfy.auth.vo.UserLoginReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @description: 加解密工具类
 * @author: DFY
 * @time: 2020/3/30 10:25
 */
@Slf4j
public class CodecUtil {

    /** AES加密密钥 */
    public static final byte[] AES_SECRET_KEY_BYTES = Base64Utils.decodeFromString("XjjkaLnlzAFbR399IP4kdQ==");

    /** SHA1加密密钥（用于增加加密的复杂度） */
    public static final String SHA1_SECRET_KEY = "QGZUanpSaSy9DEPQFVULJQ==";

    /**
     * 对数据进行加密，用AES加密再用Base64编码
     * @param data 待加密数据
     * @return
     */
    public static String aesEncrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // 加密算法/工作模式/填充方式
            byte[] dataBytes = data.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE,  new SecretKeySpec(AES_SECRET_KEY_BYTES, "AES"));
            byte[] result = cipher.doFinal(dataBytes);
            return Base64Utils.encodeToString(result);
        } catch (Exception e) {
            log.error("执行CodecUtil.aesEncrypt失败：data={}，异常：{}", data, e);
        }
        return null;
    }

    /**
     * 对数据进行加密，用AES解密
     * @param encryptedDataBase64
     * @return
     */
    public static String aesDecrypt(String encryptedDataBase64) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // 加密算法/工作模式/填充方式
            byte[] dataBytes = Base64Utils.decodeFromString(encryptedDataBase64);
            cipher.init(Cipher.DECRYPT_MODE,  new SecretKeySpec(AES_SECRET_KEY_BYTES, "AES"));
            byte[] result = cipher.doFinal(dataBytes);
            return new String(result);
        } catch (Exception e) {
            log.error("执行CodecUtil.aesDecrypt失败：data={}，异常：{}", encryptedDataBase64, e);
        }
        return null;
    }

    /**
     * 对数据进行加密，用SHA1加密再转换为16进制
     * @param data
     * @return
     */
    public static String sha1Encrypt(String data) {
        return DigestUtils.sha1Hex(data + SHA1_SECRET_KEY);
    }

    /** AES密钥长度，支持128、192、256 */
    private static final int AES_SECRET_KEY_LENGTH = 128;

    private static String generateAESSecretKeyBase64(String key) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(AES_SECRET_KEY_LENGTH);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64Utils.encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(generateAESSecretKeyBase64("dfy")); // 生成一个AES私钥 azGH8/xbxPhbnGbpClKpGg==   QGZUanpSaSy9DEPQFVULJQ==
//        String encData = aesEncrypt("dfy");
//        System.out.println(encData); // 使用AES进行加密
//        String decData = aesDecrypt(encData);
//        System.out.println(decData);
//        System.out.println(sha1Encrypt("dfy")); // 使用SHA1进行加密 54a18b6b252567733db853dd93d5999920c3581b
        System.out.println(sha1Encrypt("this is a test"));
        long timestamp = System.currentTimeMillis();
        System.out.println("时间戳：" + timestamp);
        UserLoginReq userLoginReq = new UserLoginReq("admin", "admin");
        String data = JSON.toJSONString(userLoginReq);
        System.out.println("加密前的数据：" + data);
        String encryptedData = CodecUtil.aesEncrypt(data);
        System.out.println("加密后的数据：" + encryptedData);
        String sign = CodecUtil.sha1Encrypt(encryptedData + timestamp);
        System.out.println("签名：" + sign);
        EncryptedReq<UserLoginReq> encryptedReq = new EncryptedReq<>();
        encryptedReq.setEncryptedData(encryptedData);
        encryptedReq.setTimestamp(timestamp);
        encryptedReq.setSign(sign);
        System.out.println("加密后的请求：" + JSON.toJSONString(encryptedReq));
    }
}
