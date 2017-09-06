package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.newsclient.R;

public class VDetails extends Activity {
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
