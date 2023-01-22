package com.locker.welcome.locker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.locker.welcome.locker.Global.filepaths;
import static com.locker.welcome.locker.Global.sn;
import static com.locker.welcome.locker.Tools.GetExtension;

public class UnlockFiles extends AppCompatActivity implements AdapterView.OnItemClickListener {
    GridView gridView;
    TextView txtname;
    public static File files[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_files);
        txtname=(TextView)findViewById(R.id.txtname);

        sn=new SoundFile(UnlockFiles.this);

        txtname.setText("Unlocked Files");

        FileViewAdapter myAdapter;

        new Asyncsfiles(this,UnlockFiles.this);

        gridView = (GridView) findViewById(R.id.gridView);

        File file=new File("/storage/emulated/0/Locker");
        files=new File[file.listFiles().length];
        files=file.listFiles();

        String[] MenuList = new String[files.length];
        Drawable[] ImageList = new Drawable[files.length];

        for(int i=0;i<files.length;i++)
        {
            MenuList[i]=Tools.getInstance().GetFilename(files[i]);

            if(Tools.getInstance().GetExtension(MenuList[i]).equals("png")||Tools.getInstance().GetExtension(MenuList[i]).equals("jpg")||Tools.getInstance().GetExtension(MenuList[i]).equals("gif"))
            {
                Bitmap mybitmap = BitmapFactory.decodeFile("/storage/emulated/0/Locker/"+Tools.getInstance().GetFilename(files[i]));
                Drawable drawable = new BitmapDrawable(getResources(), mybitmap);
                ImageList[i] = drawable;
            }


            if(Tools.getInstance().GetExtension(MenuList[i]).equals("mp4")||Tools.getInstance().GetExtension(MenuList[i]).equals("avi")||Tools.getInstance().GetExtension(MenuList[i]).equals("mkv"))
            {
                Bitmap mybitmap = ThumbnailUtils.createVideoThumbnail("/storage/emulated/0/Locker/"+Tools.getInstance().GetFilename(files[i]), MediaStore.Video.Thumbnails.MICRO_KIND);
                Drawable drawable = new BitmapDrawable(getResources(), mybitmap);
                ImageList[i] = drawable;
            }

            if(Tools.getInstance().GetExtension(MenuList[i]).equals("mp3")||Tools.getInstance().GetExtension(MenuList[i]).equals("wav"))
            {
                ImageList[i] = getResources().getDrawable(R.drawable.audios);
            }
            if(Tools.getInstance().GetExtension(MenuList[i]).equals("pdf"))
            {
                ImageList[i] = getResources().getDrawable(R.drawable.pdf);
            }
            if(Tools.getInstance().GetExtension(MenuList[i]).equals("txt"))
            {
                ImageList[i] = getResources().getDrawable(R.drawable.text);
            }
            if(Tools.getInstance().GetExtension(MenuList[i]).equals("doc")||Tools.getInstance().GetExtension(MenuList[i]).equals("docs"))
            {
                ImageList[i] = getResources().getDrawable(R.drawable.document);
            }
        }

        List<GridModelFile> listData = new ArrayList<>();

        for (int i = 0; i < MenuList.length; i++) {
            //listData.add(new GridModelFile(MenuList[i], ImageList[i]));
        }
        myAdapter = new FileViewAdapter(UnlockFiles.this, listData);
        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sn.NotificationSound(11);
        Global.sn.Vibtration(100);
        Global.lockorunlock=0;
        Asyncsfiles.LockorUnlock lock=new Asyncsfiles.LockorUnlock();
        lock.execute(position);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sn.NotificationSound(11);
        Global.sn.Vibtration(100);
    }
}
