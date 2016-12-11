package com.example.proj.zhaohuo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eafun on 2016/12/11.
 */

public class CommentAdapter extends BaseAdapter {
    private List<CommentInfo> list;
    private Context context;

    public CommentAdapter(Context context, List<CommentInfo> list){
        this.context = context;
        this.list = list;
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        View convertView;
        final ViewHolder viewHolder;
        if (view == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.commentitem,null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.userImg);
            viewHolder.nameText = (TextView) convertView.findViewById(R.id.username);
            viewHolder.commentText = (TextView) convertView.findViewById(R.id.input_comment);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final int userImgID = list.get(position).getUserImgID();
        final String userImgUrl = list.get(position).getUserImgUrl();
        final String userName = list.get(position).getUserName();
        final String comment = list.get(position).getComment();
        viewHolder.img.setImageResource(userImgID);
        viewHolder.nameText.setText(userName);
        viewHolder.commentText.setText(comment);
        return convertView;
    }

    private class ViewHolder {
        public ImageView img;
        public TextView nameText;
        public TextView commentText;
    }
}
