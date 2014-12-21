package com.rick.android.halalfood.http;

import com.rick.android.halalfood.constants.ApiConstants;
import com.rick.android.volley.ApiConfig;

public class ApiConfigUtil {

    public static ApiConfig getApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setAppKey(ApiConstants.appKey);
        apiConfig.setAppSecret(ApiConstants.appSecret);
        apiConfig.setHost(ApiConstants.apiHost);
        apiConfig.setVersion(ApiConstants.apiVersion);
        apiConfig.setOverTime(ApiConstants.overTime);
        apiConfig.setShowLog(true);
        return apiConfig;
    }

}
