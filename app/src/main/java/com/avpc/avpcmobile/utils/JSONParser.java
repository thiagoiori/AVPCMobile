package com.avpc.avpcmobile.utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

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