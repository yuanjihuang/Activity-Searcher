package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView exit = (TextView)findViewById(R.id.login_exit);         //退出界面
        TextView register = (TextView)findViewById(R.id.login_register); //跳转到注册界面
        TextView aboutUs = (TextView)findViewById(R.id.login_aboutUs); //跳转到人员信息
        EditText id = (EditText)findViewById(R.id.login_id);  //输入账号
        EditText password = (EditText)findViewById(R.id.login_password); //输入密码
        TextView protocol = (TextView)findViewById(R.id.protocol);  //跳转到协议界面
        Button login = (Button)findViewById(R.id.login_button);   //登录按钮
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,teamActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,registerActivity.class);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,protocolActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断用户名密码是否正确
                //跳转到下一个界面（包含资料完善功能）
            }
        });



    }
}
