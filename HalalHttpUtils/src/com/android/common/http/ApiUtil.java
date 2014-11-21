
package com.android.common.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import android.annotation.SuppressLint;

/**
 * halalfood http参数格式化，并作签名
 * 
 * @author hua
 */
public class ApiUtil {

    /** 点评的应用信息 */
    public static final String appKey = "09936603";
    public static final String secret = "865aed6bfa484976846847d00271a044";

    /**
     * 获取请求字符串
     * 
     * @param appKey
     * @param secret
     * @param paramMap
     * @return
     */
    public static String getQueryString(Map<String, String> paramMap) {
        String sign = sign(paramMap);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
        for (Entry<String, String> entry : paramMap.entrySet()) {
            stringBuilder.append('&').append(entry.getKey()).append('=').append(entry.getValue());
        }
        String queryString = stringBuilder.toString();
        return queryString;
    }

    /**
     * 获取请求字符串，参数值进行UTF-8处理
     * 
     * @param appKey
     * @param secret
     * @param paramMap
     * @return
     */
    public static String getUrlEncodedQueryString(Map<String, String> paramMap) {
        String sign = sign(paramMap);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
        for (Entry<String, String> entry : paramMap.entrySet()) {
            try {
                stringBuilder.append('&').append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(),
                        "UTF-8"));
            } catch (UnsupportedEncodingException e) {

            }
        }
        String queryString = stringBuilder.toString();
        return queryString;
    }

    /**
     * 签名
     * 
     * @param appKey
     * @param secret
     * @param paramMap
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String sign(Map<String, String> paramMap) {
        if (paramMap == null || paramMap.size() == 0) {
            return null;
        }

        // 参数名排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

        // 拼接参数
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appKey);
        for (String key : keyArray) {
            stringBuilder.append(key).append(paramMap.get(key));
        }
        stringBuilder.append(secret);
        String codes = stringBuilder.toString();

        // SHA-1签名
        // For Android
        String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toUpperCase();

        return sign;
    }
}
