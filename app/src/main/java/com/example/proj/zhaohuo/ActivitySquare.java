package com.example.proj.zhaohuo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Eafun on 2016/12/8.
 */

public class ActivitySquare extends AppCompatActivity {
    private int[] imgID = new int[11];
    private int[] follow = new int[11];
    private ListView listView;
    private ConnectHelper connectHelper;
    private String getDataUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylist);
        connectHelper = new ConnectHelper();
        getDataUrl = connectHelper.url+"Service/main_activity.jsp";
        new DownloadWebpageText().execute(getDataUrl+"?Name="+"zhangsan");

        for(int i=0; i<11; i++){
            String s = "st"+i;
            imgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
            follow[i] = i%2;
        }
        String name[] = {"五克拉舞会","SDCS学生会","SDCS团委","中大创协","中大Din","中珠职协","中东校会","SMIE学生会","中东GBT","中大Aiesec","中大闲置"};
        String info[] = {"五克拉舞会","SDCS学生会","SDCS团委","中大创协","中大Din","中珠职协","中东校会","SMIE学生会","中东GBT","中大Aiesec","中大闲置"};
        List<ActivityInfo> data = new ArrayList<>();
        for(int i=0; i<11; i++){
            ActivityInfo temp = new ActivityInfo(imgID[i],name[i],info[i],follow[i]);
            data.add(temp);
        }
        ActivityAdapter adapter = new ActivityAdapter(this,data);
        listView = (ListView) findViewById(R.id.activityList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到详情页面
            }
        });
    }
    private class DownloadWebpageText extends AsyncTask<String,Integer,List<String>> {
        @Override
        protected List<String> doInBackground(String... urls) {
            try {
                return connectHelper.downloadUrl(urls[0]); //连接并下载数据
            } catch (IOException e) {
                e.printStackTrace();
                List<String> reList = new LinkedList<>();//返回的字符串数组，若只有1个字符串取reList.get(0)
                return reList;
            }
        }
        @Override
        protected void onPostExecute(List<String> result) {
            if(result != null){
                if(result.size() == 0){
                    Toast.makeText(getApplicationContext(),"没有返回值，请再试一次！",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("relist",result.get(0));
                    /*
                    在这里更新UI
                     */

                }
            }
        }
    }
}
