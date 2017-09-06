package com.newsclient.data;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import static com.newsclient.tools.Http.sendGet;

public class DNewsList {
    public ArrayList<DSingleNews> _news_list;
    public HashSet<String> _news_id_set;
    public int newscnt;
    public String teststring;
    public DNewsList(){
        _news_list = new ArrayList<DSingleNews>();
        _news_id_set = new HashSet<String>();
        newscnt = 0;
    }
    public void addNews(String _url){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String str = sendGet(_url);
            JSONObject resultObj = new JSONObject(str);
            //获取数据项
            String article_list = resultObj.getString("list");
            final JSONArray objectList = new JSONArray(article_list);
            int length = objectList.length();
            for (int i = 0; i < length; i++) {
                JSONObject o = objectList.getJSONObject(i);
                String _id = o.getString("news_ID");
                if (!_news_id_set.contains(_id)){
                    _news_id_set.add(_id);
                    DSingleNews _new = new DSingleNews();
                    _new.lang_type = o.getString("lang_Type");
                    _new.news_author = o.getString("news_Author");
                    _new.news_id = o.getString("news_ID");
                    _new.news_pictures = o.getString("news_Pictures");
                    _new.news_time = o.getString("news_Time");
                    _new.news_title = o.getString("news_Title");
                    _new.news_url = o.getString("news_URL");
                    _new.news_video = o.getString("news_Video");
                    _new.news_intro = o.getString("news_Intro");
                    teststring = _new.news_title;
                    _news_list.add(_new);
                    newscnt = newscnt + 1;
                    String url = "http://166.111.68.66:2042/news/action/query/detail?newsId=" + _new.news_id;
                    String json = sendGet(url);
                    JSONObject rresultObj = null;
                    rresultObj = new JSONObject(json);
                    try {
                        _new.content = rresultObj.getString("news_Content");
                    } catch (JSONException e) {
                    }

                }
            }
        } catch (JSONException e) {
        }
        NewsSort ns = new NewsSort();
        Collections.sort(_news_list, ns);
    }
}

class NewsSort implements Comparator {
    @Override
    public int compare(Object A, Object B){
        DSingleNews _A = (DSingleNews) A;
        DSingleNews _B = (DSingleNews) B;
        return _A.news_id.compareTo(_B.news_id);
    }
}