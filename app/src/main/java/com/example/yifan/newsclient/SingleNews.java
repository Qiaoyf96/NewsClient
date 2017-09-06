package com.example.yifan.newsclient;

/**
 * Created by xemboliu on 6/9/2017.
 */

public class SingleNews {
    public String lang_type, newsclasstag, news_author,
            news_id, news_pictures, news_source, news_time,
            news_title, news_url, news_video, news_intro, content;
    boolean readed;
    SingleNews(){
        content = "";
        readed = false;
    };
}
