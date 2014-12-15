package com.anjuke.android.http.volley.request;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.anjuke.android.http.volley.util.CommonUtil;

public class StringRequest extends BaseRequest<String> {
    private final Listener<String> mListener;

    public StringRequest(int method, String url, Map<String, String> params,
            Listener<String> sucessListener, ErrorListener listener) {
        super(method, url, params, listener, CommonUtil.getQtime());
        mListener = sucessListener;
    }

    public StringRequest(int method, String url, String apiVersion, Map<String, String> params,
            Listener<String> sucessListener, ErrorListener listener) {
        super(method, url, apiVersion, params, listener, CommonUtil.getQtime());
        mListener = sucessListener;
    }

    public StringRequest(int method, String host, String methodUrl, String apiVersion, Map<String, String> params,
            Listener<String> sucessListener, ErrorListener listener) {
        super(method, host, methodUrl, apiVersion, params, listener, CommonUtil.getQtime());
        mListener = sucessListener;
    }

    public StringRequest(String url, Map<String, String> params, Listener<String> sucessListener,
            ErrorListener listener) {
        this(Method.GET, url, params, sucessListener, listener);
    }

    @Override
    protected void deliverResponse(String response) {
        CommonUtil.httpLog("API返回字符串结果████" + response);
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

}
