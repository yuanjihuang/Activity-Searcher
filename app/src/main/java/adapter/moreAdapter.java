package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proj.zhaohuo.R;

import java.util.List;

public class MoreAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public MoreAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else
            return list.size();
    }

    @Override
    public Object getItem(int i) {
        if (list == null) {
            return null;
        } else
            return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder viewHolder;

        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.more_item, null);
            viewHolder = new ViewHolder();
            viewHolder.First = (TextView) convertView.findViewById(R.id.more_item);
            viewHolder.Second = (ImageView) convertView.findViewById(R.id.arrow);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String info = list.get(i);
        viewHolder.First.setText(info);
        return convertView;
    }

    private class ViewHolder {
        public TextView First;
        public ImageView Second;
    }
}
