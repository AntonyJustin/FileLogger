package com.locker.welcome.locker.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.locker.welcome.locker.Asyncsfiles;
import com.locker.welcome.locker.Global;
import com.locker.welcome.locker.R;

/**
 * Created by Justin on 24-10-2020.
 */

public class Enterfilename extends Dialog {
    Context context;
    EditText Edtfilename;
    Button btnsave,btncancel;
    MsgShowListener listener;
    public Message msg;
    public Bundle bundle;
    public String filename;

    public Enterfilename(Context context,String filename,MsgShowListener listener) {
        super(context);
        this.context = context;
        this.filename=filename;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.enter_filename);

        msg=new Message();
        bundle = new Bundle();

        Edtfilename=(EditText)findViewById(R.id.edtfilename);
        btnsave=(Button)findViewById(R.id.btn_save);
        btncancel=(Button)findViewById(R.id.btn_cancel);

        setCanceledOnTouchOutside(false);

        Edtfilename.setText(filename);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edtfilename.getText().toString().equals("")==true)
                {
                    bundle.putString("Msg","Filename Shouldn't be Empty!");
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    listener.onErr();
                }
                else
                {
                    Global.UserEnterFilename=Edtfilename.getText().toString();
                    listener.onSucc();
                    cancel();
                    dismiss();
                }
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onErr();
                cancel();
                dismiss();
            }
        });
    }
    protected Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle;
            try
            {
                bundle =new Bundle();
                bundle=msg.getData();
                ToastDialogs.getInstance().Dialogs(1,bundle.getString("Msg"," "),context,0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };
}
