package com.java.group28.newsclient.data;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
import com.java.group28.newsclient.tools.Network;

import java.util.ArrayList;

public class Data extends Application{
    public DTagList dtaglist;
    public static boolean is_4G_mode_on, is_night_shift_on;
    public static ArrayList<String> blockwordlist = new ArrayList<String>();
    @Override
    public void onCreate(){
        SpeechUtility.createUtility(Data.this, "appid=59b53f3d");
        dtaglist = new DTagList();
        dtaglist.initialize();
        is_4G_mode_on = false;
        is_night_shift_on = false;
        Network.c = Data.this;
        super.onCreate();
    }
    public void readFromFile(){

    }
    public void writeToFile(){

    }


}
