package com.locker.welcome.locker;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.util.Output;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.ExpandableListView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.locker.welcome.locker.Global.MainPath;
import static com.locker.welcome.locker.Global.byte_data;
import static com.locker.welcome.locker.Global.filereldatalist;
import static com.locker.welcome.locker.Global.iswhichfile;
import static com.locker.welcome.locker.Global.lock_byte_data;
import static com.locker.welcome.locker.Global.pin;

/**
 * Created by welcome on 30-May-20.
 */

public class Tools {
    private static Tools tool;

    public static Tools getInstance() {
        if (tool == null) {
            tool = new Tools();
        }
        return tool;
    }
    public static String Getfilename(String path)
    {
        String filename[]=path.split("\\/");
        return filename[filename.length-1];
    }
    public static String getfilename(String uri)
    {
        String temp="",data="";
        for(int i=uri.length();i>0;i--)
        {
            temp=uri.substring(i-1,i);
            if(temp.equals("/"))
            {
                break;
            }
            data=temp+data;
        }
        if(data.contains(":"))
        {
            String datas[]=data.split(":");
            data=datas[1];
        }
        if(data.contains("jpg")||data.contains("png")||data.contains("gif")||data.contains("mp4")||data.contains("avi")||data.contains("mkv")||data.contains("mp3")||data.contains("wav"))
        {
            return data;
        }
        else
        {
            if(Global.determinefolder==1)
            {
                data=data + "." + "png";
            }
            if(Global.determinefolder==2)
            {
                data=data + "." + "mp4";
            }
            if(Global.determinefolder==3)
            {
                data=data + "." + "mp3";
            }
        }
        return data;
    }
    public static void directoryinitialize()
    {
        File file = new File("/storage/emulated/0/ProgramData/Android/Language/.Locker/Images");
        if (file.exists() == false) {
            file.mkdir();
        }
        File file1 = new File("/storage/emulated/0/ProgramData/Android/Language/.Locker/Videos");
        if (file1.exists() == false) {
            file1.mkdir();
        }
        File file2 = new File("/storage/emulated/0/ProgramData/Android/Language/.Locker/Audios");
        if (file2.exists() == false) {
            file2.mkdir();
        }
        File file3 = new File("/storage/emulated/0/ProgramData/Android/Language/.Locker/Others");
        if (file3.exists() == false) {
            file3.mkdir();
        }
        File file4 = new File("/storage/emulated/0/ProgramData/Android/Language/.Locker/Temp");
        if (file4.exists() == false) {
            file4.mkdir();
        }
    }
    public static void foldercreate()
    {
        File folder0 = new File("/storage/emulated/0/ProgramData");
        if (folder0.exists() == false) {
            folder0.mkdir();
        }
        File folder1 = new File("/storage/emulated/0/ProgramData/Android");
        if (folder1.exists() == false) {
            folder1.mkdir();
        }
        File folder2 = new File("/storage/emulated/0/ProgramData/Android/Language");
        if (folder2.exists() == false) {
            folder2.mkdir();
        }
        File folder3 = new File("/storage/emulated/0/ProgramData/Android/Language/.Locker");
        if (folder3.exists() == false) {
            folder3.mkdir();
        }
    }

