package com.anjuke.android.http.volley;

public class ApiConfig {
    private String host;
    private String apikey;
    private String privateKey;
    private String version;
    private String cookieVersion;
    private String authToken;
    private String userId;
    private String apiReturnType = "application/json";
    private int overTime;
    private boolean isShowLog;

    public ApiConfig() {
    }

    public ApiConfig(String privateKey, String apikey, String host, String version, String cookieVersion,
            boolean isShowLog,String apiReturnType) {
        this.privateKey = privateKey;
        this.apikey = apikey;
        this.host = host;
        this.cookieVersion = cookieVersion;
        this.version = version;
        this.isShowLog = isShowLog;
        this.apiReturnType = apiReturnType;
    }

    /** get And set method */

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCookieVersion() {
        return cookieVersion;
    }

    public void setCookieVersion(String cookieVersion) {
        this.cookieVersion = cookieVersion;
    }

    public int getOverTime() {
        return overTime;
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isShowLog() {
        return isShowLog;
    }

    public void setShowLog(boolean isShowLog) {
        this.isShowLog = isShowLog;
    }

    public String getApiReturnType() {
        return apiReturnType;
    }

    public void setApiReturnType(String apiReturnType) {
        this.apiReturnType = apiReturnType;
    }
    
}
