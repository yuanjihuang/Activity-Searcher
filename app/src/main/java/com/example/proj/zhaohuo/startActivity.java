package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;

/**
 * Created by Administrator on 2016/12/13.
 */

public class startActivity extends AppCompatActivity{
    private final int SPLASH_DISPLAY_LENGHT = 4500; // 4.5秒后进入系统
    private final int SPLASH_DISPLAY_LENGHT1 = 2000; // 两秒后下一行字
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final CTextView cTextView = (CTextView) findViewById(R.id.cTextView);
        final CTextView cTextView1 = (CTextView) findViewById(R.id.cTextView1);
        cTextView.setText("找活", AnimationUtils.loadAnimation(this, R.anim.my_start_anim), 1500);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cTextView1.setText("寻找属于你的活动", AnimationUtils.loadAnimation(startActivity.this, R.anim.my_start_anim), 200);
            }
        }, SPLASH_DISPLAY_LENGHT1);
        //cTextView1.setText("寻找属于你的活动", AnimationUtils.loadAnimation(this, R.anim.my_start_anim), 400);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(startActivity.this, Main2Activity.class);
                startActivity(intent);
                startActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}