package com.infotech.esarjdownload;


import com.infotech.common.Operations;
import com.infotech.common.Utils;
import com.infotech.model.GCharge;
import com.infotech.model.SharzNet;
import com.infotech.model.Voltrun;
import com.infotech.model.Zes;
import java.util.Scanner;


public class EsarjDownload {
    private final static String app_name = "ESarj Data Downloader";
    private final static String app_ver = "v.1.0 20230124";


    public static void main(String[] args) {
        try {
            Utils.showText("***************************************************");
            Utils.showText(app_name + " version :" + app_ver);
            Scanner keyboard = new Scanner(System.in);
            int selected = 0;
            Utils.showText("Uygulama basladi. @" + Utils.getCurrentDateTime());
            do {
                System.out.println("!!!!!UYGULAMA GUN ICINDE AYNI URL ICIN BIRDEN FAZLA CALISTIRILIRSA VAROLAN TABLO DROP EDILIP TEKRAR YARATILIR!!!!!");
                System.out.println("Lutfen indirmek istediginiz datanin numarasini giriniz");
                System.out.println("1 - Sharz.Net");
                System.out.println("2 - ZES");
                System.out.println("3 - G-Charge");
                System.out.println("4 - Voltrun");
                System.out.println("5 - Cikis");
                selected = keyboard.nextInt();

                String date = Utils.getCurrentDate();
                String table = null;
                String columns = null;
                String groupByColumns = null;
                if (selected == 1) {
                    table = Utils.getParameter("sharzTable");
                    columns = Operations.createColums(selected);
                    String tableName = table + date;
                    Operations.createTable(tableName, columns);
                    Utils.showText("************************* Veri Indirme Islemi Basladi...**************************");
                    SharzNet.findData(tableName);
                    Utils.showText("************************* Veri Indirme Islemi Bitti...**************************");
                    Utils.showText("************************* Veri Temizleme Islemi Basladi...***********************");
                    groupByColumns = Operations.clearData(tableName, selected);

                    Operations.deleteDoubleRecord(tableName, groupByColumns);
                    Utils.showText("************************* Veri Temizleme Islemi Bitti....***********************");
                } else if (selected == 2) {
                    table = Utils.getParameter("zesTable");
                    columns = Operations.createColums(selected);
                    String tableName = table + date;
                    Operations.createTable(tableName, columns);
                    Utils.showText("************************* Veri Indirme Islemi Basladi...**************************");
                    Zes.findData(tableName);
                    Utils.showText("************************* Veri Indirme Islemi Bitti...**************************");
                    Utils.showText("************************* Veri Temizleme Islemi Basladi...***********************");
                    groupByColumns = Operations.clearData(tableName, selected);

                    Operations.deleteDoubleRecord(tableName, groupByColumns);
                    Utils.showText("************************* Veri Temizleme Islemi Bitti....***********************");
                }else if (selected == 3) {
                    table = Utils.getParameter("gchargeTable");
                    columns = Operations.createColums(selected);
                    String tableName = table + date;
                    Operations.createTable(tableName, columns);
                    Utils.showText("************************* Veri Indirme Islemi Basladi...**************************");
                    GCharge.findData(tableName);
                    Utils.showText("************************* Veri Indirme Islemi Bitti...**************************");
                    Utils.showText("************************* Veri Temizleme Islemi Basladi...***********************");
                    groupByColumns = Operations.clearData(tableName, selected);
                    Operations.deleteDoubleRecord(tableName, groupByColumns);
                    Utils.showText("************************* Veri Temizleme Islemi Bitti....***********************");

                } else if (selected == 4) {
                    table = Utils.getParameter("voltrunTable");
                    columns = Operations.createColums(selected);
                    String tableName = table + date;
                    Operations.createTable(tableName, columns);
                    Utils.showText("************************* Veri Indirme Islemi Basladi...**************************");
                    Voltrun.findData(tableName);
                    Utils.showText("************************* Veri Indirme Islemi Bitti...**************************");
                    Utils.showText("************************* Veri Temizleme Islemi Basladi...***********************");
                    groupByColumns = Operations.clearData(tableName, selected);
                    Operations.deleteDoubleRecord(tableName, groupByColumns);
                    Utils.showText("************************* Veri Temizleme Islemi Bitti....***********************");
                } else {
                    Utils.showText(app_name + " cikis yapildi. @" + Utils.getCurrentDateTime());
                }

            } while (selected > 0 && selected < 5);
            Utils.showText(app_name + " uygulamasi bitti. @" + Utils.getCurrentDateTime());

        } catch (Exception e) {
            Utils.showError("Genel hata :" + e.getMessage());
        }
    }


   
    

}
