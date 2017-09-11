package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.newsclient.R;
import com.newsclient.data.DNewsList;

class MyThread extends Thread {
    public void run() {
        DNewsList.load();
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
