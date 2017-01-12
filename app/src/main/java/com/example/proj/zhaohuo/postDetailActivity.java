package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.postCommentAdapter;

/**
 * Created by Administrator on 2016/12/15.
 */

public class postDetailActivity extends AppCompatActivity {
    List<circleInfo> list = new ArrayList<>();
    ListView listView;
    //int imgID = R.drawable.ic_avatar;
    int[] imgID = new int[11];;
    postCommentAdapter adapter;
    String circleName = "";
    TextView owner;
    Button ok_comment;
    EditText editText;
    CurrentAcct currentAcct = new CurrentAcct();
    String[] name = {"Sun", "田鸡", "山大王", "我是帅哥", "paul", "nike", "addi", "antony", "james", "jay", "kyrie"};
    String[] content = {"说的好像真的一样", "同意楼上","+1", "+1","sounds great","are u sure?","oh come on","u just lie","我就笑笑", "不说话","bird"};
    private void initialize(){
        listView = (ListView) findViewById(R.id.post_comment_listView);
        owner = (TextView) findViewById(R.id.post_detail_name_owner);
        ok_comment = (Button) findViewById(R.id.send1);
        editText = (EditText) findViewById(R.id.input1);
        for(int i=0; i < 10; i++){
            String s = "st" + i;
            imgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
        }
        imgID[10] = getResources().getIdentifier("st1","drawable",getPackageName());
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
        setContentView(R.layout.activity_post_detail);
        initialize();
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        circleName = bundle.get("circleName").toString();

        String ownerName = bundle.get("name").toString();
        owner.setText(ownerName);

        for(int i = 0; i < name.length; i++){
            list.add(new circleInfo(imgID[i], name[i], content[i]));
        }
        adapter = new postCommentAdapter(this, list);
        listView.setAdapter(adapter);

        ok_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new circleInfo(imgID[1], currentAcct.AcctName, editText.getText().toString()));
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(postDetailActivity.this, circleDiscussionZone.class);
                intent.putExtra("circleName", circleName);
                postDetailActivity.this.setResult(1, intent);
                this.finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
