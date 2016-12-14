package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proj.zhaohuo.R;
import com.example.proj.zhaohuo.circleFollowerInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/11.
 */

public class CircleDiscussionZoneAdapter extends RecyclerView.Adapter<CircleDiscussionZoneAdapter.ViewHolder> {
    private ArrayList<circleFollowerInfo> follower_list;
    private LayoutInflater mInflater;
    public interface OnItemClickListener{
        void onItemClick(View view, int position, circleFollowerInfo circleFollowerInfo);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //构造函数，传参为context和一个ArrayList
    public CircleDiscussionZoneAdapter(Context context, ArrayList<circleFollowerInfo> items) {
        super();
        follower_list = items;
        mInflater = LayoutInflater.from(context);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        ImageView circle_follower_ic;
        TextView circle_follower_name;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = mInflater.inflate(R.layout.circle_follower_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        holder.circle_follower_ic = (ImageView) view.findViewById(R.id.circle_follower_ic);
        holder.circle_follower_name =(TextView)view.findViewById(R.id.circle_follower_name);
        return holder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i){
        circleFollowerInfo tmp = follower_list.get(i);
        viewHolder.circle_follower_ic.setImageResource(tmp.getCommentatorImgID());
        viewHolder.circle_follower_name.setText(tmp.getCommentatorName());
        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mOnItemClickListener.onItemClick(viewHolder.itemView, i, follower_list.get(i));
                }
            });
        }
    }
    @Override
    public int getItemCount(){
        return follower_list.size();
    }

}
