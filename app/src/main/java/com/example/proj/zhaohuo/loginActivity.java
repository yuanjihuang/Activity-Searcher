package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

public class loginActivity extends AppCompatActivity {
    private TextView exit;
    private TextView register;
    private TextView aboutUs;
    private EditText id;
    private EditText password;
    private TextView protocol;
    private Button login;
    private ConnectHelper connectHelper;
    private String check_login;
    private String Name;
    private String Pwd;
    private void initialize(){

        exit = (TextView)findViewById(R.id.login_exit);         //退出界面
        register = (TextView)findViewById(R.id.login_register); //跳转到注册界面
        aboutUs = (TextView)findViewById(R.id.login_aboutUs); //跳转到人员信息
        id = (EditText)findViewById(R.id.login_id);  //输入账号
        password = (EditText)findViewById(R.id.login_password); //输入密码
        protocol = (TextView)findViewById(R.id.protocol);  //跳转到协议界面
        login = (Button)findViewById(R.id.login_button);   //登录按钮
        connectHelper= new ConnectHelper();
        check_login = connectHelper.url+"Service/check_login.jsp";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,teamActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,registerActivity.class);
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
                Intent intent = new Intent(loginActivity.this,protocolActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断用户名密码是否正确
                Name = id.getText().toString();
                Pwd = password.getText().toString();
                try{
                    Name = URLEncoder.encode(Name,"utf-8");//在try中将编码模式换为utf-8
                    Pwd = URLEncoder.encode(Pwd,"utf-8");
                    Log.d("Name Pwd",Name+" "+Pwd);
                }catch (Exception e){}
                //跳转到下一个界面（包含资料完善功能）
                new DownloadWebpageText().execute(check_login+"?Name="+Name+"&Pwd="+Pwd);//异步线程调用，参数直接通过?+parameter=的形式传入
            }
        });
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
                    char code=result.get(0).toString().charAt(0);
                    System.out.println(result.get(0).toString());
                    if(code=='0'){
                        Toast.makeText(getApplicationContext()," 登陆成功！", Toast.LENGTH_SHORT).show();
                        Bundle bundle;

                        Intent intent = new Intent(loginActivity.this,Main2Activity.class);
                        startActivity(intent);
                    }else if(code == '1'){
                        Toast.makeText(getApplicationContext(), "密码错误！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "用户不存在！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
