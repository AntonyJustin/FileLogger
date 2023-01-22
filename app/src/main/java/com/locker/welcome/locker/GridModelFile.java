package com.locker.welcome.locker;

import android.graphics.drawable.Drawable;

/**
 * Created by welcome on 01-Jun-20.
 */

public class GridModelFile {
    String Title = "",Subtitle="",Subtitle1="";
    Drawable mImage;

    public GridModelFile(String Title,String SubTitle,String SubTitle1, Drawable mImage) {
        this.Title = Title;
        this.Subtitle=SubTitle;
        this.Subtitle1=SubTitle1;
        this.mImage = mImage;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String SubTitle) {
        this.Subtitle = SubTitle;
    }

    public String getSubtitle1() {
        return Subtitle1;
    }

    public void setSubtitle1(String SubTitle1) {
        this.Subtitle1 = SubTitle1;
    }

    public Drawable getmImage() {
        return mImage;
    }

    public void setmImage(Drawable mImage) {
        this.mImage = mImage;
    }
}
