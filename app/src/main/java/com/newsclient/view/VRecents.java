package com.newsclient.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.newsclient.R;
import com.newsclient.data.DNewsList;
import com.newsclient.tools.SwipeRefresh;

public class VRecents extends FragmentActivity {
    @Override
    protected void onResume() {
        super.onResume();
        SwipeRefresh.setList(new DNewsList());
        SwipeRefresh.setV(VRecents.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recentlist);

        DNewsList.load();

        SwipeRefresh.setList(new DNewsList());
        SwipeRefresh.setV(VRecents.this);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SwipeRefresh.SwipeRefreshListFragmentFragment fragment = new SwipeRefresh.SwipeRefreshListFragmentFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();

        }


//        String[] titleList = new String[DNewsList._size];
//
//        for (int i = 0; i < DNewsList._size; i++) {
//            titleList[i] = DNewsList._news_list.get(i).news_title;
//        }
//
//        ListView lv = (ListView)findViewById(R.id.listView);
//        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList));
//
//        ListView lv = findViewById(R.id.sample_content_fragment);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                //点击后在标题上显示点击了第几行
//                Intent intent = new Intent(VRecents.this, VDetails.class);
//                try {
//                    intent.putExtra("news_id", DNewsList._news_list.get(arg2).news_id);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                startActivity(intent);
//            }
//        });
    }
}
