package com.locker.welcome.locker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import static com.locker.welcome.locker.Global.Home_Pin;
import static com.locker.welcome.locker.Global.mypreference;
import static com.locker.welcome.locker.Global.sharedpreferences;
import static com.locker.welcome.locker.Global.sn;


public class ChangePassword extends AppCompatActivity {
    EditText txtoldpwd;
    Button btnsubmit;
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        try
        {
            Tools.getInstance().Testlog("ChangePassword - initializing");
            sn=new SoundFile(ChangePassword.this);

            txtoldpwd=(EditText)findViewById(R.id.txt_oldpassword);

            btnsubmit=(Button)findViewById(R.id.btn_submit);

            msg=(TextView)findViewById(R.id.msg);

            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tools.getInstance().Testlog("ChangePassword - User Click Submit button");
                    sn.NotificationSound(11);
                    Global.sn.Vibtration(200);
                    msg.setVisibility(View.GONE);
                    sharedpreferences = ChangePassword.this.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                    if (sharedpreferences.getString(Home_Pin, "").equals(txtoldpwd.getText().toString()))
                    {
                        finish();
                        Intent intent;
                        intent = new Intent(ChangePassword.this, HomePasswordEnter.class);
                        intent.putExtra("msg","1");
                        intent.setAction(Intent.ACTION_VIEW);
                        ChangePassword.this.startActivity(intent);
                        Tools.getInstance().Testlog("ChangePassword - User enter correct Oldpassword");
                    }
                    else
                    {
                        txtoldpwd.setText("");
                        msg.setVisibility(View.VISIBLE);
                        msg.setText("Invalid Password");
                        msg.setTextColor(getResources().getColor(R.color.colorAccent));

                        Animation shake= AnimationUtils.loadAnimation(ChangePassword.this,R.anim.shake);
                        txtoldpwd.startAnimation(shake);
                        Tools.getInstance().Testlog("ChangePassword - User enter Invalid Oldpassword");
                    }
                }
            });
        }
        catch (Exception e)
        {
            Tools.getInstance().Errorlog("ChangePassword - Oncreate - "+e.toString());
        }
    }

}
