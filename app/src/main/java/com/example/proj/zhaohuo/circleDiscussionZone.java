package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import adapter.CircleDiscussionZoneAdapter;

/**
 * Created by Administrator on 2016/12/11.
 */

public class circleDiscussionZone extends AppCompatActivity {
    Toolbar toolbar;
    ListView posted_listView;
    ArrayList<circleFollowerInfo> circleFollowerList  = new ArrayList<>();
    CircleDiscussionZoneAdapter circleDiscussionZoneAdapter;
    private RecyclerView recyclerView;
    int imgID = R.drawable.ic_avatar;
    List<Map<String, Object>> data = new ArrayList<>();
    String[] followerName = {"zhouHF", "huangYJ", "HeYF", "HongZZ"};
    String[] postedName = {"孙哔哔", "陈晴天", "文艺", "赔命"};
    String[] postedTitle = {"1405孙哔哔求组队", "组内有N个美女，来个男丁(不搞基", "来我这，我带你毁人篮球梦",
            "帅哥求组"};
    public void initialize(){
        posted_listView = (ListView) findViewById(R.id.posted_listView);
        toolbar = (Toolbar) findViewById(R.id.circle_discussion_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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
        setContentView(R.layout.circle_discussion_zone);
        initialize();
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回
//        getSupportActionBar().setTitle("畅谈区");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(circleDiscussionZone.this, circlelistActivity.class);
//                startActivity(intent);
//            }
//        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(circleDiscussionZone.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向摆放
        recyclerView.setLayoutManager(layoutManager);
        for(int i = 0; i < 4; i++){
            circleFollowerList.add(new circleFollowerInfo(imgID, followerName[i]));
        }
        circleDiscussionZoneAdapter = new CircleDiscussionZoneAdapter(this, circleFollowerList);
        recyclerView.setAdapter(circleDiscussionZoneAdapter);
        for(int i = 0; i < 4; i++){
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("posterName", postedName[i]);
            temp.put("postedTitle", postedTitle[i]);
            data.add(temp);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(circleDiscussionZone.this, data, R.layout.posted_item,
                new String[] {"posterName", "postedTitle"}, new int[] {R.id.poster_name, R.id.posted_title});
        posted_listView.setAdapter(simpleAdapter);//更新listView
        //setListViewHeight(posted_listView);
        posted_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(circleDiscussionZone.this, postDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
