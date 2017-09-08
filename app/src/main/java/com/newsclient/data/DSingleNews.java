package com.newsclient.data;

import com.newsclient.tools.StringFormatTransfer;
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
//        int displayLength = 0;
//        char[] cstr = news_title.toCharArray();
//        for (int i = 0; i < news_title.length(); i++){
//            if (displayLength >= 42){
//                return news_title.substring(0, i) + "...";
//            }
//            char ch = news_title.charAt(i);
//            Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
//            if ((ch >= 0x4E00 && ch <= 0x9FCC) || (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
//                    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//                    || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
//                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS)){
//                displayLength += 2;
//            }
//            else{
//                displayLength += 1;
//            }
//        }
        return news_title;
    }

    public String displaySource(){
        return StringFormatTransfer.toDBC("来源： " + news_source);
    }

    public String displayTime(){
        // time's format:
        // 20160912000000
        return Integer.parseInt(news_time.substring(4, 6)) + "月" + Integer.parseInt(news_time.substring(6, 8)) + "日";
    }
}
