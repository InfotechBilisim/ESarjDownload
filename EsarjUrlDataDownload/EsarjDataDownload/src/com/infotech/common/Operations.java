package com.infotech.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Operations {
   
   

    public static String createColums(int type) {
        String columns = null;
         if (type == 1) {
            columns = "ID NUMBER,";
            columns += " AD VARCHAR2(128 CHAR),";
            columns += " ADRES VARCHAR2(512 CHAR),";
            columns += " XCOOR NUMBER(20,10),";
            columns += " YCOOR NUMBER(20,10),";
            columns += " IL_ADI VARCHAR2(128 CHAR),";
            columns += " ILCE_ADI VARCHAR2(256 CHAR),";
            columns += " CURR_OPEN VARCHAR2(128 CHAR),";
            columns += " IMAGE_URL VARCHAR2(128 CHAR),";
            columns += " TEL VARCHAR2(128 CHAR),";
            columns += " TEL1 VARCHAR2(128 CHAR),";
            columns += " VERI_TARIHI DATE";
        } else if (type == 2) {
            columns = "ID VARCHAR2(64 CHAR),";
            columns += " AD VARCHAR2(128 CHAR),";
            columns += " IL_ADI VARCHAR2(256 CHAR),";
            columns += " ADRES VARCHAR2(512 CHAR),";
            columns += " TIP VARCHAR2(128 CHAR),";
            columns += " VERI_TARIHI DATE";
        }else  if (type == 3) {
            columns = "ID NUMBER,";
            columns += " AD VARCHAR2(128 CHAR),";
            columns += " ADRES VARCHAR2(512 CHAR),";
            columns += " CHARGER_NUMBER VARCHAR2(128 CHAR),";
            columns += " STATUS VARCHAR2(32 CHAR),";
            columns += " TIP VARCHAR2(128 CHAR),";
            columns += " YCOOR NUMBER(20,10),";
            columns += " XCOOR NUMBER(20,10),";
            columns += " PIN_TYPE VARCHAR2(32 CHAR),";
            columns += " IS_ACTIVE VARCHAR2(64 CHAR),";
            columns += " VERI_TARIHI DATE";
        } else if (type == 4) {
            columns = "ID NUMBER,";
            columns += " AD VARCHAR2(128 CHAR),";
            columns += " ULKE VARCHAR2(128 CHAR),";
            columns += " IL_ADI VARCHAR2(128 CHAR),";
            columns += " ILCE_ADI VARCHAR2(256 CHAR),";
            columns += " EVSE_ORDER NUMBER,";
            columns += " CODE VARCHAR2(256 CHAR),";
            columns += " XCOOR NUMBER(20,10),";
            columns += " YCOOR NUMBER(20,10),";
            columns += " ADRES VARCHAR2(512 CHAR),";
            columns += " LOCATION_ID NUMBER,";
            columns += " MAX_CURRENT NUMBER,";
            columns += " MAX_POWER VARCHAR2(128 CHAR),";
            columns += " STATION_CODE VARCHAR2(128 CHAR),";
            columns += " STATION_ID NUMBER,";
            columns += " STATION_ONLINE VARCHAR2(32 CHAR),";
            columns += " STATUS VARCHAR2(64 CHAR),";
            columns += " TARIFF_ID NUMBER,";
            columns += " USAGE_TYPE VARCHAR2(32 CHAR),";
            columns += " TARIFF_INFO VARCHAR2(64 CHAR),";
            columns += " VERI_TARIHI DATE";
        } else if (type == 5) {
            columns = "ID NUMBER(10,0),";
            columns += " XCOOR NUMBER(20,10),";
            columns += " YCOOR NUMBER(20,10),";            
            columns += " AD VARCHAR2(128 CHAR),";
            columns += " ADRES VARCHAR2(512 CHAR),";
            columns += " IL_ADI VARCHAR2(128 CHAR),";
            columns += " TEL VARCHAR2(128 CHAR),";
            columns += " KULLANIM_TIPI VARCHAR2(128 CHAR),";
            columns += " ISTASYON_TIPI VARCHAR2(128 CHAR),";
            columns += " HAFTAICI VARCHAR2(128 CHAR),";
            columns += " HAFTASONU VARCHAR2(128 CHAR),";
            columns += " SOCKET_1 VARCHAR2(128 CHAR),";
            columns += " SOCKET_2 VARCHAR2(128 CHAR),";
            columns += " VERI_TARIHI DATE";
        }
        return columns;
    }

    public static String clearData(String tableName, int type) {
        String groupByColumns = null;
        try {
            Operations.trim(tableName, "AD");
            Operations.trim(tableName, "ADRES");

             if (type == 1) {
                Operations.trim(tableName, "AD");
                Operations.trim(tableName, "ADRES");
                Operations.trim(tableName, "IL_ADI");
                Operations.trim(tableName, "ILCE_ADI");
                Utils.showText("***************************************************");
                groupByColumns = " ID, AD, IL_ADI, ILCE_ADI, ADRES,TEL, IMAGE_URL, XCOOR, YCOOR ";
                
            } else if (type == 2) {
                Operations.trim(tableName, "AD");
                Operations.trim(tableName, "ADRES");
                Operations.trim(tableName, "IL_ADI");
                Operations.replace(tableName, "IL_ADI", "/");
                Utils.showText("***************************************************");
                groupByColumns = " ID, AD, IL_ADI, ADRES, TIP ";
            } else  if (type == 3) {
                Operations.trim(tableName, "CHARGER_NUMBER");
                Operations.trim(tableName, "TIP");
                Utils.showText("***************************************************");
                groupByColumns = " ID, AD, ADRES, TIP, XCOOR, YCOOR, STATUS, IS_ACTIVE, PIN_TYPE ";
            } else if (type == 4) {
                Operations.trim(tableName, "AD");
                Operations.trim(tableName, "IL_ADI");
                Operations.trim(tableName, "ILCE_ADI");
                Operations.trim(tableName, "ADRES");
                Utils.showText("***************************************************");
                groupByColumns = " ID, AD, IL_ADI, ILCE_ADI,EVSE_ORDER,CODE, STATION_ID, ADRES, XCOOR, YCOOR, USAGE_TYPE, STATUS, STATION_CODE, STATION_ONLINE, MAX_CURRENT";
            } else if (type == 5) {
                Operations.trim(tableName, "AD");
                Operations.trim(tableName, "IL_ADI");
                Operations.trim(tableName, "ADRES");
                Utils.showText("***************************************************");
                groupByColumns = " ID, AD, IL_ADI";
            }
        } catch (Exception e) {
            Utils.showError(" clearData  hata : " + tableName + " type: " + type);
            e.getStackTrace();
        }
        return groupByColumns;
    }


    public static Document sendRequestGet(String url, boolean isTrust) {
        Document doc = null;
        try {
            if(isTrust){
              HostNamesTrust.trustAllHostnames();
            }
            doc = Jsoup.connect(url)
                       .ignoreContentType(true)
                       .timeout(9000)
                       .get();
        } catch (HttpStatusException e) {
            int err_code = e.getStatusCode();
            if (err_code != 404) {
                Utils.showError("Beklenmeyen hata olustu, hata kodu:" + e.getStatusCode());
                Utils.showError("hata aciklamasi :" + e.getMessage());
                e.printStackTrace();
            } else {
                Utils.showError(url + " bulunamadi, hata kodu:" + e.getStatusCode());
            }
        } catch (Exception e) {
            Utils.showError(" Request hata : " + url + " doc: " + doc);
            e.getStackTrace();
        }
        return doc;
    }

    public static void createTable(String tableName, String columns) {
        try {
            dropTable(tableName);
        } catch (Exception ex) {
            ;
        }
        Connection cnn = null;
        PreparedStatement pstmt = null;
        String sqlStr = null;
        try {
            cnn = DbConn.getPooledConnection();
            sqlStr = "CREATE TABLE " + tableName + " ( " + columns + " )";
            pstmt = cnn.prepareStatement(sqlStr);
            pstmt.execute();

            Utils.showText(tableName + " tablosu basarili sekilde yaratildi.");
        } catch (Exception ex) {
            ex.printStackTrace();
            Utils.showText(tableName + " tablosu yaratilirken hata olustu...");
            Utils.showText(sqlStr);
            Utils.showError("createTable: " + ex.getMessage());
        } finally {
            closeConnections(cnn, pstmt, null);
        }
    }

    private static void dropTable(String table) {
        Connection cnn = null;
        PreparedStatement pstmt = null;
        String sqlStr = "";

        try {
            cnn = DbConn.getPooledConnection();
            sqlStr = "DROP TABLE " + table;
            pstmt = cnn.prepareStatement(sqlStr);
            pstmt.executeUpdate();

            Utils.showText(table + " tablosu drop edildi.");
        } catch (Exception ex) {
            Utils.logInfo(table + " isimde tablo olsaydi silinecekti.");
        } finally {
            closeConnections(cnn, pstmt, null);
        }
    }

    public static void deleteDoubleRecord(String table, String columns) {
        Connection cnn = null;
        PreparedStatement pstmt = null;
        String sqlStr = "";

        try {
            cnn = DbConn.getPooledConnection();
            sqlStr = "DELETE FROM " + table + " WHERE ROWID NOT IN(SELECT MIN(ROWID) FROM " + table + " GROUP BY " + columns + ")";
            pstmt = cnn.prepareStatement(sqlStr);
            pstmt.executeUpdate();

            Utils.showText(table + " tekrar eden kayitlar silindi.");
        } catch (Exception ex) {
            Utils.showError(table + " deleteDoubleRecord hatasi olustu.");
        } finally {
            closeConnections(cnn, pstmt, null);
        }
    }

    public static void trim(String table, String column) {
        PreparedStatement pstmt = null;
        Connection cnn = null;
        String sql = null;

        try {
            cnn = DbConn.getPooledConnection();
            sql = "UPDATE " + table + " SET " + column + "=" + "INITCAP( " + column + " )";
            pstmt = cnn.prepareStatement(sql);
            pstmt.executeUpdate();

            pstmt = null;
            sql = "UPDATE " + table + " SET " + column + "=" + "TRIM( " + column + " )";
            pstmt = cnn.prepareStatement(sql);
            pstmt.executeUpdate();


        } catch (Exception ex) {
            ex.printStackTrace();
            Utils.showError("trım: " + ex.getMessage());
        } finally {
            closeConnections(cnn, pstmt, null);
        }
    }

    public static void replace(String table, String column, String token) {
        PreparedStatement pstmt = null;
        Connection cnn = null;
        String sql = null;

        try {
            cnn = DbConn.getPooledConnection();
            sql = "UPDATE " + table + " SET " + column + "=" + "REPLACE( " + column + ", '" + token + "','' )";
            Utils.showText(sql);
            pstmt = cnn.prepareStatement(sql);
            pstmt.executeUpdate();

            pstmt = null;
            sql = "UPDATE " + table + " SET " + column + "=" + "TRIM( " + column + " )";
            pstmt = cnn.prepareStatement(sql);
            pstmt.executeUpdate();


        } catch (Exception ex) {
            ex.printStackTrace();
            Utils.showError("trım: " + ex.getMessage());
        } finally {
            closeConnections(cnn, pstmt, null);
        }
    }


    public static String phoneNoFormat(String phoneNo) {
        phoneNo = phoneNo.replaceAll("/", "");
        phoneNo = phoneNo.replaceAll("[^\\d.]", "");
        phoneNo = phoneNo.trim();
        try {
            if (phoneNo.startsWith("0") && phoneNo.length() == 11) {
                phoneNo = phoneNo.substring(1, phoneNo.length());
            } else if (phoneNo.startsWith("90")) {
                phoneNo = phoneNo.substring(2, phoneNo.length());
            }

            if (phoneNo.length() == 8 && phoneNo.startsWith("0")) {
                phoneNo = phoneNo.substring(1, 8);
            }

            if (phoneNo.length() == 7) {
                phoneNo = "000" + phoneNo;
            }
        } catch (Exception ex) {
            Utils.showError("Beklenmedik Hata ! @getPhoneNumber()");
            Utils.showError("Beklenmeyen formatta bir telefon numarasi bulundu :" + phoneNo);
            ex.printStackTrace();
            return phoneNo;
        }
        return phoneNo;

    }

    public static HashMap getFillHashMap(Document doc, String tag) {
        HashMap<String, String> brancHM = new HashMap<String, String>();
        try {
            Elements options = doc.getElementById(tag).select("option");
            for (Element option : options) {
                String text = option.text();
                String value = option.attr("value");
                brancHM.put(value, text);
            }
        } catch (Exception ex) {
            Utils.logInfo("getFillHashMap Doc: " + doc + "tag: " + tag);
        }
        return brancHM;
    }

    public static String getToken(Document doc) {
        String strToken = null;
        try {
            strToken = doc.toString();
            if (strToken != null) {
                int startIndex = strToken.indexOf("langId:");
                int endIndex = strToken.indexOf("language:");
                strToken = strToken.substring(startIndex + 9, endIndex);
                strToken = strToken.trim();
                strToken = strToken.substring(0, strToken.length() - 2);
            }
        } catch (Exception ex) {
            Utils.logInfo("getToken Doc: " + doc);
        }
        return strToken;
    }

    public static JSONArray getJsonArrayParse(Document doc, String tag) {
        JSONParser jsonP = new JSONParser();
        JSONArray jsonA = null;
        JSONObject jsonO = null;
        if (doc != null) {
            try {
                jsonO = (JSONObject) jsonP.parse(doc.body().text());
                if (jsonO.equals("districts")) {
                    Object temp = jsonO.get(tag);
                    if (temp == null)
                        return null;
                }
                jsonA = (JSONArray) jsonO.get(tag);
            } catch (Exception ex) {
                Utils.logInfo("getJsonArrayParse Doc: " + doc + "tag: " + tag);
                doc = null;
            }
        }
        return jsonA;
    }

    public static void closeConnections(Connection cnn, PreparedStatement pstmt, ResultSet rset) {
        try {
            if (rset != null)
                rset.close();
            rset = null;
        } catch (Exception e) {
            Utils.showError("closeConnections rset: " + e.getStackTrace());
        }
        try {
            if (pstmt != null)
                pstmt.close();
            pstmt = null;
        } catch (Exception e) {
            Utils.showError("closeConnections pstmt: " + e.getStackTrace());
        }
        try {
            if (cnn != null)
                cnn.close();
            cnn = null;
        } catch (Exception e) {
            Utils.showError("closeConnections cnn: " + e.getStackTrace());
        }

    }

}
