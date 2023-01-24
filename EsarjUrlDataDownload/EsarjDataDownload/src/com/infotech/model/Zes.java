package com.infotech.model;

import com.infotech.common.DbConn;
import com.infotech.common.Operations;
import com.infotech.common.Utils;

import com.infotech.dao.GChargeDao;
import com.infotech.dao.ZesDao;

import java.sql.Connection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Zes {
    public static void findData(String tableName) {
         Connection cnn = null;
         try {
             Document doc = null;
             String url ="https://zes.net/sarj-istasyonlari";
             try {
                 doc = Operations.sendRequestGet(url, false);
             } catch (Exception e) {
                 Utils.logInfo("Doc: " + doc);
                 Utils.showError("findData URL Request hata : " + url);
                 e.getStackTrace();
             }
             if (doc != null) {
                 try {
                     Elements items = doc.getElementsByClass("items-wrapper").first().getElementsByClass("items");
                     Utils.showText("***** Insert islem yapiliyor....*********");
                     if(items.size()>0){
                        cnn = DbConn.getPooledConnection();
                     }
                     for(int t=0;t<items.size();t++){
                         try{
                              Elements contents = items.get(t).getElementsByClass("item");
                              for(int j=0;j<contents.size();j++){
                                 try{
                                     Element content = contents.get(j);
                                     String id = content.attr("data-location-id");
                                     String type = content.attr("data-charge-type");
                                     String address = content.attr("data-search");
                                     String name = "";
                                     String city = "";
                                     try{
                                       name = content.select("div.content").select("div").first().text();
                                       city = content.select("div.content").select("div").last().text();
                                     }catch(Exception e){               
                                     }
                                     
                                     if (j != 0 && j % 100 == 0){
                                         Utils.showText("Toplam insert sayisi: " + j + " Verinin indirilme yuzdesi: " + j * 100 / contents.size());
                                         cnn.commit();
                                      }
                                     ZesDao.insertDataInfo(cnn, tableName, name, address, id, city, type);
                                 }catch(Exception e){
                                     Utils.showError("İtem hata :" + e.getStackTrace());
                                 }
                              }
                          }catch(Exception e){
                            Utils.showError("İtems hata :" + e.fillInStackTrace());
                         }
                     }
                 } catch (Exception ex) {
                     Utils.showError("Doc: " + doc);
                     doc = null;
                 }
             }
         } catch (Exception e) {
             Utils.showError("insertDataInfo hata :" + e.fillInStackTrace());
         }finally{
             Operations.closeConnections(cnn, null, null);
         }

     }
}
