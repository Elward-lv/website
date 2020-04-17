package com.website.springboot.bean;

public class HttpPojo {

    String HttpHost;
    String HttpAccept;
    String HttpConnection;
    String HttpUserAgent;
    String HttpReferer;
    String HttpOrigin;

    public String getHttpHost() {
        return HttpHost;
    }

    public void setHttpHost(String httpHost) {
        HttpHost = httpHost;
    }

    public String getHttpAccept() {
        return HttpAccept;
    }

    public void setHttpAccept(String httpAccept) {
        HttpAccept = httpAccept;
    }

    public String getHttpConnection() {
        return HttpConnection;
    }

    public void setHttpConnection(String httpConnection) {
        HttpConnection = httpConnection;
    }

    public String getHttpUserAgent() {
        return HttpUserAgent;
    }

    public void setHttpUserAgent(String httpUserAgent) {
        HttpUserAgent = httpUserAgent;
    }

    public String getHttpReferer() {
        return HttpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        HttpReferer = httpReferer;
    }

    public String getHttpOrigin() {
        return HttpOrigin;
    }

    public void setHttpOrigin(String httpOrigin) {
        HttpOrigin = httpOrigin;
    }
}
