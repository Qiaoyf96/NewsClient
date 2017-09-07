package com.newsclient.data;
import android.app.Application;

import com.newsclient.data.DNewsList;

public class Data extends Application{
    public DTagList dtaglist;
    public boolean is_4G_mode_on, is_night_shift_on;
    @Override
    public void onCreate(){
        dtaglist = new DTagList();
        dtaglist.initialize();
        is_4G_mode_on = false;
        is_night_shift_on = false;
    }
    public void readFromFile(){

    }
    public void writeToFile(){

    }
}
