package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.newsclient.R;
import com.newsclient.data.DNewsList;
import com.newsclient.data.DSingleNews;

public class VDetails extends Activity {
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
        btn = findViewById(R.id.floatingActionButton);
        //实例化
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tts.speak(tv.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                //语音输出
            }});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.stop();
    }
}
