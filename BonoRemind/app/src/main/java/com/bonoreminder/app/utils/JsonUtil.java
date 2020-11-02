package com.bonoreminder.app.utils;

import android.content.Context;

import com.bonoreminder.app.R;
import com.bonoreminder.app.app.AppContext;
import com.google.gson.Gson;

import java.util.ArrayList;
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

    public static String strToJson(String str) {
        List<String> strList = new ArrayList<>();
        Context context = AppContext.getContext();
        String[] strData = str.split(",");
        for (String data : strData) {
            strList.add(data);
        }
        return strListToJson(strList);
    }

    /**
     * @param str 需要转化的字符串，格式为以逗号分隔的，例如 "提前5分钟,提前半个小时,提前一天"
     * @return  Json字符串
     */
    public static String strRemindToJson(String str) {
        List<String> strList = new ArrayList<>();
        Context context = AppContext.getContext();
        String[] strData = str.split(",");
        for (String data : strData) {
            if (data.equals(context.getString(R.string.none))) {
                data = "-1";
            }else if (data.equals(context.getString(R.string.one_time))){
                data = "0";
            }else if (data.equals(context.getString(R.string.five_mins_early))) {
                data = 5 * 60 * 1000 + "";
            }else if (data.equals(context.getString(R.string.thirty_mins_early))) {
                data = 30 * 60 * 1000 + "";
            }else if (data.equals(context.getString(R.string.one_hour_early))) {
                data = 60 * 60 * 1000 + "";
            }else if (data.equals(context.getString(R.string.one_day_early))) {
                data = 24 * 60 * 60 * 1000 + "";
            }else if (data.equals(context.getString(R.string.two_day_early))) {
                data = 2 * 24 * 60 * 60 * 1000 + "";
            }else if (data.equals(context.getString(R.string.three_day_early))) {
                data = 3 * 24 * 60 * 60 * 1000 + "";
            }else if (data.equals(context.getString(R.string.one_week_early))) {
                data = 7 * 24 * 60 * 60 * 1000 + "";
            }
            strList.add(data);
        }
        return strListToJson(strList);
    }



}
