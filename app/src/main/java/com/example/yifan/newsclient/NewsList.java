package com.example.yifan.newsclient;

import android.os.StrictMode;

import static com.example.yifan.newsclient.HttpGet.sendGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Comparator;
import java.util.Collections;

/**
 * Created by xemboliu on 6/9/2017.
 */

public class NewsList {
    public ArrayList<SingleNews> _news_list;
    public HashSet<String> _news_id_set;
    public int newscnt;
    public String teststring;
    NewsList(){
        _news_list = new ArrayList<SingleNews>();
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
                    SingleNews _new = new SingleNews();
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
                    String json = HttpGet.sendGet(url);
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

class NewsSort implements Comparator{
    @Override
    public int compare(Object A, Object B){
        SingleNews _A = (SingleNews) A;
        SingleNews _B = (SingleNews) B;
        return _A.news_id.compareTo(_B.news_id);
    }
}