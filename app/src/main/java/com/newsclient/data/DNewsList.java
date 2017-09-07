package com.newsclient.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

import static com.newsclient.tools.Http.sendGet;

public class DNewsList {
    public static ArrayList<DSingleNews> _news_list = new ArrayList<DSingleNews>();
    public static HashSet<String> _news_id_set = new HashSet<String>();
    public static int _size;

    public static void load() {
        String str = sendGet("http://166.111.68.66:2042/news/action/query/latest");
        try {
            JSONArray artList = new JSONArray(new JSONObject(str).getString("list"));
            _size = artList.length();
            for (int i = 0; i < _size; i++) {
                JSONObject art = artList.getJSONObject(i);
                DSingleNews news = new DSingleNews(art);
                _news_list.add(news);
                _news_id_set.add(art.getString("news_ID"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static DSingleNews getById(String id) {
        for (int i = 0; i < _size; i++) {
            if (_news_list.get(i).news_id.equals(id)) {
                return _news_list.get(i);
            }
        }
        return null;
    }
}

//class NewsSort implements Comparator {
//    @Override
//    public int compare(Object A, Object B){
//        DSingleNews _A = (DSingleNews) A;
//        DSingleNews _B = (DSingleNews) B;
//        return _A.news_id.compareTo(_B.news_id);
//    }
//}