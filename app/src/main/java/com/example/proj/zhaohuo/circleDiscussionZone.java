package com.example.proj.zhaohuo;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
    CurrentAcct currentAcct = new CurrentAcct();
    SimpleAdapter simpleAdapter;
    private RecyclerView recyclerView;
    //int imgID = R.drawable.ic_avatar;
    int[] imgID = new int[10];
    private int cirID = 1;
    private int followTag = 0;
    List<Map<String, Object>> data = new ArrayList<>();
    Map<String, Object> temp1 = new LinkedHashMap<>();
    private String content;
    private String[] followerName = new String[50];
    private String[] postedName = new String[50];
    private String[] postedTitle = new String[50];
    private ConnectHelper connectHelper;
    private String getDataUrl;
    ObjectAnimator animator;
    TextView tip, circleName;
    public void initialize(){
        posted_listView = (ListView) findViewById(R.id.posted_listView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tip = (TextView) findViewById(R.id.tip);
        circleName = (TextView) findViewById(R.id.circleName1);
        connectHelper = new ConnectHelper();
        content = "";
        for(int i = 0; i < 10; i++){
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
        setContentView(R.layout.circle_discussion_zone);
        initialize();
        //设置传过来的圈子名称
        Intent intent = getIntent();
        circleName.setText(intent.getStringExtra("circleName"));
        cirID = intent.getIntExtra("cirID",1);
        Log.d("cirID",cirID+".");
        getDataUrl = connectHelper.url+"Service/get_circle_detail.jsp"+"?CirID="+cirID;

        //recyclelist
        LinearLayoutManager layoutManager = new LinearLayoutManager(circleDiscussionZone.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向摆放
        recyclerView.setLayoutManager(layoutManager);
        new circleDiscussionZone.DownloadWebpageText().execute(getDataUrl);

        circleDiscussionZoneAdapter = new CircleDiscussionZoneAdapter(this, circleFollowerList);
        recyclerView.setAdapter(circleDiscussionZoneAdapter);

        simpleAdapter = new SimpleAdapter(circleDiscussionZone.this, data, R.layout.posted_item,
                new String[] {"posterName", "postedTitle"}, new int[] {R.id.poster_name, R.id.posted_title});
        posted_listView.setAdapter(simpleAdapter);//更新listView
        //setListViewHeight(posted_listView);
        posted_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(circleDiscussionZone.this, postDetailActivity.class);
                intent.putExtra("name", data.get(position).get("posterName").toString());
                intent.putExtra("circleName", circleName.getText().toString());
                intent.putExtra("cirID", cirID);
                intent.putExtra("postID", position+1);
                intent.putExtra("content", content);
                startActivityForResult(intent, 0);
            }
        });
        posted_listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING){
                    tip.setVisibility(View.VISIBLE);
                    animator = ObjectAnimator.ofFloat(tip, "translationY", 0, -80, 0, -80, 0);
                    animator.setDuration(800);
                    animator.setRepeatCount(-1);
                    animator.start();
                }else if(scrollState == SCROLL_STATE_IDLE){
                    animator.cancel();
                    tip.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.postmsg_item,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int flag=0;
        switch (item.getItemId()){
            case R.id.menu_next:
                flag=2; break;
            case android.R.id.home:
                flag = 1;
                break;
            case R.id.circle_follow:
                if(followTag == 0){
                    item.setIcon(R.drawable.like);
                    followTag = 1;
                }else{
                    item.setIcon(R.drawable.unlike);
                    followTag =0;
                }
            default:
                break;
        }
        if(flag==1) finish();
        else if(flag==2){
            Intent intent = new Intent(circleDiscussionZone.this, PostMsgActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("circleName", circleName.getText().toString());
            //bundle.putInt("cirID", cirID);
            intent.putExtras(bundle);
            intent.putExtra("cirID", cirID);
            //intent.putExtra("circleName", circleName.getText().toString());
            startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0 && resultCode == 0){
            Bundle bundle = intent.getExtras();
            String title = bundle.getString("title");
            content = bundle.getString("content");
            //String conteent = bundle.getString("content");

            //temp.put("posterName", currentAcct.AcctName);
            temp1.put("posterName", currentAcct.AcctName);//该帐号名字
            temp1.put("postedTitle", title);
            //data.add(temp1);
            //simpleAdapter.notifyDataSetChanged();
            circleName.setText(bundle.getString("circleName"));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        new circleDiscussionZone.DownloadWebpageText().execute(getDataUrl);
        posted_listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(circleDiscussionZoneAdapter);
        circleDiscussionZoneAdapter.notifyDataSetChanged();
        Log.d("request",CurrentAcct.AcctName);
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
                    JSONArray FollowerList = JsonUtils.parse(result.get(0),"Followers");//返回形式为多个键值对组成的序列
                    JSONArray PostList = JsonUtils.parse(result.get(0),"Post");//返回形式为多个键值对组成的序列
                    data.clear();
                    circleFollowerList.clear();//清除当前圈子
                    //Set<String> follow = new HashSet<>();//用于储存关注活动的id
                    for(int i=0; i<FollowerList.length(); i++){
                        try{
                            JSONObject oj = FollowerList.getJSONObject(i);
                            followerName[i] = oj.getString("UserName");
                            circleFollowerList.add(new circleFollowerInfo(imgID[i], followerName[i]));
                        }catch (Exception e){}
                    }
                    for(int i = 0; i < PostList.length(); i++){
                        try{
                            JSONObject oj = PostList.getJSONObject(i);
                            postedName[i] = oj.getString("PostOwner");
                            postedTitle[i] = oj.getString("PostTitle");
                            Map<String, Object> temp = new LinkedHashMap<>();
                            temp.put("posterName", postedName[i]);
                            temp.put("postedTitle", postedTitle[i]);
                            data.add(temp);
                        }catch (Exception e){}
                    }
                    if(!content.equals("")){
                        data.add(temp1);
                    }
                    simpleAdapter.notifyDataSetChanged();
                    circleDiscussionZoneAdapter.notifyDataSetChanged();
                    //JSONArray FavoriteList = JsonUtils.parseAct(result.get(0));
                    /*
                    在这里更新UI
                     */
                }
            }
        }
    }
}
