package com.example.proj.zhaohuo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import adapter.ActivityAdapter;

/**
 * Created by Eafun on 2016/12/8.
 */

public class ActivitySquare extends AppCompatActivity {
    private int[] imgID = new int[11];
    private ListView listView;
    private ConnectHelper connectHelper;
    private String getDataUrl;
    private List<ActivityInfo> data;
    private ActivityAdapter adapter;
    private String AcctName;
    private void initialize(){
        connectHelper = new ConnectHelper();
        data = new ArrayList<>();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylist);
        initialize();
        getDataUrl = connectHelper.url+"Service/main_activity.jsp";
        for(int i=0; i<11; i++){
            String s = "st"+i;
            imgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
        }

        adapter = new ActivityAdapter(this,data);
        listView = (ListView) findViewById(R.id.activityList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到详情页面
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new DownloadWebpageText().execute(getDataUrl+"?Name="+CurrentAcct.AcctName);
        Log.d("request","zhangsan");
    }

    private class DownloadWebpageText extends AsyncTask<String,Integer,List<String>> {
        @Override
        protected List<String> doInBackground(String... urls) {
            try {
                List<String> reList = connectHelper.downloadUrl(urls[0]);
                return reList; //连接并下载数据
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
                    for(int i = 0; i < result.size(); i++)
                    Log.d("relist",result.get(i));
                    ///////解析json信息/////////////
                    JSONArray ActList = JsonUtils.parse(result.get(0),"Act");//返回形式为多个键值对组成的序列
                    JSONArray FavoriteList = JsonUtils.parse(result.get(0),"Favorite");
                    data.clear();//清除当前活动
                    Set<Integer> favorite = new HashSet<>();//用于储存喜爱活动的ID
                    for(int i=0; i<FavoriteList.length(); i++){
                        try{
                            JSONObject oj = FavoriteList.getJSONObject(i);//获取Act数组中的第i个对象，是1个键值对
                            favorite.add(oj.getInt("ActID"));//通过访问键得到数据
                        }catch (Exception e){}
                    }
                    for(int i=0; i<ActList.length(); i++){
                        try{
                            JSONObject oj = ActList.getJSONObject(i);
                            int ID = oj.getInt("ActID");
                            int flag = favorite.contains(ID)?1:0;//判断是否为喜爱活动
                            ActivityInfo temp = new ActivityInfo(imgID[i],oj.getString("ActName"),oj.getString("ActPlace"),flag);
                            data.add(temp);
                        }catch (Exception e){}
                    }
                    adapter.notifyDataSetChanged();
                    //JSONArray FavoriteList = JsonUtils.parseAct(result.get(0));
                    /*
                    在这里更新UI
                     */

                }
            }
        }
    }
}
