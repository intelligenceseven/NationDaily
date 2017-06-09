package com.sikeandroid.nationdaily.utils;

import android.util.Log;

import com.sikeandroid.nationdaily.main.data.DailyNation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/********************************************************************************
 * using for: Json 解析工具类
 * 丁酉鸡年四月 2017/06/08 22:58
 * @author 西唐王, xtwyzh@gmail.com,blog.xtwroot.com
 * xtwroot Copyrights (c) 2017. All rights reserved.
 ********************************************************************************/

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static void parseNationLabs(List<DailyNation> items, JSONObject jsonBody) throws IOException,JSONException
    {
        JSONArray nationJsonArray = jsonBody.getJSONArray("nation");

        for(int i = 0;i < nationJsonArray.length();++i)
        {
            JSONObject nationJsonObject = nationJsonArray.getJSONObject(i);

            DailyNation item = new DailyNation();
            if(nationJsonObject.has("name"))
                item.setName(nationJsonObject.getString("name"));
            if(nationJsonObject.has("date"))
                item.setDate(nationJsonObject.getString("date"));
            if(nationJsonObject.has("describe"))
                item.setDescribe(nationJsonObject.getString("describe"));
            if(nationJsonObject.has("image_url"))
                item.setImageUrl(nationJsonObject.getString("image_url"));
            if(nationJsonObject.has("details_url"))
                item.setUrl(nationJsonObject.getString("details_url"));
            if(nationJsonObject.has("greeting"))
                item.setGreeting(nationJsonObject.getString("greeting"));
            items.add(item);
        }
        Log.i(TAG,String.valueOf(items.size()));
    }
}
