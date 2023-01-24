package com.infotech.dao;

import com.infotech.common.DbConn;
import com.infotech.common.Operations;
import com.infotech.common.Utils;
import com.infotech.model.SharzNet;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SharzNetDao {
    public static void insertDataInfo(Connection cnn,String tableName, String id, String name, String address, String lat, String lng, String city, String currentlOpen, String district, String imageUrl, String phone1, String phone2) {
        PreparedStatement pstmt = null;
        String sqlStr = null;
        try {
            sqlStr = "INSERT INTO " + tableName + " ( ID, AD, ADRES, XCOOR, YCOOR, IL_ADI, ILCE_ADI, CURR_OPEN, IMAGE_URL, TEL, TEL1, VERI_TARIHI ) ";
            sqlStr += "VALUES(?,?,?,?,?,?,?,?,?,?,?, SYSDATE)";
         
            int col = 1;
            pstmt = cnn.prepareStatement(sqlStr);
            pstmt.setString(col++, id);
            pstmt.setString(col++, name);
            pstmt.setString(col++, address);
            pstmt.setString(col++, lng);
            pstmt.setString(col++, lat);
            pstmt.setString(col++, city);
            pstmt.setString(col++, district);
            pstmt.setString(col++, currentlOpen);
            pstmt.setString(col++, imageUrl);
            pstmt.setString(col++, phone1);
            pstmt.setString(col++, phone2);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            Utils.showError("************** Hata detayi icin Log dosyasina bakiniz************************");
            ex.printStackTrace();
            Utils.showError("findEmlakBankInfo: " + ex.getMessage());
            String data = "tableName: " + tableName + " id: " + id + " name: " + name + " address: " + address + " lng: " + lng + " lat: " + lat + " city: " + city + " imageUrl: " + imageUrl+ " phone1: " + phone1;
            Utils.logInfo(data);
            Utils.logInfo(sqlStr);
        } finally {
            Operations.closeConnections(null, pstmt, null);
        }
    }
    
}
