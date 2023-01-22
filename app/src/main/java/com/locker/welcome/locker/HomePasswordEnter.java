package com.locker.welcome.locker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.locker.welcome.locker.Dialog.ToastDialogs;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static com.locker.welcome.locker.Global.Home_Pin;
import static com.locker.welcome.locker.Global.Pin_Enable;
import static com.locker.welcome.locker.Global.mypreference;
import static com.locker.welcome.locker.Global.sharedpreferences;
import static com.locker.welcome.locker.Tools.directoryinitialize;
import static com.locker.welcome.locker.Tools.foldercreate;


public class HomePasswordEnter extends AppCompatActivity implements View.OnClickListener {
    Button no0,no1,no2,no3,no4,no5,no6,no7,no8,no9,clear,cancel;
    EditText pinenter;
    String Type;
    TextView title,txt;
    public String pin="";
    private static Timer timer;
    public static Context context;
    public static boolean isCap=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_password_enter);

        try
        {
            Tools.getInstance().Testlog("HomePasswordEnter - initializing");

            Global.sn=new SoundFile(HomePasswordEnter.this);

            sharedpreferences = HomePasswordEnter.this.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
            pin=sharedpreferences.getString(Home_Pin, "");

            Intent intent=getIntent();
            Type=intent.getStringExtra("msg");

            cancel=(Button)findViewById(R.id.cancelbtn);
            title=(TextView)findViewById(R.id.title);
            txt=(TextView)findViewById(R.id.txt);
            pinenter=(EditText)findViewById(R.id.edt_pin);
            context=HomePasswordEnter.this;

            if(Type.equals("1"))
            {
                Tools.getInstance().Testlog("HomePasswordEnter - User Ready Enter New Password");
                title.setText("Enter New Password");
                cancel.setText("E");
                txt.setText("Enter");
            }
            else if(Type.equals("0"))
            {
                Tools.getInstance().Testlog("HomePasswordEnter - User Enter Ready for Password Validation");
                title.setText("Enter Password");
                cancel.setText("C");
                txt.setText("Cancel");
            }

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
            clear=(Button) findViewById(R.id.clear);
            cancel=(Button) findViewById(R.id.cancelbtn);

            no0.setBackgroundResource(R.drawable.home_selection_btn);
            no1.setBackgroundResource(R.drawable.home_selection_btn);
            no2.setBackgroundResource(R.drawable.home_selection_btn);
            no3.setBackgroundResource(R.drawable.home_selection_btn);
            no4.setBackgroundResource(R.drawable.home_selection_btn);
            no5.setBackgroundResource(R.drawable.home_selection_btn);
            no6.setBackgroundResource(R.drawable.home_selection_btn);
            no7.setBackgroundResource(R.drawable.home_selection_btn);
            no8.setBackgroundResource(R.drawable.home_selection_btn);
            no9.setBackgroundResource(R.drawable.home_selection_btn);
            clear.setBackgroundResource(R.drawable.home_selection_btn);
            cancel.setBackgroundResource(R.drawable.home_selection_btn);


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
            clear.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }
        catch(Exception e)
        {
            Tools.getInstance().Errorlog("HomePasswordEnter - Oncreate - "+e.toString());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try
        {
            StartTimer();
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("HomePasswordEnter - Onstart - "+e.toString());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try
        {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("HomePasswordEnter - Onstop - "+e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        try
        {
            Global.sn.NotificationSound(11);
            Global.sn.Vibtration(100);
            if(v==no0)
            {
                if(Global.HomePins.length()<=9)
                {
                    Global.HomePins=Global.HomePins+"0";
                    pinenter.setText(Global.HomePins);
                }

            }
            if(v==no1)
            {
                if(Global.HomePins.length()<=9) {
                    Global.HomePins = Global.HomePins + "1";
                    pinenter.setText(Global.HomePins);
                }
            }
            if(v==no2)
            {
                if(Global.HomePins.length()<=9) {
                    Global.HomePins = Global.HomePins + "2";
                    pinenter.setText(Global.HomePins);
                }
            }
            if(v==no3)
            {
                if(Global.HomePins.length()<=9) {
                    Global.HomePins = Global.HomePins + "3";
                    pinenter.setText(Global.HomePins);
                }
            }
            if(v==no4)
            {
                if(Global.HomePins.length()<=9) {
                    Global.HomePins = Global.HomePins + "4";
                    pinenter.setText(Global.HomePins);
                }
            }

            if(v==no5)
            {
                if(Global.HomePins.length()<=9) {
                    Global.HomePins = Global.HomePins + "5";
                    pinenter.setText(Global.HomePins);
                }
            }
            if(v==no6)
            {
                if(Global.HomePins.length()<=9) {
                    Global.HomePins = Global.HomePins + "6";
                    pinenter.setText(Global.HomePins);
                }
            }
            if(v==no7)
            {
                if(Global.HomePins.length()<=9) {
                    Global.HomePins = Global.HomePins + "7";
                    pinenter.setText(Global.HomePins);
                }
            }
            if(v==no8)
            {
                if(Global.HomePins.length()<=9) {
                    Global.HomePins = Global.HomePins + "8";
                    pinenter.setText(Global.HomePins);
                }
            }
            if(v==no9)
            {
                if(Global.HomePins.length()<=9) {
                    Global.HomePins = Global.HomePins + "9";
                    pinenter.setText(Global.HomePins);
                }
            }
            if(v==clear)
            {
                if(Global.HomePins.length()!=0)
                {
                    Global.HomePins=Global.HomePins.substring(0,Global.HomePins.length()-1);
                    pinenter.setText(Global.HomePins);
                }
            }
            if(v==cancel)
            {
                if(Type.equals("1"))
                {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Home_Pin, Global.HomePins);
                    editor.commit();
                    Global.HomePins="";
                    finish();
                    Intent intent;
                    intent = new Intent(HomePasswordEnter.this,SplashScreen.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    HomePasswordEnter.this.startActivity(intent);
                    Tools.getInstance().Testlog("HomePasswordEnter - User Enter New Passoword Successfully");
                }
                Global.HomePins="";
                finish();
                Tools.getInstance().Testlog("HomePasswordEnter - User Cancel HomePasswordEnter");
            }

            if(Type.equals("0"))
            {
                if(Global.HomePins.equals(pin))
                {
                    File file=new File(Global.MainPath + "Temp");
                    if(file.exists())
                    {
                        file.delete();
                    }

                    foldercreate();
                    directoryinitialize();

                    /*if(sharedpreferences.getInt(Pin_Enable, 0)==0)
                    {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt(Pin_Enable, 1);
                        editor.commit();
                    }*/

                    HomePasswordEnter.this.finish();

                    Global.HomePins="";

                    Intent intent;
                    intent = new Intent(HomePasswordEnter.this, Menu.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    HomePasswordEnter.this.startActivity(intent);
                    Tools.getInstance().Testlog("HomePasswordEnter - User Enter Correct Password OR Password Validate Successfully");
                }
            }
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("HomePasswordEnter - Onclick - "+e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try
        {
            Global.sn.NotificationSound(11);
            Global.HomePins="";
            Tools.getInstance().Testlog("HomePasswordEnter - User Cancel HomePasswordEnter Screen");
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("HomePasswordEnter - OnBackPressed - "+e.toString());
        }
    }

    public static void StartTimer()
    {
        try
        {
            if (timer != null) return;
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (isCap == true) {
                            isCap=false;
                            Tools.getInstance().Testlog("HomePasswordEnter - HomePasswordEnter Screen Cancelled for TimeOut");
                            Intent front_translucent = new Intent(context, CapPhoto.class);
                            front_translucent.putExtra("Front_Request", true);
                            context.startService(front_translucent);
                        }
                        isCap=true;
                        return;
                    }catch (Exception e) {
                        return ;
                    }
                }
            }, 0,20000);
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("HomePasswordEnter - StartTimer - "+e.toString());
        }
    }
}
