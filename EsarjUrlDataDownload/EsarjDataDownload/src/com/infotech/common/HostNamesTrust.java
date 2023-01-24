package com.infotech.common;

import java.security.cert.X509Certificate;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class HostNamesTrust {
    public HostNamesTrust() {
        super();
    }
    
    public static void trustAllHostnames() {
    try {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
          }

          public void checkClientTrusted(X509Certificate[] certs, String authType) {
          }

          public void checkServerTrusted(X509Certificate[] certs, String authType) {
          }
        }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostsValid = new HostnameVerifier() {
          public boolean verify(String hostname, SSLSession session) {
            return true;
          }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);            
    } catch (Exception ex) {
        Utils.showError("Error: Utils >> (trustAllHostnames) " + ex.fillInStackTrace());
        ex.printStackTrace();
    }
    }  
    
    public static Map<String, String> getCookies(String url) {
        Map<String,String> return_cookies = new HashMap<>();
        try {
            HttpRequestSettings urlRequestSettings = new HttpRequestSettings();
           org.jsoup.Connection.Response res = Jsoup.connect(url)
                           .method(org.jsoup.Connection.Method.GET)
                           .timeout(urlRequestSettings.getTimeout_millisecond())
                           .userAgent(urlRequestSettings.getUserAgent())
                           .execute();
        
                return_cookies = res.cookies(); 
        } catch (Exception ex) {
                Utils.showError( "(getCookies) " + ex.fillInStackTrace());
                ex.printStackTrace();
        }             
        return return_cookies;
    }
    public  static Document getDocumentFromUrl(String url) {
        Document return_document = null; 
        try {
            HttpRequestSettings urlRequestSettings = new HttpRequestSettings();
                Map<String,String> return_cookies = getCookies(url);            
                return_document = Jsoup.connect(url)
                                 .timeout(urlRequestSettings.getTimeout_millisecond())
                                 .userAgent(urlRequestSettings.getUserAgent())
                                 .cookies(return_cookies)
                                 //.ignoreContentType(true)
                                 .get();
                
        } catch (Exception ex) {
                Utils.showError("(getDocumentFromUrl) " + ex.fillInStackTrace());
                ex.printStackTrace();
        }            
        return return_document;
}
    
    public  static Document getDocumentFromUrl(String url, int timeoutValue) {
        Document return_document = null; 
        try {
            HttpRequestSettings urlRequestSettings = new HttpRequestSettings();
                Map<String,String> return_cookies = getCookies(url);            
                return_document = Jsoup.connect(url)
                                 //.timeout(urlRequestSettings.getTimeout_millisecond())
                                 .userAgent(urlRequestSettings.getUserAgent())
                                // .cookies(return_cookies)
                                 .get();
                
        } catch (Exception ex) {
                Utils.showError("(getDocumentFromUrl) " + ex.fillInStackTrace());
                ex.printStackTrace();
        }            
        return return_document;
    }
                            
}
