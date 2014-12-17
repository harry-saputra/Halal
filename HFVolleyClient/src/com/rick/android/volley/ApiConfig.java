package com.rick.android.volley;

public class ApiConfig {
    
    private String appKey;
    private String appSecret;
    private String host;
    private String version;
    private int overTime;
    private boolean isShowLog;

    public ApiConfig() {
    }

    public ApiConfig(String appKey, String appSecret, String host, String version, int overTime,
            boolean isShowLog) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.host = host;
        this.version = version;
        this.overTime = overTime;
        this.isShowLog = isShowLog;
    }

    /** get And set method */

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
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

    public int getOverTime() {
        return overTime;
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }

    public boolean isShowLog() {
        return isShowLog;
    }

    public void setShowLog(boolean isShowLog) {
        this.isShowLog = isShowLog;
    }

}
