package com.locker.welcome.locker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 30-May-20.
 */

public class Global {
    public static byte[] byte_data=null;
    public static byte[] lock_byte_data=null;
    public static String pin="";
    public static int determinefolder=0;
    public static SharedPreferences sharedpreferences;
    public static final String mypreference = "Locker";
    public static final String Key_No ="FileNo";
    public static final String Pin_Enable ="PinEnable";
    public static final String Sound_Enable ="SoundEnable";
    public static final String Home_Pin ="HomePin";
    public static final String ViewList ="ViewList";
    public static final String SortBy ="SortBy";
    public static final String Vibration_Enable ="VibrationEnable";
    public static ProgressDialog progressDialog;
    public static List<String> filepaths;
    public static int iswhichfile;
    public static ArrayList<FileView> filereldatalist;
    public static int LockStatus;
    public static String uripath;
    public static String HomePins="";
    public static int lockorunlock=0;
    public static SoundFile sn;
    public static int SoundEnable;
    public static Vibrator vibrator;
    public static int VibrationEnable;
    public static int PinEnable=0;
    public static String[] Filetypes={"Audios","Images","Others","Temp","Videos"};
    public static String MainPath="/storage/emulated/0/ProgramData/Android/Language/.Locker/";
    public static String UserEnterFilename;
    public static int Left=1;
    public static int Right=0;
    public static int ViewlistType;
    public static int Sortby;

    //Viewlist[0]=LargeView
    //Viewlist[1]=DetailsView
    //Viewlist[2]=ExtraLargeView

    //Sortby[0]=NameAscending
    //Sortby[1]=NameDescending
    //Sortby[2]=TimeAscending
    //Sortby[3]=TimeDescending
    //Sortby[4]=SizeAscending
    //Sortby[5]=SizeDescending

}
