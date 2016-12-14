package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
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


public class ActivitySquare extends AppCompatActivity {
    private int[] imgID = new int[11];
    private int[] follow = new int[11];
    private String[] name;
    private String[] info;
    private String[] remark;
    private String[] imgUrl;
    private String[] actUrl;
    private int resultFollow;
    private ListView listView;
    private ConnectHelper connectHelper;
    private String getDataUrl;
    private ActivityAdapter adapter;
    private List<ActivityInfo> list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylist);
        connectHelper = new ConnectHelper();
        getDataUrl = connectHelper.url+"Service/main_activity.jsp";

        /*for(int i=0; i<11; i++){
            String s = "st"+i;
            imgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
            follow[i] = i%2;
        }*/

        name = new String[11];
        info = new String[11];
        remark = new String[11];
        actUrl = new String[11];
        imgUrl = new String[11];
        list = new ArrayList<>();
        new DownloadWebpageText().execute(getDataUrl+"?Name="+CurrentAcct.AcctName);
        adapter = new ActivityAdapter(this,list);
        listView = (ListView) findViewById(R.id.activityList);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //跳转到详情页面
                final ActivityAdapter.ViewHolder viewHolder =(ActivityAdapter.ViewHolder) adapter.getView(position,view,parent).getTag();
                follow[position] = adapter.getCurrentFollow();
                Log.d("URL: ",name[position]);
                Bundle bundle = new Bundle();
                bundle.putString("url",actUrl[position]);
                bundle.putInt("follow",follow[position]);
                bundle.putInt("position",position);
                Intent intent = new Intent(ActivitySquare.this,actDetailActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                switch (resultCode){
                    case 0:
                        resultFollow = data.getIntExtra("resultFollow",0);
                        int position = data.getIntExtra("position",0);
                        follow[position] = resultFollow;
                        ActivityInfo temp = new ActivityInfo(imgID[position],imgUrl[position],name[position],info[position],remark[position],follow[position]);
                        list.set(position,temp);
                        adapter.notifyDataSetChanged();
                        Log.d("resultFollow",""+resultFollow);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new ActivitySquare.DownloadWebpageText().execute(getDataUrl+"?Name="+CurrentAcct.AcctName);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d("request",CurrentAcct.AcctName);
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
                    list.clear();//清除当前活动
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
                            actUrl[i] = oj.getString("ActUrl");
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
                     */

                }
            }
        }
    }
}
