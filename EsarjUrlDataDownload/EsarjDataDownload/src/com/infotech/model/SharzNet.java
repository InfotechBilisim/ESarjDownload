package com.infotech.model;

import com.infotech.common.DbConn;
import com.infotech.common.Operations;
import com.infotech.common.Utils;
import com.infotech.dao.SharzNetDao;

import java.sql.Connection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.jsoup.nodes.Document;

public class SharzNet {
    public static void findData(String tableName) {
         Connection cnn = null;
         try {
             Document doc = null;
             String  url ="https://m.sharz.net/tr/Station/StationMapsData";
             try {
                 doc = Operations.sendRequestGet(url, false);
             } catch (Exception e) {
                 Utils.logInfo("Doc: " + doc);
                 Utils.showError("findData URL Request hata : " + url);
                 e.fillInStackTrace();
             }

             JSONParser jsonP = new JSONParser();
             JSONArray jsonA = null;
             
             if (doc != null) {
                 try {
                      jsonA = (JSONArray)jsonP.parse(doc.body().text());
                 } catch (Exception ex) {
                     Utils.logInfo("Doc: " + doc);
                     doc = null;
                 }
                 Utils.showText("***** Insert islem yapiliyor....*********");
                 if(jsonA.size()>0){
                    cnn = DbConn.getPooledConnection();
                 }
                 for (int j = 0; j < jsonA.size(); j++) {
                     JSONObject rec = (JSONObject) jsonA.get(j);
                     String id = rec.get("RID") == null ? "" : rec.get("RID").toString();
                     String name = rec.get("Name") == null ? "" : rec.get("Name").toString();
                     String address = rec.get("Address") == null ? "" : rec.get("Address").toString();
                     String lat = rec.get("Lat") == null ? "" : rec.get("Lat").toString();
                     String lng = rec.get("Lon") == null ? "" : rec.get("Lon").toString();
                     String city = rec.get("CityName") == null ? "" : rec.get("CityName").toString();
                     String currentlOpen = rec.get("CurrentlyOpen") == null ? "" : rec.get("CurrentlyOpen").toString();
                     String district = rec.get("DistrictName") == null ? "" : rec.get("DistrictName").toString();
                     String imageUrl = rec.get("ImageUrl") == null ? "" : rec.get("ImageUrl").toString();
                     String phone1 = rec.get("Phone1") == null ? "" : rec.get("Phone1").toString();
                     phone1 = Operations.phoneNoFormat(phone1);
                     String phone2 = rec.get("Phone2") == null ? "" : rec.get("Phone2").toString();
                     phone2 = Operations.phoneNoFormat(phone2);

                     if (j != 0 && j % 100 == 0){
                         Utils.showText("Toplam insert sayisi: " + j + " Verinin indirilme yuzdesi: " + j * 100 / jsonA.size());
                         cnn.commit();
                      }
                     
                     SharzNetDao.insertDataInfo(cnn, tableName, id, name, address, lat, lng, city, currentlOpen, district, imageUrl, phone1,phone2);
                 }
             }
         } catch (Exception e) {
             Utils.showError("insertDataInfo hata :" + e.fillInStackTrace());
         }finally{
             Operations.closeConnections(cnn, null, null);
         }

     }
}
