package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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
    TextView circleName, circleBriefIntro;
    Toolbar toolbar;
    int[] imgID = new int[4];
    String[] name = {"职来职往", "英才节", "落叶送祝福", "环保服装设计大赛"};
    String[] briefIntro = {"这是职协的活动", "这是职协的活动", "这是绿叶社的活动", "这是绿叶社的活动"};
    ListView circle_listView;
    CircleAdapter circleAdapter;
    public static circlelistActivity instance = null;
    private void initialize(){
        circleUserIcon = (ImageView) findViewById(R.id.circle_user_ic);
        circleName = (TextView) findViewById(R.id.circle_name);
        circleBriefIntro = (TextView) findViewById(R.id.circle_briIntro);
        circle_listView = (ListView) findViewById(R.id.circle_listView);
        toolbar = (Toolbar) findViewById(R.id.circlelist_toolbar);
        for(int i=0; i<4; i++){
            String s = "st" + i;
            imgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
        }
    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circlelist);
        initialize();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回
        getSupportActionBar().setTitle("圈子列表");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(circlelistActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        instance = this;
        for(int i = 0; i < 4; i++){
            circleInfos.add(new circleInfo(imgID[i], name[i], "简介："+briefIntro[i]));
        }
        circleAdapter = new CircleAdapter(this, circleInfos);
        circle_listView.setAdapter(circleAdapter);
        /*for(int i = 0; i < circle_listView.getAdapter().getCount(); i++){
            getViewByPosition(i, circle_listView).findViewById(R.id.enter_discussion).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(circlelistActivity.this, circleDiscussionZone.class);
                            startActivity(intent);
                        }
                    }
            );
        }*/
        /*circle_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(circlelistActivity.this, circleDiscussionZone.class);
                startActivity(intent);
            }
        });*/
    }
}
