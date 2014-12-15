package com.anjuke.android.http.volley.request;

import java.util.HashMap;

public class BatchRequestEntity {
    private String method;
    private String relative_url;
    private HashMap<String, String> query_params;
    private String post_fields;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRelative_url() {
        return relative_url;
    }

    public void setRelative_url(String relative_url) {
        this.relative_url = relative_url;
    }

    public HashMap<String, String> getQuery_params() {
        return query_params;
    }

    public void setQuery_params(HashMap<String, String> query_params) {
        this.query_params = query_params;
    }

    public BatchRequestEntity addQueryParams(String key, String value) {
        if (query_params == null) {
            query_params = new HashMap<String, String>();
        }
        query_params.put(key, value);
        return this;
    }

    public String getPost_fields() {
        return post_fields;
    }

    public void setPost_fields(String post_fields) {
        this.post_fields = post_fields;
    }
}
