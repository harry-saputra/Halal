package com.rick.android.volley.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.android.volley.Request.Method;
import com.rick.android.volley.HFVolley;

/**
 * volley api 工具
 * 
 * @author hua
 */
public class ApiTool {

    /**
     * 获得http请求地址
     * 
     * @param method
     * @param host
     * @param key_url
     * @param apiVersion
     * @param params
     * @return
     */
    public static String getCommonUrl(int method, String host, String key_url,
            String apiVersion, Map<String, String> params) {
        StringBuffer tempBuffer = addCommonParams(method, host, key_url, apiVersion);
        if (method == Method.GET) {
            tempBuffer.append(
                    getUrlEncodedQueryString(HFVolley.config.getAppKey(),
                            HFVolley.config.getAppSecret(), params)
                    );
        }
        return tempBuffer.toString();
    }

    private static StringBuffer addCommonParams(int method, String host, String key_url,
            String apiVersion) {
        StringBuffer tempBuffer = new StringBuffer();
        tempBuffer.append(host);
        tempBuffer.append(apiVersion);
        tempBuffer.append(key_url);
        if (method == Method.GET) {
            tempBuffer.append("?");
        }
        return tempBuffer;
    }

    /**
     * 获取请求字符串
     * 
     * @param appKey
     * @param secret
     * @param paramMap
     * @return
     */
    public static String getQueryString(String appKey, String secret, Map<String, String> paramMap) {
        String sign = ApiSignUtil.sign(appKey, secret, paramMap);
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
    public static String getUrlEncodedQueryString(String appKey, String secret, Map<String, String> paramMap) {
        String sign = ApiSignUtil.sign(appKey, secret, paramMap);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
        for (Entry<String, String> entry : paramMap.entrySet()) {
            try {
                stringBuilder.append('&').append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String queryString = stringBuilder.toString();
        return queryString;
    }

    /**
     * url encode
     *
     * @param str
     * @return
     */
    public static String strUrlEncode(String str) {
        if (str != null) {
            try {
                return URLEncoder.encode(str, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getRequestParamsAsGetRequest(Map<String, String> mParams) {
        Iterator<Map.Entry<String, String>> mIterator = mParams.entrySet().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        while (mIterator.hasNext()) {
            Map.Entry<String, String> entry = mIterator.next();
            stringBuffer.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return stringBuffer.toString();
    }
}
