package com.locker.welcome.locker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

import static com.locker.welcome.locker.Asyncsfiles.context;
import static com.locker.welcome.locker.Global.sn;
import static com.locker.welcome.locker.Tools.directoryinitialize;

public class LockedFiles extends AppCompatActivity {
Button img,vid,aud,oth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locked_files);

        sn=new SoundFile(LockedFiles.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tools.getInstance().foldercreate();
        directoryinitialize();

        img=(Button)findViewById(R.id.btn_img);
        vid=(Button)findViewById(R.id.btn_vid);
        aud=(Button)findViewById(R.id.btn_aud);
        oth=(Button)findViewById(R.id.btn_oth);

        Global.filepaths=new ArrayList<>();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sn.NotificationSound(11);
                Global.sn.Vibtration(100);
                Global.iswhichfile=0;
                File file=new File(Global.MainPath + "Images");
                File files[]=file.listFiles();

                for(int i=0;i<files.length;i++)
                {
                    Global.filepaths.add(files[i].getPath());
                }

                    Intent intent;
                    intent = new Intent(LockedFiles.this, Content_View.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    context.startActivity(intent);
            }
        });

        vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sn.NotificationSound(11);
                Global.sn.Vibtration(100);
                Global.iswhichfile=1;
                File file=new File(Global.MainPath + "Videos");
                File files[]=file.listFiles();

                for(int i=0;i<files.length;i++)
                {
                    Global.filepaths.add(files[i].getPath());
                }

                Intent intent;
                intent = new Intent(LockedFiles.this, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);
            }
        });

        aud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sn.NotificationSound(11);
                Global.sn.Vibtration(100);
                Global.iswhichfile=2;
                File file=new File(Global.MainPath + "Audios");
                File files[]=file.listFiles();

                for(int i=0;i<files.length;i++)
                {
                    Global.filepaths.add(files[i].getPath());
                }

                Intent intent;
                intent = new Intent(LockedFiles.this, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);
            }
        });

        oth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sn.NotificationSound(11);
                Global.sn.Vibtration(100);
                Global.iswhichfile=3;
                File file=new File(Global.MainPath + "Others");
                File files[]=file.listFiles();

                for(int i=0;i<files.length;i++)
                {
                    Global.filepaths.add(files[i].getPath());
                }

                Intent intent;
                intent = new Intent(LockedFiles.this, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            sn.NotificationSound(9);
            Global.sn.Vibtration(100);

            finish();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                sn.NotificationSound(9);
                Global.sn.Vibtration(100);

                finish();
                return true;
        }
        return false;
    }
}
