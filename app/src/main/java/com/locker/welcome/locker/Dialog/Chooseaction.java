package com.locker.welcome.locker.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.locker.welcome.locker.Aboutfile;
import com.locker.welcome.locker.Asyncsfiles;
import com.locker.welcome.locker.Content_View;
import com.locker.welcome.locker.Dialog.ToastDialogs;
import com.locker.welcome.locker.Global;
import com.locker.welcome.locker.LockedFiles;
import com.locker.welcome.locker.Menu;
import com.locker.welcome.locker.R;
import com.locker.welcome.locker.SoundFile;
import com.locker.welcome.locker.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Array;

import static com.locker.welcome.locker.Asyncsfiles.context;
import static com.locker.welcome.locker.Asyncsfiles.foldername;
import static com.locker.welcome.locker.Global.Key_No;
import static com.locker.welcome.locker.Global.Pin_Enable;
import static com.locker.welcome.locker.Global.byte_data;
import static com.locker.welcome.locker.Global.filereldatalist;
import static com.locker.welcome.locker.Global.sharedpreferences;
import static com.locker.welcome.locker.Global.sn;

/**
 * Created by Justin on 17-06-2020.
 */

public class Chooseaction extends Dialog implements View.OnClickListener {
    Button btnview,btnlockorunlock,btndetail,btndelete,btnexit;
    Context context;
    public static int type,index;
    MsgShowListener listener;
    Handler handler;
    boolean isBtnClicked = false;
    Activity activity;

    public Chooseaction(Activity activity,Context context,int type,int index,MsgShowListener listener) {
        super(context);
        this.activity=activity;
        this.context=context;
        this.type=type;
        this.index=index;
        this.listener = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chooseaction);

