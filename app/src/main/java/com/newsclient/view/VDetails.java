package com.newsclient.view;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import com.newsclient.R;
import com.newsclient.data.DNewsList;
import com.newsclient.data.DSingleNews;
import com.newsclient.data.DTagList;

import java.util.ArrayList;
import java.util.HashMap;

public class VDetails extends AppCompatActivity {
    TextToSpeech tts;
    FloatingActionButton btn;
    TextView tv;
    String news_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        news_id = intent.getStringExtra("news_id");
        DTagList.addNewsToTag(-1, news_id);

        DSingleNews news = DNewsList.getById(news_id);
        news.readed = true;
        if (news == null) {
            news = new DSingleNews(news_id);
        }
        news.load();

        tv = (TextView)findViewById(R.id.textView2);
        tv.setText(news.content);

        tts = new TextToSpeech(this, null);
        btn = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        //实例化
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tts.speak(tv.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                //语音输出
            }});

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return super.onSupportNavigateUp();
//    }

    @Override
    protected void onDestroy() {
        tts.stop();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SubMenu subMenu = menu.addSubMenu(1, 100, 100, "添加到tags");

        ArrayList<HashMap<String, Object>> listItem =  DTagList.getListItem();

        int i = 0;
        for (HashMap<String, Object> item : listItem) {
            if (i == 0) {
                i++;
                continue;
            }
            subMenu.add(2, 100 + i, 100 + i, item.get("ItemText").toString());
            i++;
        }

        if (DTagList.isInTagList(0, news_id)) {
            MenuItem mi = (MenuItem) findViewById(R.id.action_favorite);
            menu.findItem(R.id.action_favorite).setIcon(android.R.drawable.btn_star_big_on);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;
        switch (id = item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                if (DTagList.addNewsToTag(0, news_id))
                    item.setIcon(android.R.drawable.btn_star_big_on);
                else item.setIcon(android.R.drawable.btn_star_big_off);
                return super.onOptionsItemSelected(item);

            case android.R.id.home:
                finish();
                return true;

            default:
                if (id > 100) {
                    DTagList.addNewsToTag(id - 100, news_id);
                }
                return super.onOptionsItemSelected(item);
        }
    }
}
