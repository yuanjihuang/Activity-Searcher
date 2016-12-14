package com.example.proj.zhaohuo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import adapter.InfoAdapter;
import adapter.MoreAdapter;

public class userInfoActivity extends AppCompatActivity {
    private ConnectHelper connectHelper;
    private InfoAdapter infoAdapter;
    private List<String> info;
    private List<String> index;
    private String get_user_info;
    private ListView user_info_listview;
    private void initialize(){
        connectHelper = new ConnectHelper();
        get_user_info = connectHelper.url+"/Service/get_user_info.jsp";
        user_info_listview = (ListView)findViewById(R.id.user_info_listview);
        info = new ArrayList<>();
        index = new ArrayList<>();
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
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //new DownloadWebpageText().execute(get_user_info+"?Name="+CurrentAcct.AcctName);
    }

    /*private class DownloadWebpageText extends AsyncTask<String,Integer,List<String>> {
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
                    info.clear();//清除当前信息
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
                            String tem = "st" + oj.getInt("ActID");
                            imgID[i] = getResources().getIdentifier(tem,"drawable",getPackageName());
                            imgUrl[i] = oj.getString("ActUrl");
                            name[i] = oj.getString("ActName");
                            info[i] = oj.getString("ActInfo");
                            remark[i] = oj.getString("ActRemark");
                            follow[i] = favorite.contains(imgID[i])?1:0;//判断是否为喜爱活动
                            ActivityInfo temp = new ActivityInfo(imgID[i],imgUrl[i],name[i],info[i],remark[i],follow[i]);
                            list.add(temp);
                        }catch (Exception e){}
                    }
                    adapter.notifyDataSetChanged();

                    //JSONArray FavoriteList = JsonUtils.parseAct(result.get(0));
                    /*
                    在这里更新UI


                }
            }
        }
    }*/
}
