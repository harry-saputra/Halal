package com.rick.android.halalfood.http.response;

import java.util.List;

import com.rick.android.halalfood.model.Businesse;
import com.rick.android.volley.response.BaseResponse;

public class FindBusinessResponse extends BaseResponse {
    private String count;
    private String total_count;
    private List<Businesse> businesses;

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

    public List<Businesse> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Businesse> businesses) {
        this.businesses = businesses;
    }

}
