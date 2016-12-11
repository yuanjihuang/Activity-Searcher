package com.example.proj.zhaohuo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yelluw on 2016/12/11.
 */

public class JsonUtils
{
    /**
     从服务器端得到的JSON字符串数据
     解析JSON字符串数据，放入List当中
     */
    public static JSONArray parse(String s, String param)
    {
        List<String> Act = new ArrayList<>();

        JSONArray jsonArray = new JSONArray();
        try
        {
            JSONObject jsonObject = new JSONObject(s);
            jsonArray = jsonObject.getJSONArray(param);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonArray;
    }
}