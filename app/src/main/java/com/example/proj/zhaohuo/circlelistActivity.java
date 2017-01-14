package com.example.proj.zhaohuo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proj.zhaohuo.circleInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import adapter.CircleAdapter;

/**
 * Created by Administrator on 2016/12/9.
 */

public class circlelistActivity extends AppCompatActivity {
    List<circleInfo> circleInfosList = new ArrayList<>();
    ImageView circleUserIcon;
    TextView circleName, circleBriefIntro;
    Toolbar toolbar;
    private int cirID = 1;
    private ProgressDialog pd1;
    private ConnectHelper connectHelper;
    private String getDataUrl;
    int[] imgID = new int[10];
    private String[] name = new String[11];
    private String[] briefIntro = new String[11];
    private StringBuilder follow = new StringBuilder();
    ListView circle_listView;
    CircleAdapter circleAdapter;
    public static circlelistActivity instance = null;
    private void initialize(){
        circleUserIcon = (ImageView) findViewById(R.id.circle_user_ic);
        circleName = (TextView) findViewById(R.id.circle_name);
        circleBriefIntro = (TextView) findViewById(R.id.circle_briIntro);
        circle_listView = (ListView) findViewById(R.id.circle_listView);
        connectHelper = new ConnectHelper();
        getDataUrl = connectHelper.url+"Service/get_circle.jsp";
        for(int i=0; i<10; i++){
            String s = "st" + i;
            imgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.circlelist);
        initialize();
        instance = this;
        new DownloadWebpageText().execute(getDataUrl);
        circleAdapter = new CircleAdapter(this, circleInfosList);
        circle_listView.setAdapter(circleAdapter);
        circle_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(circlelistActivity.this, circleDiscussionZone.class);
                intent.putExtra("circleName", name[position]);
                intent.putExtra("cirID", position+1);
                startActivity(intent);
            }
        });
        circleAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(circlelistActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        pd1 = ProgressDialog.show(circlelistActivity.this,null, "加载中……");
        new circlelistActivity.DownloadWebpageText().execute(getDataUrl);
        circle_listView.setAdapter(circleAdapter);
        circleAdapter.notifyDataSetChanged();
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
            pd1.dismiss();
            if(result != null){
                if(result.size() == 0){
                    Toast.makeText(getApplicationContext(),"没有返回值，请再试一次！",Toast.LENGTH_SHORT).show();
                }else{
                    for(int i = 0; i < result.size(); i++)
                        Log.d("relist",result.get(i));
                    ///////解析json信息/////////////
                    JSONArray CirList = JsonUtils.parse(result.get(0),"Cir");//返回形式为多个键值对组成的序列
                    circleInfosList.clear();//清除当前圈子

                    //Set<String> follow = new HashSet<>();//用于储存关注活动的id
                    for(int i=0; i<CirList.length(); i++){
                        try{
                            JSONObject oj = CirList.getJSONObject(i);
                            name[i] = oj.getString("CirName");
                            briefIntro[i] = oj.getString("CirIntro");
                            circleInfosList.add(new circleInfo(imgID[i], name[i], "简介："+briefIntro[i]));

                        }catch (Exception e){}
                    }
                    circleAdapter.notifyDataSetChanged();
                    //JSONArray FavoriteList = JsonUtils.parseAct(result.get(0));
                    /*
                    在这里更新UI
                     */
                }
            }
        }
    }
}
