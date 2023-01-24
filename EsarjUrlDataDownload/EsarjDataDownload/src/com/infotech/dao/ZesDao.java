package com.infotech.dao;

import com.infotech.common.Operations;
import com.infotech.common.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ZesDao {
    public static void insertDataInfo(Connection cnn, String tableName, String name, String address, String id, String city, String type) {
        PreparedStatement pstmt = null;
        String sqlStr = null;
        try {
            sqlStr = "INSERT INTO " + tableName + " ( AD, ADRES, ID, TIP, IL_ADI, VERI_TARIHI ) ";
            sqlStr += "VALUES(?,?,?,?,?,SYSDATE)";
            int col = 1;
            pstmt = cnn.prepareStatement(sqlStr);
            pstmt.setString(col++, name);
            pstmt.setString(col++, address);
            pstmt.setString(col++, id);
            pstmt.setString(col++, type);
            pstmt.setString(col++, city);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            Utils.showText("************** Hata detayi icin Log dosyasina bakiniz************************");
            ex.printStackTrace();
            Utils.showError("insertDataInfo: " + ex.getMessage());
            String data = "tableName: " + tableName + " name: " + name + " addres: " + address + " type: " + type + " id: " + id + " city: " + city;
            Utils.logInfo(data);
            Utils.logInfo(sqlStr);
        } finally {
            Operations.closeConnections(null, pstmt, null);
        }
    }


}
