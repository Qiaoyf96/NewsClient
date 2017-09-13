package com.java.group28.newsclient.data;

import com.java.group28.newsclient.tools.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.java.group28.newsclient.tools.Http.sendGet;

public class DSingleTag extends DNewsList {
    public int id;
    public ArrayList<DSingleNews> news_list = new ArrayList<DSingleNews>();
    public int size = 0;

    public DSingleTag(int id) {
        this.id = id;
    }

    void set(ArrayList<String> id_list) {
        for (String id: id_list) {
            DSingleNews news = getById(id);
            if (news == null) news = new DSingleNews(id);
            news.load();
            _news_list.add(news);
            news_list.add(news);
            size++;
        }
    }

    public void enlarge() {
        DTagList.enlarge(id);
        set(DTagList.lstdetail.get(id));
    }

    public void enlarge(String keyword) {
        if (!Network.isConnected()) return;
        String str = sendGet("http://166.111.68.66:2042/news/action/query/search?keyword=" + keyword);
        try {
            JSONArray artList = new JSONArray(new JSONObject(str).getString("list"));
            int length = artList.length();
            for (int i = 0; i < length; i++) {
                JSONObject art = artList.getJSONObject(i);
                DSingleNews news = new DSingleNews(art, id);
                news_list.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
