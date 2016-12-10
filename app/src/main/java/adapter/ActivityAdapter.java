package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proj.zhaohuo.ActivityInfo;
import com.example.proj.zhaohuo.R;

import java.util.List;

/**
 * Created by Eafun on 2016/12/9.
 */

public class ActivityAdapter extends BaseAdapter {
    private List<ActivityInfo> list;
    private Context context;

    public ActivityAdapter(Context context, List<ActivityInfo> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if(list == null){
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

        if(view == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activityitem,null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.actImg);
            viewHolder.nameText = (TextView) convertView.findViewById(R.id.actName);
            viewHolder.infoText = (TextView) convertView.findViewById(R.id.actInfo);
            viewHolder.follow = (ImageButton) convertView.findViewById(R.id.follow);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final int imgID = list.get(position).getImgID();
        final String name = list.get(position).getName();
        final String info = list.get(position).getInfo();
        final int follow = list.get(position).isFollow();
        viewHolder.img.setImageResource(imgID);
        viewHolder.nameText.setText(name);
        viewHolder.infoText.setText(info);
        if(follow==0){
            viewHolder.follow.setImageResource(R.drawable.empty_star);
        } else{
            viewHolder.follow.setImageResource(R.drawable.full_star);
        }
        /*viewHolder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(follow==0){
                    viewHolder.follow.setImageResource(R.drawable.full_star);
                    ActivityInfo temp = new ActivityInfo(imgID,name,info,1);
                    list.set(position,temp);
                } else{
                    viewHolder.follow.setImageResource(R.drawable.empty_star);
                    ActivityInfo temp = new ActivityInfo(imgID,name,info,0);
                    list.set(position,temp);
                }
            }
        });*/
        //把按钮放到活动详情中修改
        return convertView;
    }

    private class ViewHolder {
        public ImageView img;
        public TextView nameText;
        public TextView infoText;
        public ImageButton follow;
    }
}
