package com.example.yifan.newsclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.yifan.newsclient.HttpGet.sendGet;

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
        String news_id = intent.getStringExtra("news_id");
        String url = "http://166.111.68.66:2042/news/action/query/detail?newsId=" + news_id;
        String json = HttpGet.sendGet(url);
        JSONObject resultObj = null;
        try {
            resultObj = new JSONObject(json);
            String txt = resultObj.getString("news_Content");
            tv.setText(txt);
        } catch (JSONException e) {
            tv.setText(e.toString());
        }
        //获取数据项
    }
}
