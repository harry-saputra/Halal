
package com.android.halalfood.http;

import java.util.Map;

import com.android.halalfood.constants.ApiConstants.Method;
import com.android.halalfood.constants.ApiUrls;

/**
 * Api全部的请求
 * 
 * @author hua
 */
public class HalalApi extends ApiTools {

    /**
     * 搜索商户
     * @param paramMap
     * @return
     */
    public static String findBussiness(Map<String, String> paramMap) {
        return ApiTools.requestApi(ApiUrls.FindBussiness, Method.get, paramMap);
    }

}
