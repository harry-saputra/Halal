/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anjuke.android.http.volley.request;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.anjuke.android.http.volley.util.CommonUtil;

public class JsonRequest<BaseResponse> extends BaseRequest<BaseResponse> {
    private final Class<BaseResponse> mClazz;
    private final Listener<BaseResponse> mListener;

    public JsonRequest(int method, String url, Map<String, String> params, Class<BaseResponse> clazz,
            Listener<BaseResponse> listener, ErrorListener errorListener) {
        super(method, url, params, errorListener, CommonUtil.getQtime());
        this.mClazz = clazz;
        this.mListener = listener;
    }

    public JsonRequest(String url, Map<String, String> params, Class<BaseResponse> clazz,
            Listener<BaseResponse> listener, ErrorListener errorListener) {
        super(Method.POST, url, params, errorListener, CommonUtil.getQtime());
        this.mClazz = clazz;
        this.mListener = listener;
    }

    public JsonRequest(int method, String url, String apiVersion, Map<String, String> params,
            Class<BaseResponse> clazz,
            Listener<BaseResponse> listener, ErrorListener errorListener) {
        super(method, url, apiVersion, params, errorListener, CommonUtil.getQtime());
        this.mClazz = clazz;
        this.mListener = listener;
    }

    public JsonRequest(int method, String host, String url, String apiVersion, Map<String, String> params,
            Class<BaseResponse> clazz,
            Listener<BaseResponse> listener, ErrorListener errorListener) {
        super(method, host, url, apiVersion, params, errorListener, CommonUtil.getQtime());
        this.mClazz = clazz;
        this.mListener = listener;
    }

    public JsonRequest(String url, String apiVersion, Map<String, String> params, Class<BaseResponse> clazz,
            Listener<BaseResponse> listener, ErrorListener errorListener) {
        super(Method.POST, url, apiVersion, params, errorListener, CommonUtil.getQtime());
        this.mClazz = clazz;
        this.mListener = listener;
    }

    @Override
    protected void deliverResponse(BaseResponse response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<BaseResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            CommonUtil.httpLog(arrowDown + mMethodUrl + divider + "结果：" + json);
            return Response.success(JSON.parseObject(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception ex) {
            return Response.error(new ParseError(ex));
        }
    }
}
