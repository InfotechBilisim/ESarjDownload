package com.infotech.dao;

import com.infotech.common.Operations;
import com.infotech.common.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VoltrunDao {
    public static void insertDataInfo(Connection cnn, String tableName, String id, String name, String address, String country, String status, String evseOrder, String city, String district, String lat, String lng, String code, String locationId,
                                               String maxCurrent, String maxPower, String stationCode, String stationId, String stationOnline, String tariffId, String usageType, String tariffInfo, String phone) {
        PreparedStatement pstmt = null;
        String sqlStr = null;
        try {
            sqlStr = "INSERT INTO " + tableName + " (ID, AD, ULKE, IL_ADI, ILCE_ADI, EVSE_ORDER, CODE, XCOOR, YCOOR,";
            sqlStr += " ADRES, LOCATION_ID, MAX_CURRENT, MAX_POWER, STATION_CODE, STATION_ID, STATION_ONLINE, STATUS, TARIFF_ID, USAGE_TYPE, TARIFF_INFO, VERI_TARIHI)";
            sqlStr += "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
            int col = 1;
            pstmt = cnn.prepareStatement(sqlStr);
            pstmt.setString(col++, id);
            pstmt.setString(col++, name);
            pstmt.setString(col++, country);
            pstmt.setString(col++, city);
            pstmt.setString(col++, district);
            pstmt.setString(col++, evseOrder);
            pstmt.setString(col++, code);
            pstmt.setDouble(col++, Utils.convertStringToDoubleValue("lng",lng));
            pstmt.setDouble(col++, Utils.convertStringToDoubleValue("lat",lat));
            pstmt.setString(col++, address);
            pstmt.setString(col++, locationId);
            pstmt.setString(col++, maxCurrent);
            pstmt.setString(col++, maxPower);
            pstmt.setString(col++, stationCode);
            pstmt.setString(col++, stationId);
            pstmt.setString(col++, stationOnline);
            pstmt.setString(col++, status);
            pstmt.setString(col++, tariffId);
            pstmt.setString(col++, usageType);
            pstmt.setString(col++, tariffInfo);
            
            pstmt.executeUpdate();

        } catch (Exception ex) {
            if (!(lng.contains(".") && lat.contains(".")))
               Utils.showError("************** Koordinat Hatali!!! Hatalı kayıt için detayi  Log dosyasina bakiniz************************");
            else
               Utils.showError("************** Hata detayi icin Log dosyasina bakiniz************************");

            ex.printStackTrace();
            Utils.showError("findKuveytTurkInfo: " + ex.getMessage());
            String data =
                "tableName: " + tableName + " id: " + id + " name: " + name + " address: " + address + " country: " + country + " status: " + status + " evseOrder: " + evseOrder + " city: " + city + " district: " + district +
                " code: " + code;
            data += " lng: " + lng + " + lat: " + lat + " locationId: " + locationId + " + maxCurrent: " + maxCurrent + " maxPower: " + maxPower + " + stationCode: " + stationCode;
            data += " stationId: " + stationId + " + stationOnline: " + stationOnline + " tariffId: " + tariffId + " + usageType: " + usageType + " phone: " + phone + " + tariffInfo: " + tariffInfo;
            Utils.logInfo(data);
            Utils.logInfo(sqlStr);
        } finally {
            Operations.closeConnections(null, pstmt, null);
        }
    }

    
}
