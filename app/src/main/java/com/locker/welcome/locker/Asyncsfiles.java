package com.locker.welcome.locker;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
import android.os.Build.*;

import com.locker.welcome.locker.Dialog.ToastDialogs;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.locker.welcome.locker.Asyncsfiles.context;
import static com.locker.welcome.locker.Dialog.Chooseaction.index;
import static com.locker.welcome.locker.Global.Filetypes;
import static com.locker.welcome.locker.Global.Key_No;
import static com.locker.welcome.locker.Global.MainPath;
import static com.locker.welcome.locker.Global.Pin_Enable;
import static com.locker.welcome.locker.Global.byte_data;
import static com.locker.welcome.locker.Global.determinefolder;
import static com.locker.welcome.locker.Global.lock_byte_data;
import static com.locker.welcome.locker.Global.mypreference;
import static com.locker.welcome.locker.Global.progressDialog;
import static com.locker.welcome.locker.Global.sharedpreferences;
import static com.locker.welcome.locker.Global.sn;
import static com.locker.welcome.locker.Tools.directoryinitialize;
import static com.locker.welcome.locker.Tools.getfilename;

/**
 * Created by welcome on 30-May-20.
 */

public class Asyncsfiles {
    public static Activity activity;
    public static Context context;
    public static String foldername;
    public static String filepath;
    public static boolean unlockfile=false;

    Asyncsfiles(Activity activity,Context context)
    {
        this.activity=activity;
        this.context=context;

        Tools.getInstance().foldercreate();
        directoryinitialize();
    }
    public static class lockfiles extends AsyncTask<String,  String, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            String filename;

