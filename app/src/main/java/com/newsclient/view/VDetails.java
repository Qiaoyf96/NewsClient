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
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
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
import com.newsclient.tools.PicGetter;

import java.util.ArrayList;
import java.util.HashMap;

public class VDetails extends AppCompatActivity implements View.OnClickListener {
    DSingleNews news;
    ImageView intro;
    VAlignTextView title;
    TextView info;
    TextView content;

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
                int code = mySynthesizer.startSpeaking(news.content, mTtsListener);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        ConnectivityManager mConnectivityManager = (ConnectivityManager) VRecents.context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
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
        for(int i = 0; i < news.wordList.length; i++){
            displaykeywords.add(news.wordList[i]);
            isselected.put(i, false);
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
        intro = (ImageView) findViewById(R.id.articleDetailIntroImg);
        title = (VAlignTextView) findViewById(R.id.articleDetailTitleText);
        info = (TextView) findViewById(R.id.articleDetailSourceText);
        content = (TextView) findViewById(R.id.articleDetailContentText);


        ConnectivityManager mConnectivityManager = (ConnectivityManager) VRecents.context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            PicGetter p = new PicGetter(VDetails.this);
            p.setImageView(intro, news);
        }

        title.setLineSpacing(1.4f);
        title.setText(news.displayTitle());
        info.setText(news.displaySource() + "     " + news.displayTime());
        content.setText(news.displayContent());

    }

    @Override
    public void onBackPressed() {
        mySynthesizer.stopSpeaking();
        super.onBackPressed();
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
//                if (news.news_intropic != null) {
//                    File file = new File("/sdcard/temp.jpeg");
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                    }
//                    OutputStream os = null;
//                    try {
//                        os = new BufferedOutputStream(new FileOutputStream(file));
//                        news.news_intropic.compress(Bitmap.CompressFormat.JPEG, 100, os);
//                        os.close();
//                    } catch (Exception e) {
//                        String str = e.toString();
//                    }
//                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//                }
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
                if (id > 100) {
                    DTagList.addNewsToTag(id - 100, news_id);
                }
                return super.onOptionsItemSelected(item);
        }
    }
}
