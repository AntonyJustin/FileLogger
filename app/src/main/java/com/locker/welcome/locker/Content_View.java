package com.locker.welcome.locker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.locker.welcome.locker.Dialog.Chooseaction;
import com.locker.welcome.locker.Dialog.ToastDialogs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.locker.welcome.locker.Global.Home_Pin;
import static com.locker.welcome.locker.Global.SortBy;
import static com.locker.welcome.locker.Global.ViewList;
import static com.locker.welcome.locker.Global.filepaths;
import static com.locker.welcome.locker.Global.filereldatalist;
import static com.locker.welcome.locker.Global.mypreference;
import static com.locker.welcome.locker.Global.sharedpreferences;
import static com.locker.welcome.locker.Global.sn;
import static com.locker.welcome.locker.Tools.Convert_ListStrToFilearr;
import static com.locker.welcome.locker.Tools.GetExtension;
import static com.locker.welcome.locker.Tools.GetFormatDateFromLastModified;

public class Content_View extends AppCompatActivity implements AdapterView.OnItemClickListener {
    GridView gridView;
    TextView nodata;
    public static Context context;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content__view);
        try {
            Tools.getInstance().Testlog("Content_View - initializing");
            nodata=(TextView)findViewById(R.id.nodatas);
            Global.LockStatus=0;
            activity=this;

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            context=Content_View.this;
            sn=new SoundFile(context);

            new Asyncsfiles(this,context);

            nodata.setVisibility(View.GONE);

            FileViewAdapter myAdapter;
            gridView = (GridView) findViewById(R.id.gridView);

            String[] MenuList = new String[filepaths.size()];
            Drawable[] ImageList = new Drawable[filepaths.size()];

            if(!(Global.filepaths==null||Global.filepaths.size()==0))
            {
                //Get LargeFileHeaderDetails
                if(Tools.getInstance().GetHeaderLargeDetail(filepaths)==null)
                {
                    return;
                }

                //Get HeaderDetails
                if (Tools.getInstance().GetHeaderDetail(filepaths)==null) {
                    return;
                }

                //Set Sorting Algorithm
                if(Global.Sortby==0)
                {
                    filereldatalist=Tools.getInstance().GetSortedList(filereldatalist,0);
                }
                else if(Global.Sortby==1)
                {
                    filereldatalist=Tools.getInstance().GetSortedList(filereldatalist,1);
                }
                else if(Global.Sortby==2)
                {
                    filereldatalist=Tools.getInstance().GetSortedList(filereldatalist,2);
                }
                else if(Global.Sortby==3)
                {
                    filereldatalist=Tools.getInstance().GetSortedList(filereldatalist,3);
                }
                else if(Global.Sortby==4)
                {
                    filereldatalist=Tools.getInstance().GetSortedList(filereldatalist,4);
                }
                else if(Global.Sortby==5)
                {
                    filereldatalist=Tools.getInstance().GetSortedList(filereldatalist,5);
                }


                if(Global.iswhichfile==0) {
                    Tools.getInstance().Testlog("Content_View - User Viewed LockedImages");
                    getSupportActionBar().setTitle("Locked Images");
                }

                if(Global.iswhichfile==1) {
                    Tools.getInstance().Testlog("Content_View - User Viewed LockedVideos");
                    getSupportActionBar().setTitle("Locked Videos");
                }

                if(Global.iswhichfile==2) {
                    Tools.getInstance().Testlog("Content_View - User Viewed LockedAudios");
                    getSupportActionBar().setTitle("Locked Audios");
                }

                if(Global.iswhichfile==3) {
                    Tools.getInstance().Testlog("Content_View - User Viewed LockedOtherfiles");
                    getSupportActionBar().setTitle("Locked Otherfiles");
                }

                for (int i = 0; i < Global.filepaths.size(); i++) {
                    String filename=Global.filereldatalist.get(i).FileName;
                    if(filename.length()>20)
                    {
                        filename=filename.substring(filename.length()-20,filename.length());
                    }
                    MenuList[i]=filename;
                    Drawable drawable=new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(Global.filereldatalist.get(i).getThumbnail(),0,Global.filereldatalist.get(i).getThumbnail().length));
                    ImageList[i] = drawable;
                }

                sharedpreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                Global.ViewlistType=sharedpreferences.getInt(ViewList, 0);

                if(Global.ViewlistType==0)
                {
                    gridView.setNumColumns(2);
                }
                else if(Global.ViewlistType==1 || Global.ViewlistType==2) {
                    gridView.setNumColumns(1);
                }
                List<GridModelFile> listData = new ArrayList<>();

                for (int i = 0; i < MenuList.length; i++) {
                    listData.add(new GridModelFile(MenuList[i],Tools.formatSize(Long.parseLong(Global.filereldatalist.get(i).Filelength)),GetFormatDateFromLastModified(Long.parseLong(Global.filereldatalist.get(i).LastModified),0), ImageList[i]));
                }
                myAdapter = new FileViewAdapter(Content_View.this, listData);
                gridView.setAdapter(myAdapter);
                gridView.setOnItemClickListener(this);
            }
            else
            {
                nodata.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Tools.getInstance().Errorlog("Content_View - Oncreate - "+e.toString());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        try
        {
            //Tools.getInstance().Testlog("Content_View - User clicked File - FileName : "+Global.filerelateddata.get(position).FileName+" FileSize : "+Global.filerelateddata.get(position).Filelength+" bytes");
            sn.NotificationSound(11);
            Global.sn.Vibtration(100);
            Global.lockorunlock=1;
            Asyncsfiles.LockorUnlock lockorunlock=new Asyncsfiles.LockorUnlock();
            lockorunlock.execute(position);
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("Content_View - OnItemClick - "+e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try
        {
            sn.NotificationSound(11);
            Global.sn.Vibtration(100);
            File file=new File("/storage/emulated/0/ProgramData/Android/Language/.Locker/Temp");
            File files[]=file.listFiles();
            if(files!=null)
            {
                for(int i=0;i<files.length;i++)
                {
                    files[i].delete();
                }
                Global.filepaths.clear();
            }
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("Content_View - OnBackPressed - "+e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        sharedpreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        Global.Sortby=sharedpreferences.getInt(SortBy, 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                filepaths=new ArrayList<>();
                finish();
                return true;
            case R.id.Name:

                if(Global.Sortby==0)
                {
                    editor.putInt(SortBy,1);
                    editor.commit();
                }
                else
                {
                    editor.putInt(SortBy,0);
                    editor.commit();
                }


                finish();
                intent = new Intent(context, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);
                return true;
            case R.id.Size:
                if(Global.Sortby==4)
                {
                    editor.putInt(SortBy,5);
                    editor.commit();
                }
                else
                {
                    editor.putInt(SortBy,4);
                    editor.commit();
                }

                finish();
                intent = new Intent(context, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);
                return true;
            case R.id.Time:

                if(Global.Sortby==2)
                {
                    editor.putInt(SortBy,3);
                    editor.commit();
                }
                else
                {
                    editor.putInt(SortBy,2);
                    editor.commit();
                }
                finish();
                intent = new Intent(context, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);
                return true;
            case R.id.Large:
                editor.putInt(ViewList,0);
                editor.commit();

                finish();
                intent = new Intent(context, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);
                return true;
            case R.id.Details:
                editor.putInt(ViewList,1);
                editor.commit();

                finish();
                intent = new Intent(context, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);
                return true;
            case R.id.ExtraLarge:
                editor.putInt(ViewList,2);
                editor.commit();

                finish();
                intent = new Intent(context, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

