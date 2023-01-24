package com.infotech.dao;

import com.infotech.common.Operations;
import com.infotech.common.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class GChargeDao {

    public static void insertDataInfo(Connection cnn, String tableName, String id, String name, String address, String number, String status, String type, String isActive, String pinType, String lat, String lng) {
        PreparedStatement pstmt = null;
        String sqlStr = null;
        try {
            sqlStr = "INSERT INTO " + tableName + " (ID, AD, ADRES, CHARGER_NUMBER, STATUS, TIP, XCOOR, YCOOR,";
            sqlStr += " PIN_TYPE, IS_ACTIVE, VERI_TARIHI)";
            sqlStr += "VALUES(?,?,?,?,?,?,?,?,?,?,SYSDATE)";
            int col = 1;
            pstmt = cnn.prepareStatement(sqlStr);
            pstmt.setString(col++, id);
            pstmt.setString(col++, name);
            pstmt.setString(col++, address);
            pstmt.setString(col++, number);
            pstmt.setString(col++, status);
            pstmt.setString(col++, type);
            pstmt.setDouble(col++, Utils.convertStringToDoubleValue("lng",lng));
            pstmt.setDouble(col++, Utils.convertStringToDoubleValue("lat",lat));
            pstmt.setString(col++, pinType);
            pstmt.setString(col++, isActive);

            pstmt.executeUpdate();

        } catch (Exception ex) {
            if (!(lng.contains(".") && lat.contains(".")))
               Utils.showError("************** Koordinat Hatali!!! Hatalı kayıt için detayi  Log dosyasina bakiniz************************");
            else
               Utils.showError("************** Hata detayi icin Log dosyasina bakiniz************************");

            ex.printStackTrace();
            Utils.showError("findAlbarakaInfo: " + ex.fillInStackTrace());
            String data = "tableName: " + tableName + " id: " + id + " name: " + name + " address: " + address + " number: " + number + " type: " + type;
            data += " lng: " + lng + " + lat: " + lat + " status: " + status + " + pinType: " + pinType + " isActive: " + isActive;
            Utils.logInfo(data);
            Utils.logInfo(sqlStr);
        } finally {
            Operations.closeConnections(null, pstmt, null);
        }
    }
}
