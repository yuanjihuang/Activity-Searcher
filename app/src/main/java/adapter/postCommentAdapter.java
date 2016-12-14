package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proj.zhaohuo.R;
import com.example.proj.zhaohuo.circleInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 */

public class postCommentAdapter extends BaseAdapter {
    private Context context;
    private List<circleInfo> list;
    public postCommentAdapter(Context context, List<circleInfo> list) {
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

    public String getName(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i).getName();
    }

    public String getContent(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i).getContent();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        postCommentAdapter.ViewHolder viewHolder;
        if (view == null) {   //view为空时才全部加载布局
            //从item这个layout（使用adapter的）来传入需要的context
            convertView = LayoutInflater.from(context).inflate(R.layout.posted_comment_item, null);
            viewHolder = new postCommentAdapter.ViewHolder();
            viewHolder.commentIcon = (ImageView) convertView.findViewById(R.id.post_detail_ic);
            viewHolder.commentName = (TextView) convertView.findViewById(R.id.post_detail_name);
            viewHolder.commentContent = (TextView) convertView.findViewById(R.id.post_detail_content);
            convertView.setTag(viewHolder); //存好firstLetter和name两个控件，不需要每次都找一遍
        } else {
            convertView = view;
            viewHolder = (postCommentAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.commentIcon.setImageResource(list.get(i).getImgID());
        viewHolder.commentName.setText(list.get(i).getName());
        viewHolder.commentContent.setText(list.get(i).getContent());
        return convertView;
    }

    private class ViewHolder {
        public ImageView commentIcon;
        public TextView commentName;
        public TextView commentContent;
    }
}
