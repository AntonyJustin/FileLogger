package com.locker.welcome.locker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.locker.welcome.locker.Global.filepaths;
import static com.locker.welcome.locker.Global.sn;
import static com.locker.welcome.locker.Tools.GetExtension;

public class InvalidAttempt extends AppCompatActivity implements AdapterView.OnItemClickListener {
    GridView gridView;
    ImageView imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalid_attempt);

        gridView = (GridView) findViewById(R.id.gridView);

        Invalidattemptadapter myAdapter;

        sn=new SoundFile(InvalidAttempt.this);

        File file=new File("/storage/emulated/0/MYGALLERY");
        File filepath[]=file.listFiles();
        String[] MenuList = new String[filepath.length];

        Drawable[] ImageList = new Drawable[filepath.length];


        try {
            for(int i=0;i<filepath.length;i++)
            {
                File file1=new File(filepath[i].toString());
                Date lastmodified=new Date(file1.lastModified());
                SimpleDateFormat formatter=new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");

                MenuList[i]=formatter.format(lastmodified);

                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inJustDecodeBounds=true;
                BitmapFactory.decodeFile(file1.toString(),options);
                options.inSampleSize=100;
                options.inJustDecodeBounds=false;

                Drawable drawable = new BitmapDrawable(getResources(),BitmapFactory.decodeFile(file1.toString(),options));
                ImageList[i] = drawable;
                //mybitmap.recycle();
            }

            List<GridModelFile> listData = new ArrayList<>();

            for (int i = 0; i < MenuList.length; i++) {
                //listData.add(new GridModelFile(MenuList[i], ImageList[i]));
            }
            myAdapter = new Invalidattemptadapter(InvalidAttempt.this, listData);
            gridView.setAdapter(myAdapter);
            gridView.setOnItemClickListener(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sn.NotificationSound(11);
        Global.sn.Vibtration(100);
        Intent intent=new Intent(InvalidAttempt.this,InvalidAttemptViewer.class);
        intent.putExtra("key",position);
        startActivity(intent);
    }

    public GradientDrawable CreateShape() {
        String BGClolor;
        BGClolor = "#e5e4e2";

        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});
        shape.setColor(Color.parseColor(BGClolor));
        return shape;
    }

    public String TextColor() {
        String TxtColor;
        TxtColor = "#000000";

        return TxtColor;
    }
}
