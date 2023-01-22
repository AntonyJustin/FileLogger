package com.locker.welcome.locker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.locker.welcome.locker.Dialog.ToastDialogs;

import static com.locker.welcome.locker.Global.sn;


public class Passwordenter extends AppCompatActivity implements View.OnClickListener {
    Button no0,no1,no2,no3,no4,no5,no6,no7,no8,no9,del,enter;
    ImageView img1,img2,img3,img4,img5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordenter);

        try {
            Global.sn=new SoundFile(Passwordenter.this);

            no0=(Button) findViewById(R.id.num0);
            no1=(Button) findViewById(R.id.num1);
            no2=(Button) findViewById(R.id.num2);
            no3=(Button) findViewById(R.id.num3);
            no4=(Button) findViewById(R.id.num4);
            no5=(Button) findViewById(R.id.num5);
            no6=(Button) findViewById(R.id.num6);
            no7=(Button) findViewById(R.id.num7);
            no8=(Button) findViewById(R.id.num8);
            no9=(Button) findViewById(R.id.num9);
            no9=(Button) findViewById(R.id.num9);
            del=(Button) findViewById(R.id.del);
            enter=(Button) findViewById(R.id.enter);

            img1=(ImageView)findViewById(R.id.pin1);
            img2=(ImageView)findViewById(R.id.pin2);
            img3=(ImageView)findViewById(R.id.pin3);
            img4=(ImageView)findViewById(R.id.pin4);
            img5=(ImageView)findViewById(R.id.pin5);

            no0.setBackgroundResource(R.drawable.selection_btn_0);
            no1.setBackgroundResource(R.drawable.selection_btn_1);
            no2.setBackgroundResource(R.drawable.selection_btn_2);
            no3.setBackgroundResource(R.drawable.selection_btn_3);
            no4.setBackgroundResource(R.drawable.selection_btn_4);
            no5.setBackgroundResource(R.drawable.selection_btn_5);
            no6.setBackgroundResource(R.drawable.selection_btn_6);
            no7.setBackgroundResource(R.drawable.selection_btn_7);
            no8.setBackgroundResource(R.drawable.selection_btn_8);
            no9.setBackgroundResource(R.drawable.selection_btn_9);
            del.setBackgroundResource(R.drawable.selection_btn_del);
            enter.setBackgroundResource(R.drawable.selection_btn_cancel);


            no0.setOnClickListener(this);
            no1.setOnClickListener(this);
            no2.setOnClickListener(this);
            no3.setOnClickListener(this);
            no4.setOnClickListener(this);
            no5.setOnClickListener(this);
            no6.setOnClickListener(this);
            no7.setOnClickListener(this);
            no8.setOnClickListener(this);
            no9.setOnClickListener(this);
            del.setOnClickListener(this);
            enter.setOnClickListener(this);
            new Asyncsfiles(this,Passwordenter.this);
        }
        catch (Exception e)
        {

        }
    }
public void pinenter()
{
    if(Global.pin.length()<=5)
    {
        if(Global.pin.length()==1)
        {
            img1.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
        }
        if(Global.pin.length()==2)
        {
            img1.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
            img2.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
        }
        if(Global.pin.length()==3)
        {
            img1.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
            img2.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
            img3.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
        }
        if(Global.pin.length()==4)
        {
            img1.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
            img2.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
            img3.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
            img4.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
        }
        if(Global.pin.length()==5)
        {
            img1.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
            img2.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
            img3.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
            img4.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
            img5.setBackground(getResources().getDrawable(R.drawable.passwordenterroundcorner));
        }
    }
}

    @Override
    public void onClick(View v) {
        Global.sn.NotificationSound(4);
        Global.sn.Vibtration(100);
        if(v==no0)
        {
            if(Global.pin.length()<=4) {
                Global.pin = Global.pin + "0";
                pinenter();
            }
        }
        if(v==no1)
        {
            if(Global.pin.length()<=4) {
                Global.pin = Global.pin + "1";
                pinenter();
            }
        }
        if(v==no2)
        {
            if(Global.pin.length()<=4) {
                Global.pin = Global.pin + "2";
                pinenter();
            }
        }
        if(v==no3)
        {
            if(Global.pin.length()<=4) {
                Global.pin = Global.pin + "3";
                pinenter();
            }
        }
        if(v==no4)
        {
            if(Global.pin.length()<=4) {
                Global.pin = Global.pin + "4";
                pinenter();
            }
        }

        if(v==no5)
        {
            if(Global.pin.length()<=4) {
                Global.pin = Global.pin + "5";
                pinenter();
            }
        }
        if(v==no6)
        {
            if(Global.pin.length()<=4) {
                Global.pin = Global.pin + "6";
                pinenter();
            }
        }
        if(v==no7)
        {
            if(Global.pin.length()<=4) {
                Global.pin = Global.pin + "7";
                pinenter();
            }
        }
        if(v==no8)
        {
            if(Global.pin.length()<=4) {
                Global.pin = Global.pin + "8";
                pinenter();
            }
        }
        if(v==no9)
        {
            if(Global.pin.length()<=4) {
                Global.pin = Global.pin + "9";
                pinenter();
            }
        }
        if(v==del)
        {
            if(Global.pin.length()!=0) {
                Global.pin = Global.pin.substring(0, Global.pin.length() - 1);
                img1.setBackground(getResources().getDrawable(R.drawable.roundcorner));
                img2.setBackground(getResources().getDrawable(R.drawable.roundcorner));
                img3.setBackground(getResources().getDrawable(R.drawable.roundcorner));
                img4.setBackground(getResources().getDrawable(R.drawable.roundcorner));
                img5.setBackground(getResources().getDrawable(R.drawable.roundcorner));
                pinenter();
            }
        }
        if(v==enter)
        {
            if(Global.pin.length()!=5)
            {
                ToastDialogs.getInstance().Dialogs(1,"Password Cannot Empty!!",Passwordenter.this,0);
                return;
            }
            if(!Global.pin.equals(""))
            {
                Asyncsfiles.lockfiles lock=new Asyncsfiles.lockfiles();
                lock.execute(Global.uripath);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sn.NotificationSound(4);
        Global.sn.Vibtration(100);
    }
}



