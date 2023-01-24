package com.infotech.model;

import com.infotech.common.DbConn;
import com.infotech.common.Operations;
import com.infotech.common.Utils;

import com.infotech.dao.GChargeDao;

import java.sql.Connection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.jsoup.nodes.Document;

public class GCharge {
   public static void findData(String tableName) {
        Connection cnn = null;
        try {
            Document doc = null;
            String  url ="http://www.g-charge.com.tr/Services/Charger.aspx?Operation=GetChargers";
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
                    String id = rec.get("Id") == null ? "" : rec.get("Id").toString();
                    String name = rec.get("ChargerTitle") == null ? "" : rec.get("ChargerTitle").toString();
                    String address = rec.get("ChargerAddress") == null ? "" : rec.get("ChargerAddress").toString();
                    String number = rec.get("ChargerNumber") == null ? "" : rec.get("ChargerNumber").toString();
                    String status = rec.get("ChargerStatus") == null ? "" : rec.get("ChargerStatus").toString();
                    String type = rec.get("ChargerType") == null ? "" : rec.get("ChargerType").toString();
                    String isActive = rec.get("IsActive") == null ? "" : rec.get("IsActive").toString();
                    String pinType = rec.get("PinType") == null ? "" : rec.get("PinType").toString();
                    String lat = rec.get("ChargerLat") == null ? "" : rec.get("ChargerLat").toString();
                    String lng = rec.get("ChargerLng") == null ? "" : rec.get("ChargerLng").toString();

                    if (j != 0 && j % 100 == 0){
                        Utils.showText("Toplam insert sayisi: " + j + " Verinin indirilme yuzdesi: " + j * 100 / jsonA.size());
                        cnn.commit();
                     }
                    
                    GChargeDao.insertDataInfo(cnn, tableName, id, name, address, number, status, type, isActive, pinType, lat, lng);
                }
            }
        } catch (Exception e) {
            Utils.showError("insertDataInfo hata :" + e.fillInStackTrace());
        }finally{
            Operations.closeConnections(cnn, null, null);
        }

    }
}
