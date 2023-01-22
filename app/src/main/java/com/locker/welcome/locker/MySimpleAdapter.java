package com.locker.welcome.locker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by welcome on 27-Apr-20.
 */

public class MySimpleAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater = null;
    List<GridModel> data;
    GradientDrawable drawable;
    String TxtColor;

    public MySimpleAdapter(Context context, List<GridModel> data, GradientDrawable drawable, String TxtColor) {
        super();
        this.context = context;
        this.data = data;
        this.drawable = drawable;
        this.TxtColor =TxtColor;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {

                convertView = inflater.inflate(R.layout.file_menu, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.layout_grid = (LinearLayout) convertView.findViewById(R.id.row_grid);
                viewHolder.txt_menutitle = (TextView) convertView.findViewById(R.id.gridview_item_textview);
                viewHolder.txt_menutitle.setTextColor(Color.parseColor(TxtColor));
                viewHolder.img_grid = (ImageView) convertView.findViewById(R.id.img_grid);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            String cVal = data.get(position).getmName();
            int Image = data.get(position).getmImage();
            viewHolder.txt_menutitle.setText(cVal);
            viewHolder.layout_grid.setBackground(drawable);
            if(Image !=0)viewHolder.img_grid.setImageResource(Image);
            else viewHolder.img_grid.setImageResource(0);
        return convertView;
    }

    public static class ViewHolder {
        LinearLayout layout_grid;
        TextView txt_menutitle;
        ImageView img_grid;
    }
}