            try
            {
                Tools.getInstance().Testlog("Asyncsfiles - lockfiles - doInBackground");
                //sharedpreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                //filename=getfilename(Uri.parse(params[0]).getPath());

                filename=Tools.getInstance().GetFilename(new File(Uri.parse(params[0]).getPath()));
                //filename=filename+Tools.getInstance().GetExtension(filename);
                if(ToastDialogs.getInstance().EnterFileName(context,filename,1)==0)
                {
                    Thread.sleep(100);
                    return 0;
                }
                if(Global.UserEnterFilename.length()>25)
                {
                    Global.UserEnterFilename=Global.UserEnterFilename.substring(Global.UserEnterFilename.length()-25-1,Global.UserEnterFilename.length());
                }

                if(ToastDialogs.getInstance().Dialogs(2,"Are you Sure Unlock File?",context,1)==0)
                {
                    Thread.sleep(100);
                    return 0;
                }

                if(determinefolder==1)
                {
                    foldername="Images";
                }
                if(determinefolder==2)
                {
                    foldername="Videos";
                }
                if(determinefolder==3)
                {
                    foldername="Audios";
                }
                if(determinefolder==4)
                {
                    foldername="Others";
                }
                int size=GetFileSize(Uri.parse(params[0]));
                if(size==0)
                {
                    ToastDialogs.getInstance().Dialogs(1,"Invalid File!!",context,1);
                    return 0;
                }

                if(size<=1024*1024*40)
                {
                    byte_data = uritobytearray(Uri.parse(params[0]));
                    if(byte_data==null)
                    {
                    ToastDialogs.getInstance().Dialogs(1,"Invalid File!!",context,1);
                    return 0;
                    }

                    if(sharedpreferences.getInt(Pin_Enable, 0)==1)
                    {
                        Global.lock_byte_data=Tools.getInstance().PackingLockData(Global.byte_data,Global.UserEnterFilename,Global.pin,true,determinefolder,context,sharedpreferences.getInt(Key_No, 0));
                    }
                    else if(sharedpreferences.getInt(Pin_Enable, 0)==0)
                    {
                        Global.lock_byte_data=Tools.getInstance().PackingLockData(Global.byte_data,Global.UserEnterFilename,"",false,determinefolder,context,sharedpreferences.getInt(Key_No, 0));
                    }

                    if (lock_byte_data != null) {
                        try {
                            OutputStream fout = new FileOutputStream(Global.MainPath + foldername +"/"+"Lockfile"+sharedpreferences.getInt(Key_No, 0)+"."+"Enc");
                            fout.write(lock_byte_data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    long Deviceavailablestorage=Tools.getInstance().GetAvailableInternalMemory();

                    if(Deviceavailablestorage<size)
                    {
                        ToastDialogs.getInstance().Dialogs(1,"Internal Memory Low!!!",context,1);
                        return 0;
                    }

                    int result=0;
                    if(sharedpreferences.getInt(Pin_Enable, 0)==1)
                    {
                        result=LargeFilePacking(Uri.parse(params[0]),Global.UserEnterFilename,size,Global.pin,true,determinefolder,context,sharedpreferences.getInt(Key_No, 0));
                    }
                    else if(sharedpreferences.getInt(Pin_Enable, 0)==0)
                    {
                        result=LargeFilePacking(Uri.parse(params[0]),Global.UserEnterFilename,size,"",false,determinefolder,context,sharedpreferences.getInt(Key_No, 0));
                    }
                    if(result==-1)
                    {
                        ToastDialogs.getInstance().Dialogs(1,"Error When File Processing Try Again",context,1);
                        return 0;
                    }
                }

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(Key_No,sharedpreferences.getInt(Key_No, 0)+1);
                editor.commit();

                ToastDialogs.getInstance().Dialogs(3,"Successfully Locked File!!",context,1);
                Tools.getInstance().Testlog("Asyncsfiles - lockfiles - doInBackground - Successfully Locked File");
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - lockfiles - doInBackground - "+e.toString());
            }
            return 0;
        }

        @Override
        protected void onPreExecute() {
            try
            {
                Tools.getInstance().Testlog("Asyncsfiles - lockfiles - onPreExecute");
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - lockfiles - onPreExecute - "+e.toString());
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            try
            {
                Tools.getInstance().Testlog("Asyncsfiles - lockfiles - onPostExecute");
                progressDialog.dismiss();
                String classname=activity.getClass().getSimpleName();
                if(classname.equals("Passwordenter"))
                {
                    Global.pin="";
                    activity.finish();
                }
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - lockfiles - onPostExecute - "+e.toString());
            }
            super.onPostExecute(integer);
        }
    }

    public static class MenuList extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... Param) {
            try
            {
                Tools.getInstance().Testlog("Asyncsfiles - MenuList - doInBackground");
                Intent intent;
                int Ret = 0;
                if (Param[0] == 0) {
                    intent = new Intent(context, LockedFiles.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    context.startActivity(intent);
                }
                if (Param[0] == 1) {
                    intent = new Intent(context, UnlockFiles.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    context.startActivity(intent);
                }
                if (Param[0] == 2) {
                    intent = new Intent(context, ChangePassword.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    context.startActivity(intent);
                }
                if (Param[0] == 3) {
                    intent = new Intent(context, Setting.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    context.startActivity(intent);
                }
                if (Param[0] == 7) {
                    //   Ret=ToastDialogs.getInstance().Dialogautoclose(2,"Are You Sure Exit?",context,1);
                    if (Ret == 1) {
                        System.exit(0);
                    } else {
                        return 0;
                    }
                }
                return Ret;
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - MenuList - doInBackground - "+e.toString());
            }
            return 0;
        }

        @Override
        protected void onPreExecute() {
            try {
                Tools.getInstance().Testlog("Asyncsfiles - MenuList - onPreExecute");
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Processing...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - MenuList - onPreExecute - "+e.toString());
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            try
            {
                Tools.getInstance().Testlog("Asyncsfiles - MenuList - onPostExecute");
                progressDialog.dismiss();
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - MenuList - onPostExecute - "+e.toString());
            }
            super.onPostExecute(integer);
        }
    }

    public static class Unlock extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... Param) {
            try {
                Tools.getInstance().Testlog("Asyncsfiles - Unlock - doInBackground");
                FileOutputStream fout=null;
                int Ret = 0;
                Ret=ToastDialogs.getInstance().Dialogs(2,"Are you Sure Unlock File?",context,1);
                if(Ret!=1)
                {
                    return 0;
                }
                unlockfile=true;
                File file = new File("/storage/sdcard0/Locker");
                if (file.exists() == false) {
                    file.mkdir();
                }
                /*try {
                    filepath="/storage/sdcard0/Locker/"+Global.filereldatalist.get(Param[0]).FileName;
                    fout = new FileOutputStream(filepath);
                    fout.write(Global.filereldatalist.get(Param[0]).filebytes);
                    fout.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


                String FileType;
                File file1=new File(Global.filepaths.get(index));
                file1.delete();

                FileType=Tools.getInstance().GetFiletype(Global.filepaths.get(index));

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

                File file2=new File("/storage/emulated/0/ProgramData/Android/Language/.Locker/"+FileType);
                File files[]=file2.listFiles();

                for(int i=0;i<files.length;i++)
                {
                    Global.filepaths.add(files[i].getPath());
                }

                Intent intent;
                intent = new Intent(context, Content_View.class);
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);


                return Ret;
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - Unlock - doInBackground - "+e.toString());
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            try
            {
                Tools.getInstance().Testlog("Asyncsfiles - Unlock - onPostExecute");
                sn=new SoundFile(context);
                if(unlockfile==true)
                {
                    unlockfile=false;
                    sn.NotificationSound(11);
                    Toast.makeText(context, "File Unlocked Successfully!!"+" "+filepath, Toast.LENGTH_SHORT).show();
                    Tools.getInstance().Testlog("Asyncsfiles - Unlock - onPostExecute - File Unlocked Successfully "+filepath);
                }
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - Unlock - onPostExecute - "+e.toString());
            }
        }
    }

    public static int LargeFilePacking(Uri data,String filename,long size,String pin,boolean pinenable,int filetype,Context context,int fileindex) {
        try {
            int ptr=0;
            ByteArrayOutputStream stream;
            byte[] thumbnail=null;
            OutputStream fout;

            //Header Packing

            //Get Thumbnail
            if(filetype==0)
            {
                Bitmap otherbitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.images);
                stream=new ByteArrayOutputStream();
                otherbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                thumbnail= stream.toByteArray();
            }
            else if(filetype==1)
            {
                Bitmap otherbitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.videos);
                stream=new ByteArrayOutputStream();
                otherbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                thumbnail= stream.toByteArray();
            }
            else if(filetype==2)
            {
                Bitmap otherbitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.audios);
                stream=new ByteArrayOutputStream();
                otherbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                thumbnail= stream.toByteArray();
            }
            else if(filetype==3)
            {
                Bitmap otherbitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.otherfiles);
                stream=new ByteArrayOutputStream();
                otherbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                thumbnail= stream.toByteArray();
            }

            String Header=Tools.getInstance().Str_Padding(String.valueOf(filename.length()),"0",2,Global.Left)+filename+Tools.getInstance().Str_Padding(String.valueOf(String.valueOf(size).length()),"0",2,Global.Left)+String.valueOf(size)+Tools.getInstance().Str_Padding(String.valueOf(String.valueOf(thumbnail.length).length()),"0",2,Global.Left)+String.valueOf(thumbnail.length);
            //byte[] header_data=new byte[Header.getBytes().length+14+thumbnail.length];
            byte[] header_data=new byte[1000*1000*5];
            header_data[0]=(byte)2;
            ptr=ptr+1;
            header_data[1]=(byte)fileindex;
            ptr=ptr+1;
            String Header_Length=Tools.getInstance().Str_Padding("10","0",2,Global.Left)+Tools.getInstance().Str_Padding(String.valueOf(Header.length()+12+thumbnail.length),"0",10,Global.Left);
            System.arraycopy(Header_Length.getBytes(),0,header_data,ptr,Header_Length.getBytes().length);
            ptr=ptr+Header_Length.getBytes().length;
            System.arraycopy(Header.getBytes(),0,header_data,ptr,Header.getBytes().length);
            ptr=ptr+Header.getBytes().length;
            System.arraycopy(thumbnail,0,header_data,ptr,thumbnail.length);

            //Write Header Data
            fout = new FileOutputStream(Global.MainPath+"/Temp/Header");
            fout.write(header_data);


            //Split Bigfile into smallfile from Uri data
            List<File> files=new ArrayList<File>();
            byte[] byte_data = null;
            byte[] encbyte_data=null;
            int len = 0;
                Tools.getInstance().Testlog("Asyncsfiles - uritobytearray");
                ContentResolver cr = activity.getBaseContext().getContentResolver();
                InputStream inputStream = null;
                inputStream = cr.openInputStream(data);
                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                int buffersize = 1000*1000*20;
                byte[] buffer = new byte[buffersize];
                File f=new File(Global.MainPath+"/Temp/TempFile");
                int partCounter=1;
                int bytesAmount=0;

                while((bytesAmount=inputStream.read(buffer))!=-1)
                {
                    String filepartname=String.format("%s.%03d",f.getName(),partCounter++);
                    File newFile=new File(f.getParent(),filepartname);
                    files.add(newFile);
                    FileOutputStream out=new FileOutputStream(newFile);
                    out.write(buffer,0,bytesAmount);
                }

                //Encrypt
            File t=new File(Global.MainPath+"/Temp");
            File[] listoffiles=t.listFiles();
            byte[] packbytedata=null;

            for(int i=0;i<listoffiles.length;i++)
            {
                ptr=0;
                File encf=new File(listoffiles[i].toString());
                byte_data=new byte[(int)encf.length()];

                byte_data=Tools.getInstance().ReadByteDataFromFile(encf);

                //Load Bytedata Length
                packbytedata=new byte[byte_data.length+100];

                if(pinenable==true)
                {
                    encbyte_data=Tools.getInstance().encrypt(byte_data,pin);
                }
                else if(pinenable==false)
                {
                    encbyte_data=Tools.getInstance().encrypt(byte_data,"00000");
                }

                //Packing Data
                String datalength=Tools.getInstance().Str_Padding(String.valueOf(String.valueOf(encbyte_data.length).length()),"0",2,Global.Left)+String.valueOf(encbyte_data.length)+Tools.getInstance().Str_Padding(String.valueOf(String.valueOf(byte_data.length).length()),"0",2,Global.Left)+String.valueOf(byte_data.length);
                System.arraycopy(datalength.getBytes(),0,packbytedata,ptr,datalength.getBytes().length);

                ptr=ptr+datalength.getBytes().length;
                System.arraycopy(encbyte_data,0,packbytedata,ptr,encbyte_data.length);

                //Write Packing Data
                fout = new FileOutputStream(listoffiles[i]);
                fout.write(packbytedata);
            }


            //Get Increment No

             if(packbytedata!=null)
             {
                 //Merge
                 FileOutputStream fos;
                 FileInputStream fis=null;
                 byte[] fileBytes;
                 int bytesRead=0;
                 File ofile=new File(Global.MainPath +  "/" + foldername +"/"+"Lockfile"+sharedpreferences.getInt(Key_No, 0)+"."+"Enc");

                 fos = new FileOutputStream(ofile,true);
                 for(File file:files)
                 {
                     fis=new FileInputStream(file);
                     fileBytes=new byte[(int)file.length()];
                     bytesRead=fis.read(fileBytes,0,(int)file.length());;
                     assert(bytesRead==fileBytes.length);
                     assert(bytesRead==(int)file.length());
                     fos.write(fileBytes);
                     fos.flush();
                     fileBytes=null;
                 }
                 fis.close();
                 fis=null;
                 fos.close();
                 fos=null;
             }

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(Key_No,sharedpreferences.getInt(Key_No, 0)+1);
            editor.commit();


            //Delete Splitted File
            for(File file:listoffiles)
            {
                file.delete();
            }

            /*//Read File
            File file=new File("/storage/emulated/0/Temp/video.mp4");
            int size=(int)file.length();
            byte_data=new byte[size];
            BufferedInputStream buf=new BufferedInputStream(new FileInputStream(file));
            buf.read(byte_data,0,byte_data.length);
            buf.close();*/

        } catch (Exception e) {
            Tools.getInstance().Errorlog("Asyncsfiles - uritobytearray - "+e.toString());
            return -1;
        }
        return 0;
    }

    public static class LockorUnlock extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... Param) {
            try
            {
                Tools.getInstance().Testlog("Asyncsfiles - LockorUnlock - doInBackground");
                if(Global.lockorunlock==0)
                {
                    ToastDialogs.getInstance().ChooseAction(0,Param[0],context,activity,1);
                }
                else if(Global.lockorunlock==1)
                {
                    ToastDialogs.getInstance().ChooseAction(1,Param[0],context,activity,1);
                }
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - LockorUnlock - doInBackground"+e.toString());
            }
            return 0;
        }
    }

    public static class ClearAppData extends AsyncTask<Integer,Integer,Integer>
    {
        @Override
        protected Integer doInBackground(Integer... params) {
            try
            {
                File folder;

                for(int i=0;i<5;i++)
                {
                    folder=new File(MainPath + Filetypes[i]);
                    if(folder.exists()==true)
                    {
                        File files[]=folder.listFiles();

                        for(int j=0;j<=files.length-1;j++)
                        {
                            files[j].delete();
                        }
                    }
                }
                ToastDialogs.getInstance().Dialogs(3,"Application Cleared Succesfully!!",context,0);
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - ClearAppData - doInBackground"+e.toString());
            }
            return null;
        }
    }

    public static class ResetAppData extends AsyncTask<Integer,Integer,Integer>
    {
        @Override
        protected Integer doInBackground(Integer... params) {
            try
            {
                if(ToastDialogs.getInstance().Dialogs(2,"Are you sure Reset App? If you reset app,your data willbe lost",context,1)==1)
                {
                    if(VERSION_CODES.KITKAT<=VERSION.SDK_INT)
                    {
                        ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
                    }
                }
            }
            catch (Exception e)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - ResetAppData - doInBackground"+e.toString());
            }
            return null;
        }
    }

    public static class PositiveMsgDialog extends AsyncTask<String,  String, Integer>
    {
        @Override
        protected Integer doInBackground(String... params) {
            try
            {
                ToastDialogs.getInstance().Dialogs(3,params[0],context,0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class NegativeMsgDialog extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {
            try
            {
                ToastDialogs.getInstance().Dialogs(1,params[0],context,0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class ConfirmationMsgDialog extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {
            try
            {
                ToastDialogs.getInstance().Dialogs(2,params[0],context,0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class Exit extends AsyncTask<Integer,Integer,Integer>
    {
        @Override
        protected Integer doInBackground(Integer... params) {
            try
            {
                if(ToastDialogs.getInstance().Dialogs(2,"Are you sure exit?",context,1)==0)
                {
                    return 0;
                }
                else
                {
                    activity.finish();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static int GetFileSize(Uri data) {
        try {
            Tools.getInstance().Testlog("Asyncsfiles - uritobytearray");
            ContentResolver cr = activity.getBaseContext().getContentResolver();
            InputStream inputStream = null;
            inputStream = cr.openInputStream(data);
            int size=inputStream.available();
            return size;
        } catch (Exception e) {
            Tools.getInstance().Errorlog("Asyncsfiles - uritobytearray - "+e.toString());
            return 0;
        }
    }

    public static byte[] uritobytearray(Uri data) {
        byte[] byte_data = null;
        int len = 0;
        try {
            try
            {
                Tools.getInstance().Testlog("Asyncsfiles - uritobytearray");
                ContentResolver cr = activity.getBaseContext().getContentResolver();
                InputStream inputStream = null;
                inputStream = cr.openInputStream(data);
                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                int buffersize = 1024;
                byte[] buffer = new byte[buffersize];

                while ((len = inputStream.read(buffer)) != -1) {
                    byteBuffer.write(buffer, 0, len);
                }
                long size=byteBuffer.size();
                System.gc();
                byte_data = byteBuffer.toByteArray();
            }
            catch (OutOfMemoryError error)
            {
                Tools.getInstance().Errorlog("Asyncsfiles - uritobytearray - "+error.toString());
            }
        } catch (Exception e) {
            Tools.getInstance().Errorlog("Asyncsfiles - uritobytearray - "+e.toString());
        }
        return byte_data;
    }
}
