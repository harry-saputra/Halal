package com.anjuke.android.http.volley.util;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import android.annotation.SuppressLint;

/**
 * 签名工具(大众点评)
 * 
 * @author hua
 */
public class ApiSignUtil {

    /**
     * 签名(点评api)<br>
     * http://developer.dianping.com/app/documentation/signature <br>
     * github:https://github.com/dianping/dianping-open-sdk
     * 
     * @param appKey
     * @param secret
     * @param paramMap
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String sign(String appKey, String secret, Map<String, String> paramMap) {
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
