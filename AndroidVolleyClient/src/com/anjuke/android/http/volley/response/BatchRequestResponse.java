package com.anjuke.android.http.volley.response;

public class BatchRequestResponse extends BaseResponse {
    private BatchRequestData data;

    public BatchRequestData getData() {
        return data;
    }

    public void setData(BatchRequestData data) {
        this.data = data;
    }
}
