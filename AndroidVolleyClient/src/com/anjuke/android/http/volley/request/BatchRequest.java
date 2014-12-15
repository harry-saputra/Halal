package com.anjuke.android.http.volley.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.anjuke.android.http.volley.response.BatchRequestResponse;
import com.anjuke.android.http.volley.util.CommonUtil;

public class BatchRequest extends BaseRequest<BatchRequestResponse> {
    Listener<BatchRequestResponse> mSuccessListener;
    int mBatchSubRequestNum;

    public BatchRequest(int method, int subRequestNum, String methodUrl, Map<String, String> params,
            Listener<BatchRequestResponse> successListener, ErrorListener listener, String qtime) {
        super(method, methodUrl, params, listener, qtime);
        mBatchSubRequestNum = subRequestNum;
        mSuccessListener = successListener;
    }

    @Override
    protected void deliverResponse(BatchRequestResponse response) {
        super.deliverResponse(response);
        mSuccessListener.onResponse(response);
    }

    @Override
    protected Response<BatchRequestResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String parsed;
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            BatchRequestResponse mBatchRequestResponse = JSON.parseObject(parsed, BatchRequestResponse.class);
            String ss = mBatchRequestResponse.getData().getResponses();
            CommonUtil.httpLog(arrowDown + mMethodUrl + divider + "结果：" + parsed);
            CommonUtil.httpLog("子请求的个数" + mBatchSubRequestNum);
            List<String> results = new ArrayList<String>();
            for (int i = 0; i < mBatchSubRequestNum; i++) {
                String item = JSON.parseObject(ss).getJSONObject(i + "sub")
                        .getString("body");
                results.add(i, item);
            }
            mBatchRequestResponse.getData().setResult(results);
            return Response.success(mBatchRequestResponse,
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    public static class Builder {
        HashMap<String, BatchRequestEntity> my;
        Map<String, String> params = new HashMap<String, String>();
        int mapKey = 0;

        public Builder() {
            my = new HashMap<String, BatchRequestEntity>();
        }

        public Builder addSubRequest(BatchRequestEntity batchRequest) {
            my.put(mapKey + "sub", batchRequest);
            mapKey++;
            return this;
        }

        public BatchRequest build(Listener<BatchRequestResponse> successListener, ErrorListener listener) {
            params.put("requests", JSON.toJSONString(my));
            return new BatchRequest(Method.POST, mapKey, "batch/", params, successListener, listener,
                    CommonUtil.getQtime());
        }

    }

}
