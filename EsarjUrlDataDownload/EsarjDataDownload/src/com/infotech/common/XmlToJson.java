package com.infotech.common;

import org.json.JSONObject;
import org.json.XML;
import org.json.JSONException;

public class XmlToJson {
    public static String converXmlToJson(String xml){
       JSONObject json =  null;
       try {  
           json = XML.toJSONObject(xml); 
           if(json!=null)
             return json.toString();
       }catch (JSONException e) {  
           Utils.showError("converXmlToJson:"+e.toString());  
       }  
      return null;
   }
}
