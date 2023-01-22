package com.locker.welcome.locker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.locker.welcome.locker.Asyncsfiles.context;
import static com.locker.welcome.locker.Global.Filetypes;
import static com.locker.welcome.locker.Global.Key_No;
import static com.locker.welcome.locker.Global.MainPath;
import static com.locker.welcome.locker.Global.Pin_Enable;
import static com.locker.welcome.locker.Global.Sound_Enable;
import static com.locker.welcome.locker.Global.Vibration_Enable;
import static com.locker.welcome.locker.Global.mypreference;
import static com.locker.welcome.locker.Global.sharedpreferences;
import static com.locker.welcome.locker.Global.sn;

public class Setting extends AppCompatActivity {
    public static Switch enablepin,enablesound,enablevibration;
    public static Button btn_save,btn_cancel;
    public static Button invalidattempt,changepwd,cleardata,reset,about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        new Asyncsfiles(this,Setting.this);
        sn=new SoundFile(Setting.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enablepin=(Switch)findViewById(R.id.switchpin);
        enablesound=(Switch)findViewById(R.id.switchsound);
        enablevibration=(Switch)findViewById(R.id.switchvibration);
        btn_save=(Button)findViewById(R.id.btn_save);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);

        cleardata=(Button)findViewById(R.id.btn_cleardata);
        invalidattempt=(Button) findViewById(R.id.btn_invalidattempt);
        changepwd=(Button) findViewById(R.id.btn_changepwd);
        reset=(Button)findViewById(R.id.btn_reset);
        about=(Button)findViewById(R.id.btn_about);

        invalidattempt.setBackgroundResource(R.drawable.btn_selection_enter);

        changepwd.setBackgroundResource(R.drawable.btn_selection_enter);

        cleardata.setBackgroundResource(R.drawable.btn_selection_enter);

        reset.setBackgroundResource(R.drawable.btn_selection_enter);

        about.setBackgroundResource(R.drawable.btn_selection_enter);

        sharedpreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if(sharedpreferences.getInt(Pin_Enable, 0)==0)
        {
            enablepin.setChecked(false);
        }
        else if(sharedpreferences.getInt(Pin_Enable, 0)==1)
        {
            enablepin.setChecked(true);
        }

        if(sharedpreferences.getInt(Sound_Enable, 0)==0)
        {
            enablesound.setChecked(false);
        }
        else if(sharedpreferences.getInt(Sound_Enable, 0)==1)
        {
            enablesound.setChecked(true);
        }

        if(sharedpreferences.getInt(Vibration_Enable, 0)==0)
        {
            enablevibration.setChecked(false);
        }
        else if(sharedpreferences.getInt(Vibration_Enable, 0)==1)
        {
            enablevibration.setChecked(true);
        }

        invalidattempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sn.NotificationSound(11);
                Global.sn.Vibtration(100);
                Intent intent;
                intent = new Intent(Setting.this, InvalidAttempt.class);
                intent.setAction(Intent.ACTION_VIEW);
                Setting.this.startActivity(intent);
            }
        });

        changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sn.NotificationSound(11);
                Global.sn.Vibtration(100);
                Asyncsfiles.MenuList changepwd=new Asyncsfiles.MenuList();
                changepwd.execute(2);
            }
        });

        cleardata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Asyncsfiles.ClearAppData cleardata=new Asyncsfiles.ClearAppData();
                cleardata.execute();
                }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Asyncsfiles.ResetAppData resetAppData=new Asyncsfiles.ResetAppData();
                resetAppData.execute();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sn.NotificationSound(11);
                Global.sn.Vibtration(100);
                if(enablepin.isChecked())
                {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt(Pin_Enable, 1);
                    editor.commit();
                    Global.PinEnable=1;
                }
                else
                {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt(Pin_Enable, 0);
                    editor.commit();
                    Global.PinEnable=0;
                }

                if(enablesound.isChecked())
                {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt(Sound_Enable, 1);
                    editor.commit();
                    Global.SoundEnable=1;
                }
                else
                {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt(Sound_Enable, 0);
                    editor.commit();
                    Global.SoundEnable=0;
                }

                if(enablevibration.isChecked())
                {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt(Vibration_Enable, 1);
                    editor.commit();
                    Global.VibrationEnable=1;
                }
                else
                {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt(Vibration_Enable, 0);
                    editor.commit();
                    Global.VibrationEnable=0;
                }

                Toast.makeText(Setting.this, "Settings Saved Succesfully!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sn.NotificationSound(11);
                sn.Vibtration(100);
                finish();
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

