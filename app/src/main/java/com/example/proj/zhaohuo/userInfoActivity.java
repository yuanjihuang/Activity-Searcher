package com.example.proj.zhaohuo;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class userInfoActivity extends AppCompatActivity {
    private ConnectHelper connectHelper;
    private InfoAdapter infoAdapter;
    private List<String> info;
    private List<String> index;
    private String get_user_info;
    private String set_user_info;
    private RelativeLayout setUserInfo;
    private ListView user_info_listview;
    private TextView userIntro;
    private int kind;
    private void initialize(){
        connectHelper = new ConnectHelper();
        get_user_info = connectHelper.url+"Service/get_user_info.jsp";
        set_user_info = connectHelper.url+"Service/set_user_info.jsp";
        user_info_listview = (ListView)findViewById(R.id.user_info_listview);
        userIntro = (TextView)findViewById(R.id.UserIntro);
        setUserInfo = (RelativeLayout)findViewById(R.id.setUserInfo);
        info = new ArrayList<>();
        index = new ArrayList<>();
        index.add("学校");
        index.add("姓名");
        index.add("学号");
        index.add("年级");
        info.add("中山大学");
        info.add("炮王");
        info.add("12345677");
        info.add("1");
        info.add("大家好，我是来自移动信息工程的孙笔笔同学,我最喜欢凌晨四点的逸仙大道。");
        new DownloadWebpageText().execute(get_user_info+"?AcctName="+CurrentAcct.AcctName);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_info);
        initialize();
        setUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(userInfoActivity.this);
                View v = factory.inflate(R.layout.set_user_info_dialog,null);
                final EditText edit_school,edit_name,edit_grade,edit_id,edit_intro;
                edit_school = (EditText)v.findViewById(R.id.edit_school);
                edit_name = (EditText)v.findViewById(R.id.edit_name);
                edit_grade = (EditText)v.findViewById(R.id.edit_grade);
                edit_id = (EditText)v.findViewById(R.id.edit_id);
                edit_intro = (EditText)v.findViewById(R.id.edit_intro);
                for(int i = 0; i < info.size(); i++)
                    Log.d("info",info.get(i));
                edit_school.setText(info.get(0));
                edit_name.setText(info.get(1));
                edit_id.setText(info.get(2));
                edit_grade.setText(info.get(3));
                edit_intro.setText(info.get(4));
                AlertDialog.Builder builder = new AlertDialog.Builder(userInfoActivity.this);
                builder.setTitle("更改信息");
                builder.setView(v);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        info.clear();
                        info.add(edit_school.getText().toString());
                        info.add(edit_name.getText().toString());
                        info.add(edit_id.getText().toString());
                        info.add(edit_grade.getText().toString());
                        info.add(edit_intro.getText().toString());
                        userIntro.setText(edit_intro.getText().toString());
                        String grade;
                        switch (info.get(3)){
                            default:
                                grade = "freshman";
                                break;
                            case "1":
                                grade = "freshman";
                                break;
                            case "2":
                                grade = "sophomore";
                                break;
                            case "3":
                                grade = "junior";
                                break;
                            case "4":
                                grade = "senior";
                                break;
                        }
                        new DownloadWebpageText().execute(set_user_info+"?AcctName="+CurrentAcct.AcctName+"&AcctKind="+kind+"&UserSchool="+info.get(0)+"&UserName="+info.get(1)+"&UserID="+info.get(2)+"&UserGrade="+grade+"&UserIntro="+info.get(4)+"&UserSex="+0);
                        infoAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog2, int which) {

                    }
                });
                builder.show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        new DownloadWebpageText().execute(get_user_info+"?AcctName="+CurrentAcct.AcctName);
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
                    JSONArray KindList = JsonUtils.parse(result.get(0),"User_Kind");
                    try{
                        JSONObject oj = KindList.getJSONObject(0);
                        kind = oj.getInt("ActKind");
                    }catch (Exception e){}
                    info.clear();//清除当前信息
                    try{
                        JSONObject oj = ActList.getJSONObject(0);
                        info.add(oj.getString("UserSchool"));//学校姓名学号年级
                        info.add(oj.getString("UserName"));
                        info.add(oj.getString("UserID"));
                        info.add(oj.getString("UserGrade"));
                        info.add(oj.getString("UserIntro"));
                        userIntro.setText(info.get(4));
                        infoAdapter = new InfoAdapter(userInfoActivity.this,info,index);
                        user_info_listview.setAdapter(infoAdapter);
                    }catch (Exception e){}
                    //JSONArray FavoriteList = JsonUtils.parseAct(result.get(0));
                    /*
                    在这里更新UI
                     */
                }
            }
        }
    }
}
