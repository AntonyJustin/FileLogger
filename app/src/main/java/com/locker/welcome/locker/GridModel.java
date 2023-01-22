package com.locker.welcome.locker;

/**
 * Created by welcome on 29-Mar-20.
 */

public class GridModel {
    String mName = "";
    int mImage = 0;

    public GridModel(String mName, int mImage) {
        this.mName = mName;
        this.mImage = mImage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }
}
