package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.newsclient.R;
import com.newsclient.data.DSingleNews;
import com.newsclient.data.DSingleTag;
import com.newsclient.data.DTagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VTagList extends Activity {
    int tagId;
    //final DSingleTag dt;

    @Override
    protected void onResume() {
        final DSingleTag dt = DTagList.getNewsById(tagId);

        String[] titleList = new String[dt.size];

        for (int i = 0; i < dt.size; i++) {
            titleList[i] = dt.news_list.get(i).news_title;
        }

        ListView lv = (ListView) findViewById(R.id.listViewTag);
        //lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList));


        // simpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(this, getItemData(dt), R.layout.activity_cardview,
                new String[] {"card_title", "card_source", "card_intropic", "card_intro"},
                new int[] {R.id.card_title, R.id.card_source, R.id.card_intropic, R.id.card_intro});

        lv.setAdapter(adapter);

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



        super.onResume();
    }

    private List<Map<String, Object>> getItemData(DSingleTag tag){
        // data need:
        // String card_title
        // String card_source
        // Bitmap card_intropic
        // String card_intro
        List<Map<String, Object>> list = new ArrayList<>();
        for (DSingleNews news : tag.news_list){
            Map<String, Object> map = new HashMap<>();
            map.put("card_title", news.displayTitle());
            map.put("card_source", news.displaySource() + "  " + news.displayTime());
            map.put("card_intropic", news.news_intropic);
            map.put("card_intro", news.news_intro);
            list.add(map);
        }

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taglist);

        Intent intent = getIntent();
        tagId = intent.getIntExtra("tag_id", -1);

    }
}
