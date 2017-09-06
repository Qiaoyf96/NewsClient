package com.newsclient.data;

public class DSingleNews {
    public String lang_type, newsclasstag, news_author,
            news_id, news_pictures, news_source, news_time,
            news_title, news_url, news_video, news_intro, content;
    public boolean readed;
    DSingleNews(){
        content = "";
        readed = false;
    }
}
