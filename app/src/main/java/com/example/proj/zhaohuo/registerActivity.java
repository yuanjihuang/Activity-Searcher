package com.example.proj.zhaohuo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class registerActivity extends AppCompatActivity{
    private static final String[] type ={"个人","社团"};
    private Spinner spinner;
    private EditText username;
    private EditText password;
    private EditText check;
    private Button submit;
    private ArrayAdapter<String> adapter;
    private static final String url ="http://192.168.244.1:8300/project2/check_sign_up.jsp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        spinner = (Spinner)findViewById(R.id.login_spinner);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setVisibility(View.VISIBLE);
        username = (EditText)findViewById(R.id.login_username);
        password = (EditText)findViewById(R.id.register_password);
        check = (EditText)findViewById(R.id.register_check);
        submit = (Button)findViewById(R.id.register_button);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(TextUtils.isEmpty(username.getText().toString())){
                  Toast.makeText(registerActivity.this,"名字不能为空！",Toast.LENGTH_SHORT).show();
              }
              else if(TextUtils.isEmpty(password.getText().toString())){
                  Toast.makeText(registerActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
              }
              else if(TextUtils.isEmpty(check.getText().toString())){
                  Toast.makeText(registerActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
              }
              else if(!password.getText().toString().equals(check.getText().toString())){
                  Toast.makeText(registerActivity.this,"密码不匹配",Toast.LENGTH_SHORT).show();
              }
              else{
                  ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                  NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                  if(networkInfo!=null&&networkInfo.isConnected()) {
                      sendRequestWithHttpURLConnection();//进入到子线程中进行操作
                  }
                  else{
                      Toast.makeText(registerActivity.this,"当前没有可用网络!",Toast.LENGTH_SHORT).show();
                  }
              }
            }
        });


    }
    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                HttpURLConnection conn = null;
                try{
                    //HTTP连接
                    conn = (HttpURLConnection) ((new URL(url.toString()).openConnection()));
                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(8000);
                    conn.setConnectTimeout(8000);
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    String NAME = username.getText().toString();
                    String PWD = password.getText().toString();
                    NAME = URLEncoder.encode(NAME,"GB18030");
                    PWD = URLEncoder.encode(PWD,"GB18030");
                    out.writeBytes("Name="+NAME+"&Pwd="+PWD);
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder respone = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!=null){
                        respone.append(line); //把网页上的内容放到StringBuilder中
                    }
                    Log.i("response",respone.toString());

                }catch (Exception e){

                }
                finally {
                    if(conn!=null){conn.disconnect();}
                }
            }
        }).start();
    }
}


class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {

    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }
}

