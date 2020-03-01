package presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.work2.R;
import java.util.ArrayList;

import model.Data;


public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Data> array;

    public MyAdapter(Context context, ArrayList<Data> arry) {

        this.context = context;
        this.array = arry;


    }

    @Override
    public int getCount() {

        return array.size();
    }

    @Override
    public Object getItem(int position) {

        return array.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //反射器
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, null);//加载listview子项
            vh.tv1 = convertView.findViewById(R.id.list_title);
            vh.tv2 = convertView.findViewById(R.id.list_content);
            vh.tv3 = convertView.findViewById(R.id.list_time);

            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        vh.tv1.setText(array.get(position).getTitle());
        vh.tv2.setText(array.get(position).getContent());
        vh.tv3.setText(array.get(position).getTime());
        //将listview的item返回，也就是显示出来
        return convertView;
    }

    class ViewHolder {     //内部类，对控件进行缓存
        TextView tv1, tv2, tv3;
    }

}

