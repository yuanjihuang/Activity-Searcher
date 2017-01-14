package com.example.proj.zhaohuo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;

public class PostMsgActivity extends AppCompatActivity {
    EditText postTitle, postContent;
    private String circleName="";
    private int cirID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_post_msg);
        postTitle = (EditText) findViewById(R.id.title);
        postContent = (EditText) findViewById(R.id.content);
        Intent intent = getIntent();
        circleName = intent.getStringExtra("circleName");
        cirID = intent.getIntExtra("cirID", 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_post_ok,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int flag=0;
        switch (item.getItemId()){
            case R.id.menu_post_ok:
                flag=2; break;
            case android.R.id.home:
                flag = 1;
                break;
            default:
                break;
        }
        if(flag==1){
            Intent intent = new Intent(PostMsgActivity.this, circleDiscussionZone.class);
            startActivityForResult(intent, 1);
            this.finish();
        }
        else if(flag==2){
            String title = postTitle.getText().toString();
            String content = postContent.getText().toString();
            if(title.equals("") || content.equals("")){
                Toast.makeText(PostMsgActivity.this, "请输入完整帖子", Toast.LENGTH_SHORT).show();
            }
            else{
                //TO-DO 传入数据库 圈子ID为cirID，发布title为title，内容为content，发帖人ID就是登陆帐号的ID
                Intent intent = new Intent(PostMsgActivity.this, circleDiscussionZone.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("circleName", circleName);
                PostMsgActivity.this.setResult(0, intent);
                PostMsgActivity.this.finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
