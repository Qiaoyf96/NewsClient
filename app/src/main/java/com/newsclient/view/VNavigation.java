package com.newsclient.view;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TabHost;

import com.newsclient.R;

public class VNavigation extends TabActivity {
    private TabHost tabhost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

            //从TabActivity上面获取放置Tab的TabHost
        tabhost = getTabHost();

        tabhost.addTab(tabhost
                //创建新标签one
                .newTabSpec("one")
                //设置标签标题
                .setIndicator("Recents")
                //设置该标签的布局内容
                .setContent(new Intent(this, VRecents.class)));
        tabhost.addTab(tabhost.newTabSpec("two").setIndicator("Tags").setContent(new Intent(this, VTags.class)));
        tabhost.addTab(tabhost.newTabSpec("three").setIndicator("Settings").setContent(new Intent(this, VSettings.class)));
    }
}
