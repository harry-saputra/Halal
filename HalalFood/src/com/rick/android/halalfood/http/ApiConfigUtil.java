package com.rick.android.halalfood.http;

import com.anjuke.android.http.volley.ApiConfig;
import com.rick.android.halalfood.constants.ApiConstants;

public class ApiConfigUtil {

    public static ApiConfig getApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setAppKey(ApiConstants.appKey);
        apiConfig.setAppSecret(ApiConstants.appSecret);
        apiConfig.setHost(ApiConstants.apiHost);
        apiConfig.setVersion(ApiConstants.apiVersion);
        apiConfig.setOverTime(ApiConstants.overTime);
        return apiConfig;
    }

}
