package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.newsclient.R;
import com.newsclient.data.DNewsList;
import com.newsclient.data.DSingleNews;

public class VDetails extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        String id = intent.getStringExtra("news_id");

        DSingleNews news = DNewsList.getById(id);
        news.load();

        TextView tv = findViewById(R.id.textView2);
        tv.setText(news.content);
        //获取数据项
    }
}
