package com.example.proj.zhaohuo;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import org.w3c.dom.Text;

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
    CurrentAcct currentAcct = new CurrentAcct();
    SimpleAdapter simpleAdapter;
    private RecyclerView recyclerView;
    //int imgID = R.drawable.ic_avatar;
    int[] imgID = new int[10];
    List<Map<String, Object>> data = new ArrayList<>();
    String[] followerName = {"zhouHF", "huangYJ", "HeYF", "HongZZ", "paul", "nike", "addi", "antony", "james", "jay"};
    String[] postedName = {"Sun", "田鸡", "山大王", "我是帅哥", "高佬", "肥牛","paul", "nike", "addi", "antony", "james", "jay"};
    String[] postedTitle = {"1405Sun求组队", "陶渊明独爱*", "这个可以",
            "看我ID", "一起搞事", "好像很棒","will u join us?","我来卖鞋","楼上不行","come on,find someone reliable like me",
            "I have championship","I can sing"};
    ObjectAnimator animator;
    TextView tip, circleName;
    public void initialize(){
        posted_listView = (ListView) findViewById(R.id.posted_listView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tip = (TextView) findViewById(R.id.tip);
        circleName = (TextView) findViewById(R.id.circleName1);
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

        //recyclelist
        LinearLayoutManager layoutManager = new LinearLayoutManager(circleDiscussionZone.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向摆放
        recyclerView.setLayoutManager(layoutManager);

        for(int i = 0; i < followerName.length; i++){
            circleFollowerList.add(new circleFollowerInfo(imgID[i], followerName[i]));
        }
        circleDiscussionZoneAdapter = new CircleDiscussionZoneAdapter(this, circleFollowerList);
        recyclerView.setAdapter(circleDiscussionZoneAdapter);
        //跟帖
        for(int i = 0; i < postedName.length; i++){
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("posterName", postedName[i]);
            temp.put("postedTitle", postedTitle[i]);
            data.add(temp);
        }
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
            default:
                break;
        }
        if(flag==1) finish();
        else if(flag==2){
            Intent intent = new Intent(circleDiscussionZone.this, PostMsgActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("circleName", circleName.getText().toString());
            intent.putExtras(bundle);
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
            //String conteent = bundle.getString("content");
            Map<String, Object> temp = new LinkedHashMap<>();
            //temp.put("posterName", currentAcct.AcctName);
            temp.put("posterName", currentAcct.AcctName);//该帐号名字
            temp.put("postedTitle", title);
            data.add(temp);
            simpleAdapter.notifyDataSetChanged();
            circleName.setText(bundle.getString("circleName"));
        }
    }
}
