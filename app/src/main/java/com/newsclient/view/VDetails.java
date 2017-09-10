package com.newsclient.view;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;

import com.newsclient.R;
import com.newsclient.data.DNewsList;
import com.newsclient.data.DSingleNews;
import com.newsclient.data.DTagList;
import com.newsclient.data.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class VDetails extends AppCompatActivity {
    DSingleNews news;
    ImageView intro;
    TextView title;
    TextView info;
    TextView content;

    TextToSpeech tts;
    FloatingActionButton btn;
    //TextView tv;
    String news_id;

    VSingleItemSelected adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        news_id = intent.getStringExtra("news_id");
        DTagList.addNewsToTag(-1, news_id);

        news = DNewsList.getById(news_id);
        if (news == null) {
            news = new DSingleNews(news_id);
        }
        news.readed = true;
        news.load();

        //tv = (TextView)findViewById(R.id.textView2);
        //tv.setText(news.content);
        setViewDisplay();

        tts = new TextToSpeech(this, null);
        btn = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        //实例化
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tts.speak(news.content, TextToSpeech.QUEUE_FLUSH, null);
                //语音输出
            }});

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Button deletebtn = (Button)findViewById(R.id.details_deletebtn);
        final ListView keywordlistview = (ListView)findViewById(R.id.details_keywordlistview);
        final ArrayList<String> displaykeywords = new ArrayList<String>();
        final Data app = (Data)getApplication();
        final HashMap<Integer,Boolean> isselected = new HashMap<Integer,Boolean>();
        for(int i = 0; i < news.wordList.length; i++){
            displaykeywords.add(news.wordList[i]);
            isselected.put(i, false);
        }
        adapter = new VSingleItemSelected(this, displaykeywords, isselected);
        keywordlistview.setAdapter(adapter);
        keywordlistview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ViewGroup.LayoutParams params = keywordlistview.getLayoutParams();

        View listviewitem = adapter.getView(0, null, keywordlistview);
        listviewitem.measure(0, 0);
        params.height = news.wordList.length * listviewitem.getMeasuredHeight();
        keywordlistview.setLayoutParams(params);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = adapter.getIsSelected().size() - 1; i >= 0; i--) {
                    if (adapter.getIsSelected().get(i).equals(true)) {
                        isselected.put(i, false);
                        app.blockwordlist.add(displaykeywords.get(i));
                        displaykeywords.remove(i);
                        isselected.remove(isselected.size() - 1);
                    }
                }
                keywordlistview.setAdapter(adapter);
                keywordlistview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                ViewGroup.LayoutParams params = keywordlistview.getLayoutParams();

                View listviewitem = adapter.getView(0, null, keywordlistview);
                listviewitem.measure(0, 0);
                params.height = displaykeywords.size() * listviewitem.getMeasuredHeight();

                keywordlistview.setLayoutParams(params);
                adapter.notifyDataSetChanged();
            }
        });
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return super.onSupportNavigateUp();
//    }

    void setViewDisplay(){
        intro = (ImageView) findViewById(R.id.articleDetailIntroImg);
        title = (TextView) findViewById(R.id.articleDetailTitleText);
        info = (TextView) findViewById(R.id.articleDetailSourceText);
        content = (TextView) findViewById(R.id.articleDetailContentText);

        if (news.news_intropic != null){

            intro.setImageBitmap(news.news_intropic);
        }
        else{
            intro.setVisibility(View.GONE);
        }
        title.setText(news.displayTitle());
        info.setText(news.displaySource() + "     " + news.displayTime());
        content.setText(news.displayContent());

    }

    @Override
    protected void onDestroy() {
        tts.stop();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        SubMenu subMenu = menu.addSubMenu(1, 100, 100, "添加到tags");

        ArrayList<HashMap<String, Object>> listItem =  DTagList.getListItem();

        int i = 0;
        for (HashMap<String, Object> item : listItem) {
            if (i == 0) {
                i++;
                continue;
            }
            subMenu.add(2, 100 + i, 100 + i, item.get("ItemText").toString());
            i++;
        }

        if (DTagList.isInTagList(0, news_id)) {
            MenuItem mi = (MenuItem) findViewById(R.id.action_favorite);
            menu.findItem(R.id.action_favorite).setIcon(android.R.drawable.btn_star_big_on);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;
        switch (id = item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_share:
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, news.news_intro + news.news_url);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
                return true;

        case R.id.action_favorite:
                if (DTagList.addNewsToTag(0, news_id))
                    item.setIcon(android.R.drawable.btn_star_big_on);
                else item.setIcon(android.R.drawable.btn_star_big_off);
                return super.onOptionsItemSelected(item);

            case android.R.id.home:
                tts.stop();
                finish();
                return true;

            default:
                if (id > 100) {
                    DTagList.addNewsToTag(id - 100, news_id);
                }
                return super.onOptionsItemSelected(item);
        }
    }
}
