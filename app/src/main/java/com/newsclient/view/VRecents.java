package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.newsclient.R;
import com.newsclient.data.DNewsList;

public class VRecents extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recentlist);

        SearchView searchview = (SearchView)findViewById(R.id.recentlist_searchview);

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(VRecents.this, VSearchRes.class);
                try{
                    intent.putExtra("search_info", query);
                }catch(Exception e){
                    e.printStackTrace();
                }
                startActivity(intent);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        DNewsList.load();

        String[] titleList = new String[DNewsList._size];

        for (int i = 0; i < DNewsList._size; i++) {
            titleList[i] = DNewsList._news_list.get(i).news_title;
        }

        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //点击后在标题上显示点击了第几行
                Intent intent = new Intent(VRecents.this, VDetails.class);
                try {
                    intent.putExtra("news_id", DNewsList._news_list.get(arg2).news_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }
}
