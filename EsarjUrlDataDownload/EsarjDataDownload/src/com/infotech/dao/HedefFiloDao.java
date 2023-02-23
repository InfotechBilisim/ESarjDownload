package com.infotech.dao;

import com.infotech.common.Operations;
import com.infotech.common.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class HedefFiloDao {
    public HedefFiloDao() {

    }
   
    public static void insertDataInfo(Connection cnn,String tableName, int id, String lng, String lat, String name, String address, String ilAd, String phone, String typeOfUse, String model, String haftaIci, String haftaSonu, String socket1, String socket2) {
        PreparedStatement pstmt = null;
        String sqlStr = null;
        try {
            sqlStr = "INSERT INTO " + tableName + " ( ID, XCOOR, YCOOR, AD ,ADRES, IL_ADI, TEL, KULLANIM_TIPI, ISTASYON_TIPI, HAFTAICI, HAFTASONU, SOCKET_1, SOCKET_2, VERI_TARIHI ) ";
            sqlStr += "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?, SYSDATE)";
         
            int col = 1;
            pstmt = cnn.prepareStatement(sqlStr);
            pstmt.setInt(col++, id);
            pstmt.setString(col++, lng);
            pstmt.setString(col++, lat);
            pstmt.setString(col++, name);
            pstmt.setString(col++, address);
            pstmt.setString(col++, ilAd);
            pstmt.setString(col++, phone);
            pstmt.setString(col++, typeOfUse);
            pstmt.setString(col++, model);
            pstmt.setString(col++, haftaIci);
            pstmt.setString(col++, haftaSonu);
            pstmt.setString(col++, socket1);
            pstmt.setString(col++, socket2);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            Utils.showError("************** Hata detayi icin Log dosyasina bakiniz************************");
            ex.printStackTrace();
            Utils.showError("insertDataInfo: " + ex.fillInStackTrace());
            String data = "tableName: " + tableName + " id: " + id + " name: " + name + " address: " + address + " lng: " + lng + " lat: " + lat + " city: " + ilAd + " phone: " + phone;
            Utils.logInfo(data);
            Utils.logInfo(sqlStr);
        } finally {
            Operations.closeConnections(null, pstmt, null);
        }
    }    
}
