package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
    int[] imgID = new int[5];
    String[] name = {"职来职往", "可口可乐营销大赛", "落叶送祝福", "寒假招宣","环保服装设计大赛"};
    String[] briefIntro = {"中大职协的招牌活动，致力于为同学们创造更多的就业机会，锻炼职场能力",
            "中大职协的招牌活动，与可口可乐公司合作，锻炼实战营销能力，感受世界500强的魅力",
            "中大绿叶社举办的落叶送祝福收集落叶，制作精美书签于圣诞节送到同学们手中",
            "中大招协每年的寒宣已经开始报名啦，回母校宣传中大让更多学弟学妹来到这里吧",
            "中大绿叶社举办的环保服装设计大赛汇聚了以环保为理念的优秀作品设计并现场走秀"};
    ListView circle_listView;
    CircleAdapter circleAdapter;
    public static circlelistActivity instance = null;
    private void initialize(){
        circleUserIcon = (ImageView) findViewById(R.id.circle_user_ic);
        circleName = (TextView) findViewById(R.id.circle_name);
        circleBriefIntro = (TextView) findViewById(R.id.circle_briIntro);
        circle_listView = (ListView) findViewById(R.id.circle_listView);
        for(int i=0; i<5; i++){
            String s = "st" + i;
            imgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
        }
    }
    public void setListViewHeight(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null){
            return;
        }
        int totalHeight = 0;
        for(int i = 0; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()-1));
        params.height += 5;
        listView.setLayoutParams(params);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.circlelist);
        initialize();
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回
//        getSupportActionBar().setTitle("圈子列表");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(circlelistActivity.this, Main2Activity.class);
//                startActivity(intent);
//            }
//        });
        instance = this;
        for(int i = 0; i < 5; i++){
            circleInfos.add(new circleInfo(imgID[i], name[i], "简介："+briefIntro[i]));
        }
        circleAdapter = new CircleAdapter(this, circleInfos);
        circle_listView.setAdapter(circleAdapter);
        circle_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(circlelistActivity.this, circleDiscussionZone.class);
                intent.putExtra("circleName", name[position]);
                startActivityForResult(intent, 0);
            }
        });

    }
}
