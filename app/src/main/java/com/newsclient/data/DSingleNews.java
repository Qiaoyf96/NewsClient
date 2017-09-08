package com.newsclient.data;

import org.json.JSONException;
import org.json.JSONObject;

import static com.newsclient.tools.Http.sendGet;

import com.newsclient.tools.StringFormatTransfer;

public class DSingleNews {
    public String lang_type, newsclasstag, news_author,
            news_id, news_pictures, news_source, news_time,
            news_title, news_url, news_video, news_intro, content;
    public boolean readed;
    public boolean loaded;
    DSingleNews(JSONObject o){
        news_id = "";
        content = "";
        readed = false;
        loaded = false;
        try {
            lang_type = o.getString("lang_Type");
            news_author = o.getString("news_Author");
            news_id = o.getString("news_ID");
            news_pictures = o.getString("news_Pictures");
            news_time = o.getString("news_Time");
            news_title = o.getString("news_Title");
            news_url = o.getString("news_URL");
            news_video = o.getString("news_Video");
            news_intro = o.getString("news_Intro");
            news_source = o.getString("news_Source");
            news_time = o.getString("news_Time");
        } catch (JSONException e) {
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
        } catch (JSONException e) {
            content = e.toString();
        }
    }

    public String displayTitle(int index){
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
}
