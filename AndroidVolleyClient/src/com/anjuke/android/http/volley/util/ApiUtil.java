package com.anjuke.android.http.volley.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.anjuke.android.http.volley.AndroidVolleyClient;
import com.anjuke.android.http.volley.constant.CommonContants;

public class ApiUtil {

    /**
     * 获得http请求地址
     * 
     * @param method
     * @param host
     * @param key_url
     * @param apiVersion
     * @param params
     * @param qtime
     * @return
     */
    public static String getCommonUrl(int method, String host, String key_url,
            String apiVersion, Map<String, String> params, String qtime) {
        StringBuffer tempBuffer = addCommonParams(host, key_url, apiVersion,
                qtime);
        if (method == Method.GET) {
            addGetParams(tempBuffer, params);
        }
        return tempBuffer.toString();
    }

    private static StringBuffer addCommonParams(String host, String key_url,
            String apiVersion, String qtime) {
        StringBuffer tempBuffer = new StringBuffer();
        tempBuffer.append(host);
        tempBuffer.append(apiVersion);
        tempBuffer.append(key_url);
        tempBuffer.append("?");
        tempBuffer.append(getExtraParamsNoFirstAnd(qtime));
        return tempBuffer;
    }

    private static String addGetParams(StringBuffer buffer,
            Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                buffer.append("&")
                        .append(key)
                        .append("=")
                        .append(strUrlEncode(params.get(key)));
            }
        }
        return buffer.toString();
    }

    public static String getExtraParamsNoFirstAnd(String qtime) {
        StringBuffer retBuffer = new StringBuffer();

        HashMap<String, String> temp = getExtraParamsHashMap(qtime);
        int index = 0;
        for (String key : temp.keySet()) {
            index++;
            if (index > 1)
                retBuffer.append("&");

            retBuffer.append(key).append("=").append(strUrlEncode(temp.get(key)));
        }

        return retBuffer.toString();
    }

    public static HashMap<String, String> getExtraParamsHashMap(String qtime) {
        HashMap<String, String> ret = new HashMap<String, String>();
        return ret;
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

    /**
     * 给请求产生需要放到header里面的参数对-------get
     * 
     * @param methodUrl
     * @param params
     * @return
     */
    public static HashMap<String, String> getGetHeadSigParams(String methodUrl,
            String specifiedVersion, Map<String, String> params, String qtime) {
        HashMap<String, String> needSigParams = new HashMap<String, String>();
        needSigParams.putAll(params);
        needSigParams.putAll(getExtraParamsHashMap(qtime));
        String sigUrl = AndroidVolleyClient.config.getVersion() + methodUrl;
        if (!TextUtils.isEmpty(specifiedVersion)) {
            sigUrl = specifiedVersion + methodUrl;
        }
        String sig = ApiSignUtil.sigGet(needSigParams, sigUrl, AndroidVolleyClient.config.getApikey(),
                AndroidVolleyClient.config.getPrivateKey());
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put(CommonContants.KEY_SIG, sig);
        headerMap.put(CommonContants.KEY_API_KEY, AndroidVolleyClient.config.getApikey());
        headerMap.put(CommonContants.KEY_ACCEPT, AndroidVolleyClient.config.getApiReturnType());
        return headerMap;
    }

    /**
     * 给请求产生需要放到header里面的参数map------post
     * 
     * @param methodUrl
     * @param params
     * @return
     */
    public static HashMap<String, String> getPostHeadSigParams(
            String methodUrl, String specifiedVersion,
            Map<String, String> params, String qtime) {
        String sigUrl = AndroidVolleyClient.config.getVersion() + methodUrl;
        if (specifiedVersion != null) {
            sigUrl = specifiedVersion + methodUrl;
        }
        String sig = ApiSignUtil.sigPost(getExtraParamsHashMap(qtime), params, sigUrl,
                AndroidVolleyClient.config.getApikey(), AndroidVolleyClient.config.getPrivateKey());
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put(CommonContants.KEY_SIG, sig);
        headerMap.put(CommonContants.KEY_API_KEY, AndroidVolleyClient.config.getApikey());
        headerMap.put(CommonContants.KEY_ACCEPT, AndroidVolleyClient.config.getApiReturnType());
        headerMap.put(CommonContants.KEY_CONTENT_TYPE, AndroidVolleyClient.config.getApiReturnType());
        return headerMap;
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
