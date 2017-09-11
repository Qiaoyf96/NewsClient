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
import com.newsclient.tools.FileHelper;

import java.util.ArrayList;

class MyThread extends Thread {
    Context c;
    public void run() {
        FileHelper f = new FileHelper(c);
        try {
//            throw new Exception();
            DNewsList._news_list = (ArrayList<DSingleNews>) f.read("newslist.ser");
            DNewsList._size = (int) f.read("newssize.ser");
            if (DNewsList._news_list == null || DNewsList._news_list.size() == 0) throw new Exception();
        } catch (Exception e) {
            DNewsList.load();
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
