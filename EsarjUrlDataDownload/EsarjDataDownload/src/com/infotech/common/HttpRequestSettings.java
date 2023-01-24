package com.infotech.common;

public class HttpRequestSettings {
    private static final int timeout_millisecond = 180000;
    private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36";

    private boolean detail_mode = false;
    
    public HttpRequestSettings() {
            this.detail_mode = Boolean.valueOf(false);
    }

    public int getTimeout_millisecond() {
            return timeout_millisecond;
    }

    public String getUserAgent() {
            return userAgent;
    }

    public boolean isDebug_mode() {
            return detail_mode;
    }    
}