    public byte[] PackingLockData(byte[] byte_data,String filename,String pin,boolean pinenable,int filetype,Context context,int fileindex)
    {
        int ptr=0;
        try
        {
            if(byte_data==null)
            {
                return null;
            }
            byte[] thumbnail=GetThumbnailData(byte_data,filetype,context);
            if(thumbnail.length>=byte_data.length && iswhichfile==0)
            {
                thumbnail=new byte[byte_data.length];
                System.arraycopy(byte_data,0,thumbnail,0,byte_data.length);
            }
            String Header=Str_Padding(String.valueOf(filename.length()),"0",2,Global.Left)+filename+Str_Padding(String.valueOf(String.valueOf(byte_data.length).length()),"0",2,Global.Left)+String.valueOf(byte_data.length)+Str_Padding(String.valueOf(String.valueOf(thumbnail.length).length()),"0",2,Global.Left)+String.valueOf(thumbnail.length);
            byte[] header_data=new byte[Header.getBytes().length+14+thumbnail.length];
            header_data[0]=(byte)2;
            ptr=ptr+1;
            header_data[1]=(byte)fileindex;
            ptr=ptr+1;
            String Header_Length=Str_Padding("10","0",2,Global.Left)+Str_Padding(String.valueOf(Header.length()+12+thumbnail.length),"0",10,Global.Left);
            System.arraycopy(Header_Length.getBytes(),0,header_data,ptr,Header_Length.getBytes().length);
            ptr=ptr+Header_Length.getBytes().length;
            System.arraycopy(Header.getBytes(),0,header_data,ptr,Header.getBytes().length);
            ptr=ptr+Header.getBytes().length;
            System.arraycopy(thumbnail,0,header_data,ptr,thumbnail.length);

            ptr=0;

            byte[] Encdata=null;
            if(pinenable==true)
            {
                Encdata=encrypt(byte_data,pin);
            }
            else if(pinenable==false)
            {
                Encdata=encrypt(byte_data,"00000");
            }
            if(Encdata==null)
            {
                return null;
            }
            byte[] pack_data=new byte[byte_data.length+100];
            String length=String.valueOf(String.valueOf(Encdata.length).length());
            length=Tools.getInstance().Str_Padding(length,"0",2,Global.Left);
            System.arraycopy(length.getBytes(),0,pack_data,ptr,2);
            ptr=ptr+2;
            System.arraycopy(String.valueOf(Encdata.length).getBytes(),0,pack_data,ptr,String.valueOf(Encdata.length).getBytes().length);
            ptr=ptr+String.valueOf(Encdata.length).length();
            System.arraycopy(Encdata,0,pack_data,ptr,Encdata.length);
            ptr=ptr+Encdata.length;

            byte[] Data=new byte[header_data.length+pack_data.length+1];
            System.arraycopy(header_data,0,Data,0,header_data.length);
            System.arraycopy(pack_data,0,Data,header_data.length+1,pack_data.length);
            Data[header_data.length+Encdata.length+1]=0x03;
            return Data;

        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String paddingleft(String Str,char chr,int len)
    {
        String temp="";
        if(Str.length()>len)
        {
            Str=Str.substring(Str.length()-50,Str.length());
        }
        for(int i=Str.length();i<len;i++)
        {
            Str=chr + Str;
        }
        return Str;
    }
    public static byte[] Revdata(byte[] bytedata)
    {
        byte[] revbytedata=new byte[bytedata.length];
        int j=0;
        for(int i=bytedata.length-1;i>=0;i--)
        {
            revbytedata[j]=bytedata[i];
            j++;
        }
        return revbytedata;
    }
    public static byte[] emptybytes(byte[] bytedata)
    {
        for(int i=0;i<bytedata.length;i++)
        {
        byte_data[i]=0;
        }
        return byte_data;
    }

    public static void arraycopy(byte[] sourcebyte,int start,int length,byte[] destinationbyte,int start1)
    {
        try
        {
            for(int i=start;i<start+length;i++)
            {
                destinationbyte[start1]=sourcebyte[i];
                start1++;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static String BytetoString(byte[] data)
    {
        String dataOut = "";
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0x00)
                dataOut += (char)data[i];
        }
        return dataOut;
    }
    public static String Trimstart(String filename,String regex)
    {
        while(true)
        {
            if(filename.substring(0,1).equals(regex))
            {
                filename=filename.substring(1,filename.length());
            }
            else
            {
                break;
            }
        }
        return filename;
    }

    public static ArrayList<FileView> GetHeaderDetail(List<String> listOfFilePath)
    {
        try
        {
            String FileLength,FileName,Thumblength,LastModified;
            byte[] FileLengthbyte=null,FileNamebyte=null,Thumblengthbyte=null,length,Headerlengthbyte=null,Temp=null;
            byte[] Thumbnail=null;
            int ptr=0,len,fileindex,stx;
            String HeaderLength="";

            filereldatalist=new ArrayList<>();

            for(int i=0;i<=listOfFilePath.size()-1;i++)
            {
                File f=new File(listOfFilePath.get(i));
                if(f.length()>=1024*1024*40)
                {
                    continue;
                }

                //Get FileRelatedData From Encrypted File
                File file=new File(listOfFilePath.get(i));
                int size=(int)file.length();
                byte filedata[]=new byte[size];
                byte Headerdata[]=null;

                //Read FileFrom .Enc file
                filedata=Tools.getInstance().ReadByteDataFromFile(file);

                //Check Fileis Valid File if FileFirst Contain STX
                if(filedata[0]!=2 && filedata[filedata.length-1]!=3)
                {
                    continue;
                }

                ptr=0;
                //Skip STX,FileIndex
                ptr=ptr+2;

                //Get HeaderLength
                Temp=new byte[2];
                arraycopy(filedata,ptr,2,Temp,0);
                ptr=ptr+2;
                HeaderLength=Tools.getInstance().BytetoString(Temp);
                HeaderLength=Tools.getInstance().Trimstart(HeaderLength,"0");

                Headerlengthbyte=new byte[Integer.parseInt(HeaderLength)];
                arraycopy(filedata,ptr,Integer.parseInt(HeaderLength),Headerlengthbyte,0);
                ptr = ptr + Integer.parseInt(HeaderLength);
                HeaderLength=Tools.getInstance().BytetoString(Headerlengthbyte);
                HeaderLength=Tools.Trimstart(HeaderLength,"0");

                //Get HeaderData
                Headerdata=new byte[Integer.valueOf(HeaderLength)];
                arraycopy(filedata,0,Integer.parseInt(HeaderLength),Headerdata,0);

                ptr=0;

                //Skip STX
                ptr=ptr+1;

                //GetFileIndex
                fileindex=Headerdata[ptr];
                ptr=ptr+1;

                //Skip HeaderLength
                ptr=ptr+12;

                length=new byte[2];

                //GetFileName
                arraycopy(Headerdata,ptr,2,length,0);
                ptr=ptr+2;
                len=Integer.parseInt(Tools.getInstance().BytetoString(length));
                FileNamebyte=new byte[len];
                arraycopy(Headerdata,ptr,len,FileNamebyte,0);
                ptr=ptr+FileNamebyte.length;
                FileName=Tools.getInstance().BytetoString(FileNamebyte);

                //GetFileLength
                arraycopy(Headerdata,ptr,2,length,0);
                ptr=ptr+2;
                len=Integer.parseInt(Tools.getInstance().BytetoString(length));
                FileLengthbyte=new byte[len];
                arraycopy(Headerdata,ptr,len,FileLengthbyte,0);
                ptr=ptr+FileLengthbyte.length;
                FileLength=Tools.getInstance().BytetoString(FileLengthbyte);

                //GetThumbnailLength
                arraycopy(Headerdata,ptr,2,length,0);
                ptr=ptr+2;
                len=Integer.parseInt(Tools.getInstance().BytetoString(length));
                Thumblengthbyte=new byte[len];
                arraycopy(Headerdata,ptr,len,Thumblengthbyte,0);
                ptr=ptr+Thumblengthbyte.length;
                Thumblength=Tools.getInstance().BytetoString(Thumblengthbyte);

                //GetThumbnail
                Thumbnail=new byte[Integer.parseInt(Thumblength)];
                arraycopy(Headerdata,ptr,Integer.parseInt(Thumblength),Thumbnail,0);

                //GetLastModified
                File temp=new File(listOfFilePath.get(i));
                long lastmodified=temp.lastModified();
                LastModified=String.valueOf(lastmodified);

                filereldatalist.add(new FileView(FileName,Thumbnail,FileLength,LastModified,fileindex));
            }
            return filereldatalist;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public static String GetExtension(String filename)
    {
        String filenam[]=filename.split("\\.");
        if(filenam.length==1)
        {
            return "";
        }
        if(filename.equals(""))
        {
            return "";
        }
        return "."+filenam[filenam.length-1];
    }
    public static String GetExtension(File filepath)
    {
        if(filepath.equals(""))
        {
            return "";
        }
        String filenames[]=filepath.toString().split("\\/");
        String filename=filenames[filenames.length-1];
        String filenam[]=filename.split("\\.");
        return filenam[filenam.length-1];
    }
    public static String GetFilename(File path)
    {
        if(path.equals(""))
        {
            return "";
        }
        String filenam[]=path.toString().split("/");
        return filenam[filenam.length-1];
    }
    public static String GetFiletype(String filename)
    {
        String files[]=filename.split("\\/");
        if(!(files.equals("")))
        {
            return files[files.length-2];
        }
        return "";
    }
    public static boolean Testlog(String log)
    {
        try {
            FileOutputStream fileOutputStream=new FileOutputStream("/storage/emulated/0/Log.txt",true);
            OutputStreamWriter writer=new OutputStreamWriter(fileOutputStream);
            writer.append(log);
            writer.append("\n");
            writer.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean Errorlog(String errlog)
    {
        try {
            FileOutputStream fileOutputStream=new FileOutputStream("/storage/emulated/0/ErrorLog.txt",true);
            OutputStreamWriter writer=new OutputStreamWriter(fileOutputStream);
            writer.append(errlog);
            writer.append("\n");
            writer.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String Str_Padding(String data,String ch,int times,int direction)
    {
        if(data.length()==0)
        {
            return "";
        }
        String temp="";
        for(int i=0;i<times-data.length();i++)
        {
            temp=temp+ch;
        }
        if(direction==0)
        {
            data=data+temp;
        }
        else if(direction==1)
        {
            data=temp+data;
        }
        return data;
    }

    public static byte[] encrypt(byte[] value_bytes, String key) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        //byte[] value_bytes = value.getBytes("UTF-8");
        byte[] key_bytes = getKeyBytes(key);
        return encrypt(value_bytes, key_bytes, key_bytes);
    }

    public static byte[] encrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
    {
        try
        {
            Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            localCipher.init(1, new SecretKeySpec(paramArrayOfByte2, "AES"), new IvParameterSpec(paramArrayOfByte3));
            return localCipher.doFinal(paramArrayOfByte1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getKeyBytes(String paramString) throws UnsupportedEncodingException
    {
        byte[] arrayOfByte1 = new byte[16];
        byte[] arrayOfByte2 = paramString.getBytes("UTF-8");
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, Math.min(arrayOfByte2.length, arrayOfByte1.length));
        return arrayOfByte1;
    }

    public byte[] decrypt(byte[] value_bytes, String key)
            throws GeneralSecurityException, IOException
    {
        //byte[] value_bytes = Base64.decode(value, 0);
        byte[] key_bytes = getKeyBytes(key);
        return decrypt(value_bytes, key_bytes, key_bytes);
    }
    public byte[] decrypt(byte[] ArrayOfByte1, byte[] ArrayOfByte2, byte[] ArrayOfByte3)
    {
        try
        {
            Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            localCipher.init(2, new SecretKeySpec(ArrayOfByte2, "AES"), new IvParameterSpec(ArrayOfByte3));
            return localCipher.doFinal(ArrayOfByte1);
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte[] GetThumbnailData(byte[] data,int filetype,Context context)
    {
        byte[] bytearray=null;
        ByteArrayOutputStream stream;
        try
        {
            //Write File in TempFile
            File temp=new File(MainPath+"/Temp/"+"Tempfile");
            FileOutputStream fos1=null;
            if(data.length>0)
            {
                try
                {
                    fos1=new FileOutputStream(temp);
                    fos1.write(data);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(filetype==1)
            {
                Bitmap imgbitmap = BitmapFactory.decodeFile(MainPath+"/Temp/"+"Tempfile");
                stream=new ByteArrayOutputStream();
                imgbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                bytearray= stream.toByteArray();
            }
            else if(filetype==2)
            {
                Bitmap vidbitmap = ThumbnailUtils.createVideoThumbnail(MainPath+"/Temp/"+"Tempfile", MediaStore.Video.Thumbnails.MICRO_KIND);
                stream=new ByteArrayOutputStream();
                vidbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                bytearray= stream.toByteArray();
            }
            else if(filetype==3)
            {
                Bitmap audbitmap = getmp3thumbnail(MainPath+"/Temp/"+"Tempfile",context);
                stream=new ByteArrayOutputStream();
                audbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                bytearray= stream.toByteArray();
            }
            else if(filetype==4)
            {
                Bitmap otherbitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.otherfiles);
                stream=new ByteArrayOutputStream();
                otherbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                bytearray= stream.toByteArray();
            }
            temp.delete();
            return bytearray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static Bitmap getmp3thumbnail(String filepath,Context context)
    {
        try
        {
            File file=new File(filepath);
            Uri uri=Uri.fromFile(file);

            MediaMetadataRetriever mmr=new MediaMetadataRetriever();
            byte[] rawArt;
            BitmapFactory.Options bto=new BitmapFactory.Options();
            mmr.setDataSource(context,uri);
            rawArt=mmr.getEmbeddedPicture();
            if(null!=rawArt)
            {
                Bitmap art=BitmapFactory.decodeByteArray(rawArt,0,rawArt.length,bto);
                return art;
            }
            else if(rawArt==null)
            {
                Bitmap audiobitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.audios);
                return audiobitmap;
            }
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("Content_View - getmp3thumbnail - "+e.toString());
        }
        return null;
    }

    public static long GetAvailableInternalMemory()
    {
        StatFs stat=new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR2){
            bytesAvailable=stat.getBlockSizeLong()*stat.getAvailableBlocksLong();
        }
        else
        {
            bytesAvailable=(long)stat.getBlockSize()*(long)stat.getAvailableBlocks();
        }
        return bytesAvailable;
    }

    public static ArrayList<FileView> GetSortedList(ArrayList<FileView> Data,int SortingType)
    {
        if(SortingType==0)
        {
            //Name Ascending
            Collections.sort(Data, new Comparator<FileView>() {
                @Override
                public int compare(FileView object1, FileView object2) {
                    return object1.FileName.compareTo(object2.FileName);
                }
            });
        }
        else if(SortingType==1)
        {
            //Name Descending
            Collections.sort(Data, new Comparator<FileView>() {
                @Override
                public int compare(FileView object1, FileView object2) {
                    return object2.FileName.compareTo(object1.FileName);
                }
            });
        }
        else if(SortingType==2)
        {
            //Time Ascending
            Collections.sort(Data, new Comparator<FileView>() {
                @Override
                public int compare(FileView object1, FileView object2) {
                    long value=Long.parseLong(object1.LastModified)-Long.parseLong(object2.LastModified);
                    return ((int) value);
                }
            });
        }
        else if(SortingType==3)
        {
            //Time Descending
            Collections.sort(Data, new Comparator<FileView>() {
                @Override
                public int compare(FileView object1, FileView object2) {
                    long value=Long.parseLong(object2.LastModified)-Long.parseLong(object1.LastModified);
                    return ((int) value);
                }
            });
        }
        else if(SortingType==4)
        {
            //Size Ascending
            Collections.sort(Data, new Comparator<FileView>() {
                @Override
                public int compare(FileView object1, FileView object2) {
                    return Integer.parseInt(object1.Filelength)-Integer.parseInt(object2.Filelength);
                }
            });
        }
        else if(SortingType==5)
        {
            //Size Descending
            Collections.sort(Data, new Comparator<FileView>() {
                @Override
                public int compare(FileView object1, FileView object2) {
                    return Integer.parseInt(object2.Filelength)-Integer.parseInt(object1.Filelength);
                }
            });
        }
        return Data;
    }

    public static File[] Convert_ListStrToFilearr(List<String> lstfiles)
    {
        File[] files=new File[lstfiles.size()];

        for(int i=0;i<lstfiles.size();i++)
        {
            files[i]=new File(lstfiles.get(i));
        }

        return files;
    }

    public static List<String> Convert_FilearrToListStr(File[] files)
    {
        List<String> lstfiles=new ArrayList<>();
        for(int i=0;i<files.length;i++)
        {
            lstfiles.add(files[i].toString());
        }
        return lstfiles;
    }

    public static String[] Strarr_Memset(String Arr[],String value)
    {
        for(int i=0;i<Arr.length;i++)
        {
            Arr[i]=value;
        }
        return Arr;
    }

    public static int[] Intarr_Memset(int Arr[],int value)
    {
        for(int i=0;i<Arr.length;i++)
        {
            Arr[i]=value;
        }
        return Arr;
    }

    public static String GetFormatDateFromLastModified(long lastmodified,int type)
    {
        if(type==0)
        {
            Date lastmodifieddate=new Date(lastmodified);
            SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String formattedDateString=formatter.format(lastmodifieddate);
            return formattedDateString;
        }
        else if(type==1)
        {
            Date lastmodifieddate=new Date(lastmodified);
            SimpleDateFormat formatter=new SimpleDateFormat("MMMdd-yy hh:mm:ss");
            String formattedDateString=formatter.format(lastmodifieddate);
            return formattedDateString;
        }
        return "";
    }

    public static String formatSize(long size)
    {
        if(size>0) {
            String suffix=null;
            if(size>=1024)
            {
                suffix="KB";
                size/=1024;
                if(size>=1024)
                {
                    suffix="MB";
                    size/=1024;
                    if(size>=1024)
                    {
                        suffix="GB";
                        size/=1024;
                    }
                }
            }

            StringBuilder resultBuilder=new StringBuilder(Long.toString(size));

            int commaOffset=resultBuilder.length()-3;

            while(commaOffset>0)
            {
                resultBuilder.insert(commaOffset,',');
                commaOffset-=3;
            }

            if(suffix!=null) resultBuilder.append(suffix);
            return resultBuilder.toString();
        }
        else
        {
            return "NULL";
        }
    }

    public static String GetFolderName(int whichfolder)
    {
        if(whichfolder==0)
        {
            return "Images";
        }
        else if(whichfolder==1)
        {
            return "Videos";
        }
        else if(whichfolder==2)
        {
            return "Audios";
        }
        else if(whichfolder==3)
        {
            return "Others";
        }
        return "";
    }

    public static int UnPackData(File file,int index)
    {
        int ptr=0,len=0;
        byte[] length=null,tempbytes=null,Headerlengthbyte=null,encrypteddata=null,originaldata=null;
        byte filedata[]=ReadByteDataFromFile(file);
        String HeaderLength,TempLength;
        OutputStream out=null;

        try
        {
            //check file is valid using STX,ETX
            if(filedata[0]!=2 && filedata[filedata.length-1]!=3)
            {
                return -1;
            }

            ptr=ptr+2;
            //Get HeaderLength
            arraycopy(filedata,ptr,2,Headerlengthbyte,0);
            ptr=ptr+2;
            HeaderLength=Tools.getInstance().BytetoString(Headerlengthbyte);
            HeaderLength=Tools.getInstance().Trimstart(HeaderLength,"0");

            //Skip Headerdata
            ptr=ptr+Integer.parseInt(HeaderLength);

            //Get EncryptedData Length
            tempbytes=new byte[2];
            arraycopy(filedata,ptr,2,tempbytes,0);
            ptr=ptr+2;
            TempLength=Tools.getInstance().BytetoString(tempbytes);
            TempLength=Tools.getInstance().Trimstart(TempLength,"0");

            arraycopy(filedata,ptr,Integer.parseInt(TempLength),encrypteddata,0);

            originaldata=new byte[Integer.parseInt(filereldatalist.get(index).getFileLength())];
            originaldata=Tools.getInstance().decrypt(encrypteddata,"00000");

            out=new FileOutputStream(Global.MainPath + "/Temp/temp");
            out.write(originaldata);

            return 0;
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    public static byte[] ReadByteDataFromFile(File file)
    {
        long size=file.length();
        byte bytes[]=new byte[(int)size];
        try
        {
            BufferedInputStream buf=new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes,0,bytes.length);
            buf.close();

            return bytes;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<FileView> GetHeaderLargeDetail(List<String> listOfFilePath) {
        try {
            String FileLength, FileName, Thumblength, LastModified;
            byte[] FileLengthbyte = null, FileNamebyte = null, Thumblengthbyte = null, length, Headerlengthbyte = null, Temp = null;
            byte[] Thumbnail = null;
            int ptr = 0, len, fileindex, stx;
            String HeaderLength = "";

            filereldatalist = new ArrayList<>();

            for (int i = 0; i <= listOfFilePath.size() - 1; i++) {
                //Split Header File
                File f=new File(listOfFilePath.get(i));

                try
                {
                    if(f.length()<=1024*1024*40)
                    {
                        continue;
                    }
                    int partCounter=1;
                    int sizeofFiles=1000*1000*20+100;
                    byte[] buffer=new byte[sizeofFiles];

                    String fileName=f.getName();

                    try(FileInputStream fis=new FileInputStream(f);
                        BufferedInputStream bis=new BufferedInputStream(fis)){
                        int bytesAmount=0;
                        if((bytesAmount=bis.read(buffer))>0)
                        {
                            File newFile=new File(Global.MainPath + "/Temp/Header");
                            FileOutputStream fout=new FileOutputStream(newFile);
                            fout.write(buffer,0,bytesAmount);
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //Get FileRelatedData From Encrypted File
                File file = new File(listOfFilePath.get(i));
                int size = (int) file.length();
                byte filedata[] = new byte[size];
                byte Headerdata[] = null;

                //Read FileFrom .Enc file
                filedata = Tools.getInstance().ReadByteDataFromFile(file);

                //Check Fileis Valid File if FileFirst Contain STX
                if (filedata[0] != 2 && filedata[filedata.length - 1] != 3) {
                    continue;
                }

                ptr = 0;
                //Skip STX,FileIndex
                ptr = ptr + 2;

                //Get HeaderLength
                Temp = new byte[2];
                arraycopy(filedata, ptr, 2, Temp, 0);
                ptr = ptr + 2;
                HeaderLength = Tools.getInstance().BytetoString(Temp);
                HeaderLength = Tools.getInstance().Trimstart(HeaderLength, "0");

                Headerlengthbyte = new byte[Integer.parseInt(HeaderLength)];
                arraycopy(filedata, ptr, Integer.parseInt(HeaderLength), Headerlengthbyte, 0);
                ptr = ptr + Integer.parseInt(HeaderLength);
                HeaderLength = Tools.getInstance().BytetoString(Headerlengthbyte);
                HeaderLength = Tools.Trimstart(HeaderLength, "0");

                //Get HeaderData
                Headerdata = new byte[Integer.valueOf(HeaderLength)];
                arraycopy(filedata, 0, Integer.parseInt(HeaderLength), Headerdata, 0);

                ptr = 0;

                //Skip STX
                ptr = ptr + 1;

                //GetFileIndex
                fileindex = Headerdata[ptr];
                ptr = ptr + 1;

                //Skip HeaderLength
                ptr = ptr + 12;

                length = new byte[2];

                //GetFileName
                arraycopy(Headerdata, ptr, 2, length, 0);
                ptr = ptr + 2;
                len = Integer.parseInt(Tools.getInstance().BytetoString(length));
                FileNamebyte = new byte[len];
                arraycopy(Headerdata, ptr, len, FileNamebyte, 0);
                ptr = ptr + FileNamebyte.length;
                FileName = Tools.getInstance().BytetoString(FileNamebyte);

                //GetFileLength
                arraycopy(Headerdata, ptr, 2, length, 0);
                ptr = ptr + 2;
                len = Integer.parseInt(Tools.getInstance().BytetoString(length));
                FileLengthbyte = new byte[len];
                arraycopy(Headerdata, ptr, len, FileLengthbyte, 0);
                ptr = ptr + FileLengthbyte.length;
                FileLength = Tools.getInstance().BytetoString(FileLengthbyte);

                //GetThumbnailLength
                arraycopy(Headerdata, ptr, 2, length, 0);
                ptr = ptr + 2;
                len = Integer.parseInt(Tools.getInstance().BytetoString(length));
                Thumblengthbyte = new byte[len];
                arraycopy(Headerdata, ptr, len, Thumblengthbyte, 0);
                ptr = ptr + Thumblengthbyte.length;
                Thumblength = Tools.getInstance().BytetoString(Thumblengthbyte);

                //GetThumbnail
                Thumbnail = new byte[Integer.parseInt(Thumblength)];
                arraycopy(Headerdata, ptr, Integer.parseInt(Thumblength), Thumbnail, 0);

                //GetLastModified
                File temp = new File(listOfFilePath.get(i));
                long lastmodified = temp.lastModified();
                LastModified = String.valueOf(lastmodified);

                filereldatalist.add(new FileView(FileName, Thumbnail, FileLength, LastModified, fileindex));
            }
            return filereldatalist;
        } catch (Exception e) {
            return null;
        }
    }
}
