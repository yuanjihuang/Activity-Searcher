package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import adapter.postCommentAdapter;

/**
 * Created by Administrator on 2016/12/15.
 */

public class postDetailActivity extends AppCompatActivity {
    List<circleInfo> list = new ArrayList<>();
    ListView listView;
    private int cirID = 1;
    private int postID = 1;
    private ConnectHelper connectHelper;
    private String getDataUrl;
    int[] imgID = new int[11];;
    postCommentAdapter adapter;
    String circleName = "";
    TextView owner, postDetailContent;
    Button ok_comment;
    EditText editText;
    CurrentAcct currentAcct = new CurrentAcct();
    private String[] name = new String[50];
    private String[] content = new String[50];
    private String postContent;
    private void initialize(){
        listView = (ListView) findViewById(R.id.post_comment_listView);
        owner = (TextView) findViewById(R.id.post_detail_name_owner);
        postDetailContent = (TextView) findViewById(R.id.post_detail_content);
        ok_comment = (Button) findViewById(R.id.send1);
        editText = (EditText) findViewById(R.id.input1);
        connectHelper = new ConnectHelper();
        postContent = "";
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
        postContent = bundle.get("content").toString();
        postDetailContent.setText(postContent);
        circleName = bundle.get("circleName").toString();
        String ownerName = bundle.get("name").toString();
        cirID = intent.getIntExtra("cirID",1);
        postID = intent.getIntExtra("postID",1);
        Log.d("cirID",cirID+".");
        Log.d("postID",postID+".");
        getDataUrl = connectHelper.url+"Service/get_post.jsp?PostID="+postID;
        owner.setText(ownerName);
        new postDetailActivity.DownloadWebpageText().execute(getDataUrl);
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
    @Override
    protected void onResume() {
        super.onResume();
        new postDetailActivity.DownloadWebpageText().execute(getDataUrl);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //Log.d("request",CurrentAcct.AcctName);
    }
    private class DownloadWebpageText extends AsyncTask<String,Integer,List<String>> {
        @Override
        protected List<String> doInBackground(String... urls) {
            try {
                List<String> reList = connectHelper.downloadUrl(urls[0]);
                return reList; //连接并下载数据
            } catch (IOException e) {
                e.printStackTrace();
                List<String> reList = new LinkedList<>();//返回的字符串数组，若只有1个字符串取reList.get(0)
                return reList;
            }
        }
        @Override
        protected void onPostExecute(List<String> result) {
            if(result != null){
                if(result.size() == 0){
                    Toast.makeText(getApplicationContext(),"没有返回值，请再试一次！",Toast.LENGTH_SHORT).show();
                }else{
                    for(int i = 0; i < result.size(); i++)
                        Log.d("relist",result.get(i));
                    ///////解析json信息/////////////
                    JSONArray postContent = JsonUtils.parse(result.get(0),"Post");//返回形式为多个键值对组成的序列
                    JSONArray PostComment = JsonUtils.parse(result.get(0),"postCom");
                    list.clear();
                    //Set<String> follow = new HashSet<>();//用于储存关注活动的id
                    for(int i=0; i<postContent.length(); i++){
                        try{
                            JSONObject oj = postContent.getJSONObject(i);
                            postDetailContent.setText(oj.getString("PostText"));
                        }catch (Exception e){}
                    }
                    for(int i = 0; i < PostComment.length(); i++){
                        try{
                            JSONObject oj = PostComment.getJSONObject(i);
                            name[i] = oj.getString("name");
                            content[i] = oj.getString("content");
                            list.add(new circleInfo(imgID[i], name[i], content[i]));
                        }catch (Exception e){}
                    }
                    adapter.notifyDataSetChanged();
                    //JSONArray FavoriteList = JsonUtils.parseAct(result.get(0));
                    /*
                    在这里更新UI
                     */
                }
            }
        }
    }
}
