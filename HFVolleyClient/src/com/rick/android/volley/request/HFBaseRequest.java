package com.rick.android.volley.request;

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
import com.rick.android.volley.HFVolley;
import com.rick.android.volley.util.ApiTool;
import com.rick.android.volley.util.CommonUtil;

public class HFBaseRequest<T> extends Request<T> {

    protected String divider = "▬▬▬▬";
    protected String arrowUp = "◀▬▬▬▬▬";
    protected String arrowDown = "▬▬▬▬▬▶";

    String mMethodUrl;
    String mHost;
    String mVersion;
    Map<String, String> mParams;

    protected HFBaseRequest(int method, String methodUrl,
            Map<String, String> params, ErrorListener listener) {
        super(method, ApiTool.getCommonUrl(method, HFVolley.config.getHost(), methodUrl, HFVolley.config.getVersion(),
                params), listener);
        mMethodUrl = methodUrl;
        mParams = params;
        if (mParams == null) {
            mParams = new HashMap<String, String>();
        }
        showRequestLog();
    }

    protected HFBaseRequest(int method, String methodUrl, String apiVersion,
            Map<String, String> params, ErrorListener listener) {
        super(method, ApiTool.getCommonUrl(method, HFVolley.config.getHost(), methodUrl, apiVersion,
                params), listener);
        this.mVersion = apiVersion;
        mParams = params;
        mMethodUrl = methodUrl;
        if (mParams == null) {
            mParams = new HashMap<String, String>();
        }
        showRequestLog();
    }

    protected HFBaseRequest(int method, String host, String methodUrl,
            String apiVersion, Map<String, String> params,
            ErrorListener listener) {
        super(method, ApiTool.getCommonUrl(method, host, methodUrl, apiVersion,
                params), listener);
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
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        /**
         * 修改超时限制
         */
        RetryPolicy rp = new DefaultRetryPolicy(HFVolley.config.getOverTime(),
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
        if (!HFVolley.config.isShowLog()) {
            return;
        }
        String methodStr = "get";
        if (getMethod() == Method.POST) {
            methodStr = "post";
        }
        String url = "";
        String paramsJson = "";
        String paramsString = "";
        url = ApiTool.getCommonUrl(getMethod(), mHost, mMethodUrl,
                mVersion, mParams);
        if (mParams != null) {
            paramsJson = JSON.toJSONString(mParams);
            paramsString = ApiTool.getRequestParamsAsGetRequest(mParams);
        }
        CommonUtil.httpLog(arrowUp + mMethodUrl + divider + methodStr
                + " 参数：" + paramsJson);
        CommonUtil.httpLog(arrowUp + mMethodUrl + divider + "完整URL：" + url
                + paramsString);
    }

}
