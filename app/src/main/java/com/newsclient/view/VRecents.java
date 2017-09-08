package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.newsclient.data.DNewsList;
import com.newsclient.R;
import com.newsclient.data.DSingleNews;
import com.newsclient.view.VRecyclerView;

public class VRecents extends Activity {

    protected DNewsList newslist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newslist = new DNewsList();
        setContentView(R.layout.activity_recentlist);
    }
    public void onResume(){
        super.onResume();

        newslist.teststring = "14";
        newslist.addNews("http://166.111.68.66:2042/news/action/query/latest");

        int[] itemsId = new int[3];
        itemsId[0] = R.id.item_title;
        itemsId[1] = R.id.item_source;
        itemsId[2] = R.id.item_time;

        VRecyclerView recycler = new VRecyclerView(
                newslist.list(),
                VRecents.this,
                R.layout.item_recycler_view_item,
                R.id.recent_recycler_view,
                itemsId);
        recycler.generate();

    }
}
