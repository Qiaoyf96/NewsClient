package com.java.group28.newsclient.data;

import com.java.group28.newsclient.tools.FileHelper;
import com.java.group28.newsclient.tools.Network;
import com.java.group28.newsclient.view.VRecents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.java.group28.newsclient.tools.Http.sendGet;

public class DNewsList {
    public static ArrayList<DSingleNews> _news_list;
    public static ArrayList<DSingleNews> news_list;
    public static int _size;
    public static int[] page = new int [20];

    public static int[] readtime = new int [20];
    public static int totaltime;

    public static void enlargeRecent() {
        if (!Network.isConnected()) return;
        int size = 0;
        Random r = new Random();
        while (true) {
            Collections.shuffle(_news_list);
            int length = _news_list.size();
            for (int i = 0; i < length; i++) {
                DSingleNews news = _news_list.get(i);
                if (!news_list.contains(news) && news.news_tag >= 1 && news.news_tag <= 12) {
                    boolean ff = true;
                    for (String s: Data.blockwordlist) {
                        if (news.news_title.contains(s)) {
                            ff = false;
                            break;
                        }
                    }
                    if (!ff) continue;
                    double p;
                    if (DNewsList.totaltime == 0) p = 0.5;
                    else p = DNewsList.readtime[news.news_tag] / (double) DNewsList.totaltime;
                    if (p > r.nextDouble()) {
                        news_list.add(news);
                        size++;
                        if (size > 10) return;
                    }
                }
            }
            for (int j = 1; j <= 12; j++) {
                enlarge(j);
            }
        }
    }

    public static void enlarge(int id) {
        if (!Network.isConnected()) return;
        page[id]++;
        String str = sendGet("http://166.111.68.66:2042/news/action/query/latest?pageNo=" + page[id] + "&pageSize=" + max(10, page[id] + 1) + "&category=" + id);
        try {
            JSONArray artList = new JSONArray(new JSONObject(str).getString("list"));
            _size += artList.length();
            int length = artList.length();
            for (int i = 0; i < length; i++) {
                JSONObject art = artList.getJSONObject(i);
                DSingleNews news = new DSingleNews(art, id);
                _news_list.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static int max(int a, int b) {
        if (a > b) return a;
        return b;
    }

    public static void load() {
        _news_list = new ArrayList<>();
        _size = 0;
        for (int i = 1; i <= 12; i++)
            page[i] = 0;
        for (int i = 1; i <= 12; i++)
            enlarge(i);
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