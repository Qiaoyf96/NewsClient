package com.newsclient.data;
import android.app.Application;

import com.newsclient.data.DNewsList;

import java.util.ArrayList;

public class Data extends Application{
    public DTagList dtaglist;
    public boolean is_4G_mode_on, is_night_shift_on;
    public static ArrayList<String> blockwordlist = new ArrayList<String>();
    @Override
    public void onCreate(){
        dtaglist = new DTagList();
        is_4G_mode_on = false;
        is_night_shift_on = false;
    }
    public void readFromFile(){

    }
    public void writeToFile(){

    }
}
