package com.example.proj.zhaohuo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class actDetailActivity extends AppCompatActivity {

    private WebView webView;
    private String url;
    private int follow;
    private int position;
    private int actID;
    private String actName;
    private List<String> sign_name = new ArrayList<>();
    private List<String> sign_num = new ArrayList<>();
    private ConnectHelper connectHelper;
    private String updateFollow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        setContentView(R.layout.activity_act_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        url = bundle.getString("url");
        Log.d("url: ",url);
        follow = bundle.getInt("follow");
        position = bundle.getInt("position");
        actID = bundle.getInt("actID");
        actName = bundle.getString("actName");
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
        connectHelper = new ConnectHelper();
        updateFollow = connectHelper.url;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        if(follow==0){
            menu.getItem(0).setIcon(R.drawable.unlike);
        } else {
            menu.getItem(0).setIcon(R.drawable.like);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int flag=0;
        switch (item.getItemId()){
            case R.id.menu_comment:
                Intent intent = new Intent(actDetailActivity.this,commentActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_follow:
                if(follow==0){
                    item.setIcon(R.drawable.like);
                    follow = 1;
                    updateFollow = connectHelper.url+"Service/set_follow.jsp?=AcctName="+CurrentAcct.AcctName+"&ActID="+actID; //传回后台新增关注
                } else{
                    item.setIcon(R.drawable.unlike);
                    follow = 0;
                    updateFollow = connectHelper.url+"Service/delete_follow.jsp?=AcctName="+CurrentAcct.AcctName+"&ActID="+actID; //传回后台取消关注
                }
                break;
            case R.id.sign_up:
                LayoutInflater inflater = LayoutInflater.from(actDetailActivity.this);
                View v = inflater.inflate(R.layout.signup_layout,null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(actDetailActivity.this);
                builder.setView(v);
                final AlertDialog alertDialog = builder.show();
                final TableRow stu1 = (TableRow) v.findViewById(R.id.stu1);final TableRow stu2 = (TableRow) v.findViewById(R.id.stu2);
                final TableRow stu3 = (TableRow) v.findViewById(R.id.stu3);final TableRow stu4 = (TableRow) v.findViewById(R.id.stu4);
                final EditText name1 = (EditText) v.findViewById(R.id.name1);final EditText name2 = (EditText) v.findViewById(R.id.name2);
                final EditText name3 = (EditText) v.findViewById(R.id.name3);final EditText name4 = (EditText) v.findViewById(R.id.name4);
                final EditText num1 = (EditText) v.findViewById(R.id.num1);final EditText num2 = (EditText) v.findViewById(R.id.num2);
                final EditText num3 = (EditText) v.findViewById(R.id.num3);final EditText num4 = (EditText) v.findViewById(R.id.num4);
                RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radio0);
                Button quit = (Button) v.findViewById(R.id.dialog_btn1); Button submit = (Button) v.findViewById(R.id.dialog_btn2);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.radio1:
                                stu1.setVisibility(View.VISIBLE);
                                stu2.setVisibility(View.INVISIBLE);
                                stu3.setVisibility(View.INVISIBLE);
                                stu4.setVisibility(View.INVISIBLE);
                                break;
                            case R.id.radio2:
                                stu1.setVisibility(View.VISIBLE);
                                stu2.setVisibility(View.VISIBLE);
                                stu3.setVisibility(View.VISIBLE);
                                stu4.setVisibility(View.VISIBLE);
                                break;
                            default:
                                stu1.setVisibility(View.VISIBLE);
                                stu2.setVisibility(View.VISIBLE);
                                stu3.setVisibility(View.VISIBLE);
                                stu4.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
                quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int flag=0;
                        String temp="";String temp1="";
                        temp = name1.getText().toString();
                        temp1 = num1.getText().toString();
                        if((temp!=null && temp!="") && (temp1!=null && temp1!="")){
                            sign_name.add(temp);
                            sign_num.add(temp1);
                        } else if(temp!=""||temp1!=""){
                            flag=1;
                            Toast.makeText(actDetailActivity.this,"姓名或学号为空",Toast.LENGTH_SHORT);
                        }
                        temp = name2.getText().toString();
                        temp1 = num2.getText().toString();
                        if((temp!=null && temp!="") && (temp1!=null && temp1!="")){
                            sign_name.add(temp);
                            sign_num.add(temp1);
                        } else if(temp!=""||temp1!=""){
                            flag=1;
                            Toast.makeText(actDetailActivity.this,"姓名或学号为空",Toast.LENGTH_SHORT);
                        }
                        temp = name3.getText().toString();
                        temp1 = num3.getText().toString();
                        if((temp!=null && temp!="") && (temp1!=null && temp1!="")){
                            sign_name.add(temp);
                            sign_num.add(temp1);
                        } else if(temp!=""||temp1!=""){
                            flag=1;
                            Toast.makeText(actDetailActivity.this,"姓名或学号为空",Toast.LENGTH_SHORT);
                        }
                        temp = name4.getText().toString();
                        temp1 = num4.getText().toString();
                        if((temp!=null && temp!="") && (temp1!=null && temp1!="")){
                            sign_name.add(temp);
                            sign_num.add(temp1);
                        } else if(temp!=""||temp1!=""){
                            flag=1;
                            Toast.makeText(actDetailActivity.this,"姓名或学号为空",Toast.LENGTH_SHORT);
                        }
                        if(flag==0) alertDialog.dismiss();
                    }
                });
                break;
            case android.R.id.home:
                flag = 1;
                break;
            default:
                break;
        }
        Log.d("follow",""+follow);
        Intent newIntent = new Intent();
        newIntent.putExtra("resultFollow",follow);
        newIntent.putExtra("position",position);
        setResult(0,newIntent);
        if(flag==1) finish();
        return super.onOptionsItemSelected(item);
    }
}
