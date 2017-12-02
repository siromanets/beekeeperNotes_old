package com.example.key.beekeepernote.models;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by key on 02.11.17.
 */

public class Notifaction implements Serializable {
    public int uId;
    public String nameNotifaction;
    public int typeNotifaction;
    public long schowTime;
    public String textNotifaction;
    public String pathNotifaction;



    public Notifaction(){
    }
    public Notifaction(int uId, String nameNotifaction, int typeNotifaction, long schowTime, String textNotifaction, String pathNotifaction){
        this.uId = uId;
        this.nameNotifaction = nameNotifaction;
        this.typeNotifaction = typeNotifaction;
        this.schowTime = schowTime;
        this.textNotifaction = textNotifaction;
        this.pathNotifaction = pathNotifaction;
    }
    public String getNameNotifaction() {
        return nameNotifaction;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }
    public void setNameNotifaction(String nameNotifaction) {
        this.nameNotifaction = nameNotifaction;
    }
    public String getPathNotifaction() {
        return pathNotifaction;
    }

    public void setPathNotifaction(String pathNotifaction) {
        this.pathNotifaction = pathNotifaction;
    }
    public int getTypeNotifaction() {
        return typeNotifaction;
    }

    public void setTypeNotifaction(int typeNotifaction) {
        this.typeNotifaction = typeNotifaction;
    }

    public long getSchowTime() {
        return schowTime;
    }

    public void setSchowTime(long schowTime) {
        this.schowTime = schowTime;
    }

    public String getTextNotifaction() {
        return textNotifaction;
    }

    public void setTextNotifaction(String textNotifaction) {
        this.textNotifaction = textNotifaction;
    }

    public String createPath(String apiary, String beehive, String beeColony){
        Uri uri =   new Uri.Builder().appendPath(apiary).appendPath(beehive).appendPath(beeColony).build();

        return uri.toString();
    }
}
