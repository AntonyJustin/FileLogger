package com.locker.welcome.locker.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.locker.welcome.locker.Asyncsfiles;
import com.locker.welcome.locker.Global;
import com.locker.welcome.locker.Menu;
import com.locker.welcome.locker.R;
import com.locker.welcome.locker.Sample;
import com.locker.welcome.locker.SoundFile;
import com.locker.welcome.locker.Tools;

import static com.locker.welcome.locker.Global.sn;

public class DialogBox extends Dialog implements View.OnClickListener {
    Context context;
    int Type;

    MsgShowListener listener;
    private TextView msg,msg1,msg2;
    private Button btnenter,btncancel;
    Handler handler;
    Runnable MSGRunnable;
    boolean isBtnClicked = false;
    RelativeLayout failure,confir,success;
    String message;

    public DialogBox(Context context,int Type,String message,MsgShowListener listener) {
        super(context);
        this.context = context;
        this.Type = Type;
        this.message=message;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_box);
        try
        {
            Tools.getInstance().Testlog("DialogBox - initializing");
            sn=new SoundFile(context);
            setCancelable(true);
            msg=(TextView)findViewById(R.id.message);
            msg1=(TextView)findViewById(R.id.message1);
            msg2=(TextView)findViewById(R.id.message2);
            btnenter = (Button) findViewById(R.id.btn_enter) ;
            btncancel = (Button) findViewById(R.id.btn_cancel) ;

            success=(RelativeLayout)findViewById(R.id.Sucess);
            confir=(RelativeLayout)findViewById(R.id.Confirmation);
            failure=(RelativeLayout)findViewById(R.id.failure);
            handler = new Handler(Looper.getMainLooper());

            if(Type==1) {
                Tools.getInstance().Testlog("DialogBox - User Called Failure DialogBox");
                sn.NotificationSound(5);
                Global.sn.Vibtration(100);
                failure.setVisibility(View.VISIBLE);
                success.setVisibility(View.GONE);
                confir.setVisibility(View.GONE);
                msg1.setText(message);
                handler.postDelayed(setRunnable(), 3000);
            }
            else if(Type==2)
            {
                Tools.getInstance().Testlog("DialogBox - User Called Confirmed DialogBox");
                sn.NotificationSound(5);
                Global.sn.Vibtration(100);
                confir.setVisibility(View.VISIBLE);
                success.setVisibility(View.GONE);
                failure.setVisibility(View.GONE);
                msg2.setText(message);
                handler.postDelayed(setRunnable(), 5000);
            }
            else if(Type==3) {
                Tools.getInstance().Testlog("DialogBox - User Called Success DialogBox");
                sn.NotificationSound(10);
                Global.sn.Vibtration(100);
                success.setVisibility(View.VISIBLE);
                confir.setVisibility(View.GONE);
                failure.setVisibility(View.GONE);
                msg.setText(message);
                handler.postDelayed(setRunnable(), 3000);
            }

            setCanceledOnTouchOutside(false);

            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            btncancel.setOnClickListener(this);
            btnenter.setOnClickListener(this);
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("DialogBox - OnCreate - "+e.toString());
        }
    }
    public Runnable setRunnable() {
        MSGRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isBtnClicked) {
                    isBtnClicked =true;
                    listener.onErr();
                    dismiss();
                    cancel();
                }
            }
        };
        return MSGRunnable;
    }

    @Override
    public void onClick(View view) {
        try{
            if(view==btncancel)
            {
                Tools.getInstance().Testlog("DialogBox - User Clicked Cancel Button");
                sn.NotificationSound(11);
                Global.sn.Vibtration(100);
                if (!isBtnClicked) {
                    isBtnClicked =true;
                    listener.onErr();
                    dismiss();
                    cancel();
                }
            }
            if(view==btnenter)
            {
                Tools.getInstance().Testlog("DialogBox - User Clicked Enter Button");
                sn.NotificationSound(11);
                Global.sn.Vibtration(100);
                if (!isBtnClicked) {
                    isBtnClicked =true;
                    listener.onSucc();
                    dismiss();
                    cancel();
                }
            }
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("DialogBox - OnClick - "+e.toString());
        }
    }
}
