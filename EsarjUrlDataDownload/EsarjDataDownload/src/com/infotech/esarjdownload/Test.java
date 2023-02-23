package com.infotech.esarjdownload;

import com.infotech.common.HostNamesTrust;
import com.infotech.common.HttpRequestSettings;
import com.infotech.common.Operations;
import com.infotech.common.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
    public static void main(String[] args) {
       String groupByColumns = Operations.clearData("URL_DATA_VOLTRUN_20230124", 2);
       Operations.deleteDoubleRecord("URL_DATA_VOLTRUN_20230124", groupByColumns);
       
       //esarj(url) ;
        //System.out.println(commonPostRequest(url, "{stations: \"esarj.public.web.stations\"}","82TATVR9-dKi65TB-YuGODGfAFXyZd-8gJ_Y", "JSxLpmnxU4Lf_RhOjKoXEM1w",true));
        
       /* Map<String,String> return_cookies = HostNamesTrust.getCookies(url); 
  
        
        for (Map.Entry<String, String> entry : return_cookies.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }*/
    }
    
    
    public static Map<String, String> getCookies(String url) {
        Map<String,String> return_cookies = new HashMap<>();
        try {
            HttpRequestSettings urlRequestSettings = new HttpRequestSettings();
           org.jsoup.Connection.Response res = Jsoup.connect(url)
                           .method(org.jsoup.Connection.Method.POST)
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
                                 .ignoreContentType(true)
                                 .post();
                
        } catch (Exception ex) {
                Utils.showError("(getDocumentFromUrl) " + ex.fillInStackTrace());
                ex.printStackTrace();
        }            
        return return_document;
    }
    
    private static void esarj( String requestUrl) {
        try {
            HostNamesTrust.trustAllHostnames();
            Document doc = HostNamesTrust.getDocumentFromUrl(requestUrl,1);
            System.out.print(doc);
            if( doc!=null ){
                Elements contents = doc.getElementsByClass("geolocation-location");
    
                for(int i=0;i<contents.size();i++){
                   try{
                       Element content = contents.get(i);
                       String lat = content.attr("data-lat");
                       String lng = content.attr("data-lng");
                       String name = content.select("div.field-content.maps__title").text();
                       String address = content.select("div.location-content").select("p").first().text();
                   }catch(Exception e){
                       e.fillInStackTrace();
                   }
                }
            }
        } catch (Exception e) {
            Utils.showText(e.getStackTrace().toString());
            Utils.showError("findZiraatData hata :" + e.getStackTrace());
        }

    }
    
    
    public static String commonPostRequest(String requestUrl, String params,String xsrfToken,String csrf, boolean isContentTypeJson) {
        String line = null;
        BufferedReader inpBuffer = null;
        StringBuilder respDataBuilder = null;
        HttpURLConnection httpClient = null;
        InputStreamReader inpStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inpStream = null;
        int responseCode = 0;
        
        try {
            Utils.showText("POST requestURI : " + requestUrl + " params:" + params);
            httpClient = (HttpURLConnection) new URL(requestUrl).openConnection();
            httpClient.setRequestMethod("POST");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
            httpClient.setRequestProperty("charset", StandardCharsets.UTF_8.toString());

            if (isContentTypeJson) {
                httpClient.setRequestProperty("Content-Type", "application/json");
            } else {
                httpClient.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
           // httpClient.setRequestProperty("Accept", "application/json");
           
           // httpClient.setRequestProperty("x-xsrf-token", xsrfToken);
           // httpClient.setRequestProperty("_csrf", csrf);
            
            
          //  httpClient.setRequestProperty(":authority", "esarj.com");
           //  httpClient.setRequestProperty(":method", "POST");
          //   httpClient.setRequestProperty(":path", "/api/stations");
             //httpClient.setRequestProperty(":scheme", "https");
             httpClient.setRequestProperty("accept", "application/json, text/plain, */*");
             httpClient.setRequestProperty("accept-encoding", "gzip, deflate, br");
             httpClient.setRequestProperty("accept-language", "en-US,en;q=0.9");
             httpClient.setRequestProperty("content-length", "40");
             httpClient.setRequestProperty("content-type", "application/json;charset=UTF-8");
             httpClient.setRequestProperty("cookie", "_ga=GA1.2.52022823.1672986417; _csrf=JSxLpmnxU4Lf_RhOjKoXEM1w; _gid=GA1.2.1216831066.1673432509; NG_TRANSLATE_LANG_KEY=tr; _gat=1; XSRF-TOKEN=82TATVR9-dKi65TB-YuGODGfAFXyZd-8gJ_Y");
             httpClient.setRequestProperty("origin", "https://esarj.com");
             httpClient.setRequestProperty("referer", "https://esarj.com/");

             httpClient.setRequestProperty("sec-fetch-dest", "empty");
             httpClient.setRequestProperty("sec-fetch-mode", "cors");
             httpClient.setRequestProperty("sec-fetch-site", "same-origin");
             httpClient.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
             httpClient.setRequestProperty("x-xsrf-token", "82TATVR9-dKi65TB-YuGODGfAFXyZd-8gJ_Y");
            
            
            //cookie: _ga=GA1.2.52022823.1672986417; _csrf=JSxLpmnxU4Lf_RhOjKoXEM1w; _gid=GA1.2.1216831066.1673432509; _gat=1; NG_TRANSLATE_LANG_KEY=tr; XSRF-TOKEN=TvxlIwZA-OfXyW3UJ59CDzrsk4iiFuN6vLLo
            httpClient.setUseCaches(false);
            httpClient.setDoOutput(true);
            httpClient.setDoInput(true);
            httpClient.setConnectTimeout(2000);
            httpClient.setReadTimeout(2000);

            outputStreamWriter = new OutputStreamWriter(httpClient.getOutputStream());
            outputStreamWriter.write(params);
            outputStreamWriter.flush();
            responseCode = httpClient.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                respDataBuilder = new StringBuilder();
                inpStream = httpClient.getInputStream();
                inpStreamReader = new InputStreamReader(httpClient.getInputStream(), StandardCharsets.UTF_8);
                inpBuffer = new BufferedReader(inpStreamReader);
                
                while ((line = inpBuffer.readLine()) != null) {
                    respDataBuilder.append(line.toString());
                }

            } else {
                Utils.showError("responseCode:" + responseCode + " commonPostRequest POST request not worked: " + requestUrl);
            }
        } catch (Exception e) {
            Utils.showError("commonPostRequest: " + requestUrl + " params: " + params + e.getMessage());
        } finally {
            Utils.closeHttpConn(httpClient, inpStream, null, inpBuffer, outputStreamWriter);
        }
        return respDataBuilder.toString();
    }


    
    public static void findZiraatData(String tableName) {
        try {
            String  url ="https://esarj.com/api/stations";
            //String doc = Utils.commonGetRequest(url, false,null, true);
         //  Document doc = Operations.sendRequestGet(url);
            
           /*Document doc = Jsoup.connect(url)
                       .ignoreContentType(true)
                       .timeout(9000)
                       .get();*/
              Document doc =Operations.sendRequestGet(url, true);
            if( doc!=null ){
                
                Elements items = doc.getElementsByClass("items-wrapper").first().getElementsByClass("items");
               for(int j=0;j<items.size();j++){
                   try{
                        Elements contents = items.get(j).getElementsByClass("item");
                        for(int i=0;i<contents.size();i++){
                           try{
                               Element content = contents.get(i);
                               Utils.showText("*****************" + content);
                               String id = content.attr("data-location-id");
                               String sarjType = content.attr("data-charge-type");
                               String address = content.attr("data-search");
                               String name = "";
                               String city = "";
                               try{
                                 name = content.select("div.content").select("div").first().text();
                                 city = content.select("div.content").select("div").last().text();
                               }catch(Exception e){
                              
                               }
                               Utils.showText("id: "+ id + " sarjType: "+ sarjType+" address: "+address + " name: "+name + " city: "+city);
                           }catch(Exception e){
                               e.fillInStackTrace();
                           }
                        }
                    }catch(Exception e){
                        e.fillInStackTrace();
                    }
               }
            }
            
           // String requestUrl ="https://www.ziraatkatilim.com.tr/sube-ve-atmler?tur[sube]=sube";
            //https://www.ziraatkatilim.com.tr/sube-ve-atmler?tur%5Bsube%5D=sube
            //writeData( requestUrl, "Şube", tableName);
           
        } catch (Exception e) {
            Utils.showText(e.getStackTrace().toString());
            Utils.showError("findZiraatData hata :" + e.getStackTrace());
        }
    }
    
    private static void writeData( String requestUrl, String tip, String tableName) {
        try {
            HostNamesTrust.trustAllHostnames();
            Document doc = HostNamesTrust.getDocumentFromUrl(requestUrl,1);
            System.out.print(doc);
            if( doc!=null ){
                Elements contents = doc.getElementsByClass("geolocation-location");
    
                for(int i=0;i<contents.size();i++){
                   try{
                       Element content = contents.get(i);
                       String lat = content.attr("data-lat");
                       String lng = content.attr("data-lng");
                       String name = content.select("div.field-content.maps__title").text();
                       String address = content.select("div.location-content").select("p").first().text();
                   }catch(Exception e){
                       e.fillInStackTrace();
                   }
                }
            }
        } catch (Exception e) {
            Utils.showText(e.getStackTrace().toString());
            Utils.showError("findZiraatData hata :" + e.getStackTrace());
        }

    }
}
