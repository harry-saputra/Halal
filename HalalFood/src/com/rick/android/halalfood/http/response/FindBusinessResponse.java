package com.rick.android.halalfood.http.response;

import com.anjuke.android.http.volley.response.BaseResponse;

public class FindBusinessResponse extends BaseResponse {
    private String count;
    private String total_count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

}
