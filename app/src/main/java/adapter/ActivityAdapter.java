package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proj.zhaohuo.ActivityInfo;
import com.example.proj.zhaohuo.ConnectHelper;
import com.example.proj.zhaohuo.CurrentAcct;
import com.example.proj.zhaohuo.JsonUtils;
import com.example.proj.zhaohuo.R;
import com.example.proj.zhaohuo.commentActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Eafun on 2016/12/9.
 */

public class ActivityAdapter extends BaseAdapter {
    private List<ActivityInfo> list;
    private Context context;
    private Integer currentFollow;
    private ConnectHelper connectHelper;
    private String updateFollow;

    private Integer actID;
    private Integer imgID;
    private String imgUrl;
    private String name;
    private String info;
    private String remark;
    private Integer follow;

    public ActivityAdapter(Context context, List<ActivityInfo> list) {
        this.list = list;
        this.context = context;
        currentFollow = 0;
        connectHelper = new ConnectHelper();
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list == null) {
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View convertView;
        final ViewHolder viewHolder;
        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activityitem, null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.actImg);
            viewHolder.nameText = (TextView) convertView.findViewById(R.id.actName);
            viewHolder.infoText = (TextView) convertView.findViewById(R.id.actInfo);
            viewHolder.remarkText = (TextView) convertView.findViewById(R.id.actRemark);
            viewHolder.follow = (ImageView) convertView.findViewById(R.id.follow);
            viewHolder.comment = (ImageView) convertView.findViewById(R.id.comment);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imgID = list.get(position).getImgID();
        imgUrl = list.get(position).getImgUrl();
        name = list.get(position).getName();
        info = list.get(position).getInfo();
        remark = list.get(position).getRemark();
        follow = list.get(position).isFollow();
        actID = list.get(position).getActID();
        Log.d("ActID:",""+actID);
        //Bitmap bp = getHttpBitmap(imgUrl);
        viewHolder.img.setImageResource(imgID);
        //viewHolder.img.setImageBitmap(bp);
        viewHolder.nameText.setText(name);
        viewHolder.infoText.setText(info);
        viewHolder.remarkText.setText(remark);
        if (follow == 0) {
            viewHolder.follow.setImageResource(R.drawable.unlike);
        } else {
            viewHolder.follow.setImageResource(R.drawable.like);
        }
        viewHolder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actID = list.get(position).getActID();
                if (follow == 0) {
                    viewHolder.follow.setImageResource(R.drawable.like);
                    currentFollow = 1;
                    ActivityInfo temp = new ActivityInfo(actID,imgID, imgUrl, name, info, remark, 1);
                    list.set(position, temp);
                    updateFollow = connectHelper.url+"Service/set_follow.jsp?AcctName="+CurrentAcct.AcctName+"&ActID="+actID; //传回后台新增关注
                    new SetFollow().execute(updateFollow);
                    Log.d("ActID onClick:",""+actID);
                } else {
                    viewHolder.follow.setImageResource(R.drawable.unlike);
                    currentFollow = 0;
                    ActivityInfo temp = new ActivityInfo(actID,imgID, imgUrl, name, info, remark, 0);
                    list.set(position, temp);
                    updateFollow = connectHelper.url+"Service/delete_follow.jsp?AcctName="+CurrentAcct.AcctName+"&ActID="+actID; //传回后台取消关注
                    new SetFollow().execute(updateFollow);
                    Log.d("ActID onClick:",""+actID);
                }
            }
        });
        follow = currentFollow;
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, commentActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public ImageView img;
        public TextView nameText;
        public TextView infoText;
        public TextView remarkText;
        public ImageView follow;
        public ImageView comment;
    }

    public Integer getCurrentFollow() {
        return currentFollow;
    }

    public Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(false);
            //不使用缓存
            conn.setUseCaches(true);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    private class SetFollow extends AsyncTask<String,Integer,List<String>> {
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

        }
    }
}
