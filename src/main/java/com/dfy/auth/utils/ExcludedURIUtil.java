package com.dfy.auth.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 不作拦截的URL工具类
 * @author: DFY
 * @time: 2020/3/30 15:14
 */
public class ExcludedURIUtil {

    /** 单例 */
    private static ExcludedURIUtil excludedUriUtil;

    /** uri、正则表达式映射表 */
    private static Map<String, String> uriRegexMap = new HashMap<String, String>();

    private ExcludedURIUtil() {}

    /**
     * 获取单例
     * @param uris
     * @return
     */
    public static ExcludedURIUtil getInstance(String[] uris) {
        if (excludedUriUtil == null) {
            synchronized (ExcludedURIUtil.class) {
                if (excludedUriUtil == null) {
                    excludedUriUtil = new ExcludedURIUtil();
                    if (uris != null && uris.length > 0) {
                        for (String uri : uris) {
                            String regex = uri2Regex(uri);
                            uriRegexMap.put(uri, regex);
                        }
                    }
                }
            }
        }
        return excludedUriUtil;
    }

    /**
     * 判断给定uri是否匹配映射表中的正则表达式
     * @param uri
     * @return
     */
    public boolean match(String uri) {
        for (String regex : uriRegexMap.values()) {
            if (uri.matches(regex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将URI转换为正则表达式
     * URL中特殊字符（均可使用%加16进制表示）
     * +：空格  /：分隔目录和子目录  ?分隔实际的URL和参数  %：指定特殊字符  #：书签  &：参数分隔符  =：参数的值
     * @param uri
     * @return
     */
    public static String uri2Regex(String uri) {
        int lastPointIndex = uri.lastIndexOf('.');
        char[] uriChars = uri.toCharArray();
        StringBuilder regexBuilder = new StringBuilder();
        for (int i = 0, length = uriChars.length; i < length; i++) {
            if (uriChars[i] == '*' && uriChars[i + 1] == '*') {
                regexBuilder.append("(/[^/]*)*");
                i++;
            } else if (uriChars[i] == '*') {
                regexBuilder.append("/[^/]*");
            } else if (uriChars[i] == '.' && i == lastPointIndex) {
                regexBuilder.append("\\.");
                regexBuilder.append(uri.substring(i + 1));
                break;
            } else if (uriChars[i] == '/') {
                if (!uri.substring(i + 1, i + 2).equals("*")) {
                    regexBuilder.append("/");
                }
            } else {
                regexBuilder.append(uriChars[i]);
            }
        }
        return regexBuilder.toString();
    }
}
