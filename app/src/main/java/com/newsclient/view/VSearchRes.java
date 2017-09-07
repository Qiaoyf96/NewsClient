package com.newsclient.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.content.Intent;
import android.widget.TextView;

import com.newsclient.R;

public class VSearchRes extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchres_progressbar);

        Intent intent = getIntent();
        String searchkey = intent.getStringExtra("search_info");
    }
}
