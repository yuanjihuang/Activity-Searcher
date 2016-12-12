package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proj.zhaohuo.R;
import com.example.proj.zhaohuo.circleCommentatorInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/12/11.
 */

public class CircleDiscussionZoneAdapter extends BaseAdapter {
    private Context context;
    private List<circleCommentatorInfo> list;

    public CircleDiscussionZoneAdapter(Context context, List<circleCommentatorInfo> list) {
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

    public int getCommentatorImgID(int i) {
        if (list == null) {
            return 0;
        }
        return list.get(i).getCommentatorImgID();
    }

    public String getCommentatorName(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i).getCommentatorName();
    }

    public String getCommentContent(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i).getCommentContent();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        CircleDiscussionZoneAdapter.ViewHolder viewHolder;
        if (view == null) {   //view为空时才全部加载布局
            //从item这个layout（使用adapter的）来传入需要的context
            convertView = LayoutInflater.from(context).inflate(R.layout.circle_discussion_zone_item, null);
            viewHolder = new CircleDiscussionZoneAdapter.ViewHolder();
            viewHolder.commentatorIcon = (ImageView) convertView.findViewById(R.id.circle_commentator_ic);
            viewHolder.commentatorName = (TextView) convertView.findViewById(R.id.circle_commentator_name);
            viewHolder.commentContent = (TextView) convertView.findViewById(R.id.circle_comment_content);
            convertView.setTag(viewHolder); //存好firstLetter和name两个控件，不需要每次都找一遍
        } else {
            convertView = view;
            viewHolder = (CircleDiscussionZoneAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.commentatorIcon.setImageResource(list.get(i).getCommentatorImgID());
        viewHolder.commentatorName.setText(list.get(i).getCommentatorName());
        viewHolder.commentContent.setText(list.get(i).getCommentContent());
        return convertView;
    }

    private class ViewHolder {
        public ImageView commentatorIcon;
        public TextView commentatorName;
        public TextView commentContent;
    }
}