        try
        {
            Tools.getInstance().Testlog("Chooseaction - initializing");
            sn=new SoundFile(context);
            btnview=(Button)findViewById(R.id.btnviewfile);
            btnlockorunlock=(Button)findViewById(R.id.btnlockorunlock);
            btndelete=(Button)findViewById(R.id.btndeletefile);
            btndetail=(Button)findViewById(R.id.btndetailfile);
            btnexit=(Button)findViewById(R.id.btnexit);

            setCanceledOnTouchOutside(false);

            handler = new Handler(Looper.getMainLooper());

            if(type==0)
            {
                btnlockorunlock.setText("Lock");
            }
            else if(type==1)
            {
                btnlockorunlock.setText("UnLock");
            }

            btnview.setOnClickListener(this);
            btnlockorunlock.setOnClickListener(this);
            btndetail.setOnClickListener(this);
            btndelete.setOnClickListener(this);
            btnexit.setOnClickListener(this);
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("Chooseaction - Oncreate - "+e.toString());
        }
    }
    @Override
    public void onClick(View v) {
        try
        {
            sn.NotificationSound(11);
            Global.sn.Vibtration(100);
            if(v==btnview)
            {
                Tools.getInstance().Testlog("Chooseaction - User select View Option");
                if (type == 0) {
                        File file=new File(Global.MainPath + foldername +"/"+"Lockfile"+index+"."+"Enc");
                        if(file.exists()==false)
                        {
                            Toast.makeText(context, "File Cannot Create", Toast.LENGTH_SHORT).show();
                        }

                        if(Integer.parseInt(filereldatalist.get(index).Filelength)<=1024*1024*40)
                        {
                            if(Tools.getInstance().UnPackData(file,index)==-1)
                            {
                                Toast.makeText(context, "Operation Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{

                        }

                        File temp=new File(Global.MainPath + "Temp/temp");
                        //File file=new File(String.valueOf(tempfiles[index]));
                        Uri path = Uri.fromFile(file);
                        Intent Openintent = new Intent(Intent.ACTION_VIEW);
                        Openintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        String fileextension=Tools.getInstance().GetExtension(file);

                        if(fileextension.equals("jpg")||fileextension.equals("png")||fileextension.equals("gif"))
                        {
                            Openintent.setDataAndType(path, "image/*");
                        }
                        if(fileextension.equals("mp3")||fileextension.equals("wav"))
                        {
                            Openintent.setDataAndType(path, "audio/mp3");
                        }
                        if(fileextension.equals("pdf"))
                        {
                            Openintent.setDataAndType(path, "application/pdf");
                        }
                        if(fileextension.equals("txt"))
                        {
                            Openintent.setDataAndType(path, "text/*");
                        }
                        if(fileextension.equals("mp4"))
                        {
                            Openintent.setDataAndType(path, "application/mp4");
                        }
                    context.startActivity(Openintent);

                    if (!isBtnClicked) {
                        isBtnClicked =true;
                        listener.onErr();
                        dismiss();
                        cancel();
                    }

                } else if (type == 1)
                {
                        File temp=new File("/storage/emulated/0/ProgramData/Android/Language/.Locker/Temp");
                        File tempfiles[]=temp.listFiles();

                        File file=new File(String.valueOf(tempfiles[index]));
                        Uri path = Uri.fromFile(file);
                        Intent Openintent = new Intent(Intent.ACTION_VIEW);
                        Openintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        String fileextension= Tools.getInstance().GetExtension(String.valueOf(tempfiles[index]));
                        if(fileextension.equals("jpg")||fileextension.equals("png")||fileextension.equals("gif"))
                        {
                            Openintent.setDataAndType(path, "image/*");
                        }
                        if(fileextension.equals("mp3")||fileextension.equals("wav"))
                        {
                            Openintent.setDataAndType(path, "audio/mp3");
                        }
                        if(fileextension.equals("pdf"))
                        {
                            Openintent.setDataAndType(path, "application/pdf");
                        }
                        if(fileextension.equals("txt"))
                        {
                            Openintent.setDataAndType(path, "text/*");
                        }
                        if(fileextension.equals("mp4"))
                        {
                            Openintent.setDataAndType(path, "application/mp4");
                        }
                          context.startActivity(Openintent);
                        if (!isBtnClicked) {
                            isBtnClicked =true;
                            listener.onErr();
                            dismiss();
                            cancel();
                        }
                }

            }
            if(v==btnlockorunlock)
            {
                Tools.getInstance().Testlog("Chooseaction - user Select Lock Or Unlock Option");
                if (!isBtnClicked) {
                    isBtnClicked =true;
                    listener.onSucc();
                    dismiss();
                    cancel();
                }
            }
            if(v==btndetail)
            {
                Tools.getInstance().Testlog("Chooseaction - User select Detail Option");
                Aboutfile dialog=new Aboutfile(context,index);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                if (!isBtnClicked) {
                    isBtnClicked =true;
                    listener.onErr();
                    dismiss();
                    cancel();
                }
            }
            if(v==btndelete)
            {
                Tools.getInstance().Testlog("Chooseaction - user select Delete Option");
                String FileType;

                File file=new File(Global.MainPath + Tools.getInstance().GetFolderName(Global.iswhichfile) +"/"+"Lockfile"+Global.filereldatalist.get(index).FileIndex+"."+"Enc");
                file.delete();

                FileType=Tools.getInstance().GetFiletype(Global.filepaths.get(index));

                filereldatalist.remove(index);

                activity.finish();

                if(FileType.equals("Images")) {
                    Global.iswhichfile = 0;
                }
                else if(FileType.equals("Videos")) {
                    Global.iswhichfile = 1;
                }
                else if(FileType.equals("Audios")) {
                    Global.iswhichfile = 2;
                }
                else if(FileType.equals("Others")) {
                    Global.iswhichfile = 3;
                }

                Global.filepaths.clear();


                File file1=new File("/storage/emulated/0/ProgramData/Android/Language/.Locker/"+FileType);
                File files[]=file1.listFiles();

                for(int i=0;i<files.length;i++)
                {
                    Global.filepaths.add(files[i].getPath());
                }

                Intent intent;
                intent = new Intent(context, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);

                if (!isBtnClicked) {
                    isBtnClicked =true;
                    listener.onErr();
                    dismiss();
                    cancel();
                }
            }
            if(v==btnexit)
            {
                Tools.getInstance().Testlog("Chooseaction - User Select Exit Option");
                if (!isBtnClicked) {
                    isBtnClicked =true;
                    listener.onErr();
                    dismiss();
                    cancel();
                }
            }
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("Chooseaction - OnClick - "+e.toString());
        }
    }
}