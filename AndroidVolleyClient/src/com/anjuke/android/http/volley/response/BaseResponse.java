
package com.anjuke.android.http.volley.response;

import android.text.TextUtils;

public class BaseResponse {
    private String status;
    private String errcode;

    public String getStatus() {
        return status;
    }

    public BaseResponse(String errorStr) {
        message = errorStr;
    }

    public BaseResponse() {
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public boolean isStatusOk() {
        if (TextUtils.isEmpty(status) || status.equals("error")) {
            return false;
        } else if (status.equals("ok")) {
            return true;
        }
        return false;
    }
}
