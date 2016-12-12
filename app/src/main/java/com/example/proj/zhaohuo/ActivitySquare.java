package com.example.proj.zhaohuo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import adapter.ActivityAdapter;

/**
 * Created by Eafun on 2016/12/8.
 */

public class ActivitySquare extends AppCompatActivity {
    private int[] imgID = new int[11];
    private int[] follow = new int[11];
    private String[] name;
    private String[] info;
    private String[] remark;
    private String[] imgUrl;
    private int resultFollow;
    private ListView listView;
    private ConnectHelper connectHelper;
    private String getDataUrl;
    private ActivityAdapter adapter;
    private List<ActivityInfo> list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylist);
        connectHelper = new ConnectHelper();
        getDataUrl = connectHelper.url+"Service/main_activity.jsp";
        new DownloadWebpageText().execute(getDataUrl+"?Name="+"zhangsan");

        for(int i=0; i<11; i++){
            String s = "st"+i;
            imgID[i] = getResources().getIdentifier(s,"drawable",getPackageName());
            follow[i] = i%2;
        }
        name = new String[]{"五克拉·倒计时","万象丨演讲终战，谁执牛耳？","宿舍文化俱乐部","YES GO行动 第四期 招募启动","招新 | 加入我们，成为最可爱的人","走进职场","征文比赛","决战来袭|是梦想和青春","潜能待定义","25天速配","2017 中国实验室项目正式接受报名"};
        info = new String[]{"相逢的人会再相逢","诸位王公，准备好参加这场盛大的宴会了吗？","大学宿舍承载着大学生活太多太多的记忆，让我们不得不去重视","这可能是中国大陆最接近教育本质的成长行动","中大Din的七代目招新，在此时此刻，正式拉开序幕","职协带你去参访第三期:走入【网易有道】","“一忆职涯，一眼之间，一念执着”","激动人心的决战之夜即将来临，让我们共同期待最好的你们！","猎星决赛暨维纳斯歌友会观赏指南","这个假期，来一次“印”象最深的跨国实习","岭南MBA王牌项目中的王牌"};
        remark = new String[]{"2016.12.09 19:00 工学院小广场","12月8日19:00 公共教学楼B栋204","2016年12月","2016年9月-12月 广州|中山","","12月8日9:30-11:30 广州网易大厦","截稿时间：2016年12月15日24时","12月10日、11日 风雨球场","12月10日18:30 风雨球场前舞台","2016.12.1-2017.3.9 印度Adglobal360","报名截止时间：2016年12月22日17:00"};
        final String actUrl[] = {"https://mp.weixin.qq.com/s/R61BcilPrrhe3Q6fhk6fZw","https://mp.weixin.qq.com/s/jPYeAbAxi6pzgDvC0XsN0A","https://mp.weixin.qq.com/s/kEC45zpPAqWjdsFjpYBf7w","https://mp.weixin.qq.com/s/qma4qGrF0NZlK22r22dcmg","https://mp.weixin.qq.com/s/hzS2OX29mkpVjKqpkOTG0Q","https://mp.weixin.qq.com/s/H3s6EuNhgH3XbUY0tqdZJg","https://mp.weixin.qq.com/s/0anqJwCEjoB4C0YA5Ewaig","https://mp.weixin.qq.com/s/sRXp4xgkKQdMyG0QDc-8eQ","https://mp.weixin.qq.com/s/NyI1VzaQgL6VQju6cnOeww","https://mp.weixin.qq.com/s/l84m_cx-5Gb9PxVGDaYpHw","https://mp.weixin.qq.com/s/3CqnLrHCSus7jxHUkOKVTA"};
        imgUrl = new String[]{"http://ww1.sinaimg.cn/mw1024/863448e2gw1faljvpzj9gj2050050747.jpg","http://ww2.sinaimg.cn/mw1024/863448e2gw1faljvqmbiij20hs0hsaax.jpg","http://ww1.sinaimg.cn/mw1024/863448e2gw1faljvr758vj20hs0hst9x.jpg","http://ww2.sinaimg.cn/mw1024/863448e2gw1faljvs1xppj20hs0hsgo5.jpg","http://ww4.sinaimg.cn/mw1024/863448e2gw1faljvsg5ijj205f05fjrh.jpg","http://ww2.sinaimg.cn/mw1024/863448e2gw1faljvt7auij20hs0hsdg7.jpg","http://ww3.sinaimg.cn/mw1024/863448e2gw1faljvu1ujkj20hs0hsdic.jpg","http://ww1.sinaimg.cn/mw1024/863448e2gw1faljvucsm1j20hs0hsgna.jpg","http://ww4.sinaimg.cn/mw1024/863448e2gw1faljvv14uij20hs0hsjt9.jpg","http://ww3.sinaimg.cn/mw1024/863448e2gw1falk65xv0dj20b40b4q3v.jpg","http://ww2.sinaimg.cn/mw1024/863448e2gw1falk663x01j20hs0hswgl.jpg"};
        list = new ArrayList<>();
        for(int i=0; i<11; i++){
            ActivityInfo temp = new ActivityInfo(imgID[i],imgUrl[i],name[i],info[i],remark[i],follow[i]);
            list.add(temp);
        }
        adapter = new ActivityAdapter(this,list);
        listView = (ListView) findViewById(R.id.activityList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //跳转到详情页面
                final ActivityAdapter.ViewHolder viewHolder =(ActivityAdapter.ViewHolder) adapter.getView(position,view,parent).getTag();
                follow[position] = adapter.getCurrentFollow();
                Bundle bundle = new Bundle();
                bundle.putString("url",actUrl[position]);
                bundle.putInt("follow",follow[position]);
                bundle.putInt("position",position);
                Intent intent = new Intent(ActivitySquare.this,actDetailActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                switch (resultCode){
                    case 0:
                        resultFollow = data.getIntExtra("resultFollow",0);
                        int position = data.getIntExtra("position",0);
                        follow[position] = resultFollow;
                        ActivityInfo temp = new ActivityInfo(imgID[position],imgUrl[position],name[position],info[position],remark[position],follow[position]);
                        list.set(position,temp);
                        adapter.notifyDataSetChanged();
                        Log.d("resultFollow",""+resultFollow);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new ActivitySquare.DownloadWebpageText().execute(getDataUrl+"?Name="+CurrentAcct.AcctName);
        Log.d("request","zhangsan");
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
                    JSONArray ActList = JsonUtils.parse(result.get(0),"Act");//返回形式为多个键值对组成的序列
                    JSONArray FavoriteList = JsonUtils.parse(result.get(0),"Favorite");
                    list.clear();//清除当前活动
                    Set<Integer> favorite = new HashSet<>();//用于储存喜爱活动的ID
                    for(int i=0; i<FavoriteList.length(); i++){
                        try{
                            JSONObject oj = FavoriteList.getJSONObject(i);//获取Act数组中的第i个对象，是1个键值对
                            favorite.add(oj.getInt("ActID"));//通过访问键得到数据
                        }catch (Exception e){}
                    }
                    for(int i=0; i<ActList.length(); i++){
                        try{
                            JSONObject oj = ActList.getJSONObject(i);
                            String tem = "st" + oj.getInt("ActID");
                            imgID[i] = getResources().getIdentifier(tem,"drawable",getPackageName());
                            imgUrl[i] = oj.getString("ActUrl");
                            name[i] = oj.getString("ActName");
                            info[i] = oj.getString("ActInfo");
                            remark[i] = oj.getString("ActRemark");
                            follow[i] = favorite.contains(imgID[i])?1:0;//判断是否为喜爱活动
                            ActivityInfo temp = new ActivityInfo(imgID[i],imgUrl[i],name[i],info[i],remark[i],follow[i]);
                            list.add(temp);
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
