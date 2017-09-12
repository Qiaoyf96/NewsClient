package com.newsclient.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.newsclient.R;
import com.newsclient.data.DNewsList;
import com.newsclient.data.DSingleNews;
import com.newsclient.data.DTagList;
import com.newsclient.tools.FileHelper;
import com.newsclient.tools.Network;

import java.util.ArrayList;
import java.util.HashMap;

class MyThread extends Thread {
    Context c;
    public void run() {
        FileHelper f = new FileHelper(c);
        try {
            if (Network.isConnected()) throw new Exception();
            DNewsList._news_list = (ArrayList<DSingleNews>) f.read("newslist.ser");
            DNewsList._size = (int) f.read("newssize.ser");
            DNewsList.page = (int[]) f.read("page.ser");
            if (DNewsList._news_list == null || DNewsList._news_list.size() == 0) throw new Exception();
        } catch (Exception e) {
            DNewsList.load();
        }
        try {
            DTagList.lstImageitem = (ArrayList<HashMap<String, Object>>) f.read("lstImageitem.ser");
            DTagList.lstItem = (ArrayList<String>) f.read("lstItem.ser");
            DTagList.lstdetail = (ArrayList<ArrayList<String>>) f.read("lstdetail.ser");
            DTagList.readedlist = (ArrayList<String>) f.read("readedlist.ser");
            DTagList.is_initialized = (boolean) f.read("isinitialized.ser");
        } catch (Exception e) {
        }
        if (Network.isConnected()) {
            for (int i = 1; i <= 12; i++) {
                DTagList.lstdetail.get(i).clear();
            }
        }
    }
}

public class VSplash extends Activity {
    private final int SPLASH_LENGTH = 2000;
    MyThread m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        m = new MyThread();
        m.c = getApplicationContext();
        m.start();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    m.join();
                } catch (InterruptedException e) {
                }
                Intent mainIntent = new Intent(VSplash.this,
                        VNavigation.class);
                VSplash.this.startActivity(mainIntent);
                VSplash.this.finish();
            }
        }, SPLASH_LENGTH);
    }
}
