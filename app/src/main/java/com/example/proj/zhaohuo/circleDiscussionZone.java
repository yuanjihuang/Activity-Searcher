package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import adapter.CircleDiscussionZoneAdapter;

/**
 * Created by Administrator on 2016/12/11.
 */

public class circleDiscussionZone extends AppCompatActivity {
    Toolbar toolbar;
    ListView circleCommentListView;
    Button commentBtn;
    TextView myComment;
    ImageView editImage;
    Button commitContent, cancelCommitContent;
    List<circleCommentatorInfo> circleCommentatorInfos  = new ArrayList<>();
    CircleDiscussionZoneAdapter circleDiscussionZoneAdapter;
    int[] imgID = new int[4];
    String[] name = {"zhouHF", "huangYJ", "HeYF", "HongZZ"};
    String[] commentContent = {"有谁要一起吗~", "@zhouHF 一起啊", "看起来好像很有趣哦，报名玩玩", "@huangYJ 卧槽你web写完了？"};
    public void initialize(){
        toolbar = (Toolbar) findViewById(R.id.circle_discussion_toolbar);
        circleCommentListView = (ListView) findViewById(R.id.circle_comment_listView);
        commentBtn = (Button) findViewById(R.id.commentCircleBtn);
        editImage = (ImageView) findViewById(R.id.editImageCircleBtn);
        editImage.setTag(0);
        for(int i=0; i<4; i++){
            String s = "st" + i;
            imgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
        }
    }
    /*public void setListViewHeight(ListView listView){
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
    }*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_discussion_zone);
        initialize();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回
        getSupportActionBar().setTitle("互动区");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(circleDiscussionZone.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        for(int i = 0; i < 4; i++){
            circleCommentatorInfos.add(new circleCommentatorInfo(imgID[i], name[i], commentContent[i]));
        }
        circleDiscussionZoneAdapter = new CircleDiscussionZoneAdapter(this, circleCommentatorInfos);
        circleCommentListView.setAdapter(circleDiscussionZoneAdapter);
        //setListViewHeight(circleCommentListView);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Integer)editImage.getTag()==0){
                    commentBtn.setVisibility(View.VISIBLE);
                    commentBtn.setEnabled(true);
                    editImage.setTag(1);
                }
                else{
                    commentBtn.setVisibility(View.INVISIBLE);
                    commentBtn.setEnabled(false);
                    editImage.setTag(0);
                }
            }
        });
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(circleDiscussionZone.this);    //使用LayoutInflater类自定义对话框
                View myView = factory.inflate(R.layout.circle_comment, null);       //布局为
                final AlertDialog.Builder builder = new AlertDialog.Builder(circleDiscussionZone.this);
                builder.setView(myView);
                final AlertDialog dialog = builder.show();//调用show显示dialog
                myComment = (EditText) myView.findViewById(R.id.circle_comment_content);
                commitContent = (Button) myView.findViewById(R.id.commitContent);
                cancelCommitContent = (Button) myView.findViewById(R.id.cancelCommitContent);
                commitContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String content = myComment.getText().toString();
                        if(!content.equals("")){
                            circleCommentatorInfos.add(new circleCommentatorInfo(imgID[0], "ME", content));
                            circleDiscussionZoneAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(circleDiscussionZone.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelCommitContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}
