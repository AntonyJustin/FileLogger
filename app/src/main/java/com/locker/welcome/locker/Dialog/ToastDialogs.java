package com.locker.welcome.locker.Dialog;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.locker.welcome.locker.Asyncsfiles;
import com.locker.welcome.locker.Global;
import com.locker.welcome.locker.LockerApp;
import com.locker.welcome.locker.Menu;
import com.locker.welcome.locker.UnlockFiles;

import static com.locker.welcome.locker.LockerApp.getApp;


/**
 * Created by welcome on 01-Apr-20.
 */

public class ToastDialogs {
    boolean Dialogshow =true;
    private static ToastDialogs toasts;
    public Context context;
    int ret;

    public static ToastDialogs getInstance() {
        if (toasts == null) {
            toasts = new ToastDialogs();
        }
        return toasts;
    }

    public int Dialogs(final int Type,final String msg,final Context context,final  int Complete)
    {
        Dialogshow = true;
        LockerApp.getApp().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DialogBox cDialog = new DialogBox(context,Type,msg, new MsgShowListener() {
                                                    @Override
                                                    public void onSucc() {
                                                        ret=1;
                                                        Dialogshow = false;
                                                    }

                                                    @Override
                                                    public void onErr() {
                                                        ret=0;
                                                        Dialogshow = false;
                                                    }
                                                });
                                                cDialog.show();
                                            }
                                        }

        );

        if(Complete == 1)
        {
            while (Dialogshow) {
                SystemClock.sleep(400);
            }
        }
        return ret;
    }

    public int ChooseAction(final int type, final int index, final Context context, final Activity activity, final  int Complete)
    {
        Dialogshow = true;
        LockerApp.getApp().runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {
                                                 Chooseaction cDialog = new Chooseaction(activity,context,type,index,new MsgShowListener() {
                                                     @Override
                                                     public void onSucc() {
                                                         ret=1;
                                                         Dialogshow = false;
                                                         if(Global.lockorunlock==1)
                                                         {
                                                             Asyncsfiles.Unlock unlock=new Asyncsfiles.Unlock();
                                                             unlock.execute(index);
                                                         }
                                                         else if(Global.lockorunlock==0)
                                                         {
                                                             Global.uripath= Uri.fromFile(UnlockFiles.files[index]).getPath();
                                                             Menu.lockfiles();
                                                         }
                                                     }

                                                     @Override
                                                     public void onErr() {
                                                         ret=0;
                                                         Dialogshow = false;
                                                     }
                                                 });
                                                 cDialog.show();
                                             }
                                         }

        );

        if(Complete == 1)
        {
            while (Dialogshow) {
                SystemClock.sleep(400);
            }
        }
        return ret;
    }

    public int EnterFileName(final Context context,final String filename,final  int Complete)
    {
        Dialogshow = true;
        LockerApp.getApp().runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {
                                                 Enterfilename cDialog = new Enterfilename(context,filename, new MsgShowListener() {
                                                     @Override
                                                     public void onSucc() {
                                                         ret=1;
                                                         Dialogshow = false;
                                                     }

                                                     @Override
                                                     public void onErr() {
                                                         ret=0;
                                                         Dialogshow = false;
                                                     }
                                                 });
                                                 cDialog.show();
                                             }
                                         }

        );

        if(Complete == 1)
        {
            while (Dialogshow) {
                SystemClock.sleep(400);
            }
        }
        return ret;
    }
}
