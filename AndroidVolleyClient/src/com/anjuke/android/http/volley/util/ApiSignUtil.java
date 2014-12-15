package com.anjuke.android.http.volley.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

/**
 * 签名工具
 * 
 * @author hua
 */
public class ApiSignUtil {
    
    public static String sigGet(HashMap<String, String> params, String sigUrl, String apiKey,
            String privateKey) {
        if (apiKey == null) {
            throw new RuntimeException("apiKey is empty.");
        }

        if (privateKey == null) {
            throw new RuntimeException("privateKey is empty.");
        }

        String ret = null;
        List<String> keyList = new ArrayList<String>();

        Iterator<Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            keyList.add(key + "=" + value);
        }

        // // --------添加APIKEY----------
        // keyList.add("api_key" + "=" + sApiKey);

        // -----------排序-----------
        Collections.sort(keyList);

        StringBuffer sb = new StringBuffer();
        for (String string : keyList) {
            if (sb.toString().length() == 0) {
                sb.append(string);
            } else {
                sb.append("&" + string);
            }
        }

        // -------添加了pirvateKey的url，作为被加密的原始字符串---------
        String url_key = sigUrl + sb.toString() + privateKey;
        ret = CommonUtil.Md5(url_key);

        return ret;
    }

    /**
     * 获取post的加密sig
     * 
     * @param extraParams 公共参数
     * @param params 传递参数
     * @param sigUrl
     * @param apiKey
     * @param privateKey
     * @return
     */
    public static String sigPost(HashMap<String, String> extraParams, Map<String, String> params,
            String sigUrl, String apiKey, String privateKey) {
        if (apiKey == null) {
            throw new RuntimeException("apiKey is empty.");
        }

        if (privateKey == null) {
            throw new RuntimeException("privateKey is empty.");
        }

        String ret = null;
        List<String> keyList = new ArrayList<String>();

        Iterator<Entry<String, String>> it = extraParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            keyList.add(key + "=" + value);
        }

        // // --------添加APIKEY----------
        // keyList.add("api_key" + "=" + sApiKey);

        // -----------排序-----------
        Collections.sort(keyList);

        StringBuffer sb = new StringBuffer();
        for (String string : keyList) {
            if (sb.toString().length() == 0) {
                sb.append(string);
            } else {
                sb.append("&" + string);
            }
        }

        // post表单传递的参数
        String paramStr = JSON.toJSONString(params);

        // -------添加了pirvateKey的url，作为被加密的原始字符串---------
        String url_key = sigUrl + sb.toString() + paramStr + privateKey;
        // DevUtil.v("zlq", "签名的string" + url_key);
        ret = CommonUtil.Md5(url_key);
        return ret;
    }
}
