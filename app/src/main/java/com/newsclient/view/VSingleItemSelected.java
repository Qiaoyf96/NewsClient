package com.newsclient.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import com.newsclient.R;

/**
 * Created by Xembo Liu on 9/8/2017.
 */
public class VSingleItemSelected extends BaseAdapter {

    private Context context;
    private ArrayList<String> beans;


    // 用来控制CheckBox的选中状况
    private HashMap<Integer, Boolean> isSelected;


    class ViewHolder {
        TextView tvName;
        CheckBox cb;
        LinearLayout LL;
    }

    public VSingleItemSelected(Context context, ArrayList<String> beans,HashMap<Integer,Boolean> isSelected) {
        this.beans = beans;
        this.context = context;
        this.isSelected = isSelected;
        // 初始化数据
        initDate();

    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < beans.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        String bean = beans.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.settings_tag_item, null);
            holder = new ViewHolder();
            holder.cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_device_name);
            holder.LL = (LinearLayout) convertView.findViewById(R.id.linear_layout_up);
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }
        System.out.println(isSelected.toString());
        holder.tvName.setText(bean);
        // 监听checkBox并根据原来的状态来设置新的状态
        holder.LL.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("点击："+position);
                if (isSelected.get(position)) {
                    isSelected.put(position, false);
                    setIsSelected(isSelected);
                } else {
                    isSelected.put(position, true);
                    setIsSelected(isSelected);
                }
                notifyDataSetChanged();
            }
        });

        // 根据isSelected来设置checkbox的选中状况
        holder.cb.setChecked(getIsSelected().get(position));
        return convertView;
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
    }
}