package com.locker.welcome.locker;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import android.provider.MediaStore.Video.Thumbnails;
import android.widget.Toast;

import com.locker.welcome.locker.Dialog.ToastDialogs;

import org.w3c.dom.NameList;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes;

import static com.locker.welcome.locker.Global.Home_Pin;
import static com.locker.welcome.locker.Global.Key_No;
import static com.locker.welcome.locker.Global.MainPath;
import static com.locker.welcome.locker.Global.filereldatalist;
import static com.locker.welcome.locker.Global.mypreference;
import static com.locker.welcome.locker.Global.sharedpreferences;
import static com.locker.welcome.locker.HomePasswordEnter.context;
import static com.locker.welcome.locker.Tools.ReadByteDataFromFile;
import static com.locker.welcome.locker.Tools.arraycopy;
import static com.locker.welcome.locker.Tools.getmp3thumbnail;
import static java.util.jar.Pack200.Packer.ERROR;

public class Sample extends AppCompatActivity {
    byte[] data=null;
    static Activity activity;
    long length = 0;
    static Context context;
    ImageView imageView,imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        activity = this;
        context = this;

        imageView = (ImageView) findViewById(R.id.imgview);
        imageView1 = (ImageView) findViewById(R.id.imgview1);

        File f=new File(Global.MainPath+"/Videos/Lockfile19.Enc");
        try
        {
            int partCounter=1;
            int sizeofFiles=1024*1024;
            byte[] buffer=new byte[sizeofFiles];

            String fileName=f.getName();

            try(FileInputStream fis=new FileInputStream(f);
                BufferedInputStream bis=new BufferedInputStream(fis)){
                int bytesAmount=0;
                while((bytesAmount=bis.read(buffer))>0)
                {
                    String filePartName=String.format("%s.%03d",fileName,partCounter++);
                    File newFile=new File(f.getParent(),filePartName);
                    FileOutputStream fout=new FileOutputStream(newFile);
                    fout.write(buffer,0,bytesAmount);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public static byte[] UnPackData(File file)
    {
        int ptr=0,len=0;
        byte[] length=null,temp=null,FileNamebyte=null,FileLengthbyte=null,Thumblengthbyte=null,Thumbnailbyte=null,HeaderLengthbyte=null;
        byte encrypteddata[]=ReadByteDataFromFile(file);
        int fileindex;
        String FileName,FileLength,Thumblength,HeaderLength;
        ByteArrayOutputStream stream;
        byte bytearray[]=null;


        if(encrypteddata[0]!=2 && encrypteddata[encrypteddata.length]!=3)
        {
            return null;
        }

        ptr=ptr+1;

        //GetFileIndex
        fileindex=encrypteddata[ptr];

        ptr=ptr+1;

        length=new byte[2];

        //GetHeaderLength
        arraycopy(encrypteddata,ptr,2,length,0);
        ptr=ptr+2;
        len=Integer.parseInt(Tools.getInstance().BytetoString(length));
        HeaderLengthbyte=new byte[len];
        arraycopy(encrypteddata,ptr,len,HeaderLengthbyte,0);
        ptr=ptr+HeaderLengthbyte.length;
        HeaderLength=Tools.getInstance().BytetoString(HeaderLengthbyte);
        HeaderLength=Tools.getInstance().Trimstart(HeaderLength,"0");

        //GetFileName
        arraycopy(encrypteddata,ptr,2,length,0);
        ptr=ptr+2;
        len=Integer.parseInt(Tools.getInstance().BytetoString(length));
        FileNamebyte=new byte[len];
        arraycopy(encrypteddata,ptr,len,FileNamebyte,0);
        ptr=ptr+FileNamebyte.length;
        FileName=Tools.getInstance().BytetoString(FileNamebyte);

        //GetFileLength
        arraycopy(encrypteddata,ptr,2,length,0);
        ptr=ptr+2;
        len=Integer.parseInt(Tools.getInstance().BytetoString(length));
        FileLengthbyte=new byte[len];
        arraycopy(encrypteddata,ptr,len,FileLengthbyte,0);
        ptr=ptr+FileLengthbyte.length;
        FileLength=Tools.getInstance().BytetoString(FileLengthbyte);

        //GetThumbnailLength
        arraycopy(encrypteddata,ptr,2,length,0);
        ptr=ptr+2;
        len=Integer.parseInt(Tools.getInstance().BytetoString(length));
        Thumblengthbyte=new byte[len];
        arraycopy(encrypteddata,ptr,len,Thumblengthbyte,0);
        ptr=ptr+Thumblengthbyte.length;
        Thumblength=Tools.getInstance().BytetoString(Thumblengthbyte);

        //GetThumbnail
        Thumbnailbyte=new byte[Integer.parseInt(Thumblength)];
        arraycopy(encrypteddata,ptr,Integer.parseInt(Thumblength),Thumbnailbyte,0);

        return null;
    }
}