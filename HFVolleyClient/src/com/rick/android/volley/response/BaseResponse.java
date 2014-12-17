package com.rick.android.volley.response;

import android.text.TextUtils;

public class BaseResponse {
    private String status;

    public BaseResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isStatusOk() {
        if (TextUtils.isEmpty(status) || status.equals("error")) {
            return false;
        } else if (status.equals("OK")) {
            return true;
        }
        return false;
    }
}
