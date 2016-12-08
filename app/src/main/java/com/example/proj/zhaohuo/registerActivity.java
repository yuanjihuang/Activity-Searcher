package com.example.proj.zhaohuo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;


public class registerActivity extends AppCompatActivity{
    String check_sign_up ="http://192.168.191.1" +
            ":8300/project2/Service/check_sign_up.jsp";
    //private static final String[] type ={"个人","社团"};
    private String Kind = "false";
    private String Name;
    private String Pwd;
    private Spinner spinner;
    private EditText username;
    private EditText password;
    private EditText check;
    private Button submit;
    private RadioGroup Kind_select;
    private ConnectHelper connectHelper;
    private ArrayAdapter<String> adapter;
    //所有findView操作集中findView中管理
    private void initialize(){
        setContentView(R.layout.register);
        username = (EditText)findViewById(R.id.login_username);
        password = (EditText)findViewById(R.id.register_password);
        check = (EditText)findViewById(R.id.register_check);
        submit = (Button)findViewById(R.id.register_button);
        Kind_select = (RadioGroup)findViewById(R.id.select_kind);
        connectHelper = new ConnectHelper();
        /*
        spinner = (Spinner)findViewById(R.id.login_spinner);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setVisibility(View.VISIBLE);*/
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
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
              else if((password.getText().toString().length()<6) || (password.getText().toString().length()>16)){
                  Toast.makeText(registerActivity.this,"密码长度为6到16位",Toast.LENGTH_SHORT).show();
              }
              else if((username.getText().toString().length()<6) || (username.getText().toString().length()>16)){
                  Toast.makeText(registerActivity.this,"用户名长度为6到16位",Toast.LENGTH_SHORT).show();
              }
              else if(!password.getText().toString().equals(check.getText().toString())){
                  Toast.makeText(registerActivity.this,"密码不匹配",Toast.LENGTH_SHORT).show();
              }
              else{
                  ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                  NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                  if(networkInfo!=null&&networkInfo.isConnected()) {
///////////////////////////////////重点:调用网络方法/////////////////////////////////////////
                      Name = username.getText().toString();//获取所需字符串
                      Pwd = password.getText().toString();
                      try{
                          Name = URLEncoder.encode(Name,"GB18030");//在try中将编码模式换为GB18030
                          Pwd = URLEncoder.encode(Pwd,"GB18030");
                          Kind = URLEncoder.encode(Kind,"GB18030");
                          Log.d("Name Pwd Kind",Name+" "+Pwd+" "+Kind);
                      }catch (Exception e){}
                      new DownloadWebpageText().execute(check_sign_up+"?Name="+Name+"&Pwd="+Pwd+"&Kind="+Kind);//异步线程调用，参数直接通过?+parameter=的形式传入
                  }
                  else{
                      Toast.makeText(registerActivity.this,"当前没有可用网络!",Toast.LENGTH_SHORT).show();
                  }
              }
            }
        });
        Kind_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Kind = checkedId==1?"True":"False";
            }
        });
    }
    //读取返回字符输入流中字符串

    //异步线程
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
                    Toast.makeText(getApplicationContext(),"没有返回值",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), result.get(0), Toast.LENGTH_SHORT).show();
                    /*
                    在这里更新UI
                     */
                }
            }
        }
    }
    /*private class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            Kind = arg2==1?"true":"false";
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }*/
}


