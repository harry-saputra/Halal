package com.rick.android.halalfood.http;

import java.util.Map;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.anjuke.android.http.volley.HFVolley;
import com.anjuke.android.http.volley.request.HFJsonRequest;
import com.rick.android.halalfood.constants.ApiUrls;
import com.rick.android.halalfood.http.response.FindBusinessResponse;

/**
 * 商户类 API
 * 
 * @author hua
 */
public class BusinessApi {

    /**
     * 搜索商户 business/find_businesses
     * 
     * @param url
     * @param paramMap
     * @param sucessListener
     * @param errorListener
     * @param requestTag
     */
    public static void findBussiness(Map<String, String> paramMap, Listener<FindBusinessResponse> sucessListener,
            ErrorListener errorListener, String requestTag) {
        HFJsonRequest<FindBusinessResponse> mRequest = new HFJsonRequest<FindBusinessResponse>(
                Method.GET, ApiUrls.FindBussiness, paramMap, FindBusinessResponse.class, sucessListener, errorListener);
        HFVolley.addtoRequestQueue(mRequest, requestTag);
    }

}
