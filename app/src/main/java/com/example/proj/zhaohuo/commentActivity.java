package com.example.proj.zhaohuo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

public class commentActivity extends AppCompatActivity {
    private ArrayList<Integer> userImgID;
    private int actImg;
    private String actName;
    private int actID;
    private ArrayList<String> userImgUrl;
    private ArrayList<String> username;
    private ArrayList<String> comment;
    private ImageView image;
    private TextView title;
    private ListView listView;
    private EditText editText;
    private Button btn;
    private CommentAdapter adapter;
    private List<CommentInfo> list;
    private ConnectHelper connectHelper;
    private String updateComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        connectHelper = new ConnectHelper();
        updateComment = connectHelper.url;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        actName = bundle.getString("actName");
        actImg = bundle.getInt("imgID");
        actID = bundle.getInt("actID");
        userImgID = new ArrayList<>();
        comment = new ArrayList<>();
        userImgUrl = new ArrayList<>();
        username = new ArrayList<>();
        String []Tname = new String[]{"James","Paul","Anthony","Wade"};
        String []Tcomment = new String[]{"有人带我划划水吗","我就上去传传球","带我的有甜瓜吃","@James@Paul@Anthony 来来来风尘四侠"};
        String []TuserImgUrl = new String[]{"https://imgsa.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=238a521cc511728b24208470a995a8ab/aa18972bd40735fa1447933496510fb30e24088c.jpg",
                "http://c.hiphotos.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=6cf30b4496510fb36c147fc5b85aa3f0/8326cffc1e178a82333f3a5dfe03738da877e8c8.jpg",
                "http://c.hiphotos.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=2bcd93ee47c2d562e605d8bf8678fb8a/ca1349540923dd54c4bb9ae6d909b3de9d824877.jpg",
                "http://b.hiphotos.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=06c1c24aa8ec08fa320d1bf538875608/0d338744ebf81a4ca0cd8a3cdf2a6059242da64c.jpg"};
        for(int i=0; i<4; i++) {
            String s = "user"+i;
            userImgID.add(getResources().getIdentifier(s,"drawable",getPackageName()));
            comment.add(Tcomment[i]);
            username.add(Tname[i]);
            userImgUrl.add(TuserImgUrl[i]);
        }
        listView = (ListView) findViewById(R.id.commentList);
        editText = (EditText) findViewById(R.id.input);
        btn = (Button) findViewById(R.id.send);
        image = (ImageView) findViewById(R.id.commentImg);
        title = (TextView) findViewById(R.id.commentTitle);

        title.setText(actName);
        image.setImageResource(actImg);

        list = new ArrayList<>();
        for(int i=0; i<username.size(); i++){
            CommentInfo temp = new CommentInfo(userImgID.get(i),userImgUrl.get(i),username.get(i),comment.get(i));
            list.add(temp);
        }
        adapter = new CommentAdapter(this,list);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                int userImg = getResources().getIdentifier("ic_avatar","drawable",getPackageName());
                updateComment = updateComment+"Service/set_comment.jsp?AcctName="
                        +CurrentAcct.AcctName+"&ActID="+actID+"&Comment="+input;
                new SetComment().execute(updateComment);
                CommentInfo temp = new CommentInfo(userImg,"",CurrentAcct.AcctName,input);
                list.add(temp);
                editText.setText("");
                hideKeyBoard();//隐藏键盘
            }
        });
    }
    // 隐藏键盘
    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }
    private class SetComment extends AsyncTask<String,Integer,List<String>> {
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
                    userImgID = new ArrayList<>();
                    comment = new ArrayList<>();
                    userImgUrl = new ArrayList<>();
                    username = new ArrayList<>();
                    for(int i = 0; i < result.size(); i++)
                        Log.d("relist",result.get(i));
                    ///////解析json信息/////////////
                    JSONArray ActList = JsonUtils.parse(result.get(0),"Act");//返回形式为多个键值对组成的序列
                    list.clear();//清除当前活动
                    Log.d("Length: ",""+ActList.length());
                    for(int i=0; i<ActList.length(); i++){
                        try{
                            JSONObject oj = ActList.getJSONObject(i);
                            String tem = "user"+i;
                            userImgID.add(getResources().getIdentifier(tem,"drawable",getPackageName()));
                            userImgUrl.add(oj.getString("userImgUrl"));
                            username.add(oj.getString("username"));
                            comment.add(oj.getString("comment"));
                            CommentInfo temp = new CommentInfo(userImgID.get(i),userImgUrl.get(i),username.get(i),comment.get(i));
                            list.add(temp);
                        }catch (Exception e){}
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
