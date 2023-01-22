package com.locker.welcome.locker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.locker.welcome.locker.Global.MainPath;
import static com.locker.welcome.locker.Global.iswhichfile;
import static com.locker.welcome.locker.Tools.GetExtension;

public class Aboutfile extends Dialog {
    public ImageView img;
    public TextView txt_filename,txt_filesize,txt_status,txt_usingpin,txt_modifiedDate,txt_duration,lbl_duration;
    public int index;
    public Context context;
    OutputStream temp;
    Bitmap imgbitmap;

    public Aboutfile(Context context,int index) {
        super(context);
        this.index=index;
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_aboutfile);

        try
        {
            Tools.getInstance().Testlog("Aboutfile - initializing");
            img=(ImageView)findViewById(R.id.img);
            txt_filename=(TextView)findViewById(R.id.txtfilename);
            txt_filesize=(TextView)findViewById(R.id.txtfilesize);
            txt_usingpin=(TextView)findViewById(R.id.txtpin);
            txt_status=(TextView)findViewById(R.id.txtfilestatus);
            txt_modifiedDate=(TextView)findViewById(R.id.txtfilelockeddate);
            txt_duration=(TextView)findViewById(R.id.txtduration);
            lbl_duration=(TextView)findViewById(R.id.lblduration);

            if(Global.filereldatalist != null)

                //Thumbnail Create
                temp=new FileOutputStream(Global.MainPath + "Temp/temp");
                temp.write(Global.filereldatalist.get(index).thumbnail);

                //Set Thumbnail ImageView
                imgbitmap = BitmapFactory.decodeFile(MainPath+"Temp/temp");
                img.setImageBitmap(imgbitmap);

                txt_filename.setText(Global.filereldatalist.get(index).FileName);
                txt_filesize.setText(Tools.getInstance().formatSize(Long.parseLong(Global.filereldatalist.get(index).Filelength)));
                txt_modifiedDate.setText(Tools.getInstance().GetFormatDateFromLastModified(Long.parseLong(Global.filereldatalist.get(index).LastModified),1));

                if(iswhichfile==1||iswhichfile==2)
                {

                }
                else if(iswhichfile==0||iswhichfile==3)
                {
                    lbl_duration.setVisibility(View.GONE);
                    txt_duration.setVisibility(View.GONE);
                }

                /*if (Tools.getInstance().GetExtension(Global.filereldatalist.get(index).FileName).equals("mp4") || Tools.getInstance().GetExtension(Global.filereldatalist.get(index).FileName).equals("avi") || Tools.getInstance().GetExtension(Global.filereldatalist.get(index).FileName).equals("mkv") || Tools.getInstance().GetExtension(Global.filereldatalist.get(index).FileName).equals("mp3") || Tools.getInstance().GetExtension(Global.filereldatalist.get(index).FileName).equals("wav")) {
                    lbl_duration.setVisibility(View.VISIBLE);
                    txt_duration.setVisibility(View.VISIBLE);
                    File file1=new File("/storage/sdcard0/ProgramData/Android/Language/.Locker/Temp/"+"temp"+index+"."+Tools.getInstance().GetExtension(Global.filereldatalist.get(index).FileName));
                    MediaMetadataRetriever retriever=new MediaMetadataRetriever();
                    retriever.setDataSource(context, Uri.fromFile(file1));
                    String time=retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    long timeMillisec=Long.parseLong(time);
                    String sec=String.valueOf((timeMillisec/1000)%60);
                    String min=String.valueOf((timeMillisec/1000)/60);
                    String hou=String.valueOf(((timeMillisec/1000)/60)/60);
                    txt_duration.setText(String.format(hou,"%.2d")+":"+ String.format(min,"%.2d" )+":"+ String.format(sec,"%.2d"));
                }
                else
                {
                    lbl_duration.setVisibility(View.GONE);
                    txt_duration.setVisibility(View.GONE);
                }*/
               Tools.getInstance().Testlog("Aboutfile - AboutfileViewed for FileName : "+Global.filereldatalist.get(index).FileName +" FileSize : "+Global.filereldatalist.get(index).Filelength+"bytes");
            }
        catch(Exception e)
        {
            Tools.getInstance().Errorlog("Aboutfile - OnCreate - "+e.toString());
        }
        }
    }
