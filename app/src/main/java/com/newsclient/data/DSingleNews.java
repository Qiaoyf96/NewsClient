package com.newsclient.data;

public class DSingleNews {
    public String lang_type, newsclasstag, news_author,
            news_id, news_pictures, news_source, news_time,
            news_title, news_url, news_video, news_intro, content;
    public boolean readed;
    public DSingleNews(){
        content = "";
        readed = false;
    }

    public String displayTitle(int index){
        return "[" + (index + 1) + "] " + news_title;
    }

    public String displaySource(){
        return "来源： " + news_source;
    }
}
