package com.infotech.model;

import com.infotech.common.DbConn;
import com.infotech.common.HostNamesTrust;
import com.infotech.common.Operations;
import com.infotech.common.Utils;

import com.infotech.dao.GChargeDao;
import com.infotech.dao.VoltrunDao;

import java.sql.Connection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Voltrun {
    public static void findData(String tableName) {
         Connection cnn = null;
         try {
             Document doc = null;
             String  url ="https://app.voltrun.com:7777/stations/all";
             try {
                 doc = Operations.sendRequestGet(url, true);
             } catch (Exception e) {
                 Utils.logInfo("Doc: " + doc);
                 Utils.showError("findData URL Request hata : " + url);
                 e.getStackTrace();
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
                     String id = rec.get("id") == null ? "" : rec.get("id").toString();
                     String country = rec.get("country") == null ? "" : rec.get("country").toString();
                     String evseOrder = rec.get("evseOrder") == null ? "" : rec.get("evseOrder").toString();
                     String city = rec.get("city") == null ? "" : rec.get("city").toString();
                     String district = rec.get("district") == null ? "" : rec.get("district").toString();
                     String code = rec.get("code") == null ? "" : rec.get("code").toString();
                     String lat = rec.get("latitude") == null ? "" : rec.get("latitude").toString();
                     String lng = rec.get("longitude") == null ? "" : rec.get("longitude").toString();
                     String name = rec.get("locationName") == null ? "" : rec.get("locationName").toString();
                     String address = rec.get("addressDefinition") == null ? "" : rec.get("addressDefinition").toString();
                     String locationId = rec.get("locationId") == null ? "" : rec.get("locationId").toString();
                     String maxCurrent = rec.get("maxCurrent") == null ? "" : rec.get("maxCurrent").toString();
                     String maxPower = rec.get("maxPower") == null ? "" : rec.get("maxPower").toString();
                     String stationCode = rec.get("stationCode") == null ? "" : rec.get("stationCode").toString();
                     String stationId = rec.get("stationId") == null ? "" : rec.get("stationId").toString();
                     String stationOnline = rec.get("stationOnline") == null ? "" : rec.get("stationOnline").toString();
                     String status = rec.get("status") == null ? "" : rec.get("status").toString();
                     String tariffId = rec.get("tariffId") == null ? "" : rec.get("tariffId").toString();
                     String usageType = rec.get("usageType") == null ? "" : rec.get("usageType").toString();
                     String tariffInfo = rec.get("tariffInfo") == null ? "" : rec.get("tariffInfo").toString();
                     String phone = rec.get("phone") == null ? "" : rec.get("phone").toString();
                     phone = Operations.phoneNoFormat(phone);
                     
                     if (j != 0 && j % 100 == 0){
                         Utils.showText("Toplam insert sayisi: " + j + " Verinin indirilme yuzdesi: " + j * 100 / jsonA.size());
                         cnn.commit();
                      }
                     
                     VoltrunDao.insertDataInfo(cnn, tableName, id, name, address, country, status, evseOrder, city, district, lat, lng, code, locationId,
                                               maxCurrent, maxPower, stationCode,stationId, stationOnline, tariffId, usageType, tariffInfo,phone);
                 }
             }
         } catch (Exception e) {
             Utils.showError("insertDataInfo hata :" + e.getStackTrace());
         }finally{
             Operations.closeConnections(cnn, null, null);
         }

     }
}
