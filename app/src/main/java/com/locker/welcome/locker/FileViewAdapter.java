package com.locker.welcome.locker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static com.locker.welcome.locker.Global.Home_Pin;
import static com.locker.welcome.locker.Global.ViewList;
import static com.locker.welcome.locker.Global.mypreference;
import static com.locker.welcome.locker.Global.sharedpreferences;

/**
 * Created by welcome on 01-Jun-20.
 */

public class FileViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater = null;
    List<GridModelFile> data;
    GradientDrawable drawable=new GradientDrawable();

    public FileViewAdapter(Context context, List<GridModelFile> data) {
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
        try
        {

            ViewHolder viewHolder = null;
            if (convertView == null) {

                if(Global.ViewlistType==0)
                {
                    convertView = inflater.inflate(R.layout.large_view, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.layout_grid = (RelativeLayout) convertView.findViewById(R.id.large_grid);
                    viewHolder.txt_menutitle = (TextView) convertView.findViewById(R.id.header_textview);
                    viewHolder.txt_menusubtitle = (TextView) convertView.findViewById(R.id.subheader_textview);
                    viewHolder.txt_menusubtitle1 = (TextView) convertView.findViewById(R.id.subheader_textview1);
                    viewHolder.txt_menutitle.setTextColor(Color.parseColor("#000000"));
                    viewHolder.txt_menusubtitle.setTextColor(Color.parseColor("#000000"));
                    viewHolder.txt_menusubtitle1.setTextColor(Color.parseColor("#000000"));
                    viewHolder.img_grid = (ImageView) convertView.findViewById(R.id.img_grid);
                }
                else if(Global.ViewlistType==1)
                {
                    convertView = inflater.inflate(R.layout.details_view, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.layout_grid = (RelativeLayout) convertView.findViewById(R.id.details_grid);
                    viewHolder.txt_menutitle = (TextView) convertView.findViewById(R.id.header_textview);
                    viewHolder.txt_menusubtitle = (TextView) convertView.findViewById(R.id.subheader_textview);
                    viewHolder.txt_menusubtitle1 = (TextView) convertView.findViewById(R.id.subheader_textview1);
                    viewHolder.txt_menutitle.setTextColor(Color.parseColor("#000000"));
                    viewHolder.txt_menusubtitle.setTextColor(Color.parseColor("#000000"));
                    viewHolder.txt_menusubtitle1.setTextColor(Color.parseColor("#000000"));
                    viewHolder.img_grid = (ImageView) convertView.findViewById(R.id.img_grid);
                }
                else if(Global.ViewlistType==2){
                    convertView = inflater.inflate(R.layout.xtra_large_view, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.layout_grid = (RelativeLayout) convertView.findViewById(R.id.xtralarge_grid);
                    viewHolder.txt_menutitle = (TextView) convertView.findViewById(R.id.header_textview);
                    viewHolder.txt_menusubtitle = (TextView) convertView.findViewById(R.id.subheader_textview);
                    viewHolder.txt_menusubtitle1 = (TextView) convertView.findViewById(R.id.subheader_textview1);
                    viewHolder.txt_menutitle.setTextColor(Color.parseColor("#000000"));
                    viewHolder.txt_menusubtitle.setTextColor(Color.parseColor("#000000"));
                    viewHolder.txt_menusubtitle1.setTextColor(Color.parseColor("#000000"));
                    viewHolder.img_grid = (ImageView) convertView.findViewById(R.id.img_grid);
                }

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            drawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});
            drawable.setColor(Color.parseColor("#fafad2"));
            drawable.setStroke(2,Color.parseColor("#000000"));

            Drawable Image = data.get(position).getmImage();
            viewHolder.txt_menutitle.setText(data.get(position).Title);
            viewHolder.txt_menusubtitle.setText(data.get(position).Subtitle);
            viewHolder.txt_menusubtitle1.setText(data.get(position).Subtitle1);
            viewHolder.layout_grid.setBackground(drawable);
            if(Image !=null)viewHolder.img_grid.setImageDrawable(Image);
            else viewHolder.img_grid.setImageResource(0);
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("FileViewAdapter - getView - "+e.toString());
        }
        return convertView;
    }

    public static class ViewHolder {
        RelativeLayout layout_grid;
        TextView txt_menutitle;
        TextView txt_menusubtitle;
        TextView txt_menusubtitle1;
        ImageView img_grid;
    }
    }
