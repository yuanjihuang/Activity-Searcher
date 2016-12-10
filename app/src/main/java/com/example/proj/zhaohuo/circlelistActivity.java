package com.example.proj.zhaohuo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.proj.zhaohuo.circleInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import adapter.CircleAdapter;

/**
 * Created by Administrator on 2016/12/9.
 */

public class circlelistActivity extends AppCompatActivity {
    List<circleInfo> circleInfos = new ArrayList<>();
    ImageView circleUserIcon;
    TextView circleName, circleActivity, circleBriefIntro;
    int[] imgID = new int[4];
    String[] name = {"ZhouHF", "HuangYJ", "HeYF", "HongZZ"};
    String[] activity = {"打球", "打电话", "打炮", "打炮"};
    String[] briefIntro = {"他的日常", "他的日常", "他的日常", "他的日常"};
    ListView circle_listView;
    CircleAdapter circleAdapter;
    private void initialize(){
        circleUserIcon = (ImageView) findViewById(R.id.circle_user_ic);
        circleName = (TextView) findViewById(R.id.circle_name);
        circleActivity = (TextView) findViewById(R.id.circle_activity);
        circleBriefIntro = (TextView) findViewById(R.id.circle_briIntro);
        circle_listView = (ListView) findViewById(R.id.circle_listView);
        for(int i=0; i<4; i++){
            String s = "st" + i;
            imgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circlelist);
        initialize();

        for(int i = 0; i < 4; i++){
            circleInfos.add(new circleInfo(imgID[i], name[i], "活动："+activity[i], "简介："+briefIntro[i]));
        }
        circleAdapter = new CircleAdapter(this, circleInfos);
        circle_listView.setAdapter(circleAdapter);
    }
}
