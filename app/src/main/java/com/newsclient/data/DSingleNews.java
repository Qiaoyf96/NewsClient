package com.newsclient.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newsclient.tools.StringFormatTransfer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import static com.newsclient.tools.Http.sendGet;

public class DSingleNews {
    public String lang_type, newsclasstag, news_author,
            news_id, news_source, news_time,
            news_title, news_url, news_video, news_intro, content;
    public String[] news_pictures;
    public Bitmap news_intropic;
    public boolean readed;
    public boolean loaded;
    public String[] wordList;
    public double[] scoreList;
    DSingleNews(JSONObject o){
        news_id = "";
        content = "";
        readed = false;
        loaded = false;
        try {
            lang_type = o.getString("lang_Type");
            news_author = o.getString("news_Author");
            news_id = o.getString("news_ID");
            news_pictures = o.getString("news_Pictures").split(";");
            news_time = o.getString("news_Time");
            news_title = o.getString("news_Title");
            news_url = o.getString("news_URL");
            news_video = o.getString("news_Video");
            news_intro = o.getString("news_Intro");
            news_source = o.getString("news_Source");
            news_time = o.getString("news_Time");
        } catch (JSONException e) {
        }
        if (news_pictures.length > 0) {
            try {
                String Url = news_pictures[0];
                URL url = new URL(Url);

                String responseCode = url.openConnection().getHeaderField(0);

                news_intropic = BitmapFactory.decodeStream(url.openStream());

            } catch (Exception e) {
            }
        }
    }
    public DSingleNews(String id) {
        news_id = id;
    }
    public void load() {
        loaded = true;
        String url = "http://166.111.68.66:2042/news/action/query/detail?newsId=" + news_id;
        String txt = sendGet(url);
        try {
            JSONObject art = new JSONObject(txt);
            content = art.getString("news_Content");
            news_title = art.getString("news_Title");
            news_time = art.getString("news_Time");
            news_source = art.getString("news_Source");
            news_intro = "";
            news_pictures = art.getString("news_Pictures").split(";");
            if (news_pictures.length > 0) {
                try {
                    String Url = news_pictures[0];
                    URL pic_url = new URL(Url);

                    String responseCode = pic_url.openConnection().getHeaderField(0);

                    news_intropic = BitmapFactory.decodeStream(pic_url.openStream());

                } catch (Exception e) {
                }
            }

            JSONArray wordsList = art.getJSONArray("Keywords");
            int length = wordsList.length();
            length = min(5, length);
            wordList = new String [length];
            scoreList = new double [length];
            for (int i = 0; i < length; i++) {
                JSONObject word = wordsList.getJSONObject(i);
                wordList[i] = word.getString("word");
                scoreList[i] = word.getDouble("score");
            }
        } catch (JSONException e) {
            content = e.toString();
        }
    }

    private int min(int a, int b) {
        if (a < b) return a;
        return b;
    }

    public String displayTitle(){
        return news_title;
    }

    public String displaySource(){
        return StringFormatTransfer.toDBC("来源： " + news_source);
    }

    public String displayTime(){
        // time's format:
        // 20160912000000
        return Integer.parseInt(news_time.substring(4, 6)) + "月" + Integer.parseInt(news_time.substring(6, 8)) + "日";
    }

    public String displayContent(){
        return content.replaceAll("  ", "\n");
    }
}
