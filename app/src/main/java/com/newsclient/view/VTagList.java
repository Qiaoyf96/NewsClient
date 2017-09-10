package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.newsclient.R;
import com.newsclient.data.DNewsList;
import com.newsclient.data.DSingleTag;
import com.newsclient.data.DTagList;

public class VTagList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taglist);

        Intent intent = getIntent();
        int tagId = intent.getIntExtra("tag_id", -1);

        final DSingleTag dt = DTagList.getNewsById(tagId);

        String[] titleList = new String[DNewsList._size];

        for (int i = 0; i < dt.size; i++) {
            titleList[i] = dt.news_list.get(i).news_title;
        }

        ListView lv = (ListView) findViewById(R.id.listViewTag);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //点击后在标题上显示点击了第几行
                Intent intent = new Intent(VTagList.this, VDetails.class);
                try {
                    intent.putExtra("news_id", dt.news_list.get(arg2).news_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }
}
