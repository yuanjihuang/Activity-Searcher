package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proj.zhaohuo.R;

import java.util.List;

public class InfoAdapter extends BaseAdapter {

    private Context context;
    private List<String> index_list;
    private List<String> list;

    public InfoAdapter(Context context, List<String> list, List<String> index_list) {
        this.context = context;
        this.list = list;
        this.index_list = index_list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.user_info_item, null);
            viewHolder = new ViewHolder();
            viewHolder.First = (TextView) convertView.findViewById(R.id.user_info_first);
            viewHolder.Second = (TextView) convertView.findViewById(R.id.user_info_second);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String index = index_list.get(i);
        String info = list.get(i);
        viewHolder.First.setText(index);
        viewHolder.Second.setText(info);
        return convertView;
    }

    private class ViewHolder {
        public TextView First;
        public TextView Second;
    }
}
