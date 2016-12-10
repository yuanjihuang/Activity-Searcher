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

public class communityInfoActivity extends AppCompatActivity {
    private ConnectHelper connectHelper;
    private List<String> more;
    private List<HashMap<String,String>> data;
    private String commander,level,introduction;
    private String[] type = {"负责人","社团级别","社团简介"};
    private ListView lv;
    private String get_user_info;
    private SimpleAdapter simpleAdapter;
    private void initialize(){
        connectHelper = new ConnectHelper();
        get_user_info = connectHelper.url+"/Service/get_user_info.jsp";
        HashMap<String,String> temp = new HashMap<>();
        HashMap<String,String> temp1 = new HashMap<>();
        HashMap<String,String> temp2 = new HashMap<>();
        commander = "XXX";
        level = "校级";
        introduction = "这是一个sb的社团，这是一个sb的社团，这是一个sb的社团，这是一个sb的社团，这是一个sb的社团";
        data = new ArrayList<>();
        temp.put("Type",type[0]);temp.put("Info",commander);data.add(temp);
        temp1.put("Type",type[1]); temp1.put("Info",level);data.add(temp1);
        temp2.put("Type",type[2]);temp2.put("Info",introduction);data.add(temp2);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_info);
        lv = (ListView)findViewById(R.id.com_user_info_listview);
        initialize();
        simpleAdapter = new SimpleAdapter(this,data,R.layout.user_info_item_show,new String[]{"Type","Info"},new int[]{R.id.show_user_type,R.id.show_user_info});
        lv.setAdapter(simpleAdapter);
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
