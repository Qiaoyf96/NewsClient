package com.java.group28.newsclient.view;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.java.group28.newsclient.R;
import com.java.group28.newsclient.data.DNewsList;
import com.java.group28.newsclient.data.DSingleNews;
import com.java.group28.newsclient.data.DTagList;
import com.java.group28.newsclient.data.Data;
import com.java.group28.newsclient.tools.FileHelper;

import java.util.ArrayList;
import java.util.List;

class SaveData extends Thread {
    Context c;
    @Override
    public void run() {
        while (!VRecents.stop) {
            FileHelper f = new FileHelper(c);
            try {
                f.save("newslist.ser", DNewsList._news_list);
                f.save("newssize.ser", DNewsList._size);
                f.save("lstImageitem.ser", DTagList.lstImageitem);
                f.save("lstItem.ser", DTagList.lstItem);
                f.save("lstdetail.ser", DTagList.lstdetail);
                f.save("readedlist.ser", DTagList.readedlist);
                f.save("isinitialized.ser", DTagList.is_initialized);
                f.save("page.ser", DNewsList.page);
                f.save("readtime.ser", DNewsList.readtime);
                f.save("totaltime.ser", DNewsList.totaltime);
                f.save("anothernewslist.ser", DNewsList.news_list);
                f.save("4gmode.ser", Data.is_4G_mode_on);
                f.save("nightshift.ser", Data.is_night_shift_on);
                f.save("block.ser", Data.blockwordlist);
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
    static boolean stop = false;
    int totaldy = 0;
    int firstposition;
    SearchView searchview;
    VRecyclerView vRecyclerView;

    @Override
    protected void onResume() {
        super.onResume();

        if (vRecyclerView != null){
            vRecyclerView.refresh();
        }
        vRecyclerView.mRecyclerView.scrollToPosition(firstposition);
        setTheme((app.is_night_shift_on) ? R.style.DarkTheme : R.style.LightTheme);

        searchview.setBackgroundColor((app.is_night_shift_on)
                ? getResources().getColor(R.color.dark_mainBackgroundColor)
                : getResources().getColor(R.color.light_mainBackgroundColor));
        TextView tv = (TextView) searchview.findViewById(searchview.getResources().getIdentifier("android:id/search_src_text", null, null));
        tv.setTextColor((app.is_night_shift_on)
                ? getResources().getColor(R.color.dark_contentTextColor)
                : getResources().getColor(R.color.light_contentTextColor));

        SpannableString spanText = new SpannableString("Search News");
        spanText.setSpan(new ForegroundColorSpan(
                (app.is_night_shift_on)
                        ? getResources().getColor(R.color.dark_contentTextColor)
                        : getResources().getColor(R.color.light_contentTextColor)),
                0,
                spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        searchview.setQueryHint(spanText);
    }

    private NotificationHelper noti;
    private static final String TAG = VRecents.class.getSimpleName();

    class Tthread extends Thread {
        @Override
        public void run() {
            DNewsList.news_list = new ArrayList<>();
            DNewsList.enlargeRecent();
            super.run();
        }
    }

    class LoadThread extends Thread {
        @Override
        public void run() {
            DNewsList.enlargeRecent();
            super.run();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (Data) this.getApplication();
        setTheme((app.is_night_shift_on) ? R.style.DarkTheme : R.style.LightTheme);
        setContentView(R.layout.activity_recentlist);

        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeView.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                final Tthread t = new Tthread();
                t.start();
                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            t.join();
                        } catch (InterruptedException e) {
                        }
                        vRecyclerView.newsList = DNewsList.news_list;
                        vRecyclerView.generate();
                        swipeView.setRefreshing(false);
                    }
                }, 1000);
            }
        });

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

        searchview = (SearchView)findViewById(R.id.recentlist_searchview);


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

        if (DNewsList.news_list == null || DNewsList.news_list.size() == 0) {
            DNewsList.news_list = new ArrayList<>();
            DNewsList.enlargeRecent();
        }
        int[] itemsId = new int[]{
                R.id.imageView2,
            R.id.item_title,
            R.id.item_source,
            R.id.item_time
        };

        vRecyclerView = new VRecyclerView(
                DNewsList.news_list,
                VRecents.this,
                R.layout.item_recycler_view_item,
                R.id.recent_recycler_view,
                itemsId);
        vRecyclerView.mRecyclerView = (RecyclerView)findViewById(vRecyclerView.targetLayout);
        vRecyclerView.mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,int dy){
                super.onScrolled(recyclerView, dx, dy);
                totaldy = totaldy + dy;
                System.out.println(totaldy + " " + dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = layoutManager.getItemCount();
                int lastposition = layoutManager.findLastVisibleItemPosition();
                firstposition = layoutManager.findFirstVisibleItemPosition();
                if (itemCount < lastposition + 10) {
                    LoadThread t = new LoadThread();
                    t.start();
                }
            }

        });
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
