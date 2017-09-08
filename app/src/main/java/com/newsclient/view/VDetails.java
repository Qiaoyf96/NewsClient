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

public class VDetails extends AppCompatActivity {
    TextToSpeech tts;
    FloatingActionButton btn;
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        String id = intent.getStringExtra("news_id");

        DSingleNews news = DNewsList.getById(id);
        if (news == null) {
            news = new DSingleNews(id);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SubMenu subMenu = menu.addSubMenu(1, 100, 100, "添加到tags");
        subMenu.add(2, 101, 101, "科技");
        subMenu.add(2, 102, 102, "娱乐");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                item.setIcon(android.R.drawable.btn_star_big_on);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
