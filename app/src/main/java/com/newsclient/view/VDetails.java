package com.newsclient.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.newsclient.R;
import com.newsclient.data.DNewsList;
import com.newsclient.data.DSingleNews;
import com.newsclient.data.DTagList;
import com.newsclient.data.Data;
import com.newsclient.tools.Network;
import com.newsclient.tools.PicGetter;

import java.util.ArrayList;
import java.util.HashMap;

public class VDetails extends AppCompatActivity implements View.OnClickListener {
    DSingleNews news;
    ImageView intropic;
    VAlignTextView title;
    TextView info;
    TextView content;
    Data app;

    FloatingActionButton btn;
    //TextView tv;
    String news_id;

    VSingleItemSelected adapter;

    private SpeechSynthesizer mySynthesizer;

    private InitListener myInitListener = new InitListener() {
        @Override
        public void onInit(int i) {
        }
    };

    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingActionButton:
                ConnectivityManager mConnectivityManager = (ConnectivityManager) VRecents.context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo == null) {
                    Toast.makeText(VDetails.this, "没有网络连接", Toast.LENGTH_SHORT).show();
                }

                if (mySynthesizer.isSpeaking()) {
                    mySynthesizer.stopSpeaking();
                    break;
                }
                //设置发音人
                mySynthesizer.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
                //设置音调
                mySynthesizer.setParameter(SpeechConstant.PITCH,"50");
                //设置音量
                mySynthesizer.setParameter(SpeechConstant.VOLUME,"50");
                int code = mySynthesizer.startSpeaking(news.readable_content, mTtsListener);
                break;
            default:
                break;
        }
    }

//    @Override
//    protected void onResume(){
//        super.onResume();
//        setTheme((app.is_night_shift_on) ? R.style.DarkTheme : R.style.LightTheme);
//        setContentView(R.layout.activity_article);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (Data)this.getApplication();
        setTheme((app.is_night_shift_on) ? R.style.DarkTheme : R.style.LightTheme);
        //setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_article);


        SpeechUtility.createUtility(VDetails.this, "appid=59b53f3d");
        mySynthesizer = SpeechSynthesizer.createSynthesizer(this, myInitListener);

        Intent intent = getIntent();
        news_id = intent.getStringExtra("news_id");

        news = DNewsList.getById(news_id);
        if (news == null) {
            news = new DSingleNews(news_id);
            DNewsList._news_list.add(news);
            DNewsList._size++;
        }
        news.readed = true;

        if (news.news_tag > 0 && news.news_tag <= 12) {
            DNewsList.totaltime++;
            DNewsList.readtime[news.news_tag]++;
        }

        if (Network.isConnected()) {
            news.load();
        }

        DTagList.addNewsToTag(-1, news_id);

        setViewDisplay();

        btn = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        btn.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Button deletebtn = (Button)findViewById(R.id.details_deletebtn);
        final ListView keywordlistview = (ListView)findViewById(R.id.details_keywordlistview);
        final ArrayList<String> displaykeywords = new ArrayList<String>();
        final Data app = (Data)getApplication();
        final HashMap<Integer,Boolean> isselected = new HashMap<Integer,Boolean>();
        if (news.wordList != null) {
            for (int i = 0; i < news.wordList.length; i++) {
                displaykeywords.add(news.wordList[i]);
                isselected.put(i, false);
            }
        }
        adapter = new VSingleItemSelected(this, displaykeywords, isselected);
        keywordlistview.setAdapter(adapter);
        keywordlistview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ViewGroup.LayoutParams params = keywordlistview.getLayoutParams();
        try {
            View listviewitem = adapter.getView(0, null, keywordlistview);
            listviewitem.measure(0, 0);
            params.height = news.wordList.length * listviewitem.getMeasuredHeight();
        }catch (Exception e){
            params.height = 0;
        }
        keywordlistview.setLayoutParams(params);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getIsSelected().size() > 0){
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
                    try {
                        View listviewitem = adapter.getView(0, null, keywordlistview);
                        listviewitem.measure(0, 0);
                        params.height = displaykeywords.size() * listviewitem.getMeasuredHeight();
                    }catch (Exception e){
                        params.height = 0;
                    }

                    keywordlistview.setLayoutParams(params);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    void setViewDisplay(){
        intropic = (ImageView) findViewById(R.id.articleDetailIntroImg);
        title = (VAlignTextView) findViewById(R.id.articleDetailTitleText);
        info = (TextView) findViewById(R.id.articleDetailSourceText);
        content = (TextView) findViewById(R.id.articleDetailContentText);

        if (app.is_4G_mode_on){
            intropic.setVisibility(View.GONE);
        }
        else {
            if (Network.isConnected()) {
                PicGetter p = new PicGetter(VDetails.this);
                p.setImageView(intropic, news);
            }
            else {
                intropic.setVisibility(View.GONE);
            }
        }

        title.setLineSpacing(1.4f);
        title.setText(news.displayTitle());
        info.setText(news.displaySource() + "     " + news.displayTime());
        content.setText(Html.fromHtml(news.displayContent()));
        //
        content.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public void onBackPressed() {
        mySynthesizer.stopSpeaking();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
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
            case R.id.action_share:
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "「Instant News：」" + news.news_intro + news.news_url);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
                return true;

            case R.id.action_favorite:
                if (DTagList.addNewsToTag(0, news_id))
                    item.setIcon(android.R.drawable.btn_star_big_on);
                else item.setIcon(android.R.drawable.btn_star_big_off);
                return super.onOptionsItemSelected(item);

            case android.R.id.home:
                mySynthesizer.stopSpeaking();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
