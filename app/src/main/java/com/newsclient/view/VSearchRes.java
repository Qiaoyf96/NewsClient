package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.newsclient.R;
import com.newsclient.data.DSearchResListData;

public class VSearchRes extends Activity {
    int pagenum;
    DSearchResListData searchres;
    LayoutInflater inflater;
    View progressbar, loadedbar;
    int last_index;
    int total_index;
    boolean isloading;
    String searchkey;
    ListView reslistview;
    int stop_position;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchres);
        inflater = LayoutInflater.from(this);
        progressbar = getLayoutInflater().inflate(R.layout.activity_searchres_progressbar, null);
        progressbar.setVisibility(View.VISIBLE);
        reslistview = (ListView)findViewById(R.id.searchres_listView);
        reslistview.addFooterView(progressbar);
        isloading = true;

        reslistview.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                last_index = firstVisibleItem + visibleItemCount;
                total_index = totalItemCount;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (last_index == total_index && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)) {
                    //表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
                    stop_position = reslistview.getFirstVisiblePosition();
                    if (isloading){
                        loadNewView();
                    }
                }
            }
        });

        reslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //点击后在标题上显示点击了第几行
                Intent intent = new Intent(VSearchRes.this, VDetails.class);
                try {
                    intent.putExtra("news_id", searchres._news_id_set.get(arg2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        searchkey = intent.getStringExtra("search_info");
        searchres = new DSearchResListData();
        pagenum = 0;
        loadNewView();
        last_index = 0;
        total_index = 0;
    }

    public void loadNewView(){
        TextView tv6 = (TextView)findViewById(R.id.textView6);
        pagenum = pagenum + 1;
        tv6.setText((5 << pagenum) + ".");
        searchres.load(searchkey, pagenum);
        if (searchres._news_title_list.size() == searchres._total)
            loadComplete();
        reslistview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchres._news_title_list));
        reslistview.setSelection(stop_position + 1);
    }

    public void loadComplete(){
        progressbar.setVisibility(View.GONE);
        loadedbar = getLayoutInflater().inflate(R.layout.activity_searchres_loaded, null);
        loadedbar.setVisibility(View.VISIBLE);
        reslistview.addFooterView(loadedbar);
        isloading = false;
        reslistview.removeFooterView(progressbar);
    }
}
