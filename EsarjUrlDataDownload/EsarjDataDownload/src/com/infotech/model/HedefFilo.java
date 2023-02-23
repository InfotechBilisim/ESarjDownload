package com.infotech.model;

import com.infotech.common.DbConn;
import com.infotech.common.Operations;
import com.infotech.common.Utils;
import com.infotech.dao.HedefFiloDao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import java.sql.Connection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class HedefFilo {

    public HedefFilo() {
        
    }
    
    public static void findData(String tableName) {
        Connection cnn = null;
        try {
            JSONParser jsonP = new JSONParser();
            JSONArray jsonA = null;
            
            // File("C://temp/sarj_20230223_.json");
            File file = new File("sarj_20230223_.json");
            String dataJson = "";
            try (FileInputStream fis = new FileInputStream(file);
                 InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(isr)
            ) {
                String line_str;
                while ((line_str = reader.readLine()) != null) {
                    dataJson += line_str;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }            
            Utils.showText("***** Insert islem yapiliyor....*********");
            jsonA = (JSONArray)jsonP.parse(dataJson);
            if(jsonA.size()>0){
               cnn = DbConn.getPooledConnection();
            }
            for (int j = 0; j < jsonA.size(); j++) {
                JSONObject rec = (JSONObject) jsonA.get(j);
                String lat = rec.get("lat") == null ? "" : rec.get("lat").toString();
                String lng = rec.get("lng") == null ? "" : rec.get("lng").toString();
                JSONObject rec_data = (JSONObject) rec.get("data");
                String name = rec_data.get("title") == null ? "" : rec_data.get("title").toString();
                String address = rec_data.get("address") == null ? "" : rec_data.get("address").toString();
                String phone = rec_data.get("phone") == null ? "" : rec_data.get("phone").toString();
                phone = Operations.phoneNoFormat(phone);
                String typeOfUse = rec_data.get("typeOfUse") == null ? "" : rec_data.get("typeOfUse").toString();
                String model = rec_data.get("officeModel") == null ? "" : rec_data.get("officeModel").toString();
                String haftaIci = rec_data.get("weekdaysTime") == null ? "" : rec_data.get("weekdaysTime").toString();
                String haftaSonu = rec_data.get("weekendTime") == null ? "" : rec_data.get("weekendTime").toString();
                String socket1 = rec_data.get("socket1") == null ? "" : rec_data.get("socket1").toString();
                String socket2 = rec_data.get("socket2") == null ? "" : rec_data.get("socket2").toString();
                String city = rec_data.get("city") == null ? "" : rec_data.get("city").toString();
                if (j != 0 && j % 100 == 0){
                    Utils.showText("Toplam insert sayisi: " + j + " Verinin indirilme yuzdesi: " + j * 100 / jsonA.size());
                    cnn.commit();
                 }
                HedefFiloDao.insertDataInfo(cnn, tableName, j, lng, lat, name, address, city, phone, typeOfUse, model, haftaIci, haftaSonu, socket1, socket2);
                
            }
            
            
        } catch (Exception e) {
            Utils.showError("insertDataInfo hata :" + e.fillInStackTrace());
        }finally{
            Operations.closeConnections(cnn, null, null);
        }            
    }
    
}
