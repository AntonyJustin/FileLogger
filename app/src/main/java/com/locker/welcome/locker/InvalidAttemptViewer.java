package com.locker.welcome.locker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import static com.locker.welcome.locker.Global.sn;
import static com.locker.welcome.locker.Tools.GetExtension;

public class InvalidAttemptViewer extends AppCompatActivity {
ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalid_attempt_viewer);

        img=(ImageView)findViewById(R.id.img);

        sn=new SoundFile(InvalidAttemptViewer.this);

        Bundle extras=getIntent().getExtras();

        String value=extras.get("key").toString();

        File file=new File("/storage/emulated/0/MYGALLERY");

        String files[]=file.list();

        Bitmap mybitmap = BitmapFactory.decodeFile("/storage/emulated/0/MYGALLERY/"+files[Integer.parseInt(value)]);

        img.setRotation(-90);
        img.setImageBitmap(mybitmap);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sn.NotificationSound(9);
        Global.sn.Vibtration(100);
    }
}
