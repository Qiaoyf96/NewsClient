package com.newsclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by Yifan on 2017/9/5.
 */

public class Article extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        TextView tv = findViewById(R.id.textView2);
        Intent intent = getIntent();
        String txt = intent.getStringExtra("news_content");
        tv.setText(txt);
        //获取数据项
    }
}
