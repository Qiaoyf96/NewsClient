package com.newsclient.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.newsclient.tools.Http.sendGet;

/**
 * Created by xemboliu on 8/9/2017.
 */

public class DSearchResListData {
    public ArrayList<String> _news_title_list = new ArrayList<String>();
    public ArrayList<String> _news_id_set = new ArrayList<String>();
    public int _size;
    public int _total;

    public void load(String query, int pagecnt) {
        int pageSize = 5 << pagecnt;
        String str;
        if (pagecnt == 1)
            str = sendGet("http://166.111.68.66:2042/news/action/query/search?keyword=" + query + "&pageSize=20&pageNo=1");
        else
            str = sendGet("http://166.111.68.66:2042/news/action/query/search?keyword=" + query + "&pageSize=" + pageSize + "&pageNo=2");
        try {
            JSONArray artList = new JSONArray(new JSONObject(str).getString("list"));
            _size = artList.length();
            for (int i = 0; i < _size; i++) {
                JSONObject art = artList.getJSONObject(i);
                _news_id_set.add(art.getString("news_ID"));
                _news_title_list.add(art.getString("news_Title"));
            }
            _total = Integer.parseInt(new JSONObject(str).getString("totalRecords"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
