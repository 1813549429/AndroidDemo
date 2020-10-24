package com.example.remind.utils;

import com.google.gson.Gson;


import java.util.List;

public class JsonUtil {

    public static List<String> jsonToStrList(String jsonData) {
        Gson gson = new Gson();
        List<String> strList = gson.fromJson(jsonData, List.class);
        return strList;
    }

    public static String strListToJson(List<String> strList) {
        Gson gson = new Gson();
        String jsonData = gson.toJson(strList);
        return jsonData;
    }

}
