package com.newsclient.data;

import com.newsclient.tools.FileHelper;
import com.newsclient.view.VRecents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static com.newsclient.tools.Http.sendGet;

public class DNewsList {
    public static ArrayList<DSingleNews> _news_list;
    public static int _size;

    public static void load() {
        _news_list = new ArrayList<>();
        String str = sendGet("http://166.111.68.66:2042/news/action/query/latest?pageNo=1&pageSize=20");
        try {
            JSONArray artList = new JSONArray(new JSONObject(str).getString("list"));
            _size = artList.length();
            for (int i = 0; i < _size; i++) {
                JSONObject art = artList.getJSONObject(i);
                DSingleNews news = new DSingleNews(art);
                _news_list.add(news);
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

    public static ArrayList<String> list() {
        Collections.shuffle(_news_list);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < _size; i++)
            list.add(_news_list.get(i).news_title);
        return list;
    }

    public static DSingleNews getRandomNews() {
        Collections.shuffle(_news_list);
        _news_list.get(0).load();
        return _news_list.get(0);
    }

    @Override
    protected void finalize() throws Throwable {
        FileHelper f = new FileHelper(VRecents.context);
        try {
            f.save("/tmp/newslist.ser", DNewsList._news_list);
            f.save("/tmp/newssize.ser", DNewsList._size);
        } catch (Exception e) {
        }
        super.finalize();
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