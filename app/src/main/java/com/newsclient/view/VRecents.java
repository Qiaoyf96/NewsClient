package com.newsclient.view;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.SearchView;
import android.os.Build.VERSION;

import com.newsclient.R;
import com.newsclient.data.DNewsList;
import com.newsclient.data.DSingleNews;
import com.newsclient.data.Data;
import com.newsclient.tools.FileHelper;
import com.newsclient.tools.SwipeRefresh;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

class SaveData extends Thread {
    Context c;
    @Override
    public void run() {
        while (true) {
            FileHelper f = new FileHelper(c);
            try {
                f.save("newslist.ser", DNewsList._news_list);
                f.save("newssize.ser", DNewsList._size);
                sleep(10000);
            } catch (Exception e1) {
                String str = e1.toString();
            }
        }
    }
}

public class VRecents extends FragmentActivity {
    Data app;
    static View v;
    static Drawable d;
    static boolean isthreadexist = false;
    static final int SDK_INT = VERSION.SDK_INT;
    public static Context context;
    VRecyclerView vRecyclerView;

    @Override
    protected void onResume() {
        super.onResume();
        SwipeRefresh.setList(new DNewsList());
        SwipeRefresh.setV(VRecents.this);

        if (vRecyclerView != null){
            vRecyclerView.refresh();
        }

        setTheme((app.is_night_shift_on) ? R.style.DarkTheme : R.style.LightTheme);

    }

    private NotificationHelper noti;
    private static final String TAG = VRecents.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (Data) this.getApplication();
        setTheme((app.is_night_shift_on) ? R.style.DarkTheme : R.style.LightTheme);
        setContentView(R.layout.activity_recentlist);


        context = VRecents.this;
        SaveData s = new SaveData();
        s.c = getApplication();
        s.start();

        if (SDK_INT > 25)
            noti = new NotificationHelper(this);
        if (isthreadexist == false) {
            isthreadexist = true;
            Thread pushthread = new mythread();
            pushthread.start();
        }

        SearchView searchview = (SearchView)findViewById(R.id.recentlist_searchview);

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(VRecents.this, VSearchRes.class);
                try{
                    intent.putExtra("search_info", query);
                }catch(Exception e){
                    e.printStackTrace();
                }
                startActivity(intent);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        int[] itemsId = new int[]{
                R.id.imageView2,
            R.id.item_title,
            R.id.item_source,
            R.id.item_time
        };

        vRecyclerView = new VRecyclerView(
                DNewsList._news_list,
                VRecents.this,
                R.layout.item_recycler_view_item,
                R.id.recent_recycler_view,
                itemsId);
        vRecyclerView.generate();
    }

    public void sendNotification(String title, String content) {
        Notification.Builder nb = null;
        nb = noti.getNotification2(title, content);

        if (nb != null) {
            noti.notify(1200, nb);
        }
    }

    /**
     * Send Intent to load system Notification Settings for this app.
     */
    public void goToNotificationSettings() {
        Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(i);
    }

    /**
     * Send intent to load system Notification Settings UI for a particular channel.
     *
     * @param channel Name of channel to configure
     */
    public void goToNotificationSettings(String channel) {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
        startActivity(i);
    }
    public int cnt;
    public void push(){
        if (isBackground(this)){
            DSingleNews pushnew = DNewsList.getRandomNews();
            sendNotification(pushnew.news_title, pushnew.news_intro.replace(" ", "").replace("\t", "").replace("　", ""));
        }
    }
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return false;
                }else{
                    return true;
                }
            }
        }
        return true;
    }
    class mythread extends Thread {
        @Override
        public void run() {
            isthreadexist = true;
            while (true){
                try {
                    sleep(60000);
                    push();
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
