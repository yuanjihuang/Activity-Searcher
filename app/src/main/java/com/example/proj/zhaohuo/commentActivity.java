package com.example.proj.zhaohuo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class commentActivity extends AppCompatActivity {
    private int[] userImgID = new int[11];
    private String[] userImgUrl;
    private String[] username;
    private String[] comment;
    private ListView listView;
    private EditText editText;
    private Button btn;
    private CommentAdapter adapter;
    private List<CommentInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        username = new String[]{"五克拉","万象","宿舍文化俱乐部","YES GO","最可爱的人","走进职场","征文比赛","青春","潜能待定义","25","报名"};
        comment = new String[]{"相逢的人会再相逢","诸位王公，准备好参加这场盛大的宴会了吗？","大学宿舍承载着大学生活太多太多的记忆，让我们不得不去重视","这可能是中国大陆最接近教育本质的成长行动","中大Din的七代目招新，在此时此刻，正式拉开序幕","职协带你去参访第三期:走入【网易有道】","“一忆职涯，一眼之间，一念执着”","激动人心的决战之夜即将来临，让我们共同期待最好的你们！","猎星决赛暨维纳斯歌友会观赏指南","这个假期，来一次“印”象最深的跨国实习","岭南MBA王牌项目中的王牌"};
        userImgUrl = new String[]{"http://ww1.sinaimg.cn/mw1024/863448e2gw1faljvpzj9gj2050050747.jpg","http://ww2.sinaimg.cn/mw1024/863448e2gw1faljvqmbiij20hs0hsaax.jpg","http://ww1.sinaimg.cn/mw1024/863448e2gw1faljvr758vj20hs0hst9x.jpg","http://ww2.sinaimg.cn/mw1024/863448e2gw1faljvs1xppj20hs0hsgo5.jpg","http://ww4.sinaimg.cn/mw1024/863448e2gw1faljvsg5ijj205f05fjrh.jpg","http://ww2.sinaimg.cn/mw1024/863448e2gw1faljvt7auij20hs0hsdg7.jpg","http://ww3.sinaimg.cn/mw1024/863448e2gw1faljvu1ujkj20hs0hsdic.jpg","http://ww1.sinaimg.cn/mw1024/863448e2gw1faljvucsm1j20hs0hsgna.jpg","http://ww4.sinaimg.cn/mw1024/863448e2gw1faljvv14uij20hs0hsjt9.jpg","http://ww3.sinaimg.cn/mw1024/863448e2gw1falk65xv0dj20b40b4q3v.jpg","http://ww2.sinaimg.cn/mw1024/863448e2gw1falk663x01j20hs0hswgl.jpg"};
        for(int i=0; i<11; i++){
            String s = "st"+i;
            userImgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
        }
        listView = (ListView) findViewById(R.id.commentList);
        editText = (EditText) findViewById(R.id.input);
        btn = (Button) findViewById(R.id.send);

        list = new ArrayList<>();
        for(int i=0; i<11; i++){
            CommentInfo temp = new CommentInfo(userImgID[i],userImgUrl[i],username[i],comment[i]);
            list.add(temp);
        }
        adapter = new CommentAdapter(this,list);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
}
