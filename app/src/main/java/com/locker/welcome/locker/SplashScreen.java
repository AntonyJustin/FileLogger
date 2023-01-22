package com.locker.welcome.locker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import static com.locker.welcome.locker.Asyncsfiles.context;
import static com.locker.welcome.locker.Global.Home_Pin;
import static com.locker.welcome.locker.Global.Key_No;
import static com.locker.welcome.locker.Global.Pin_Enable;
import static com.locker.welcome.locker.Global.Sound_Enable;
import static com.locker.welcome.locker.Global.Vibration_Enable;
import static com.locker.welcome.locker.Global.mypreference;
import static com.locker.welcome.locker.Global.sharedpreferences;
import static com.locker.welcome.locker.Tools.directoryinitialize;

    public class SplashScreen extends AppCompatActivity {
    private int count =0;
    public static boolean isstopload;
        public static final int PERMISSION_REQUEST_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Tools.getInstance().Testlog("Splash Screen - Locker has Started");

        Tools.getInstance().Testlog("Splash Screen - loading");

        new Thread(myThread).start();
    }

        @Override
        protected void onStop() {
            super.onStop();
        }

        private Runnable myThread = new Runnable()
    {
        public void run()
        {
            while(count < 100)
            {
                try
                {
                    if(isstopload==true)
                    {
                        Tools.getInstance().Testlog("Splash Screen - Locker Exited");
                        finish();
                        return;
                    }
                    Thread.sleep(50);
                    handler.sendEmptyMessage(0);

                }
                catch(Exception e)
                {
                  Tools.getInstance().Errorlog("SplashScreen - mythread(Runnable) - "+e.toString());
                }
            }
        }

        private final Handler handler  = new Handler()
        {

            public void handleMessage(Message msg)
            {
                try
                {
                    count+=2;
                    if(count > 100)
                    {
                        sharedpreferences = SplashScreen.this.getSharedPreferences(mypreference, Context.MODE_PRIVATE);

                        Global.PinEnable=sharedpreferences.getInt(Pin_Enable,0);
                        Global.SoundEnable=sharedpreferences.getInt(Sound_Enable, 0);
                        Global.VibrationEnable=sharedpreferences.getInt(Vibration_Enable, 0);

                        if (sharedpreferences.getString(Home_Pin, "").equals(""))
                        {
                            finish();
                            Intent intent;
                            intent = new Intent(SplashScreen.this, HomePasswordEnter.class);
                            intent.putExtra("msg","1");
                            intent.setAction(Intent.ACTION_VIEW);
                            SplashScreen.this.startActivity(intent);
                        }
                        else
                        {
                            finish();
                            Intent intent;
                            intent = new Intent(SplashScreen.this, HomePasswordEnter.class);
                            intent.putExtra("msg","0");
                            intent.setAction(Intent.ACTION_VIEW);
                            SplashScreen.this.startActivity(intent);
                        }
                        return;
                    }
                }
                catch (Exception e)
                {
                    Tools.getInstance().Errorlog("SplashScreen - handler(Handler) - "+e.toString());
                }
            }
        };

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*finish();
        Intent intent=new Intent(SplashScreen.this,SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS|Intent.FLAG_ACTIVITY_NO_ANIMATION);
        SplashScreen.this.startActivity(intent);*/
        isstopload=true;
    }

        public void checkPermission()
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                if(!(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED))
                {
                    Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+getPackageName()));
                    startActivity(intent);
                    finish();
                }
                if(!(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED))
                {
                    Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+getPackageName()));
                    startActivity(intent);
                    finish();
                }
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            /*if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }*/
        }
    }
