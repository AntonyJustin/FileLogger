package com.locker.welcome.locker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.locker.welcome.locker.Dialog.ToastDialogs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static com.locker.welcome.locker.Global.Pin_Enable;
import static com.locker.welcome.locker.Global.SortBy;
import static com.locker.welcome.locker.Global.ViewList;
import static com.locker.welcome.locker.Global.determinefolder;
import static com.locker.welcome.locker.Global.filepaths;
import static com.locker.welcome.locker.Global.mypreference;
import static com.locker.welcome.locker.Global.sharedpreferences;
import static com.locker.welcome.locker.Global.sn;

public class Menu extends AppCompatActivity implements AdapterView.OnItemClickListener {
    GridView gridView;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context=Menu.this;

        sn=new SoundFile(Menu.this);

        try {
            MySimpleAdapter myAdapter;
            gridView = (GridView) findViewById(R.id.gridView);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            new Asyncsfiles(this,Menu.this);

            String[] MenuList = new String[8];
            int[] ImageList = new int[8];

            MenuList[0] = "Images";
            ImageList[0] = R.drawable.images;
            MenuList[1] = "Videos";
            ImageList[1] = R.drawable.videos;
            MenuList[2] = "Audios";
            ImageList[2] = R.drawable.audios;
            MenuList[3] = "Other Files";
            ImageList[3] = R.drawable.otherfiles;
            MenuList[4] = "Locked Files";
            ImageList[4] = R.drawable.encrypt;
            MenuList[5] = "Unlocked Files";
            ImageList[5] = R.drawable.decrypt;
            MenuList[6] = "Setting";
            ImageList[6] = R.drawable.settings;
            MenuList[7] = "Exit";
            ImageList[7] = R.drawable.exit;

            List<GridModel> listData = new ArrayList<>();

            for (int i = 0; i < MenuList.length; i++) {
                listData.add(new GridModel(MenuList[i], ImageList[i]));
            }
            myAdapter = new MySimpleAdapter(Menu.this, listData,CreateShape(),TextColor());
            gridView.setAdapter(myAdapter);
            gridView.setOnItemClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        new Asyncsfiles(this,Menu.this);
        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        sn.NotificationSound(9);
        Global.sn.Vibtration(100);
        Intent intent;
        switch (i) {

            case 0:
            {
                intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent, "Choose Video"), 123);

                determinefolder=1;

                break;
            }
            case 1:
            {
                intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("video/*");

                startActivityForResult(Intent.createChooser(intent, "Choose Video"), 123);

                determinefolder=2;
                break;
            }
            case 2:
            {
                intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("audio/*");

                startActivityForResult(Intent.createChooser(intent, "Choose Audio"), 123);

                determinefolder=3;
                break;
            }
            case 3:
            {
                String mimetypes[]={"application/pdf","text/plain","application/msword"};

                intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.addCategory(Intent.CATEGORY_OPENABLE);

                intent.setType(mimetypes.length == 1 ? mimetypes[0] : "*/*");

                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);

                startActivityForResult(Intent.createChooser(intent, "Choose Other Files"), 123);

                determinefolder=4;
                break;
            }
            case 4:
            {
                new Asyncsfiles(this,Menu.this);
                Asyncsfiles.MenuList lock=new Asyncsfiles.MenuList();
                lock.execute(0);
                break;
            }
            case 5:
            {
                Asyncsfiles.MenuList unlock=new Asyncsfiles.MenuList();
                unlock.execute(1);
                break;
            }
            case 6:
            {
                Asyncsfiles.MenuList setting=new Asyncsfiles.MenuList();
                setting.execute(3);
                break;
            }
            case 7:
            {
                Asyncsfiles.MenuList manageAccount=new Asyncsfiles.MenuList();
                manageAccount.execute();
                break;
            }
            case 8:
            {
                Asyncsfiles.Exit exit=new Asyncsfiles.Exit();
                exit.execute();
                break;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            sn.NotificationSound(11);
            Global.sn.Vibtration(100);
            Global.uripath=data.getData().toString();
            lockfiles();
        }
    }

    public static void lockfiles()
    {
        if(Global.PinEnable==1)
        {
            Intent intent;
            intent = new Intent(context, Passwordenter.class);
            intent.setAction(Intent.ACTION_VIEW);
            context.startActivity(intent);
        }
        else if(Global.PinEnable==0)
        {
            Asyncsfiles.lockfiles lock=new Asyncsfiles.lockfiles();
            lock.execute(Global.uripath);
        }
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

            Asyncsfiles.Exit exit=new Asyncsfiles.Exit();
            exit.execute();
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

                Asyncsfiles.Exit exit=new Asyncsfiles.Exit();
                exit.execute();
                return true;
        }
        return false;
    }
}
