package com.anjuke.android.http.volley.request;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.RetryPolicy;
import com.anjuke.android.http.volley.AndroidVolleyClient;
import com.anjuke.android.http.volley.util.ApiUtil;
import com.anjuke.android.http.volley.util.CommonUtil;

public class BaseRequest<T> extends Request<T> {

    protected String divider = "▬▬▬▬";
    protected String arrowUp = "◀▬▬▬▬▬";
    protected String arrowDown = "▬▬▬▬▬▶";
    private String mQtime;

    String mMethodUrl;
    String mHost;
    String mVersion;
    Map<String, String> mParams;

    protected BaseRequest(int method, String methodUrl,
            Map<String, String> params, ErrorListener listener, String qtime) {
        super(method, methodUrl, listener);
        mQtime = qtime;
        mMethodUrl = methodUrl;
        mParams = params;
        if (mParams == null) {
            mParams = new HashMap<String, String>();
        }
        showRequestLog();
    }

    protected BaseRequest(int method, String methodUrl, String apiVersion,
            Map<String, String> params, ErrorListener listener, String qtime) {
        super(method, ApiUtil.getCommonUrl(method, null, methodUrl, apiVersion,
                params, qtime), listener);
        mQtime = qtime;
        this.mVersion = apiVersion;
        mParams = params;
        mMethodUrl = methodUrl;
        if (mParams == null) {
            mParams = new HashMap<String, String>();
        }
        showRequestLog();
    }

    protected BaseRequest(int method, String host, String methodUrl,
            String apiVersion, Map<String, String> params,
            ErrorListener listener, String qtime) {
        super(method, ApiUtil.getCommonUrl(method, host, methodUrl, apiVersion,
                params, qtime), listener);
        mQtime = qtime;
        this.mVersion = apiVersion;
        mParams = params;
        mMethodUrl = methodUrl;
        mHost = host;
        if (mParams == null) {
            mParams = new HashMap<String, String>();
        }
        showRequestLog();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return JSON.toJSONString(mParams).getBytes();
    }

    @Override
    public String getBodyContentType() {
        return super.getBodyContentType();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> mHeaders = null;
        switch (getMethod()) {
            case Method.GET:
                mHeaders = ApiUtil.getGetHeadSigParams(mMethodUrl, mVersion,
                        mParams, mQtime);
                break;
            case Method.POST:
                mHeaders = ApiUtil.getPostHeadSigParams(mMethodUrl, mVersion,
                        mParams, mQtime);
                break;

            default:
                break;
        }
        if (mHeaders == null) {
            mHeaders = new HashMap<String, String>();
        }
        return mHeaders;
    }

    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        /**
         * 修改超时限制
         */
        RetryPolicy rp = new DefaultRetryPolicy(AndroidVolleyClient.config.getOverTime(),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return super.setRetryPolicy(rp);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return null;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(T response) {

    }

    /**
     * 打印Http Log
     */
    private void showRequestLog() {
        if (!AndroidVolleyClient.config.isShowLog()) {
            return;
        }
        String methodStr = "get";
        if (getMethod() == Method.POST) {
            methodStr = "post";
        }
        String url = "";
        String paramsJson = "";
        String paramsString = "";
        url = ApiUtil.getCommonUrl(getMethod(), mHost, mMethodUrl,
                mVersion, mParams, mQtime);
        if (mParams != null) {
            paramsJson = JSON.toJSONString(mParams);
            paramsString = ApiUtil.getRequestParamsAsGetRequest(mParams);
        }
        CommonUtil.httpLog(arrowUp + mMethodUrl + divider + methodStr
                + " 参数：" + paramsJson);
        CommonUtil.httpLog(arrowUp + mMethodUrl + divider + "完整URL：" + url
                + paramsString);
    }

}
