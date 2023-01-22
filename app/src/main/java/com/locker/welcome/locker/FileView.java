package com.locker.welcome.locker;

import java.io.File;

/**
 * Created by welcome on 01-Jun-20.
 */

public class FileView {
    public String FileName = "";
    public String Filelength="";
    public String LastModified="";
    public byte[] thumbnail=null;
    public int FileIndex=0;

    public FileView(String FileName, byte[] thumbnail,String Filelength,String LastModified,int FileIndex) {
        this.FileName = FileName;
        this.thumbnail = thumbnail;
        this.Filelength = Filelength;
        this.LastModified=LastModified;
        this.FileIndex=FileIndex;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getFileLength() {
        return FileName;
    }

    public void setFileLength(String FileName) {
        this.FileName = FileName;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLastModified() {
        return LastModified;
    }

    public void setLastModified(String LastModified) {
        this.LastModified = LastModified;
    }

    public int getFileIndex()
    {
        return FileIndex;
    }

    public void setFileIndex(int FileIndex)
    {
        this.FileIndex=FileIndex;
    }

}
