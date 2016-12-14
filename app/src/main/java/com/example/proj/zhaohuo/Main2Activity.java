package com.example.proj.zhaohuo;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by Eafun on 2016/12/8.
 */

public class Main2Activity extends TabActivity {
    TabHost tabHost = null;
    TabHost.TabSpec tab1,tab2,tab3 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabhost);
        tabHost=getTabHost();
        tab1=tabHost.newTabSpec("tabA");
        tab2=tabHost.newTabSpec("tabB");
        tab3=tabHost.newTabSpec("tabC");
        tab1.setIndicator("活动广场");
        Intent intentA=new Intent();
        intentA.setClass(Main2Activity.this,ActivitySquare.class);
        tab1.setContent(intentA);
        tab2.setIndicator("圈子");
        Intent intentB=new Intent();
        intentB.setClass(Main2Activity.this, circlelistActivity.class);//第二个参数填上圈子的activity
        tab2.setContent(intentB);
        tab3.setIndicator("个人信息");
        Intent intentC=new Intent();
        intentC.setClass(Main2Activity.this,UserActivity.class );//第二个参数填上个人信息的activity
        tab3.setContent(intentC);
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
    }
}
