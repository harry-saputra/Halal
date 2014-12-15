package com.anjuke.android.http.volley.response;

import java.util.List;

public class BatchRequestData {
    private String responses;
    private List<String> result;

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }

}
