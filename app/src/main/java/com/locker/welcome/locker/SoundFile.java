package com.locker.welcome.locker;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Vibrator;

import java.io.IOException;

import static com.locker.welcome.locker.Asyncsfiles.context;

/**
 * Created by Justin on 12-07-2020.
 */

public class SoundFile {
    static Context context;

    public SoundFile(Context context)
    {
        this.context=context;
    }

    public static MediaPlayer Button_click_sound;
    public static MediaPlayer Card_flip_sound;
    public static MediaPlayer Clunk_sound_effect;
    public static MediaPlayer Coin_drop_sound;
    public static MediaPlayer Horror_blade_swoosh;
    public static MediaPlayer Metal_door_lock_sound;
    public static MediaPlayer Push_button_sound_effect;
    public static MediaPlayer Single_coin;
    public static MediaPlayer Small_rock_water_splash;
    public static MediaPlayer Success_sound_effect;
    public static MediaPlayer Switch_click_sound;
    public static MediaPlayer Fuzzy_Beep;

    public static AssetFileDescriptor afd;


    public static void Vibtration(long value) {
        if (Global.VibrationEnable==1)
        {
            Global.vibrator=(Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
            Global.vibrator.vibrate(value);
        }
    }

    public static void NotificationSound(int type)
    {
        try {
            if(Global.SoundEnable==1)
            {
                if (type == 1)
                {
                    Button_click_sound=new MediaPlayer();
                    afd=context.getAssets().openFd("Button-click-sound.mp3");
                    Button_click_sound.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Button_click_sound.prepare();
                    Button_click_sound.start();
                }
                else if(type == 2)
                {
                    Card_flip_sound=new MediaPlayer();
                    afd=context.getAssets().openFd("Card-flip-sound.mp3");
                    Card_flip_sound.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Card_flip_sound.prepare();
                    Coin_drop_sound.start();
                }
                else if(type == 3)
                {
                    Clunk_sound_effect=new MediaPlayer();
                    afd=context.getAssets().openFd("Clunk-sound-effect.mp3");
                    Clunk_sound_effect.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Clunk_sound_effect.prepare();
                    Clunk_sound_effect.start();
                }
                else if(type == 4)
                {
                    Coin_drop_sound=new MediaPlayer();
                    afd=context.getAssets().openFd("Coin-drop-sound.mp3");
                    Coin_drop_sound.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Coin_drop_sound.prepare();
                    Coin_drop_sound.start();
                }
                else if(type == 5)
                {
                    Horror_blade_swoosh=new MediaPlayer();
                    afd=context.getAssets().openFd("Horror-blade-swoosh.mp3");
                    Horror_blade_swoosh.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Horror_blade_swoosh.prepare();
                    Horror_blade_swoosh.start();
                }
                else if(type == 6)
                {
                    Metal_door_lock_sound=new MediaPlayer();
                    afd=context.getAssets().openFd("Metal-door-lock-sound.mp3");
                    Metal_door_lock_sound.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Metal_door_lock_sound.prepare();
                    Metal_door_lock_sound.start();
                }
                else if(type == 7)
                {
                    Push_button_sound_effect=new MediaPlayer();
                    afd=context.getAssets().openFd("Push-button-sound-effect.mp3");
                    Push_button_sound_effect.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Push_button_sound_effect.prepare();
                    Push_button_sound_effect.start();
                }
                else if(type == 8)
                {
                    Single_coin=new MediaPlayer();
                    afd=context.getAssets().openFd("Single-coin.mp3");
                    Single_coin.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Single_coin.prepare();
                    Single_coin.start();
                }
                else if(type == 9)
                {
                    Small_rock_water_splash=new MediaPlayer();
                    afd=context.getAssets().openFd("Small-rock-water-splash.mp3");
                    Small_rock_water_splash.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Small_rock_water_splash.prepare();
                    Small_rock_water_splash.start();
                }
                else if(type == 10)
                {
                    Success_sound_effect=new MediaPlayer();
                    afd=context.getAssets().openFd("Success-sound-effect.mp3");
                    Success_sound_effect.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Success_sound_effect.prepare();
                    Success_sound_effect.start();
                }
                else if(type == 11)
                {
                    Switch_click_sound=new MediaPlayer();
                    afd=context.getAssets().openFd("Switch-click-sound.mp3");
                    Switch_click_sound.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Switch_click_sound.prepare();
                    Switch_click_sound.start();
                }
                else if(type == 12)
                {
                    Fuzzy_Beep=new MediaPlayer();
                    afd=context.getAssets().openFd("Fuzzy-Beep.mp3");
                    Fuzzy_Beep.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    Fuzzy_Beep.prepare();
                    Fuzzy_Beep.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SoundRelease()
    {
        Switch_click_sound.release();
    }

    public static void SoundReset()
    {
        Button_click_sound.reset();
        Card_flip_sound.reset();
        Clunk_sound_effect.reset();
        Coin_drop_sound.reset();
        Horror_blade_swoosh.reset();
        Metal_door_lock_sound.reset();
        Push_button_sound_effect.reset();
        Single_coin.reset();
        Small_rock_water_splash.reset();
        Success_sound_effect.reset();
        Switch_click_sound.reset();
        Fuzzy_Beep.reset();
    }
}
