package com.newsclient.data;

import java.util.ArrayList;

public class DSingleTag extends DNewsList {
    public ArrayList<DSingleNews> news_list = new ArrayList<DSingleNews>();
    public int size = 0;

    void set(ArrayList<String> id_list) {
        for (String id: id_list) {
            DSingleNews news = getById(id);
            news_list.add(news);
            size++;
        }
    }
}
