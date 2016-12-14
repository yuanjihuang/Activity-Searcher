package com.example.proj.zhaohuo;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import adapter.InfoAdapter;
import adapter.MoreAdapter;

public class communityInfoActivity extends AppCompatActivity {
    private ConnectHelper connectHelper;
    private InfoAdapter infoAdapter;
    private MoreAdapter moreAdapter;
    private List<String> info;
    private List<String> index;
    private List<String> more;
    private String get_user_info;
    private ListView user_info_listview;
    private ListView more_listview;
    private void initialize(){
        connectHelper = new ConnectHelper();
        get_user_info = connectHelper.url+"/Service/get_user_info.jsp";
        user_info_listview = (ListView)findViewById(R.id.group_user_info_listview);
        info = new ArrayList<>();
        index = new ArrayList<>();
        index.add("负责人");
        index.add("所属学校");
        index.add("社团级别");
        info.add("孙笔笔");
        info.add("中山大学");
        info.add("校级");
        infoAdapter = new InfoAdapter(this,info,index);
        user_info_listview.setAdapter(infoAdapter);
        more_listview = (ListView)findViewById(R.id.group_more_listview);
        more = new ArrayList<>();
        more.add("社团活动");
        moreAdapter = new MoreAdapter(this,more);
        more_listview.setAdapter(moreAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_info);
        initialize();
    }
   /* private class DownloadWebpageText extends AsyncTask<String,Integer,List<String>> {
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
                    /*
                    在这里更新UI


                }
            }
        }
    }*/
}
