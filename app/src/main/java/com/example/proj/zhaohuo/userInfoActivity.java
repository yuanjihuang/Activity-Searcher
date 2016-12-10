package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import adapter.InfoAdapter;
import adapter.MoreAdapter;

public class userInfoActivity extends AppCompatActivity {
    private ConnectHelper connectHelper;
    private InfoAdapter infoAdapter;
    private MoreAdapter moreAdapter;
    private List<String> more;
    private List<String> info;
    private List<String> index;
    private String get_user_info;
    private ListView user_info_listview;
    private ListView more_listview;
    private void initialize(){
        connectHelper = new ConnectHelper();
        get_user_info = connectHelper.url+"/Service/get_user_info.jsp";
        user_info_listview = (ListView)findViewById(R.id.user_info_listview);
        more_listview = (ListView)findViewById(R.id.more_listview);
        more = new ArrayList<>();
        info = new ArrayList<>();
        index = new ArrayList<>();
        more.add("我的圈子");
        index.add("学校");
        index.add("姓名");
        index.add("学号");
        index.add("年级");
        info.add("中山大学");
        info.add("炮王");
        info.add("12345677");
        info.add("大一");
        infoAdapter = new InfoAdapter(this,info,index);
        user_info_listview.setAdapter(infoAdapter);
        moreAdapter = new MoreAdapter(this,more);
        more_listview.setAdapter(moreAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initialize();
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
                    /*
                    在这里更新UI
                     */

                }
            }
        }
    }
}
