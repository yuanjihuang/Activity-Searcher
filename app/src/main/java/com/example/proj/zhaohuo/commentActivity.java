package com.example.proj.zhaohuo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class commentActivity extends AppCompatActivity {
    private int[] userImgID = new int[11];
    private String[] userImgUrl;
    private String[] username;
    private String[] comment;
    private ListView listView;
    private EditText editText;
    private Button btn;
    private CommentAdapter adapter;
    private List<CommentInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        username = new String[]{"James","Paul","Anthony","Wade"};
        comment = new String[]{"有人带我划划水吗","我就上去传传球","带我的有甜瓜吃","@James@Paul@Anthony 来来来风尘四侠"};
        userImgUrl = new String[]{"https://imgsa.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=238a521cc511728b24208470a995a8ab/aa18972bd40735fa1447933496510fb30e24088c.jpg",
        "http://c.hiphotos.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=6cf30b4496510fb36c147fc5b85aa3f0/8326cffc1e178a82333f3a5dfe03738da877e8c8.jpg",
        "http://c.hiphotos.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=2bcd93ee47c2d562e605d8bf8678fb8a/ca1349540923dd54c4bb9ae6d909b3de9d824877.jpg",
        "http://b.hiphotos.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=06c1c24aa8ec08fa320d1bf538875608/0d338744ebf81a4ca0cd8a3cdf2a6059242da64c.jpg"};
        for(int i=0; i<4; i++){
            String s = "user"+i;
            userImgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
        }
        listView = (ListView) findViewById(R.id.commentList);
        editText = (EditText) findViewById(R.id.input);
        btn = (Button) findViewById(R.id.send);

        list = new ArrayList<>();
        for(int i=0; i<11; i++){
            CommentInfo temp = new CommentInfo(userImgID[i],userImgUrl[i],username[i],comment[i]);
            list.add(temp);
        }
        adapter = new CommentAdapter(this,list);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
}
