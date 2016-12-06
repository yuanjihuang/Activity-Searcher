package com.example.proj.zhaohuo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class teamActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team);
        TextView hzz = (TextView)findViewById(R.id.login_hzz);
        TextView hyj = (TextView)findViewById(R.id.login_hyj);
        TextView hyf = (TextView)findViewById(R.id.login_hyf);
        TextView zhf = (TextView)findViewById(R.id.login_zhf);
        TextView work1 = (TextView)findViewById(R.id.login_work1);
        TextView work2 = (TextView)findViewById(R.id.login_work2);
        TextView work3 = (TextView)findViewById(R.id.login_work3);
        TextView work4 = (TextView)findViewById(R.id.login_work4);
        Animation animation = AnimationUtils.loadAnimation(teamActivity.this,R.anim.anim);
        hzz.startAnimation(animation);
        hyf.startAnimation(animation);
        zhf.startAnimation(animation);
        hyj.startAnimation(animation);
        work1.startAnimation(animation);
        work2.startAnimation(animation);
        work3.startAnimation(animation);
        work4.startAnimation(animation);
    }
}
