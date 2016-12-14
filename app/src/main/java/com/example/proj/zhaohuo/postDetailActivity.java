package com.example.proj.zhaohuo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.postCommentAdapter;

/**
 * Created by Administrator on 2016/12/15.
 */

public class postDetailActivity extends AppCompatActivity {
    List<circleInfo> list = new ArrayList<>();
    ListView listView;
    int imgID = R.drawable.ic_avatar;
    postCommentAdapter adapter;
    String[] name = {"文艺", "晴天", "炮王", "鸡总"};
    String[] content = {"说的好像真的一样", "同意楼上", "+1", "+1"};
    private void initialize(){
        listView = (ListView) findViewById(R.id.post_comment_listView);
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
        setContentView(R.layout.activity_post_detail);
        initialize();
        for(int i = 0; i < 4; i++){
            list.add(new circleInfo(imgID, name[i], content[i]));
        }
        adapter = new postCommentAdapter(this, list);
        listView.setAdapter(adapter);
        setListViewHeight(listView);
    }

}
