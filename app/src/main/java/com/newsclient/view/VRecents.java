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

    DSingleNews[] gotNews;
    //.
    protected DNewsList newslist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newslist = new DNewsList();
        setContentView(R.layout.activity_recentlist);
    }
    public void onResume(){
        super.onResume();

//        newslist.teststring = "14";

//        try {
//            ListView lv = (ListView)findViewById(R.id.listView);
//            TextView tv = (TextView)findViewById(R.id.textView3);
//            newslist.addNews("http://166.111.68.66:2042/news/action/query/latest");
//            tv.setText(newslist.teststring);
//
//            String[] strs = new String[newslist.newscnt];
//
//            for(int i = 0; i < newslist.newscnt; i++)
//                strs[i] = newslist._news_list.get(i).news_title;
//
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                        long arg3) {
//                    //点击后在标题上显示点击了第几行
//                    Intent intent = new Intent(VRecents.this, VDetails.class);
//                    try {
//                        intent.putExtra("news_content", newslist._news_list.get(arg2).content);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    startActivity(intent);
//                }
//            });
//
//            lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs));
//        } catch (Exception e) {
//        }

        gotNews = new DSingleNews[20];
        for (int i = 0; i < gotNews.length; i++){
            gotNews[i] = new DSingleNews();
            gotNews[i].news_title = "---" + i + "---";
            gotNews[i].news_source = "src";
        }
        int[] itemsId = new int[2];
        itemsId[0] = R.id.item_title;
        itemsId[1] = R.id.item_source;
        VRecyclerView recycler = new VRecyclerView(
                gotNews,
                VRecents.this,
                R.layout.item_recycler_view_item,
                R.id.recent_recycler_view,
                itemsId);
        recycler.generate();

    }
}
