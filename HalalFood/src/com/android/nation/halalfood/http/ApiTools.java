
package com.android.nation.halalfood.http;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.android.common.http.NativeHttpUtil;
import com.android.nation.halalfood.constants.ApiConstants;
import com.android.nation.halalfood.constants.ApiConstants.Method;
import com.android.nation.halalfood.utils.DevUtil;

public class ApiTools {

    /**
     * get或post向apiurl发送request
     * 
     * @param apiUrl
     * @param method
     * @param paramMap
     * @return
     */
    public static String requestApi(String apiUrl, int method, Map<String, String> paramMap) {
        String result = null;
        if (method == Method.get) {
            result = NativeHttpUtil.get(ApiConstants.apiHost + ApiConstants.apiVersion + apiUrl, paramMap);
        } else if (method == Method.post) {
            result = NativeHttpUtil.post(ApiConstants.apiHost + ApiConstants.apiVersion + apiUrl, paramMap);
        }
        // debug 模式下才打印http请求日志
        if (DevUtil.isDebug()) {
            String methodString = null;
            if (method == Method.get) {
                methodString = "Get";
            } else {
                methodString = "Post";
            }
            DevUtil.i("halal_http_param", methodString + " —— " + JSON.toJSONString(paramMap, true));
            DevUtil.i("halal_http_result", result + "");
        }
        return result;
    }

    /**
     * get或post向apiurl发送request (可设置api版本)
     * 
     * @param apiUrl
     * @param method
     * @param version
     * @param paramMap
     * @return
     */
    public static String requestApi(String apiUrl, int method, String version, Map<String, String> paramMap) {
        String result = null;
        if (method == Method.get) {
            result = NativeHttpUtil.get(ApiConstants.apiHost + version + apiUrl, paramMap);
        } else if (method == Method.post) {
            result = NativeHttpUtil.post(ApiConstants.apiHost + version + apiUrl, paramMap);
        }
        // debug 模式下才打印http请求日志
        if (DevUtil.isDebug()) {
            String methodString = null;
            if (method == Method.get) {
                methodString = "Get";
            } else {
                methodString = "Post";
            }
            DevUtil.i("halal_http_param", methodString + " —— " + JSON.toJSONString(paramMap, true));
            DevUtil.i("halal_http_result", result);
        }
        return null;
    }

}
