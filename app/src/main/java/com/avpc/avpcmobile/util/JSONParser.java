package com.avpc.avpcmobile.util;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

//    public static String getName(JSONObject obj) {
//        String s1 = "";
//        try {
//            s1 = obj.getString("name");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return s1;
//    }
//
//    public static String getId(JSONObject obj) {
//        String s1 = "";
//        try {
//            s1 = obj.getString("id");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return s1;
//    }

    public static String getId(JSONObject obj, String property) {
        String s1 = "";
        try {
            s1 = obj.getString(property);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s1;
    }
}