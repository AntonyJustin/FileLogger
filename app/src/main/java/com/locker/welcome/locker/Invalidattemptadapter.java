package com.locker.welcome.locker;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Justin on 06-07-2020.
 */

public class Invalidattemptadapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater = null;
    List<GridModelFile> data;
    GradientDrawable drawable=new GradientDrawable();

    public Invalidattemptadapter(Context context, List<GridModelFile> data) {
        super();
        this.context = context;
        this.data = data;
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
        Invalidattemptadapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.invalid_attempt_view, parent, false);
            viewHolder = new Invalidattemptadapter.ViewHolder();
            viewHolder.layout_grid = (RelativeLayout) convertView.findViewById(R.id.invalid_attempt_grid);
            viewHolder.txt_menutitle = (TextView) convertView.findViewById(R.id.gridview_item_textview);
            viewHolder.txt_menutitle.setTextColor(Color.parseColor("#000000"));
            viewHolder.img_grid = (ImageView) convertView.findViewById(R.id.img_grid);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Invalidattemptadapter.ViewHolder) convertView.getTag();
        }

        drawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});
        drawable.setColor(Color.parseColor("#fafad2"));
        drawable.setStroke(2,Color.parseColor("#000000"));

        Drawable Image = data.get(position).getmImage();
        viewHolder.txt_menutitle.setText(data.get(position).Title);
        viewHolder.layout_grid.setBackground(drawable);
        if(Image !=null) {
            viewHolder.img_grid.setImageDrawable(Image);
            viewHolder.img_grid.setRotation(-90);
        }
        else viewHolder.img_grid.setImageResource(0);
        return convertView;
    }

    public static class ViewHolder {
        RelativeLayout layout_grid;
        TextView txt_menutitle;
        ImageView img_grid;
    }





}
