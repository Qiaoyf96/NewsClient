package com.newsclient.data;

import java.util.ArrayList;

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
            news_list.add(news);
            size++;
        }
    }

    public void enlarge() {
        DTagList.enlarge(id);
        set(DTagList.lstdetail.get(id));
    }
}
