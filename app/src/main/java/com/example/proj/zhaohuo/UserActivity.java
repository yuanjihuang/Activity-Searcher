package com.example.proj.zhaohuo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import adapter.InfoAdapter;
import adapter.MoreAdapter;

public class UserActivity extends AppCompatActivity {
    private ConnectHelper connectHelper;
    private MoreAdapter moreAdapter;
    private List<String> more;
    private String get_user_info;
    private ListView more_listview;
    private RelativeLayout userInfo;
    private Button myFavorite;
    private void initialize(){
        connectHelper = new ConnectHelper();
        get_user_info = connectHelper.url+"/Service/get_user_info.jsp";
        more_listview = (ListView)findViewById(R.id.more_listview);
        userInfo = (RelativeLayout)findViewById(R.id.userInfo);
        myFavorite = (Button) findViewById(R.id.myFavorite);
        more = new ArrayList<>();
        more.add("问题反馈");
        more.add("关于我们");
        more.add("退出登录");
        moreAdapter = new MoreAdapter(this,more);
        more_listview.setAdapter(moreAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        setContentView(R.layout.activity_user);
        initialize();
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this,userInfoActivity.class);
                startActivity(intent);
            }
        });
        myFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this,MyFollow.class);
                startActivity(intent);
            }
        });
    }
}
