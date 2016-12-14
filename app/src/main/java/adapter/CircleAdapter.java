package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proj.zhaohuo.R;
import com.example.proj.zhaohuo.circleDiscussionZone;
import com.example.proj.zhaohuo.circleInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 */

public class CircleAdapter extends BaseAdapter {
    private Context context;
    private List<circleInfo> list;
    class BtnOnClickListener implements android.view.View.OnClickListener{
        private int position;
        public BtnOnClickListener(int p){
            position = p;
        }
        @Override
        public void onClick(View v){
            Intent intent = new Intent(context, circleDiscussionZone.class);
            context.startActivity(intent);
        }
    }
    public CircleAdapter(Context context, List<circleInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i);
    }

    public int getImgID(int i) {
        if (list == null) {
            return 0;
        }
        return list.get(i).getImgID();
    }

    public String getCircleName(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i).getCircleName();
    }

    public String getBriIntro(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i).getBriIntro();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder viewHolder;
        if (view == null) {   //view为空时才全部加载布局
            //从item这个layout（使用adapter的）来传入需要的context
            convertView = LayoutInflater.from(context).inflate(R.layout.circlelist_item, null);
            viewHolder = new ViewHolder();
            viewHolder.circleUserIcon = (ImageView) convertView.findViewById(R.id.circle_user_ic);
            viewHolder.circleName = (TextView) convertView.findViewById(R.id.circle_name);
            viewHolder.circleBriIntro = (TextView) convertView.findViewById(R.id.circle_briIntro);
            viewHolder.enterDiscussion = (Button) convertView.findViewById(R.id.enter_discussion);
            viewHolder.enterDiscussion.setOnClickListener(new BtnOnClickListener(i));
            convertView.setTag(viewHolder); //存好firstLetter和name两个控件，不需要每次都找一遍
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.circleUserIcon.setImageResource(list.get(i).getImgID());
        viewHolder.circleName.setText(list.get(i).getCircleName());
        viewHolder.circleBriIntro.setText(list.get(i).getBriIntro());
        return convertView;
    }

    private class ViewHolder {
        public ImageView circleUserIcon;
        public TextView circleName;
        public TextView circleBriIntro;
        public Button enterDiscussion;
    }
}
